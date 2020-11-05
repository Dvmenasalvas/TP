package tp.pr1;

import java.util.Random ;

public class Game {
	private Board _board;
	private int _size;
	private int _initCells;
	private Random _myRandom;
	private int _highest;
	private int _score;
	private boolean _posMove;
	
	public boolean getPosMove() {return _posMove;}
	public int getHighest() {return _highest;}
	
	public Game(int dim, int cells, Random random){
		_myRandom = random;
		_board = new Board(dim);
		_size = dim;
		_initCells = cells;
		for(int i = 0; i < _initCells; i++)
			_board.setCell(newPosition(), newValue(10,4,2));
		_score = 0;
		_highest = 0;
		_posMove = true;
	}
	
	private int newValue(int prob, int leastLikely, int mostLikely) {
		return (_myRandom.nextInt(100) < prob ? leastLikely : mostLikely);
	}

	private Position newPosition() {
		ArrayAsList free = _board.freeCells();
		return free.get(_myRandom.nextInt(free.getTam() - 1));
	}
	
	 public void move(Direction dir){
		 MoveResults results = _board.executeMove(dir);
		 if(results.getMaxToken() > _highest) _highest = results.getMaxToken();
		 _score += results.getPoints();
		 if(results.getMoved()) {
			 ArrayAsList free = _board.freeCells();	//No usamos la función newPosition porque nos interesa saber si quedan celdas libres para ver si la partida ha acabado
			if(free.getTam() > 1) _board.setCell(free.get(_myRandom.nextInt(free.getTam() - 1)), newValue(10,4,2));
			else _board.setCell(free.get(0), newValue(10,4,2));
			if (free.getTam() == 1) { //Si no quedan celdas vacias comprovamos si se puede hacer mas movimientos
				_posMove = _board.posMove();
			}
		 }
	 }
	 
	 public void print() {
		 String game = _board.toString();
		 game += '\n';
		 game += " highest: ";
		 game += _highest;
		 game += MyStringUtils.repeat(" ",10);
		 game += "score: ";
		 game += _score;
		 game += '\n';
		 System.out.println(game);
	 }
	 
	 public void reset() {
		 _board = new Board(_size);
			for(int i = 0; i < _initCells; i++)
				_board.setCell(newPosition(), newValue(10,4,2));
			_score = 0;
			_highest = 0;
			_posMove = true;
	 }
}
