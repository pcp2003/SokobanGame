package gameElements;

import pt.iscte.poo.utils.Point2D;

public class Martelo extends GameElement {

	private boolean isRemovable;

	public Martelo(Point2D initialPos) {
		super(initialPos, 2, "Martelo");
		this.isRemovable = false;
	}

	@Override
	public boolean isRemovable() {
		return isRemovable;
	}

	public void beRemovable() {
		isRemovable = true;
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
		return true;
	}

}
