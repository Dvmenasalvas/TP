package tp.pr3.logic.multigames;

import java.util.Random;

import tp.pr3.Board;
import tp.pr3.Cell;
import tp.pr3.Position;
import tp.pr3.util.MyMathsUtil;

public class RulesFib implements GameRules {
	private final int _winValue = 144;

	/**Se encarga de la generación de nuevos valores para las celdas en la modalidad inverse*/
	@Override
	public void addNewCellAt(Board board, Position pos, Random rand) {
		board.setCell(pos, newValue(10,2,1,rand));
	}

	/**Se encarga de la fusión para la modalidad inverse*/
	@Override
	public int merge(Cell self, Cell other) {
		int result = 0;
		if(self.getValue() == MyMathsUtil.nextFib(other.getValue())) { //Son consecutivos y self es mayor
			self.setValue(MyMathsUtil.nextFib(self.getValue()));
			other.setValue(0);
			result = self.getValue();
		}
		else if(other.getValue() == MyMathsUtil.nextFib(self.getValue())) { //Son consecutivos y other es mayor
			self.setValue(MyMathsUtil.nextFib(other.getValue()));
			other.setValue(0);
			result = self.getValue();
		}
		else if(other.getValue() == 1 && self.getValue() == 1) {	//Son dos unos
			self.setValue(MyMathsUtil.nextFib(other.getValue()));
			other.setValue(0);
			result = self.getValue();
		}
		return result;
	}

	/**Se encarga de encontrar el mejor valor para la modalidad inverse*/
	@Override
	public int getWinValue(Board board) {
		return board.getHighest();
	}

	/**Se encarga de comprobar si se ha alcanzado el valor ganador para la modalidad inverse*/
	@Override
	public boolean win(Board board) {
		return getWinValue(board) >= _winValue;
	}
	public boolean isWinValue(int value) {
		return value == _winValue;
	}

	/**Se encarga de comprobar si se puede realizar la fusión de dos celdas para la modalidad inverse*/
	@Override
	public boolean canMergeNeighbours(Cell self, Cell other) {
		return self.getValue() == MyMathsUtil.nextFib(other.getValue()) || other.getValue() == MyMathsUtil.nextFib(self.getValue()) || (other.getValue() == 1 && self.getValue() == 1);
	}

}
