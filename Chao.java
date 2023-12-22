package gameElements;

import pt.iscte.poo.utils.Point2D;

public class Chao extends GameElement {

	public Chao(Point2D Point2D) {
		super(Point2D, 0, "Chao");
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
