package tp.pr3.logic.multigames;

import java.io.*;
import java.io.IOException;
import java.util.EmptyStackException;
import java.util.Random;

import tp.pr3.exceptions.*;
import tp.pr3.*;
import tp.pr3.util.*;

import java.util.EmptyStackException;

public class Game {
	private Board _board;
	private int _size;
	private int _initCells;
	private Random _myRandom;
	private int _score = 0;
	private boolean _exit = false;
	private boolean _posMove = true;
	private GameType _currentType;
	private GameStateStack _undoStack = new GameStateStack();
	private GameStateStack _redoStack = new GameStateStack();
	private final String fileMsg = "Este fichero guarda un juego 2048.";
	
	/**Devuelve la modalidad de juego a la que se está juando*/
	public GameRules getCurrentRules() {return _currentType.getRules();}
	/**Devuelve el valor booleano del atributo exit*/
	public boolean getExit() {return _exit;}
	
	public void setGame(int dim, int cells, long seed, GameType currentType) {
		_myRandom = new Random(seed);
		_currentType = currentType;
		_size = dim;
		_initCells = cells;
		reset();
	}
	
	/**Cambia el valor de _exit a true*/
	public void exit() {_exit = true;}
	
	/**Construye el juego teniendo en cuenta la modalidad, el tamaño del tablero y el número inicial de celdas con valor distinto que 0*/
	public Game(int dim, int cells, long seed, GameType currentType){
		_myRandom = new Random(seed);
		_currentType = currentType;
		_size = dim;
		_initCells = cells;
		reset();
	}
	
	/**Hace el movimiento y devuelve false si no es posible hacer mas movimientos*/
	 public boolean move(Direction dir, GameRules currentRules) throws GameOverException{ //Hace el movimiento y devuelve false si no es posible hacer mas movimientos
		 if(_posMove) {
			 _undoStack.push(getState());		//Guardamos el estado antes de hacer el movimiento
			 MoveResults results = _board.executeMove(dir, currentRules); //Hacemos movimiento
			 _score += results.getPoints();	
			 if(results.getMoved()) {
				 _redoStack.reset(); 					//Si se ha producido el movimiento, vaciamos el vector de redo
				boolean anyCellFree = currentRules.addNewCell(_board, _myRandom);//Añadimos nueva celda
				if(results.getWon()) {									//Comprobamos si hemos ganado
					exit();
					throw new GameOverException("�Enhorabuena, has ganado!");
				}
				else if(!anyCellFree) {						//Si no hemos ganado, comprobamos si hemos perdido
					_posMove = !_currentType.getRules().lose(_board);	
					System.out.println(this);			//Pintamos el juego por última vez
				}
			 }
			 else {
				 _undoStack.pop();
				 System.out.print("No es posible realizar el movimiento." + '\n');
			 }
		 }
		 if(!_posMove) {
			 throw new GameOverException("Has perdido, puedes intentar rectificar(undo), reiniciar(reset), cambiar de juego(play) o simplemente salir(exit)");
		 }
		 return _posMove;
	 }
	 
	 /**Dibuja el tablero y además proporciona al jugador distintos datos como la puntuación o la casilla con mejor valor del tablero*/
	 public String toString() {
		 String game = _board.toString();
		 game += '\n';
		 game += "Mejor valor: ";
		 game += _currentType.getRules().getWinValue(_board);
		 game += MyStringUtils.repeat(" ",10);
		 game += "score: ";
		 game += _score;
		 game += '\n';
		 return game;
	 }
	 
	 /**Resetea el juego volviendo a crear un tablero con la modalidad de juego correcta*/
	 public void reset() {
		 _board = _currentType.getRules().createBoard(_size);
		_currentType.getRules().initBoard(_board, _initCells, _myRandom);
		_score = 0;
		if (_initCells == _size * _size) { //Si no quedan celdas vacias comprobamos si se puede hacer mas movimientos
			_posMove = !_currentType.getRules().lose(_board);
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
	 /**Permite cambiar el estado del tablero al estado aState*/
	 public void setState(GameState aState) {
		 _board.setState(aState.getBoardState());
		 _score = aState.getScore();
	 }
	 
	 /**Comprueba si se pueden deshacer más movimientos y de ser así deshace uno y actualiza la pila de estados*/
	 public boolean Undo() {
		 boolean success = false;
		 try {
			if(_undoStack.isEmpty()) throw new EmptyStackException();
			else {
				_redoStack.push(getState());
				setState(_undoStack.pop());
				success = true;
				_posMove = true;				//Al deshacer un movimiento, permites que se realizen mas
			}
		 } catch (EmptyStackException e) {
			 System.out.println("No se pueden deshacer mas movimientos." + '\n');
		 }
		
		return success;
	}
	 
	 /**Comprueba si se pueden rehacer más movimientos y de ser así rehace uno y actualiza la pila de estados*/
	 public boolean Redo() {
		boolean success = false;
		try {
		if(_redoStack.isEmpty()) throw new EmptyStackException();
		else {
			_undoStack.push(getState());
			setState(_redoStack.pop());
			success = true;
			_posMove = !_currentType.getRules().lose(_board);			//Comprobamos si se pueden hacer movimientos
		}
		} catch(EmptyStackException e) {
			System.out.println("No se pueden rehacer mas movimientos." + '\n');
		}
		return success;
		}
	 
	 public void store(BufferedWriter out) throws IOException{
		 out.write(fileMsg + '\n');
		 _board.store(out);
		 out.write(_initCells + " " + _score + " " + _currentType.getParameterName());
	 }
	 
	 public GameType load(BufferedReader in) throws IOException, ExecutionException{
		 String entrada = in.readLine();
		 if(!entrada.equals(fileMsg)) throw new ExecutionException("Este fichero no contiene un juego de 2048.");
		 _undoStack.reset();
		 _redoStack.reset();
		 _board.load(in);
		 entrada = in.readLine();
		 String[] words = entrada.split("\\s+");
		 _initCells = Integer.parseInt(words[0]);
		 _size = _board.getSize(); 
		 _score = Integer.parseInt(words[1]);
		 _currentType = GameType.parse(words[2]);
		 _posMove = !_currentType.getRules().lose(_board);	
		 return _currentType;
	 }
}
