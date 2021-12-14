import java.util.Random;

/**
 * This class manages the game state by keeping track of what entity is
 * contained in each position on the game board.
 * 
 * @author harry
 *
 */
public class Ocean implements OceanInterface {

	/**
	 * A 10x10 2D array of Ships, which can be used to quickly determine which ship
	 * is in any given location.
	 */
	protected Ship[][] ships;
	
	/**
	 * A 10x10 2D array of boolean values, which can be used to quickly determine if
	 * a location has been shot at before
	 */
	protected boolean[][] shots;

	/**
	 * The total number of shots fired by the user
	 */
	protected int shotsFired;

	/**
	 * The number of times a shot hit a ship. If the user shoots the same part of a
	 * ship more than once, every hit is counted, even though the additional "hits"
	 * don't do the user any good.
	 */
	protected int hitCount;

	/**
	 * The number of ships totally sunk.
	 * 
	 */
	protected int shipsSunk;

	/**
	 * Creates an "empty" ocean, filling every space in the <code>ships</code> array
	 * with EmptySea objects. Should also initialize the other instance variables
	 * appropriately.
	 */
	public Ocean() {
		ships = new Ship[10][10];
		shots = new boolean[10][10];
		shotsFired = 0;
		hitCount = 0;
		shipsSunk = 0;
		// Fill ocean with empty
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				ships[i][j] = new EmptySea();
				shots[i][j] = false;
			}
		}
	}

	/**
	 * Place all ten ships randomly on the (initially empty) ocean. Larger ships
	 * must be placed before smaller ones to avoid cases where it may be impossible
	 * to place the larger ships.
	 * 
	 * @see java.util.Random
	 */
	public void placeAllShipsRandomly() {
		Random random = new Random();
		boolean horiz = random.nextBoolean();
		int startLoc = (int) (Math.random() * 100);
		int[] coords = new int[2];
		int count = 0;
		// Place 1 battleship
		while (true) {
			Battleship battleship = new Battleship();
			horiz = random.nextBoolean();
			coords = translateCoord(startLoc);
			if (battleship.okToPlaceShipAt(coords[0], coords[1], horiz, this)) {
				battleship.placeShipAt(coords[0], coords[1], horiz, this);
				break;
			}
			startLoc++;
		}
		// Place 2 cruisers
		startLoc = (int) (Math.random() * 100);
		while (count < 2) {
			Cruiser cruiser = new Cruiser();
			horiz = random.nextBoolean();
			coords = translateCoord(startLoc);
			if (cruiser.okToPlaceShipAt(coords[0], coords[1], horiz, this)) {
				cruiser.placeShipAt(coords[0], coords[1], horiz, this);
				startLoc = (int) (Math.random() * 100);
				coords = translateCoord(startLoc);
				count++;
				continue;
			}
			startLoc++;
		}
		// Place 3 destroyers
		count = 0;
		startLoc = (int) (Math.random() * 100);
		while (count < 3) {
			Destroyer destroyer = new Destroyer();
			horiz = random.nextBoolean();
			coords = translateCoord(startLoc);
			if (destroyer.okToPlaceShipAt(coords[0], coords[1], horiz, this)) {
				destroyer.placeShipAt(coords[0], coords[1], horiz, this);
				startLoc = (int) (Math.random() * 100);
				coords = translateCoord(startLoc);
				count++;
				continue;
			}
			startLoc++;
		}
		// Place 4 submarines
		count = 0;
		startLoc = (int) (Math.random() * 100);
		while (count < 4) {
			Submarine submarine = new Submarine();
			horiz = random.nextBoolean();
			coords = translateCoord(startLoc);
			if (submarine.okToPlaceShipAt(coords[0], coords[1], horiz, this)) {
				submarine.placeShipAt(coords[0], coords[1], horiz, this);
				startLoc = (int) (Math.random() * 100);
				coords = translateCoord(startLoc);
				count++;
				continue;
			}
			startLoc++;
		}
	}
	
	/**
	 * When given a randomly generated number, parses out the associated row and column values
	 * 
	 * @param input    randomly generated int between 0 and 100
	 * @return an int[] where int[0] is the row value and int[1] is the column value
	 */
	private int[] translateCoord(int input) {
		int[] returnList = new int[2];
		input = input % 100;
		returnList[0] = input / 10;
		returnList[1] = input % 10;
		return returnList;
	}

	/**
	 * Checks if this coordinate is not empty; that is, if this coordinate does not
	 * contain an EmptySea reference.
	 * 
	 * @param row    the row (0 to 9) in which to check for a floating ship
	 * @param column the column (0 to 9) in which to check for a floating ship
	 * @return {@literal true} if the given location contains a ship, and
	 *         {@literal false} otherwise.
	 */
	public boolean isOccupied(int row, int column) {
		if (ships[row][column].getShipType().equals("empty")) {
			return false;
		} else {
			return true;
		}
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
	 * @return {@literal true} if the given location contains an afloat ship (not an
	 *         EmptySea), {@literal false} if it does not.
	 */
	public boolean shootAt(int row, int column) {
		shotsFired++;
		shots[row][column] = true;
		boolean before = ships[row][column].isSunk();
		boolean retVal = ships[row][column].shootAt(row, column);
		boolean after = ships[row][column].isSunk();
		if (before != after) {
			shipsSunk++;
		}
		if (retVal) {
			hitCount++;
		}
		return retVal;
	}

	/**
	 * @return the number of shots fired in this game.
	 */
	public int getShotsFired() {
		return this.shotsFired;
	}

	/**
	 * @return the number of hits recorded in this game.
	 */
	public int getHitCount() {
		return this.hitCount;
	}

	/**
	 * @return the number of ships sunk in this game.
	 */
	public int getShipsSunk() {
		return this.shipsSunk;
	}

	/**
	 * @return {@literal true} if all ships have been sunk, otherwise
	 *         {@literal false}.
	 */
	public boolean isGameOver() {
		if (shipsSunk == 10) {
			return true;
		}
		return false;
	}

	/**
	 * When given tile location, returns the ship type at that location
	 * 
	 * @param row    the row (0 to 9) in which to shoot
	 * @param column the column (0 to 9) in which to shoot
	 * @return ship type at location
	 */
	public String getShipType(int row, int column) {
		return ships[row][column].getShipType();
	}

	/**
	 * Provides access to the grid of ships in this Ocean. The methods in the Ship
	 * class that take an Ocean parameter must be able to read and even modify the
	 * contents of this array. While it is generally undesirable to allow methods in
	 * one class to directly access instancce variables in another class, in this
	 * case there is no clear and elegant alternatives.
	 * 
	 * @return the 10x10 array of ships.
	 */
	public Ship[][] getShipArray() {
		return ships;
	}

	/**
	 * Prints the ocean. To aid the user, row numbers should be displayed along the
	 * left edge of the array, and column numbers should be displayed along the top.
	 * Numbers should be 0 to 9, not 1 to 10. The top left corner square should be
	 * 0, 0.
	 * <ul>
	 * <li>Use 'S' to indicate a location that you have fired upon and hit a (real)
	 * ship</li>
	 * <li>'-' to indicate a location that you have fired upon and found nothing
	 * there</li>
	 * <li>'x' to indicate a location containing a sunken ship</li>
	 * <li>'.' (a period) to indicate a location that you have never fired
	 * upon.</li>
	 * </ul>
	 * 
	 * This is the only method in Ocean that has any printing capability, and it
	 * should never be called from within the Ocean class except for the purposes of
	 * debugging.
	 * 
	 */
	public void print() {
		// Print out header
		System.out.println("              Board             ");
		System.out.println("    0  1  2  3  4  5  6  7  8  9");
		// Print out rest of board
		String location = "E";
		for (int i = 0; i < 10; i++) {
			System.out.print(" " + i);
			for (int j = 0; j < 10; j++) {
				// Check if a shot has been fired at position
				if (shots[i][j] == false) {
					location = ".";
				} else if (ships[i][j].getShipType().equals("empty")) {
					location = "-";
				} else {
					location = ships[i][j].toString();
				}
				System.out.printf("  %s", location);
			}
			System.out.print("\n");
		}
		System.out.println("");
	}
}
