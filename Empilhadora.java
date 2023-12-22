package gameElements;

import interfaces.Interactable;
import interfaces.Movable;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends GameElement implements Movable, Interactable {

	private Direction direction;
	private int energy;
	private boolean hasHammer;
	private int moves;

	// Construtor da Empilhadora
	public Empilhadora(Point2D initialPos) {
		super(initialPos, 2, "Empilhadora_U");
		this.energy = 100;
		this.hasHammer = false;
		this.direction = Direction.UP;
		this.moves = 0;
	}

	// Método que retorna o nome da imagem da Empilhadora com base na direção
	@Override
	public String getName() {
		switch (direction) {
		case DOWN:
			return "Empilhadora_D";
		case UP:
			return "Empilhadora_U";
		case LEFT:
			return "Empilhadora_L";
		case RIGHT:
			return "Empilhadora_R";
		default:
			return null;
		}
	}

	// Métodos de acesso aos atributos

	public Direction getDirection() {
		return direction;
	}

	public int getEnergy() {
		return energy;
	}

	@Override
	public boolean isCollidable() {
		return false;
	}

	public boolean hasHammer() {
		return hasHammer;
	}

	// Método para pegar o martelo
	public void grabHammer() {
		hasHammer = true;
	}

	// Método para diminuir a energia
	public void decreaseEnergy() {
		energy--;
	}

	// Método para adicionar um movimento
	public void addMove() {
		moves++;
	}

	public int getMoves() {
		return moves;
	}

	// Método para consumir bateria e aumentar a energia
	public void consumeBatery() {
		energy = energy + 50;
		if (energy > 100)
			energy = 100;
	}

	@Override
	public boolean isRemovable() {
		return false;
	}

	@Override
	public boolean isMovable() {
		return true;
	}

	// Calcula nova posição através de uma direção dada.
	@Override
	public Point2D newPosition(Direction newDirection) {
		// Vira o objeto
		direction = newDirection;
		return getPosition().plus(newDirection.asVector());
	}

	// Move a empilhadora para a nova posição, diminui a energia e incrementa os movimentos
	@Override
	public void move(Point2D newPosition) {
		setPosition(newPosition);
		decreaseEnergy();
		moves++;
	}

	// Método para definir o número de movimentos
	public void setMoves(int moves) {
		this.moves = moves;
	}

	// Método de interação com outros elementos do jogo
	@Override
	public void interact(GameElement element) {
		String name = element.getName();

		switch (name) {
		case "Bateria":
			Bateria bateria = (Bateria) element;
			bateria.beRemovable();
			this.consumeBatery();
			break;
		case "Martelo":
			Martelo martelo = (Martelo) element;
			martelo.beRemovable();
			this.grabHammer();
			break;
		case "ParedeRachada":
			ParedeRachada pR = (ParedeRachada) element;
			if (this.hasHammer)
				pR.beRemovable();
			break;
		default:
			break;
		}
	}

	@Override
	public boolean isInteractable() {
		return true;
	}
}
