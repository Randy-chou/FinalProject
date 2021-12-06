
public class Battleship extends Ship {
	
	public Battleship() {
		super();
		length = 4;
		hit = new boolean[length];
		hit[0] = false;
		hit[1] = false;
		hit[2] = false;
		hit[3] = false;
	}
	
	@Override
	public String getShipType() {
		return "Battleship";
	}

}
