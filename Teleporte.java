package gameElements;

import pt.iscte.poo.utils.Point2D;
import sokoban.GameEngine;

public class Teleporte extends GameElement {

	private boolean hasElement;

	public Teleporte(Point2D initialPos) {
		super(initialPos, 1, "Teleporte");
		this.hasElement = false;
	}

	public boolean hasElement() {
		GameEngine instance = GameEngine.getInstance();
		GameElement element = instance.getCorrectElementInPosition(this.getPosition());
		if ("Teleporte".equals(element.getName()))
			setHasElement(false);
		else {
			setHasElement(true);
		}
		return hasElement;
	}

	public void setHasElement(boolean hasElement) {
		this.hasElement = hasElement;
	}

	@Override
	public boolean isRemovable() {
		return false;
	}

	@Override
	public boolean isMovable() {
		return false;
	}

	@Override
	public boolean isInteractable() {
		return true;
	}

	@Override
	public boolean isCollidable() {
		return false;
	}

}
