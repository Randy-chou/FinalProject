
public class EmptySea extends Ship{

	public EmptySea() {
		super();
		length = 1;
		hit = new boolean[length];
		hit[0]=false;
	}
	
	@Override
	public String getShipType() {
		return "empty";
	}
	
	// Always returns false since nothing will be hit
	@Override
	public boolean shootAt(int row, int column) {
		return false;
	}
	
	// Always returns false since thre is nothing to be sunk
	@Override
	public boolean isSunk() {
		return false;
	}
	
	@Override
	public String toString() {
		return "S";
	}

}
