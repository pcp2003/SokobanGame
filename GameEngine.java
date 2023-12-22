package sokoban;

import java.util.ArrayList;
import java.util.List;

import estatisticas.Estatistica;
import estatisticas.Registro;
import gameElements.Empilhadora;
import gameElements.GameElement;
import gameElements.Teleporte;
import interfaces.Interactable;
import interfaces.Movable;
import interfaces.Teleportable;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class GameEngine implements Observer {

    // Tamanho do grid
    public static final int GRID_HEIGHT = 10;
    public static final int GRID_WIDTH = 10;

    private List<GameElement> elementsList;
    private static GameEngine instance;
    private ImageMatrixGUI gui;
    private Level level;
    private Empilhadora empilhadora;
    private int lvl;
    private Estatistica estatistica;
    private String player;

    private GameEngine() {
    }

    // Método estático para obter a instância única do GameEngine
    public static GameEngine getInstance() {
        if (instance == null)
            return instance = new GameEngine();
        return instance;
    }

    // Inicializa o jogo
    public void start() {
        gui = ImageMatrixGUI.getInstance();
        configureGUI();
        initializeGame();
        sendImagesToGUI();
    }

    // Envia imagens para a GUI
    private void sendImagesToGUI() {
        List<ImageTile> tileList = new ArrayList<>();
        for (GameElement element : level.getElementList()) {
            tileList.add(element);
        }
        gui.addImages(tileList);
    }

    // Configura a GUI
    private void configureGUI() {
        gui.setSize(GRID_HEIGHT, GRID_WIDTH);
        gui.registerObserver(this);
        gui.go();
    }

    // Inicializa o jogo
    private void initializeGame() {
        lvl = 7;
        level = Level.createLevel(lvl);
        empilhadora = level.getEmpilhadora();
        elementsList = level.getElementList();
        player = gui.askUser("Enter your name:");
        estatistica = new Estatistica();
        estatistica.carregarEstatisticasDoArquivo("scoreBoard.txt");
    }

    // Trata a vitória no jogo
    public void win() {
        gui.clearImages();
        gui.setMessage("Passed to the next level, press any arrow key to continue.");
        gui.go();
        estatistica.adicionarPontuacao(new Registro(player, empilhadora.getMoves()), lvl);
        estatistica.salvarEstatisticasEmArquivo("scoreBoard.txt");
        level = Level.createLevel(++lvl);
        empilhadora = level.getEmpilhadora();
        elementsList = level.getElementList();
        sendImagesToGUI();
    }

    // Trata a derrota no jogo
    public void loose() {
        gui.clearImages();
        gui.setMessage("Lost");
        gui.go();
        level = Level.createLevel(lvl);
        empilhadora = level.getEmpilhadora();
        elementsList = level.getElementList();
        sendImagesToGUI();
    }

    // Método chamado quando há uma atualização
    @Override
    public void update(Observed source) {
        handleKeyPress();
        updateGame();
        updateGUI();
    }

    // Atualiza o estado do jogo
    private void updateGame() {
        if (hasLost())
            loose();
        if (hasWon())
            win();
    }

    // Atualiza a interface gráfica do usuário
    private void updateGUI() {
        gui.setStatusMessage("Sokoban Starter - Energy: " + empilhadora.getEnergy() + " - Level: " + lvl
                + " - Hammer: " + empilhadora.hasHammer());
        gui.update();
    }

    // Verifica se o jogo foi perdido
    public boolean hasLost() {
        if (empilhadora.getEnergy() <= 0)
            return true;
        if (level.getNumberOfCaixotes() < level.getNumberOfAlvos())
            return true;
        for (GameElement element : elementsList) {
            if ("Buraco".equals(element.getName()) && element.getPosition().equals(empilhadora.getPosition()))
                return true;
        }
        return false;
    }

    // Verifica se o jogo foi ganho
    public boolean hasWon() {
        List<Point2D> alvoPositions = new ArrayList<>();
        for (GameElement element : level.getElementList()) {
            if ("Alvo".equals(element.getName()))
                alvoPositions.add((element.getPosition()));
        }

        for (Point2D alvoPosition : alvoPositions) {
            boolean hasCaixoteAbove = false;
            for (GameElement element : level.getElementList()) {
                if ("Caixote".equals(element.getName()) && element.getPosition().equals(alvoPosition)) {
                    hasCaixoteAbove = true;
                    break;
                }
            }
            if (!hasCaixoteAbove)
                return false;
        }
        return true;
    }

    // Remove elementos removíveis do jogo
    private void removeElements() {
        List<GameElement> elementsToRemove = new ArrayList<>();

        for (GameElement element : elementsList) {
            if (element.isRemovable()) {
                elementsToRemove.add(element);
            }
        }

        for (GameElement element : elementsToRemove) {
            if ("Caixote".equals(element.getName())) {
                level.decreaseNumberOfCaixotes();
            }
            elementsList.remove(element);
            gui.removeImage(element);
        }
    }

    // Trata o pressionamento de teclas
    private void handleKeyPress() {
        int directionKey = gui.keyPressed();
        Direction newDirection = Direction.directionFor(directionKey);
        Point2D empilhadoraNewPosition = empilhadora.newPosition(newDirection);
        GameElement element = getCorrectElementInPosition(empilhadoraNewPosition);

        if (isValidPosition(empilhadoraNewPosition, "Empilhadora")) {
            empilhadora.move(empilhadoraNewPosition);
            handleInteractions(empilhadora);
        } else {
            handleCollisionEmpilhadora(element);
        }

        removeElements();
    }

    // Trata colisões com a empilhadora
    public void handleCollisionEmpilhadora(GameElement element) {
        if (element.isMovable()) {
            Movable movable = (Movable) element;
            Point2D movableNewPostion = movable.newPosition(empilhadora.getDirection());

            if (isValidPosition(movableNewPostion, element.getName())) {
                movable.move(movableNewPostion);
                empilhadora.move(empilhadora.newPosition(empilhadora.getDirection()));
                empilhadora.decreaseEnergy();
                empilhadora.addMove();
                handleInteractions(element);
                if (element instanceof Teleportable)
                    ((Teleportable) element).setTeleported(false);
            }
        }
    }

    // Trata interações com elementos do jogo
    public void handleInteractions(GameElement element) {
        Interactable interactable = (Interactable) element;
        for (GameElement E : elementsList) {
            if (E.getPosition().equals(element.getPosition()) && !E.equals(element))
                interactable.interact(E);
        }
    }
    
    // Obtém o destino de um teleporte
    public Teleporte getTeleportDestination(Point2D thisPostiion) {
        for (GameElement element : elementsList) {
            if ("Teleporte".equals(element.getName()) && !element.getPosition().equals(thisPostiion))
                return (Teleporte) element;
        }
        return null;
    }

    // Verifica se a posição é válida para um determinado elemento
    public boolean isValidPosition(Point2D newPosition, String elementName) {
        switch (elementName) {
            case "Empilhadora":
                for (GameElement E : elementsList) {
                    if (E.getPosition().equals(newPosition)) {
                        if (!(E.isInteractable() || "Chao".equals(E.getName()) || "Alvo".equals(E.getName())
                                || "ParedeRachada".equals(E.getName()) && empilhadora.hasHammer())) {
                            return false;
                        }
                    }
                }
                return true;
            default:
                for (GameElement E : elementsList) {
                    if (E.getPosition().equals(newPosition)) {
                        String EName = E.getName();
                        if (!(EName.equals("Buraco") || EName.equals("Teleporte") || EName.equals("Chao")
                                || EName.equals("Alvo"))) {
                            return false;
                        }
                    }
                }
                return true;
        }
    }

    // Obtém o elemento correto em uma determinada posição
    public GameElement getCorrectElementInPosition(Point2D position) {
        List<GameElement> elementsAtPosition = new ArrayList<>();

        for (GameElement element : elementsList) {
            if (element.getPosition().equals(position)) {
                elementsAtPosition.add(element);
            }
        }

        if (elementsAtPosition.size() > 1) {
            for (GameElement e : elementsAtPosition) {
                if (!"Chao".equals(e.getName()) && !"Alvo".equals(e.getName()) && !"Teleporte".equals(e.getName()))
                    return e;
            }
        }

        return elementsAtPosition.get(0);
    }
}