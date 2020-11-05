package tp.pr2.logic.multigames;

import java.util.Random;

import tp.pr2.Board;
import tp.pr2.Cell;
import tp.pr2.Position;

public class Rules2048 implements GameRules {
	private final int _winValue = 2048;
	
	/**Se encarga de la generación de nuevos valores para las celdas en la modalidad original*/
	@Override
	public void addNewCellAt(Board board, Position pos, Random rand) {
		board.setCell(pos, newValue(10,4,2,rand));
	}

	/**Se encarga de la fusión para la modalidad original*/
	@Override
	public int merge(Cell self, Cell other) {
		int result = 0;
		if(self.getValue() == other.getValue()) {
			self.setValue(self.getValue() * 2);
			other.setValue(0);
			result = self.getValue();
		}
		return result;
	}

	/**Se encarga de encontrar el mejor valor para la modalidad original*/
	@Override
	public int getWinValue(Board board) {
		return board.getHighest();
	}

	/**Se encarga de comprobar si se ha alcanzado el valor ganador para la modalidad original*/
	@Override
	public boolean win(Board board) {
		return getWinValue(board) >= _winValue;
	}
	
	/***/
	public boolean isWinValue(int value) {
		return value == _winValue;
	}

	/**Se encarga de comprobar si se puede realizar la fusión de dos celdas para la modalidad */
	@Override
	public boolean posMerge(Cell self, Cell other) {
		return self.getValue() == other.getValue();
	}

}
