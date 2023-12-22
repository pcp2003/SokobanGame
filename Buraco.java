package gameElements;

import pt.iscte.poo.utils.Point2D;

public class Buraco extends GameElement {

	private boolean isRemovable;

	public Buraco(Point2D initialPos) {
		super(initialPos, 1, "Buraco");
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
		return false;
	}

}
