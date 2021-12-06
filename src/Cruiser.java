
public class Cruiser extends Ship {
	
	public Cruiser() {
		super();
		length = 3;
		hit = new boolean[length];
		hit[0] = false;
		hit[1] = false;
		hit[2] = false;
	}
	
	@Override
	public String getShipType() {
		return "Cruiser";
	}

}
