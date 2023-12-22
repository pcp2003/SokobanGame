package gameElements;

import pt.iscte.poo.utils.Point2D;

public class Vazio extends GameElement {

	public Vazio(Point2D initialPos) {
		super(initialPos, 0, "Vazio");
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
		return false;
	}

}
