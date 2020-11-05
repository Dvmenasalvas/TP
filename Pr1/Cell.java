package tp.pr1;

public class Cell {
	private int _value;
	
	public Cell() {_value = 0;}
	
	public int getValue() {return _value;}
	public boolean isEmpty() {return _value == 0;}
	public void setValue(int nuevo){_value = nuevo;}
	
	public boolean doMerge(Cell neighbour){
		boolean success = false;
		if(neighbour._value == _value){
			_value *= 2;
			neighbour._value = 0;
			success = true;
		}
		return success;
	}
	
}
