package tp.pr2.logic.multigames;

import java.util.Random;
import tp.pr2.util.*;
import tp.pr2.*;

public class Game {
	private Board _board;
	private int _size;
	private int _initCells;
	private Random _myRandom;
	private int _score = 0;
	private boolean _exit = false;
	private boolean _posMove = true;
	private GameRules _currentRules;
	private GameStateStack _undoStack = new GameStateStack();
	private GameStateStack _redoStack = new GameStateStack();
	
	/**Devuelve la modalidad de juego a la que se está juando*/
	public GameRules getCurrentRules() {return _currentRules;}
	/**Devuelve el valor booleano del atributo exit*/
	public boolean getExit() {return _exit;}
	
	/**Cambia el valor de _exit a true*/
	public void exit() {_exit = true;}
	
	/**Construye el juego teniendo en cuenta la modalidad, el tamaño del tablero y el número inicial de celdas con valor distinto que 0*/
	public Game(int dim, int cells, long seed, GameRules currentRules){
		_myRandom = new Random(seed);
		_currentRules = currentRules;
		_size = dim;
		_initCells = cells;
		reset();
	}
	
	/**Hace el movimiento y devuelve false si no es posible hacer mas movimientos*/
	 public boolean move(Direction dir, GameRules currentRules){ //Hace el movimiento y devuelve false si no es posible hacer mas movimientos
		 if(_posMove) {
			 _undoStack.push(getState());		//Guardamos el estado antes de hacer el movimiento
			 MoveResults results = _board.executeMove(dir, currentRules); //Hacemos movimiento
			 _score += results.getPoints();	
			 if(results.getMoved()) {
				 _redoStack.reset(); 					//Si se ha producido el movimiento, vaciamos el vector de redo
				boolean anyCellFree = currentRules.addNewCell(_board, _myRandom);//Añadimos nueva celda
				if(results.getWon()) {									//Comprobamos si hemos ganado
					System.out.println("¡Enhorabuena, has ganado!");
					exit();
				}
				else if(!anyCellFree) {						//Si no hemos ganado, comprobamos si hemos perdido
					_posMove = !_currentRules.lose(_board);	
					System.out.println(this);			//Pintamos el juego por última vez
				}
			 }
			 else _undoStack.pop();				//Si no se puede hacer el movimiento, borramos el estado
		 }
		 if(!_posMove)
			 System.out.println("Has perdido, puedes intentar rectificar(undo), reiniciar(reset), cambiar de juego(play) o simplemente salir(exit)");
		 return _posMove;
	 }
	 
	 /**Dibuja el tablero y además proporciona al jugador distintos datos como la puntuación o la casilla con mejor valor del tablero*/
	 public String toString() {
		 String game = _board.toString();
		 game += '\n';
		 game += "Mejor valor: ";
		 game += _currentRules.getWinValue(_board);
		 game += MyStringUtils.repeat(" ",10);
		 game += "score: ";
		 game += _score;
		 game += '\n';
		 return game;
	 }
	 
	 /**Resetea el juego volviendo a crear un tablero con la modalidad de juego correcta*/
	 public void reset() {
		 _board = _currentRules.createBoard(_size);
		_currentRules.initBoard(_board, _initCells, _myRandom);
		_score = 0;
		if (_initCells == _size * _size) { //Si no quedan celdas vacias comprobamos si se puede hacer mas movimientos
			_posMove = !_currentRules.lose(_board);
		}
		else _posMove = true;
		_undoStack.reset();
		_redoStack.reset();
	 }
	 
	 /**Devuelve el estado del tablero*/
	 public GameState getState() {
		 GameState state = new GameState(_score,_board.getState());
		 return state;
	 }
	 /**pPermite cambiar el estado del tablero al estado aState*/
	 public void setState(GameState aState) {
		 _board.setState(aState.getBoardState());
		 _score = aState.getScore();
	 }
	 
	 /**Comprueba si se pueden deshacer más movimientos y de ser así deshace uno y actualiza la pila de estados*/
	 public boolean Undo() {
		 boolean success = false;
		if(_undoStack.isEmpty()) System.out.println("No se pueden deshacer mas movimientos." + '\n');
		else {
			_redoStack.push(getState());
			setState(_undoStack.pop());
			success = true;
			_posMove = true;				//Al deshacer un movimiento, permites que se realizen mas
		}
		return success;
	}
	 
	 /**Comprueba si se pueden rehacer más movimientos y de ser así rehace uno y actualiza la pila de estados*/
	 public boolean Redo() {
		boolean success = false;
		if(_redoStack.isEmpty()) System.out.println("No se pueden rehacer mas movimientos." + '\n');
		else {
			_undoStack.push(getState());
			setState(_redoStack.pop());
			success = true;
			_posMove = !_currentRules.lose(_board);			//Comprobamos si se pueden hacer movimientos
		}
		return success;
		}
}
