package tp.pr3;

import tp.pr3.logic.multigames.*;
/**
 * La clase publica Cell implementa los metodos relacionados con el uso y gestion de 
 *las celdas del tablero, las celdas llevan asociado un valor
 */
public class Cell {
	private int _value;
	
	/**Construye una celda con valor cuyo valor es 0*/
	public Cell() {_value = 0;}
	
	/**Devuelve el valor de la celda*/
	public int getValue() {return _value;}
	/**Comprueba si la celda esta vacia*/
	public boolean isEmpty() {return _value == 0;}
	/**Permite cambiar el valor de la celda*/
	public void setValue(int nuevo){_value = nuevo;}
	
	/**Realiza el movimiento teniendo en cuenta la modalidad de juego y devuelve el valor de la celda despues de la fusion*/
	public int doMerge(Cell neighbour, GameRules currentRules){
		return currentRules.merge(this, neighbour);
	}
	
}
