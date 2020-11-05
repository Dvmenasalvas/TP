package tp.pr2;
/**La clase pública Position sirve para determinar la posición de una celda en el tablero*/
public class Position {
	private int _row, _column;
	
	/**Devuelve la columna*/
	public int getColumn() {return _column;}
	/**Devuelve la fila*/
	public int getRow() {return _row;}
	
	/**Permite cambiar los valores de la fila y la columna*/
	public void setPosition(int row, int column) {
		_row = row;
		_column = column;
	}
}
