
import java.util.Scanner;

// The "main" class, containing the main method and an instace variable of type ocean
public class BattleshipGame {
	
	private Ocean ocean;
	private Scanner scnr;
	
	public BattleshipGame() {
		// Start Scanner
		scnr = new Scanner(System.in);
	}
	
	public void start() {
		// Set up new ocean instance
		ocean = new Ocean();
		ocean.placeAllShipsRandomly();
		int[] parsedShot = new int[2];
		// Begin game loop until game is over
		while(true) {
			if(ocean.isGameOver()) {
				break;
			}
			// Print current ocean state
			ocean.print();
			// Get the next shot from the user
			parsedShot[0] = getCoord("x");
			parsedShot[1] = getCoord("y");
			System.out.println("");
			// Record number of ships sunk
			int numSunk = ocean.getShipsSunk();
			// Check if shot hits
			checkHit(parsedShot[0], parsedShot[1]);
			System.out.println("shots:" + ocean.getShotsFired());
			// Check if ship is sunk
			if(numSunk != ocean.getShipsSunk()) {
				String type = ocean.getShipType(parsedShot[0], parsedShot[1]);
				System.out.printf("You just sunk a %s\n", type);
			}
		}
		// display game over
		ocean.print();
		gameOver();
		// ask if the player wants to play again
		playAgain();
	}
	
	// Shoot at the coords and determines if user hit something
	private void checkHit(int x , int y) {
		if(ocean.shootAt(x, y)) {
			System.out.println("hit");
		}else {
			System.out.println("miss");
		}
	}
	
	// Notifies the game is over, displays the user's final score
	private void gameOver() {
		System.out.println("Game over!");
		System.out.printf("Score: %d shots\n", ocean.getShotsFired());
	}
	
	// Check if the player wants to play again. Restarts game or exits by closing the scanner
	private void playAgain() {
		System.out.println("Do you want to play again y/n?");
		String userInput;
		while(true) {
			userInput = scnr.next();
			if(userInput.equals("y")) {
				System.out.println("Reseting game...");
				start();
				break;
			}else if (userInput.equals("n")) {
				System.out.println("Thank you for playing!");
				scnr.close();
				break;
			}else {
				System.out.println("please enter a valid response");
			}
		}
	}
	
	// Get valid coordinate from user
	private int getCoord(String coord) {
		int retVal = -1;
		while(true) {
			// Ask user for input
			System.out.printf("Enter %s coordinate of next shot:", coord);
			// grab first integer input
			if(!scnr.hasNextInt()) {
				System.out.println("Please enter a number value");
				scnr.next();
				continue;
			}
			retVal = scnr.nextInt();
			if(retVal > 9 || retVal < 0) {
				System.out.println("Please enter a value within bounds");
			} else {
				break;
			}
		}
		return retVal;
	}
	
	
	
	public static void main(String[] args) {
		BattleshipGame newGame = new BattleshipGame();
		newGame.start();
	}
	
}
