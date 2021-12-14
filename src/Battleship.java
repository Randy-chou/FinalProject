
/**
 * A ship with a length of four tiles.
 * 
 * @author randy
 *
 */

public class Battleship extends Ship {
	
	
	/**
	 * Sets the inherited length variable and initializes the hit array, based on the size of this ship (4 tiles).
	 */
	public Battleship() {
		super();
		length = 4;
		hit = new boolean[length];
		hit[0] = false;
		hit[1] = false;
		hit[2] = false;
		hit[3] = false;
	}
	
	/**
	 * Returns "Battleship", indicating that this is a Battleship.
	 * 
	 * @return the String "Battleship"
	 */
	@Override
	public String getShipType() {
		return "Battleship";
	}

}
