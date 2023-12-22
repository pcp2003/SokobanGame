package interfaces;

// Interface responsável pela realização de teletransportes. A função hasTeleported() retorna true
// caso o elemento já tenha se teleportado, impedindo que ele realiza um "vai e vem". O setTeleported
// basicamente altera a posição do gameElement.

public interface Teleportable {

	public void setTeleported(boolean teleported);

	public boolean hasTeleported();
}
