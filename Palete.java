package gameElements;

import interfaces.Interactable;
import interfaces.Movable;
import interfaces.Teleportable;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import sokoban.GameEngine;

public class Palete extends GameElement implements Movable, Interactable, Teleportable {

	private boolean isRemovable;
	private boolean hasTeleported;

	public Palete(Point2D initialPos) {
		super(initialPos, 2, "Palete");
		this.isRemovable = false;
		this.hasTeleported = false;
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
		return true;
	}

	@Override
	public boolean isInteractable() {
		return false;
	}

	@Override
	public boolean isCollidable() {
		return true;
	}

	@Override
	public Point2D newPosition(Direction newDirection) {

		return getPosition().plus(newDirection.asVector());
	}

	@Override
	public void move(Point2D newPosition) {

		setPosition(newPosition);

	}

	// Interação do Caixote com o buraco, alvo e teleporte

	@Override
	public void interact(GameElement element) {

		switch (element.getName()) {

		case "Buraco":
			Buraco buraco = (Buraco) element;
			buraco.beRemovable();
			beRemovable();
			break;

		case "Teleporte":
			GameEngine instance = GameEngine.getInstance();
			Teleporte OtherTeleport = instance.getTeleportDestination(this.getPosition());

			if (!hasTeleported() && !OtherTeleport.hasElement()) {
				setTeleported(true);
				OtherTeleport.setHasElement(true);
				move(OtherTeleport.getPosition());
			}
		}

	}

	@Override
	public void setTeleported(boolean teleported) {
		this.hasTeleported = teleported;
	}

	@Override
	public boolean hasTeleported() {
		return hasTeleported;
	}

}
