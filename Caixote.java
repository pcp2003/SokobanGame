package gameElements;

import interfaces.Interactable;
import interfaces.Movable;
import interfaces.Teleportable;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import sokoban.GameEngine;

public class Caixote extends GameElement implements Movable, Interactable, Teleportable {

	private boolean isRemovable;
	private boolean hasTeleported;

	public Caixote(Point2D initialPos) {
		super(initialPos, 2, "Caixote");
		this.isRemovable = false;
		this.hasTeleported = false;
	}

	public void beRemovable() {
		isRemovable = true;
	}

	public Point2D newPosition(Direction newDirection) {

		return getPosition().plus(newDirection.asVector());
	}

	@Override
	public void move(Point2D newPosition) {

		setPosition(newPosition);

	}

	@Override
	public boolean isRemovable() {
		return isRemovable;
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

	// Interação do Caixote com o buraco, alvo e teleporte

	@Override
	public void interact(GameElement element) {

		switch (element.getName()) {
		case "Buraco":
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
			break;
		default:
			break;
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
