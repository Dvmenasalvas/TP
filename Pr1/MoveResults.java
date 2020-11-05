package tp.pr1;

public class MoveResults {
	private boolean _moved; 
	private int _points;
	private int _maxToken;
	
	public MoveResults() {
		_moved = false;
		_points = 0;
		_maxToken = 0;
	}
	
	public boolean getMoved(){return _moved;}
	public int getPoints(){return _points;}
	public int getMaxToken(){return _maxToken;}
	
	public void setMoved(boolean moved) {_moved = moved;}
	public void setPoints(int points) {_points = points;}
	public void setMaxToken(int maxToken) {_maxToken = maxToken;}
}
