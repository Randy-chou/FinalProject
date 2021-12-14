
/**
 * Considered a type of Ship so that the Ocean's ship 2D array can consist of EmptySea references for empty tiles
 * and proper ships for tiles with ships actually inside of them.
 * 
 * @author harry
 *
 */

public class EmptySea extends Ship {
	
	/**
	 * Sets the inherited length variable and initializes the hit array, based on the size of this Empty Sea (1 tiles).
	 */
	public EmptySea() {
		super();
		length = 1;
		hit = new boolean[length];
		hit[0] = false;
	}
	
	/**
	 * Returns "empty", indicating that this is an Empty sea tile.
	 * 
	 * @return the String "empty"
	 */
	@Override
	public String getShipType() {
		return "empty";
	}

	/**
	 * Fires a shot at this coordinate. This will update the number of shots that
	 * have been fired (and potentially the number of hits, as well). If a location
	 * contains a real, not sunk ship, this method should return {@literal true}
	 * every time the user shoots at that location. If the ship has been sunk,
	 * additional shots at this location should return {@literal false}.
	 * 
	 * @param row    the row (0 to 9) in which to shoot
	 * @param column the column (0 to 9) in which to shoot
	 * @return false always, since nothing will be hit.
	 */
	@Override
	public boolean shootAt(int row, int column) {
		return false;
	}

	/**
	 * Since an EmptySea is empty by definition, it is not possible that one can be sunk.
	 * 
	 * @return false always, since nothing will be hit.
	 */
	@Override
	public boolean isSunk() {
		return false;
	}
	
	/**
	 * Returns a single character String to use in the Ocean's print method.
	 * This method should return "x" if the ship has been sunk, and "S" if it 
	 * has not yet been sunk. This method can only be used to print out locations 
	 * in the ocean that have been shot at; it should not be used to print locations 
	 * that have not been the target of a shot yet.
	 * 
	 * @return "x" if this ship has been sunk, and "S" otherwise.
	 */
	@Override
	public String toString() {
		if(isSunk()) {
			return "x";
		}
		return "S";
	}

}
