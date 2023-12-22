package gameElements;

import pt.iscte.poo.utils.Point2D;

public class ParedeRachada extends GameElement {

	private boolean isRemovable;

	public ParedeRachada(Point2D initialPos) {
		super(initialPos, 2, "ParedeRachada");
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
		return isRemovable;
	}

	@Override
	public boolean isCollidable() {
		return true;
	}

}
