package tp.pr2;
/**La clase GameStateStack gestiona la pila de estados del tablero*/
public class GameStateStack {
	private static final int CAPACITY = 20;
	private GameState[] _buffer = new GameState[CAPACITY];
	private int _elements = 0;
	private int _actual = 0;

	/**Elimina una posicion del array de estados*/
	public GameState pop() {
		GameState state = null;
		if(_elements > 0) {
			if(_actual == 0) {
				state = _buffer[CAPACITY - 1];
				_actual = CAPACITY - 1;
			}
			else {
				state = _buffer[_actual - 1];
				_actual--;
			}
			_elements--;
		}
		return state;
	}
	
	/**Aniade un nuevo estado del tablero al array*/
	public void push(GameState state) {
			_buffer[_actual] = state;
			if(_elements < CAPACITY)_elements++;
			if(_actual == CAPACITY - 1)_actual = 0;
			else _actual++;
	}
	/**Devuelve si el array esta vacio*/
	public boolean isEmpty() {return _elements == 0;}
	/**Resetea el array*/
	public void reset() {_elements = 0; _actual = 0;}

}
