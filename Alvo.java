package gameElements;

import pt.iscte.poo.utils.Point2D;

public class Alvo extends GameElement {

	public Alvo(Point2D initialPos) {
		super(initialPos, 1, "Alvo");
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
