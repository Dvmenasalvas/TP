package tp.pr2.logic.multigames;

import java.util.Random;

import tp.pr2.Board;
import tp.pr2.Cell;
import tp.pr2.Position;

public class RulesInverse implements GameRules {
	private final int _winValue = 0;
	
	/**Se encarga de la generación de nuevos valores para las celdas en la modalidad fib*/
	@Override
	public void addNewCellAt(Board board, Position pos, Random rand) {
		board.setCell(pos, newValue(10,1024,2048,rand));
	}

	/**Se encarga de la fusión para la modalidad fib*/
	@Override
	public int merge(Cell self, Cell other) {
		int result = 0;
		if(self.getValue() == other.getValue()) {
			self.setValue(self.getValue() / 2);
			other.setValue(0);
			if(self.getValue() != 0) {
				int exp = 0, act = self.getValue();
				while(act < 2048) { //Calculamos cuantas veces hay que multiplicar el valor de la casilla por 2 para obtener 2048
					act *= 2;
					exp++;
				}
				result = 1;
				for(int i = 0; i < exp; i++) { //El resultado es 2 elevado al numero obtenido anteriormente(no usamos Math.pow porque devuelve un double y no queremos hacer conversion de tipos)
					result *= 2;
				}
			}
			else result = 2048; //Si el valor es 0, devolvemos 2048
		}
		return result;
	}

	/**Se encarga de encontrar el mejor valor para la modalidad fib*/
	@Override
	public int getWinValue(Board board) {
		return board.getLowest();
	}

	/**Se encarga de comprobar si se ha alcanzado el valor ganador para la modalidad fib*/
	@Override
	public boolean win(Board board) {
		return getWinValue(board) == _winValue;
	}
	
	public boolean isWinValue(int value) {
		return value == _winValue;
	}

	/**Se encarga de comprobar si se puede realizar la fusión de dos celdas para la modalidad fib*/
	@Override
	public boolean posMerge(Cell self, Cell other) {
		return self.getValue() == other.getValue();
	}

}
