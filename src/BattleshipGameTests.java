import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BattleshipGameTests {

	static InputStream origionalIn;
	static OutputStream origionalOut;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		origionalIn = System.in;
		origionalOut = System.out;
	}

	@AfterEach
	void tearDown() throws Exception {
		System.in.close();
		System.setIn(origionalIn);
		System.out.close();
		PrintStream origionalStream = new PrintStream(origionalOut);
		System.setOut(origionalStream);
	}

	@Test
	void printOcean() {
		OutputStream outputStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(outputStream);
		System.setOut(printStream);

		Ocean test = new Ocean();
		test.print();

		String printedContents = outputStream.toString();
		StringTokenizer st = new StringTokenizer(printedContents, "\n");

		assertEquals(st.nextToken(), "              Board             ");
		assertEquals(st.nextToken(), "    0  1  2  3  4  5  6  7  8  9");
		assertEquals(st.nextToken(), " 0  .  .  .  .  .  .  .  .  .  .");
		assertEquals(st.nextToken(), " 1  .  .  .  .  .  .  .  .  .  .");
		assertEquals(st.nextToken(), " 2  .  .  .  .  .  .  .  .  .  .");
		assertEquals(st.nextToken(), " 3  .  .  .  .  .  .  .  .  .  .");
		assertEquals(st.nextToken(), " 4  .  .  .  .  .  .  .  .  .  .");
		assertEquals(st.nextToken(), " 5  .  .  .  .  .  .  .  .  .  .");
		assertEquals(st.nextToken(), " 6  .  .  .  .  .  .  .  .  .  .");
		assertEquals(st.nextToken(), " 7  .  .  .  .  .  .  .  .  .  .");
		assertEquals(st.nextToken(), " 8  .  .  .  .  .  .  .  .  .  .");
		assertEquals(st.nextToken(), " 9  .  .  .  .  .  .  .  .  .  .");
	}

	@Test
	void gameStart() {
		OutputStream outputStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(outputStream);
		System.setOut(printStream);

		String data = " p 11 -1 ";
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				data = data + i + " " + j + " ";
			}
		}

		data = data + "n ";

		InputStream inputData = new ByteArrayInputStream(data.getBytes());
		System.setIn(inputData);

		BattleshipGame test = new BattleshipGame();
		test.start();

		String printedContents = outputStream.toString();
		StringTokenizer st = new StringTokenizer(printedContents, "\n");
		ArrayList<String> output = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			output.add(st.nextToken());
		}
		int size = output.size();
		int hitCount = 0;
		for (String curr : output) {
			if (curr.equals("hit")) {
				hitCount++;
			}
		}
		assertEquals(hitCount, 20);
		assertEquals(output.get(size - 1), "Thank you for playing!");
	}

	@Test
	void restart() {
		OutputStream outputStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(outputStream);
		System.setOut(printStream);

		String data = "";
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				data = data + i + " " + j + " ";
			}
		}

		data = data + " y ";

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				data = data + i + " " + j + " ";
			}
		}

		data = data + "n ";

		InputStream inputData = new ByteArrayInputStream(data.getBytes());
		System.setIn(inputData);

		BattleshipGame test = new BattleshipGame();
		test.start();

		String printedContents = outputStream.toString();
		StringTokenizer st = new StringTokenizer(printedContents, "\n");
		ArrayList<String> output = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			output.add(st.nextToken());
		}
		int size = output.size();
		int hitCount = 0;
		for (String curr : output) {
			if (curr.equals("hit")) {
				hitCount++;
			}
		}
		assertEquals(hitCount, 40);
		assertEquals(output.get(size - 1), "Thank you for playing!");
	}

	@Test
	void shipsSubclasses() {
		EmptySea testEmpty = new EmptySea();
		Battleship battleship = new Battleship();
		Cruiser cruiser = new Cruiser();
		Destroyer destroyer = new Destroyer();
		Submarine submarine = new Submarine();

		assertEquals(testEmpty.getShipType(), "empty");
		assertEquals(testEmpty.shootAt(0, 0), false);
		assertEquals(testEmpty.isSunk(), false);

		assertEquals(battleship.getShipType(), "Battleship");
		assertEquals(battleship.getLength(), 4);
		assertEquals(cruiser.getShipType(), "Cruiser");
		assertEquals(cruiser.getLength(), 3);
		assertEquals(destroyer.getShipType(), "Destroyer");
		assertEquals(destroyer.getLength(), 2);
		assertEquals(submarine.getShipType(), "Submarine");
		assertEquals(submarine.getLength(), 1);
	}
	
	@Test
	void bowRow() {
		Battleship battleship = new Battleship();
		
		// set bow row to zero
		battleship.setBowRow(0);
		assertEquals(0, battleship.getBowRow());
		
		// set bow row to negative number
		battleship.setBowRow(-5);
		assertEquals(-5, battleship.getBowRow());
		
		// set bow row to positive number
		battleship.setBowRow(5);
		assertEquals(5, battleship.getBowRow());
	}
	
	@Test
	void bowColumn() {
		Battleship battleship = new Battleship();
		
		// set bow column to zero
		battleship.setBowColumn(0);
		assertEquals(0, battleship.getBowColumn());
		
		// set bow row to column number
		battleship.setBowColumn(-5);
		assertEquals(-5, battleship.getBowColumn());
		
		// set bow row to column number
		battleship.setBowColumn(5);
		assertEquals(5, battleship.getBowColumn());
	}
	
	@Test
	void horinzontal() {
		Battleship battleship = new Battleship();
		
		// set horizontal to true
		battleship.setHorizontal(true);
		assertEquals(true, battleship.isHorizontal());
		
		// set same ship horizontal to false
		battleship.setHorizontal(false);
		assertEquals(false, battleship.isHorizontal());
	}
	
	@Test
	void placeShipAt() {
		Ocean ocean = new Ocean();
		Battleship battleship = new Battleship();
		Cruiser cruiser = new Cruiser();
		Destroyer destroyer = new Destroyer();
		Submarine submarine = new Submarine();
		
		// Add ship to ocean
		battleship.placeShipAt(8, 2, true, ocean);
		assertEquals(false, ocean.isOccupied(8, 1));
		assertEquals(true, ocean.isOccupied(8, 2));
		assertEquals(true, ocean.isOccupied(8, 3));
		assertEquals(true, ocean.isOccupied(8, 4));
		assertEquals(true, ocean.isOccupied(8, 5));
		assertEquals(false, ocean.isOccupied(8, 6));
		
		// Add another ship to ocean
		cruiser.placeShipAt(4, 2, false, ocean);
		assertEquals(false, ocean.isOccupied(3, 2));
		assertEquals(true, ocean.isOccupied(4, 2));
		assertEquals(true, ocean.isOccupied(5, 2));
		assertEquals(true, ocean.isOccupied(6, 2));
		assertEquals(false, ocean.isOccupied(7, 2));

		// Add another ship to ocean
		destroyer.placeShipAt(5, 4, false, ocean);
		assertEquals(false, ocean.isOccupied(4, 4));
		assertEquals(true, ocean.isOccupied(5, 4));
		assertEquals(true, ocean.isOccupied(6, 4));
		assertEquals(false, ocean.isOccupied(7, 4));
		
		// Add another ship to ocean
		submarine.placeShipAt(2, 3, false, ocean);
		assertEquals(false, ocean.isOccupied(1, 3));
		assertEquals(true, ocean.isOccupied(2, 3));
		assertEquals(false, ocean.isOccupied(3, 3));
	}
	
	@Test
	void okToPlaceShip() {
		Ocean ocean = new Ocean();
		Battleship battleship = new Battleship();
		Cruiser cruiser = new Cruiser();
		Destroyer destroyer = new Destroyer();
		Submarine submarine = new Submarine();

		// Ship must fit within board boundries
		assertEquals(true, battleship.okToPlaceShipAt(0, 6, true, ocean));
		assertEquals(false, battleship.okToPlaceShipAt(0, 7, true, ocean));
		assertEquals(false, battleship.okToPlaceShipAt(0, 8, true, ocean));

		assertEquals(true, battleship.okToPlaceShipAt(6, 0, false, ocean));
		assertEquals(false, battleship.okToPlaceShipAt(7, 0, false, ocean));
		assertEquals(false, battleship.okToPlaceShipAt(8, 0, false, ocean));

		// Add ship to ocean
		battleship.placeShipAt(8, 2, true, ocean);
		assertEquals(false, ocean.isOccupied(8, 1));
		assertEquals(true, ocean.isOccupied(8, 2));
		assertEquals(true, ocean.isOccupied(8, 3));
		assertEquals(true, ocean.isOccupied(8, 4));
		assertEquals(true, ocean.isOccupied(8, 5));
		assertEquals(false, ocean.isOccupied(8, 6));

		// Ship must no be adjacent to other ships
		assertEquals(true, cruiser.okToPlaceShipAt(4, 2, false, ocean));
		assertEquals(false, cruiser.okToPlaceShipAt(5, 2, false, ocean));
		assertEquals(false, cruiser.okToPlaceShipAt(6, 2, false, ocean));
		assertEquals(true, cruiser.okToPlaceShipAt(4, 1, false, ocean));
		assertEquals(false, cruiser.okToPlaceShipAt(5, 1, false, ocean));
		assertEquals(false, cruiser.okToPlaceShipAt(6, 1, false, ocean));

		// Add another ship to ocean
		cruiser.placeShipAt(4, 2, false, ocean);

		assertEquals(true, destroyer.okToPlaceShipAt(6, 4, true, ocean));
		assertEquals(false, destroyer.okToPlaceShipAt(6, 4, false, ocean));
		assertEquals(true, destroyer.okToPlaceShipAt(5, 4, false, ocean));
		assertEquals(false, destroyer.okToPlaceShipAt(6, 3, true, ocean));
		assertEquals(false, destroyer.okToPlaceShipAt(7, 3, true, ocean));
		assertEquals(false, destroyer.okToPlaceShipAt(4, 3, true, ocean));

		// Add another ship to ocean
		destroyer.placeShipAt(5, 4, false, ocean);

		assertEquals(true, submarine.okToPlaceShipAt(2, 3, true, ocean));
		assertEquals(true, submarine.okToPlaceShipAt(9, 9, true, ocean));
		assertEquals(false, submarine.okToPlaceShipAt(3, 3, true, ocean));
		submarine.placeShipAt(2, 3, false, ocean);
		submarine.placeShipAt(2, 7, true, ocean);
		assertEquals(false, submarine.okToPlaceShipAt(2, 4, true, ocean));
		assertEquals(true, submarine.okToPlaceShipAt(2, 5, false, ocean));
		assertEquals(true, submarine.okToPlaceShipAt(2, 5, true, ocean));
		assertEquals(false, submarine.okToPlaceShipAt(2, 6, true, ocean));
	}

	@Test
	void shootingAtOcean() {
		Ocean ocean = new Ocean();
		Battleship battleship = new Battleship();
		Submarine submarine = new Submarine();

		// Add battleship to seas
		battleship.placeShipAt(8, 2, true, ocean);
		submarine.placeShipAt(2, 3, false, ocean);

		// Shoot at an empty sea location
		assertEquals(false, ocean.isOccupied(1, 1));
		assertEquals(false, ocean.shootAt(1, 1));
		assertEquals(1, ocean.getShotsFired());
		assertEquals(0, ocean.getHitCount());
		assertEquals(0, ocean.getShipsSunk());

		// Shoot at battleship location
		assertEquals(true, ocean.isOccupied(8, 2));
		assertEquals(true, ocean.shootAt(8, 2));
		assertEquals(2, ocean.getShotsFired());
		assertEquals(1, ocean.getHitCount());
		assertEquals(0, ocean.getShipsSunk());

		// Shoot at same location for unsunk ship
		assertEquals(true, ocean.shootAt(8, 2));
		assertEquals(3, ocean.getShotsFired());
		assertEquals(2, ocean.getHitCount());
		assertEquals(0, ocean.getShipsSunk());

		// Shoot at same location for sunk ship
		assertEquals(true, ocean.shootAt(8, 3));
		assertEquals(true, ocean.shootAt(8, 4));
		assertEquals(true, ocean.shootAt(8, 5));
		assertEquals(false, ocean.shootAt(8, 2));
		assertEquals(7, ocean.getShotsFired());
		assertEquals(5, ocean.getHitCount());
		assertEquals(1, ocean.getShipsSunk());

		// Sink another ship
		assertEquals(true, ocean.shootAt(2, 3));
		assertEquals(8, ocean.getShotsFired());
		assertEquals(6, ocean.getHitCount());
		assertEquals(2, ocean.getShipsSunk());
	}

	@Test
	void shipShoot() {
		Ocean ocean = new Ocean();
		Battleship battleship = new Battleship();
		Battleship battleship2 = new Battleship();
		battleship.placeShipAt(8, 2, true, ocean);
		assertEquals(battleship.shootAt(8, 1), false);
		assertEquals(battleship.shootAt(8, 8), false);
		battleship2.placeShipAt(2, 0, false, ocean);
		assertEquals(battleship2.shootAt(1, 0), false);
		assertEquals(battleship2.shootAt(6, 0), false);
		assertEquals(battleship2.shootAt(2, 0), true);
		assertEquals(battleship2.shootAt(3, 0), true);
		assertEquals(battleship2.shootAt(4, 0), true);
		assertEquals(battleship2.shootAt(5, 0), true);
		assertEquals(battleship2.shootAt(5, 0), false);
		
		// Empty Ship tile shootAt method
		EmptySea empty = new EmptySea();
		empty.placeShipAt(0, 0, false, ocean);
		assertEquals(false, empty.shootAt(0, 0));
	}
	
	@Test
	void testIsSunk() {
		Ocean ocean = new Ocean();
		Destroyer destroyer = new Destroyer();
		destroyer.placeShipAt(8, 2, true, ocean);
		assertEquals(false, destroyer.isSunk());
		destroyer.shootAt(8, 2);
		destroyer.shootAt(8, 3);
		destroyer.shootAt(8, 4);
		assertEquals(true, destroyer.isSunk());
		
		// Test isSunk for empty string
		EmptySea empty = new EmptySea();
		empty.placeShipAt(0, 0, false, ocean);
		assertEquals(false, empty.isSunk());
		empty.shootAt(0, 0);
		assertEquals(false, empty.isSunk());
	}
	
	@Test
	void testToString() {
		Ocean ocean = new Ocean();
		Destroyer destroyer = new Destroyer();
		destroyer.placeShipAt(8, 2, true, ocean);
		assertEquals("S", destroyer.toString());
		destroyer.shootAt(8, 2);
		destroyer.shootAt(8, 3);
		destroyer.shootAt(8, 4);
		assertEquals("x", destroyer.toString());
		
		// Test isSunk for empty string
		EmptySea empty = new EmptySea();
		empty.placeShipAt(0, 0, false, ocean);
		assertEquals("S", empty.toString());
		empty.shootAt(0, 0);
		assertEquals("S", empty.toString());
	}

	@Test
	void gameOver() {
		Ocean ocean = new Ocean();
		Submarine submarine = new Submarine();
		Submarine submarine1 = new Submarine();
		Submarine submarine2 = new Submarine();
		Submarine submarine3 = new Submarine();
		Submarine submarine4 = new Submarine();
		Submarine submarine5 = new Submarine();
		Submarine submarine6 = new Submarine();
		Submarine submarine7 = new Submarine();
		Submarine submarine8 = new Submarine();
		Submarine submarine9 = new Submarine();

		// Add battleship to seas
		assertEquals(ocean.isGameOver(), false);
		submarine.placeShipAt(0, 0, false, ocean);
		submarine1.placeShipAt(0, 1, false, ocean);
		submarine2.placeShipAt(0, 2, false, ocean);
		submarine3.placeShipAt(0, 3, false, ocean);
		submarine4.placeShipAt(0, 4, false, ocean);
		submarine5.placeShipAt(0, 5, false, ocean);
		submarine6.placeShipAt(0, 6, false, ocean);
		submarine7.placeShipAt(0, 7, false, ocean);
		submarine8.placeShipAt(0, 8, false, ocean);
		submarine9.placeShipAt(0, 9, false, ocean);
		
		// Test getShipsSunk
		assertEquals(ocean.getShipsSunk(), 0);
		
		ocean.shootAt(0, 0);
		ocean.shootAt(0, 1);
		ocean.shootAt(0, 2);
		ocean.shootAt(0, 3);
		ocean.shootAt(0, 4);
		
		// Test getShipsSunk
		assertEquals(ocean.getShipsSunk(), 5);
		
		ocean.shootAt(0, 5);
		ocean.shootAt(0, 6);
		ocean.shootAt(0, 7);
		ocean.shootAt(0, 8);
		ocean.shootAt(0, 9);
		
		// Test getShipsSunk
		assertEquals(ocean.getShipsSunk(), 10);
		
		assertEquals(ocean.isGameOver(), true);
	}

	@Test
	void placeRandomShips() {
		Ocean ocean = new Ocean();
		ocean.placeAllShipsRandomly();
		int countShips = 0;

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (ocean.isOccupied(i, j)) {
					countShips++;
				}
			}
		}
		assertEquals(countShips, 20);
	}
	
	@Test
	void getShipArray() {
		Ocean ocean = new Ocean();
		Destroyer destroyer = new Destroyer();
		destroyer.placeShipAt(8, 2, true, ocean);
		Ship[][] test = ocean.getShipArray();
		int count = 0;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if(test[i][j].getShipType().equals("empty")) {
					count++;
				}
			}
		}
		assertEquals(count, 98);
	}
	
	@Test
	void getShipType() {
		Ocean ocean = new Ocean();
		Destroyer destroyer = new Destroyer();
		Cruiser cruiser = new Cruiser();
		destroyer.placeShipAt(8, 2, true, ocean);
		cruiser.placeShipAt(0, 0, true, ocean);
		
		assertEquals(ocean.getShipType(8, 2), "Destroyer");
		assertEquals(ocean.getShipType(8, 3), "Destroyer");
		assertEquals(ocean.getShipType(8, 4), "empty");
		assertEquals(ocean.getShipType(0, 0), "Cruiser");
	}

}
