package tp.pr1;

public class Board {
	private Cell [ ][ ] _board;
	private int _boardSize;
	
	public Board(int size){
		_boardSize = size;
		_board = new Cell[size][size];
		for(int i = 0; i < _boardSize; i++)
			for(int j = 0; j < _boardSize; j++)
				_board[i][j] = new Cell();
	}
	
	public void setCell(Position pos, int value){
		_board[pos.getRow()][pos.getColumn()].setValue(value);
	}
	
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
	
	public MoveResults moveRight(){
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
						if(!_board[i][j].doMerge(_board[i][checked])) {	//No es posible la fusion
							if(checked != j - 1) {	//La casilla no vacia no es vecina de la actual
								results.setMoved(true);
								_board[i][j - 1].setValue(_board[i][checked].getValue());
								_board[i][checked].setValue(0);
							}
						}
						else { //La fusión es posible
							results.setMoved(true);
							results.setPoints(results.getPoints() + 1);
						}	
					}
					checked --;
				}
				if(_board[i][j].getValue() > results.getMaxToken()) results.setMaxToken(_board[i][j].getValue());
			}
		}
		return results;
	}
	
	public ArrayAsList freeCells() { //Devuelve un array de celdas libres
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
	
	public boolean posMove() { //Cuando el tablero esta lleno, comprueba si se puede hacer algun movimiento mas
		boolean result = false;
		int i = 0;
		while(i < _boardSize && !result) {
			int j = 0;
			while(j < _boardSize && !result) {
				if(i < _boardSize - 1) //Compara con el de abajo
					if(_board[i][j].getValue() == _board[i + 1][j].getValue()) result = true; 
				if(j < _boardSize - 1)//Compara con el de la derecha
					if(_board[i][j].getValue() == _board[i][j + 1].getValue()) result = true; 
				j++;
			}
			i++;
		}
		return result;
	}
	
	public MoveResults executeMove(Direction dir) {
		MoveResults results = new MoveResults();
		switch(dir) {
		case RIGHT:	results = moveRight();
					break;
		case LEFT:	reflect();
					results = moveRight();
					reflect();
					break;
		case UP:	transpose();
					//Mover izquierda
					reflect();
					results = moveRight();
					reflect();
					//Dejar normal otra vez
					transpose();
					break;
		case DOWN:	transpose();
					results = moveRight();
					transpose();
		}
		return results;
	}
	

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
}
