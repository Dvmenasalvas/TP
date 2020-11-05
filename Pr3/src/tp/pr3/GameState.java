package tp.pr3;
/**La clase GameState tiene como atributos la puntuación y el estado de tablero(los valores que tienen cada celda)*/
public class GameState {
	private int _score;
	private int[][] _boardState;
	
	/**Construye un GameState guardando la puntuación y el estado del tablero*/
	public GameState(int score, int[][] board) {
		_score = score;
		_boardState = board;
	}
	
	/**Devuelve la puntuación*/
	public int getScore() {return _score;}
	/**Devuelve el estado del tablero*/
	public int[][] getBoardState() {return _boardState;}
}
