package tp.pr2.util;

import tp.pr2.*;

public class ArrayAsList {
	private Position [ ] _list;
	private int _cap, _tam;
	
	/**Construye el array de Position*/
	public ArrayAsList() {
		_tam = 0; _cap = 1;
		_list = new Position[_cap];
	}
	/**Devuelve el tamaño del vector*/
	public int getTam() {return _tam;}
	/**Devuelve la Position en la posición pos del vector*/
	public Position get(int pos) {
		if(pos >= 0 && pos < _tam) return _list[pos];
		else return _list[0];
	}
	/**Añade una nueva Position al vector*/
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
	/**Elimina una Position del vector, para ello cambiamos el orden del vector pero no importa ya que este no es de ninguna utilidad*/
	public void pop(int pos) { //Elimina una posición, cambiamos el orden del vector ya que este no es de ninguna utilidad
		if(pos < _tam) {
			_list[pos] = _list[_tam - 1];
			_tam--;
		}
	}
}