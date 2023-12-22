package interfaces;

import gameElements.GameElement;

// Interface que generaliza as interações entre elementos, permitindo através de uma função interact
// realizar qualquer tipo de interação com um elemento recebido como parâmetro

// O interact usualmente é utilizado em conjunto com o switch() case{} para tratar as diferentes interações
// que o elemento Interactable pode ter!!

public interface Interactable {

	public void interact(GameElement element);

}
