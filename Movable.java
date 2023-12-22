package interfaces;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

// Interface implementada em GameElements capazes de se mover como (Empilhadora, caixote, palete), generaliza a movimentação
// e torna fácil a implementação em novos elementos.

public interface Movable {

	public void move(Point2D newPosition);

	public Point2D newPosition(Direction newDirection);

}