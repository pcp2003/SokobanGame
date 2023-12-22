package sokoban;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import gameElements.Alvo;
import gameElements.Bateria;
import gameElements.Buraco;
import gameElements.Caixote;
import gameElements.Chao;
import gameElements.Empilhadora;
import gameElements.GameElement;
import gameElements.Martelo;
import gameElements.Palete;
import gameElements.Parede;
import gameElements.ParedeRachada;
import gameElements.Teleporte;
import gameElements.Vazio;
import pt.iscte.poo.utils.Point2D;

public class Level {

	// Dimensoes da grelha de jogo
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;

	public final List<GameElement> elementList; // Lista de imagens
	public Empilhadora empilhadora;
	public int NumberOfCaixotes;
	public int NumberOfAlvos;

	// Construtor privado para criar uma instância de Level
	private Level(List<GameElement> elementList, Empilhadora empilhadora, int NumberOfCaixotes, int NumberOfAlvos) {
		this.elementList = elementList;
		this.empilhadora = empilhadora;
		this.NumberOfCaixotes = NumberOfCaixotes;
		this.NumberOfAlvos = NumberOfAlvos;
	}

	// Métodos de acesso aos atributos
	public List<GameElement> getElementList() {
		return elementList;
	}

	public Empilhadora getEmpilhadora() {
		return empilhadora;
	}

	public int getNumberOfCaixotes() {
		return NumberOfCaixotes;
	}

	public void decreaseNumberOfCaixotes() {
		NumberOfCaixotes--;
	}

	public int getNumberOfAlvos() {
		return NumberOfAlvos;
	}

	// Método estático para criar uma instância de Level a partir de um arquivo
	public static Level createLevel(int level) {
		try {
			// Caminho do arquivo do nível
			File file = new File(
					"C:\\Users\\pedro\\Desktop\\area de trabalho\\ImportProj\\SokobanStarterDemo\\levels\\level" + level
							+ ".txt");
			Scanner s = new Scanner(file);

			int NumberOfAlvos = 0;
			int NumberOfCaixotes = 0;
			List<GameElement> elementList = new ArrayList<>();
			Empilhadora empilhadora = new Empilhadora(new Point2D(0, 0));
			Level lvl = new Level(elementList, empilhadora, NumberOfCaixotes, NumberOfAlvos);

			int y = 0;

			// Itera sobre cada linha do arquivo
			while (y != GRID_HEIGHT) {
				String line = s.nextLine();
				char[] characters = line.toCharArray();

				// Itera sobre cada posição da linha
				for (int x = 0; x != GRID_WIDTH; x++) {
					char character = characters[x];

					// Switch para decidir o tipo de elemento com base no caractere
					switch (character) {
					case '#':
						lvl.elementList.add(new Parede(new Point2D(x, y)));
						break;
					case 'E':
						lvl.elementList.add(new Chao(new Point2D(x, y)));
						empilhadora.setPosition(new Point2D(x, y));
						lvl.elementList.add(empilhadora);
						break;
					case 'C':
						lvl.elementList.add(new Chao(new Point2D(x, y)));
						lvl.elementList.add(new Caixote(new Point2D(x, y)));
						NumberOfCaixotes++;
						break;
					case 'X':
						lvl.elementList.add(new Alvo(new Point2D(x, y)));
						NumberOfAlvos++;
						break;
					case 'B':
						lvl.elementList.add(new Chao(new Point2D(x, y)));
						lvl.elementList.add(new Bateria(new Point2D(x, y)));
						break;
					case ' ':
						lvl.elementList.add(new Chao(new Point2D(x, y)));
						break;
					case '=':
						lvl.elementList.add(new Vazio(new Point2D(x, y)));
						break;
					case 'O':
						lvl.elementList.add(new Chao(new Point2D(x, y)));
						lvl.elementList.add(new Buraco(new Point2D(x, y)));
						break;
					case 'P':
						lvl.elementList.add(new Chao(new Point2D(x, y)));
						lvl.elementList.add(new Palete(new Point2D(x, y)));
						break;
					case 'M':
						lvl.elementList.add(new Chao(new Point2D(x, y)));
						lvl.elementList.add(new Martelo(new Point2D(x, y)));
						break;
					case '%':
						lvl.elementList.add(new Chao(new Point2D(x, y)));
						lvl.elementList.add(new ParedeRachada(new Point2D(x, y)));
						break;
					case 'T':
						lvl.elementList.add(new Teleporte(new Point2D(x, y)));
						break;
					}
				}
				y++;
			}

			s.close();
			return lvl;

		} catch (FileNotFoundException e) {
			// Tratamento de exceção se o arquivo não for encontrado
			System.err.println("Level " + level + " não encontrado!!");
			return null;
		}
	}
}
