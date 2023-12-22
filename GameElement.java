package gameElements;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class GameElement implements ImageTile {

	private Point2D Point2D;
	private int layer;
	private String imageName;

	// Construtor da classe GameElement
	public GameElement(Point2D point2d, int layer, String imageName) {
		this.Point2D = point2d;
		this.layer = layer;
		this.imageName = imageName;
	}

	// Método para obter a posição do elemento
	@Override
	public Point2D getPosition() {
		return Point2D;
	}

	// Método para atualizar a posição do elemento
	public void setPosition(Point2D newPosition) {
		this.Point2D = newPosition;
	}

	// Método para obter a camada do elemento
	@Override
	public int getLayer() {
		return layer;
	}

	// Método para obter o nome da imagem associada ao elemento
	@Override
	public String getName() {
		return imageName;
	}

	// Métodos abstratos que serão implementados nas subclasses
	public abstract boolean isRemovable(); // Retorna true se o elemento pode ser removido

	public abstract boolean isCollidable(); // Retorna true se o elemento é sólido (não pode ser atravessado)

	public abstract boolean isMovable(); // Retorna true se o elemento pode ser movido pelo jogador

	public abstract boolean isInteractable(); // Retorna true se for possível interagir com outros GameElements

	// Método toString para representação textual do objeto
	@Override
	public String toString() {
		return "GameElement [Point2D=" + Point2D + ", layer=" + layer + ", imageName=" + imageName
				+ ", isRemovable()=" + isRemovable() + ", isCollidable()=" + isCollidable() + ", isMovable()="
				+ isMovable() + ", isInteractable()=" + isInteractable() + "]";
	}

}
