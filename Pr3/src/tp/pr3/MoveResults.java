package tp.pr3;
/**La clase MoveResults gestiona los distintos parámetros que varían a lo largo de la partida*/
public class MoveResults {
	private boolean _moved; 
	private int _points;
	private boolean _won;

	/**Inicializa los parámetros: no se ha hecho movimiento, los puntos obtenidos en el movimiento son 0 y no se ha ganado todavía*/
	public MoveResults() {
		_moved = false;
		_points = 0;
		_won = false;
	}
	/**Devuelve si se ha realizado movimiento*/
	public boolean getMoved(){return _moved;}
	/**Devuelve los puntos obtenidos en el movimiento*/
	public int getPoints(){return _points;}
	/**Devuelve si se ha ganado*/
	public boolean getWon() {return _won;}
	
	/**Permite cambiar la variable booleana que dice si se ha movido o no*/ 
	public void setMoved(boolean moved) {_moved = moved;}
	/**Permite cambiar los puntos obtenidos en el movimiento*/
	public void setPoints(int points) {_points = points;}
	/**Permite cambiar la variable booleana que dice si se ha ganado o no*/
	public void setWon(boolean won) {_won = won;}
}
