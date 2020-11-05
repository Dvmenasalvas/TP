package tp.pr1;

public class ArrayAsList {
	private Position [ ] _list;
	private int _cap, _tam;
	
	public ArrayAsList() {
		_tam = 0; _cap = 1;
		_list = new Position[_cap];
	}
	
	public int getTam() {return _tam;}
	public Position get(int pos) {
		if(pos >= 0 && pos < _tam) return _list[pos];
		else return _list[0];
	}
	
	public void add(Position pos) {
		if(_tam == _cap) {
			_cap *= 2;
			Position []nueva = new Position[_cap];
			for(int i = 0; i < _tam; i++)
				nueva[i] = _list[i];
			_list = nueva;
		}
		_list[_tam] = pos;
		_tam++;
	}
}