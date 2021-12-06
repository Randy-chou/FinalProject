
// Provides characteristics common to all ships
public abstract class Ship {
	
	protected int bowColumn = 0;
	
	protected int bowRow = 0;
	
	protected boolean[] hit;
	
	protected boolean horizontal;
	
	protected int length;
	
	//returns the length of the ship
	public int getLength() {
		return length;
	}
	
	//returns the row of the bow of the ship
	public int getBowRow() {
		return bowRow;
	}
	
	//returns the column of the bow of the ship
	public int getBowColumn() {
		return bowColumn;
	}
	
	//sets the row of the bow of the ship
	public void setBowRow(int bowRow) {
		this.bowRow = bowRow;
	}
	
	//sets the column of the bow of the ship
	public void setBowColumn(int bowColumn) {
		this.bowColumn = bowColumn;
	}
	
	//set whether the boat is horizontal
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}
	
	//returns the type of ship
	public abstract String getShipType();
	
	//determine if this is a valid placement for the ship
	public boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
		int calRow;
		int calCol;
		//determine of starting row and column are viable
		if(horizontal && column+length-1 > 9) {
			return false;
		}else if(!horizontal && row+length-1 > 9){
			return false;
		}
		//starting from the bow check for overlapping ships
		for(int i = -1 ; i < 2 ; i++) {
			for(int j = -1 ; j <= this.length ; j++) {
				//calculate the row and column to check
				if(horizontal) {
					calRow = row + i;
					calCol = column + j;
				}else {
					calRow = row + j;
					calCol = column + i;
				}
				//check if tile to check is within bounds
				if(calRow < 0 || calRow > 9 || calCol < 0 || calCol > 9) {
					continue;
				}
				//check if tile is empty
				if(ocean.isOccupied(calRow, calCol)) {
					return false;
				}
			}
		}
		return true;
	}
	
	//places ship in the corresponding configuration
	public void placeShipAt(int row, int column, boolean horizontal, Ocean ocean) {
		this.setBowRow(row);
		this.setBowColumn(column);
		this.setHorizontal(horizontal);
		Ship[][] ships = ocean.getShipArray();
		for(int i = 0 ; i < this.length ; i++) {
			if(horizontal) {
				ships[row][column + i] = this;
			} else {
				ships[row + i][column] = this;
			}
		}
	}
	
	//if part of this ship ocupies this coordinate, and the ship hasn't been sunk then mark portion as hit
	public boolean shootAt(int row, int column) {
		int diff;
		if(horizontal) {
			diff = column - bowColumn;
			if(diff >= 0 && diff < length && isSunk() == false) {
				hit[diff] = true;
				return true;
			}
			return false;
		}else {
			diff = row - bowRow;
			if(diff >= 0 && diff < length && isSunk() == false) {
				hit[diff] = true;
				return true;
			}
			return false;
		}
	}
	
	//returns true if ship has been sunk
	public boolean isSunk() {
		for(int i = 0 ; i < hit.length ; i++) {
			if(hit[i] == false) {
				return false;
			}
		}
		return true;
	}
	
	//returns a single character for whether the ship has been sunk
	@Override
	public String toString() {
		if(isSunk()) {
			return "x";
		}else {
			return "S";
		}
	}
}
