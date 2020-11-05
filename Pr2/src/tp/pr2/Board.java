package tp.pr2;

import tp.pr2.util.*;
import tp.pr2.logic.multigames.*;
/**
 * La clase Board implementa metodos para gestionar la cuadricula de juego, hacer los movimientos, guardar el estado del tablero,... asi como una constructora
 * para generar dicho tablero.
 * */
public class Board {
	private Cell [ ][ ] _board;
	private int _boardSize;
	
	/**devuelve el tamanio del tablero*/
	public int getSize() {return _boardSize;} 
	/**devuelve el valor de la celda [i][j]*/
	public Cell getCell(int i, int j) {return _board[i][j];} 
	
	/**construye un tablero del tamanio size*/
	public Board(int size){ 
		_boardSize = size;
		_board = new Cell[size][size];
		for(int i = 0; i < _boardSize; i++)
			for(int j = 0; j < _boardSize; j++)
				_board[i][j] = new Cell();
	} 
	
	/**Introduce el entero value en la celda del tablero en la posicion pos*/
	public void setCell(Position pos, int value){
		_board[pos.getRow()][pos.getColumn()].setValue(value);
	}
	
	/**traspone la matriz que forman las celdas del tablero*/
	public void transpose(){
		Cell aux;
		for(int i = 0; i < _boardSize; i++){
			for(int j = i + 1; j < _boardSize; j++){	
				aux = _board[i][j];
				_board[i][j] = _board[j][i];
				_board[j][i] = aux;
			}
		}
	}
	
	/**refleja la matriz que forman las celdas del tablero*/
	public void reflect(){
		Cell aux;
		for(int i = 0; i < _boardSize; i++){
			for(int j = 0; j < _boardSize/2; j++){	
				aux = _board[i][j];
				_board[i][j] = _board[i][_boardSize - j - 1];
				_board[i][_boardSize - j - 1] = aux;
			}
		}
	}
	
	/**realiza el movimiento hacia la derecha y devuelve los resultados del movimiento*/
	public MoveResults moveRight(GameRules currentRules){
		MoveResults results = new MoveResults();
		for(int i = 0; i < _boardSize; i++){
			int checked = _boardSize - 2;		//Usamos esta variable para buscar las casillas no vacias
			for(int j = _boardSize - 1; j > 0; j--) {
				boolean found = false;
				if(_board[i][j].getValue() == 0) {	//Si la casilla actual esta vacia
					while(!found && checked >= 0) {	//Hacemos la busqueda
						if(_board[i][checked].getValue() != 0) {	//Al encontrar una no vacia la movemos para que ocupe la casilla actual(que esta vacia)
							results.setMoved(true);
							found = true;
							_board[i][j].setValue(_board[i][checked].getValue());
							_board[i][checked].setValue(0);
						}
						checked --;
					}
				}
				found = false;
				while(!found && checked >= 0) {		//Ahora que la actual es no vacia, buscamos la siguiente no vacia para posible fusion
					if(_board[i][checked].getValue() != 0) {
						found = true;
						int points = _board[i][j].doMerge(_board[i][checked], currentRules);
						if(points == 0) {	//No es posible la fusion
							if(checked != j - 1) {	//La casilla no vacia no es vecina de la actual
								results.setMoved(true);
								_board[i][j - 1].setValue(_board[i][checked].getValue());
								_board[i][checked].setValue(0);
							}
						}
						else { //La fusión es posible
							results.setMoved(true);
							results.setPoints(results.getPoints() + points); //Sumamos los puntos correspondientes
							if(!results.getWon())							//Si aun no hemos ganado, comprobamos si esta fusion nos da la victoria
								results.setWon(currentRules.isWinValue(_board[i][j].getValue()));			
						}
					}
					checked --;
				}
			}
		}
		return results;
	}
	
	/**Devuelve un array con las casillas vacias del tablero*/
	public ArrayAsList freeCells() { 
		ArrayAsList list = new ArrayAsList();
		for(int i = 0; i < _boardSize; i++)
			for(int j = 0; j < _boardSize; j++)
				if(_board[i][j].getValue() == 0){
					Position pos = new Position();
					pos.setPosition(i, j);
					list.add(pos);
				}
		return list;
	}
	
	/**Realiza el movimiento en cualquier direccion(utilizando la funcion
	 *               moveRight, y los metodos transpose y reflect) y devuelve los resultados del movimiento*/
	public MoveResults executeMove(Direction dir, GameRules currentRules) {
		MoveResults results = new MoveResults();
		switch(dir) {
		case RIGHT:	results = moveRight(currentRules);
					break;
		case LEFT:	reflect();
					results = moveRight(currentRules);
					reflect();
					break;
		case UP:	transpose();
					//Mover izquierda
					reflect();
					results = moveRight(currentRules);
					reflect();
					//Dejar normal otra vez
					transpose();
					break;
		case DOWN:	transpose();
					results = moveRight(currentRules);
					transpose();
		}
		return results;
	}
	
	/**Dibuja el tablero en consola partiendo de un String game = "" al que se le va sumando los distintos caracteres
	 *               que forman el tablero*/
	public String toString() {
		int cellSize = 7;
		String space = " ";
		String vDelimiter = "|";
		String hDelimiter = "-";
		String game = "";
		for(int i = 0; i < _boardSize; i++) {
			game += space;
			for(int j = 0; j < _boardSize; j++) {	//Separador ---
				game += MyStringUtils.repeat(hDelimiter,cellSize);
				if(j != _boardSize - 1) game += hDelimiter;
				else game += space;
			}
			game += '\n';
			game += vDelimiter;
			for(int j = 0; j < _boardSize; j++) {	//Casillas de la fila i
				if(_board[i][j].getValue() == 0) game += MyStringUtils.repeat(space,cellSize);
				else game += MyStringUtils.centre(Integer.toString(_board[i][j].getValue()),cellSize);
				game += vDelimiter;
			}
			game += '\n';
		}
		game += space;
		for(int j = 0; j < _boardSize; j++) { //Ultimo separador ---
			game += MyStringUtils.repeat(hDelimiter,cellSize);
			if(j != _boardSize - 1) game += hDelimiter;
			else game += space;
		}
		return game;
	}

	/**Devuelve el estado del tablero, es decir, una matriz con el valor de cada celda en su correspondiente posicion*/
	public int[][] getState(){
		int [][] state = new int [_boardSize][_boardSize];
		for(int i = 0; i < _boardSize; i++)
			for(int j = 0; j < _boardSize; j++) 
				state[i][j] = _board[i][j].getValue();
		return state;
	}
	
	/**Introduce en el tablero las posiciones y valores de las celdas del estado aState*/
	public void setState(int[][] aState) {
		for(int i = 0; i < _boardSize; i++)
			for(int j = 0; j < _boardSize; j++) 
				_board[i][j].setValue(aState[i][j]);
	}
	
	/**Devuelve la casilla con mayor valor del tablero*/
	public int getHighest() {
		int highest = _board[0][0].getValue();
		for(int i = 0; i < _boardSize; i++)
			for(int j = 0; j < _boardSize; j++) 
				if(_board[i][j].getValue() > highest)
					highest = _board[i][j].getValue();
		return highest;
	}
	
	/**Devuelve la casilla distinta de 0 con menor valor del tablero*/
	public int getLowest() {
		int lowest = 2048;
		for(int i = 0; i < _boardSize; i++)
			for(int j = 0; j < _boardSize; j++) 
				if(_board[i][j].getValue() < lowest && _board[i][j].getValue() != 0)
					lowest = _board[i][j].getValue();
		return lowest;
	}
	
}
