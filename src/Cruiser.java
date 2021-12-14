
/**
 * A ship with a length of three tiles.
 * 
 * @author harry
 *
 */

public class Cruiser extends Ship {
	
	/**
	 * Sets the inherited length variable and initializes the hit array, based on the size of this ship (3 tiles).
	 */
	public Cruiser() {
		super();
		length = 3;
		hit = new boolean[length];
		hit[0] = false;
		hit[1] = false;
		hit[2] = false;
	}
	
	/**
	 * Returns "Cruiser", indicating that this is a Cruiser.
	 * 
	 * @return the String "Cruiser"
	 */
	@Override
	public String getShipType() {
		return "Cruiser";
	}

}
