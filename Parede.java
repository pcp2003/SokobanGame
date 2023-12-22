package gameElements;

import pt.iscte.poo.utils.Point2D;

public class Parede extends GameElement {

	public Parede(Point2D initialPos) {
		super(initialPos, 2, "Parede");
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
		return false;
	}

	@Override
	public boolean isCollidable() {
		return true;
	}

}
