package tp.pr3.logic.multigames;

import java.util.Random;

import tp.pr3.*;
import tp.pr3.util.ArrayAsList;

/**Está clase incorpora las reglas que sigue el juego para cada una de las modalidades, en concreto en GamesRules se describen las definiciones
 *  comunes para todas las modalidades, y se deja la descripcion de los metodos que necesitan informacion concreta sobre 
 * cada modalidad a las clases Rules2048, RulesFib y Rules*/
public interface GameRules {
	/**incorpora una célula con valor aleatorio en la posición pos del tablero board*/
	public void addNewCellAt(Board board, Position pos, Random rand);//incorpora una célula con valor aleatorio en la posición pos del tablero board,
	/**fusiona dos células y devuelve el número de puntos obtenido por la fusión*/
	public int merge(Cell self, Cell other); //fusiona dos células y devuelve el número de puntos obtenido por la fusión,
	/**devuelve el mejor valor del tablero, según las reglas de ese juego, comprobándose si es un valor ganador y se ha ganado el juego*/
	public int getWinValue(Board board); //devuelve el mejor valor del tablero, según las reglas de ese juego, comprobándose si es un valor ganador y se ha ganado el juego,
	/**devuelve si el juego se ha ganado o no*/
	public boolean win(Board board); //devuelve si el juego se ha ganado o no,
	/***/
	public boolean isWinValue(int value);
	
	/**Comprueba si dos celdas se pueden fusionar*/
	default boolean canMergeNeighbours(Cell self, Cell other) { //Comprueba si dos celdas se pueden fusionar
		return self.getValue() == other.getValue();
	}
	
	/**Usando el método rand, genera un valor con probabilidad prob o uno con probabilidad 100-prob*/
	default int newValue(int prob, int leastLikely, int mostLikely, Random rand) {
		return (rand.nextInt(100) < prob ? leastLikely : mostLikely);
	}
	
	/**Devuelve si el juego se ha perdido o no*/
	default boolean lose(Board board){ //devuelve si el juego se ha perdido o no. Para mejorar la eficiencia, comprobamos a la vez si el tablero est� lleno y si se puede hacer alguna fusi�n.
		boolean result = true;
		int i = 0;
		while(i < board.getSize() && result) {
			int j = 0;
			while(j < board.getSize() && result) {
				if(board.getCell(i,j).getValue() == 0) result = false;  //Comprueba si está vacia
				else {
					if(i < board.getSize() - 1) //Compara con el de abajo
						if(canMergeNeighbours(board.getCell(i,j),board.getCell(i + 1,j))) result = false; 
					if(j < board.getSize() - 1)//Compara con el de la derecha
						if(canMergeNeighbours(board.getCell(i,j),board.getCell(i,j + 1)))result = false; 
				}
				j++;
			}
			i++;
		}
		return result;
	}
	
	/**crea y devuelve un tablero size × size*/
	default Board createBoard(int size) {//crea y devuelve un tablero size × size, 
		return new Board(size);
	}
	
	/**elige una posición libre de board e invoca el método addNewCellAt() para añadir una 
	* célula en esa posición, devuelve false si todas las casillas quedan ocupadas*/
	default boolean addNewCell(Board board, Random rand) { //elige una posición libre de board e invoca el método addNewCellAt() para añadir una célula en esa posición, devuelve false si todas las casillas quedan ocupadas
		ArrayAsList free = board.freeCells();
		boolean anyCellFree = true;
		if(free.getTam() > 1) addNewCellAt(board,free.get(rand.nextInt(free.getTam() - 1)), rand);
		else { addNewCellAt(board,free.get(0),rand); anyCellFree = false;}
		return anyCellFree;
	}
	
	/**inicializa board eligiendo numCells posiciones libres, e invoca el método addNewCellAt() para añadir nuevas células en esas posiciones.*/
	default void initBoard(Board board, int numCells, Random rand) { //inicializa board eligiendo numCells posiciones libres, e invoca el método addNewCellAt() para añadir nuevas células en esas posiciones.
		ArrayAsList free = board.freeCells();
		for(int i = 0; i < numCells; i++) {
			if(free.getTam() > 1) {
				int pos = rand.nextInt(free.getTam() - 1);
				addNewCellAt(board,free.get(pos), rand);
				free.pop(pos);
			}
			else addNewCellAt(board,free.get(0),rand);
		}
	}
}
