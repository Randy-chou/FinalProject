
public class Destroyer extends Ship {
	
	public Destroyer() {
		super();
		length = 2;
		hit = new boolean[2];
		hit[0] = false;
		hit[1] = false;
	}
	
	@Override
	public String getShipType() {
		return "Destroyer";
	}

}
