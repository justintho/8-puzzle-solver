import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class EightPuzzle {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in); //Used for receiving user input
		
		//Defining variables
		int inputChoice;
		int functionChoice;
		int depth;
		String puzzle = "";
	
		System.out.println("CS 4200 Project 1: 8-Puzzle\n"); //Prints introduction
		
		//Constantly repeats until user selects option 3 to exit program
		while (true) {
			//Determines input type and either generates random maze or creates puzzle from user input
			inputChoice = promptInputType(scan);
			if (inputChoice == 1) {
				depth = promptDepth(scan);
				puzzle = generateRandomPuzzle(depth);
				System.out.println("Puzzle:");
				printPuzzle(puzzle);
			}
			else if (inputChoice == 2) {
				puzzle = promptPuzzleInput(scan);
				System.out.println("Puzzle:");
				printPuzzle(puzzle);
			}
			else {
				System.out.println("Program terminated.");
				System.exit(0);
			}
			
			//If puzzle is not solvable, end program.
			//Else, prompt for h function to be used and solve accordingly.
			if (!solvable(puzzle)) {
				System.out.println("The puzzle is not solvable.\n");
			}
			else {
				System.out.println("The puzzle is solvable.\n");
				functionChoice = promptHFunction(scan);
				solvePuzzle(puzzle, functionChoice);
			}
		}
	}
	
	//*********************************************************************************************
	// Helper Functions
	//*********************************************************************************************

	//Prints the input type prompt
	//Receives input and validates it
	//Returns valid input
	public static int promptInputType(Scanner scan) {
		int choice;
		System.out.println("Select Input Type:");
		System.out.println("[1] Random Input");
		System.out.println("[2] Manual Input");
		System.out.println("[3] Exit");
		System.out.print("Input: ");
		choice = scan.nextInt();
		while(choice != 1 && choice != 2 && choice != 3) {
			System.out.println("Please select option 1, 2, or 3.");
			System.out.print("Input: ");
			choice = scan.nextInt();
		}
		System.out.println();
		return choice;
	}
	
	//Prints the solution depth prompt
	//Receives input and validates it
	//Returns valid input
	public static int promptDepth(Scanner scan) {
		int depth;
		System.out.println("Enter Solution Depth (2-20):");
		System.out.print("Input: ");
		depth = scan.nextInt();
		while(depth < 2 || depth > 20) {
			System.out.println("Please enter a solution depth between 2 and 20.");
			System.out.print("Input: ");
			depth = scan.nextInt();
		}
		System.out.println();
		return depth;
	}
	
	//Prints the H function prompt
	//Receives input and validates it
	//Returns valid input
	public static int promptHFunction(Scanner scan) {
		int choice;
		System.out.println("Select H Function:");
		System.out.println("[1] H1");
		System.out.println("[2] H2");
		System.out.print("Input: ");
		choice = scan.nextInt();
		while(choice != 1 && choice != 2) {
			System.out.println("Please select option 1 or 2.");
			System.out.print("Input: ");
			choice = scan.nextInt();
		}
		System.out.println();
		return choice;
	}
	
	//Prints the puzzle input prompt
	//Receives input and validates it
	//Returns valid input
	public static String promptPuzzleInput(Scanner scan) {
		String puzzle = "";
		System.out.println("Please enter the puzzle, separating each value with a space.");
		System.out.print("Input: ");
		for (int i = 0; i < 9; i++) {
			puzzle += scan.next();
		}
		
		//If invalid puzzle, resets input and prompts user to try again
		while (!checkPuzzle(puzzle)) {
			puzzle = "";
			System.out.println("Please enter the puzzle, separating each value with a space.");
			System.out.print("Input: ");
			for (int i = 0; i < 9; i++) {
				puzzle += scan.next();
			}
		}
		return puzzle;
	}
	
	//Checks if the puzzle is solvable
	public static boolean checkPuzzle(String puzzle) {
		if (puzzle.length() != 9) {
			System.out.println("Please only enter values 0-8.");
			return false;
		}
		
		String[] values = new String[9];
		for(int i = 0; i < 9; i++) {
			values[i] = String.valueOf(puzzle.charAt(i));
		}
		
		Set<String> valueSet = new HashSet<String>();
		for(String i : values) {
			if (valueSet.contains(i)) {
				System.out.println("Repeated values are not allowed!");
				return false;
			}
			if (i.equals("9")) {
				System.out.println("Please only enter values 0-8.");
				return false;
			}
			valueSet.add(i);
		}
		
		return true;
	}
	
	//Counts number of inversions, determines if the puzzle is solvable, and prints results.
	public static boolean solvable(String puzzle) {
		int count = 0;
		for (int i = 0; i < 9; i++) {
			if (puzzle.charAt(i) != '0') {
				for (int j = i; j < 9; j++) {
					if ((int)puzzle.charAt(i) > (int)puzzle.charAt(j) && puzzle.charAt(j) != '0') {
						count++;
					}
				}
			}
		}
		System.out.println("\nTotal Inversions: " + count);
		if (count % 2 == 0)
			return true;
		else 
			return false;
	}
	
	//Generates a random 8 puzzle using the solution depth input
	//Puzzles may be solvable in less steps than the solution depth
	public static String generateRandomPuzzle(int depth) {
		String puzzle = "012345678"; //Starts from goal state
		String newPuzzle = "";
		int position = 0; //Current position of empty tile
		String options = ""; //Possible moves
		Random rand = new Random();
		int choice;
		boolean validPuzzle = false;
		Map<String, Integer> previousStates = new HashMap<String, Integer>();
		previousStates.put(puzzle, position);
		
		for (int i = 0; i < depth; i++) {
			validPuzzle = false;
			options = possibleMoves(position);
			while (!validPuzzle) {
				choice = rand.nextInt(options.length());
				newPuzzle = moveTiles(puzzle, position, Character.getNumericValue(options.charAt(choice)));
				position = Character.getNumericValue(options.charAt(choice));
				if (!previousStates.containsKey(newPuzzle)) {
					previousStates.put(newPuzzle, position);
					puzzle = newPuzzle;
					validPuzzle = true;
				}
				else {
					StringBuilder sb = new StringBuilder(options);
					sb.deleteCharAt(choice);
					options = sb.toString();
					position = findPosition(puzzle, 0);
				}
			}
		}
		return puzzle;
	}
	
	//Moves the blank tile to the indicated position and returns the new puzzle as a String.
	public static String moveTiles(String puzzle, int startPosition, int endPosition) {
		StringBuilder sb = new StringBuilder(puzzle);
		sb.setCharAt(startPosition, puzzle.charAt(endPosition));
		sb.setCharAt(endPosition, puzzle.charAt(startPosition));
		return sb.toString();
	}
	
	//Solves the puzzle step by step, using the appropriate h function
	public static void solvePuzzle(String puzzle, int functionChoice) {
		//Defining variables
		int count  = 0; //Optimal path step counter
		double startTime; //Start time
		double endTime; //End time
		double time = 0; //Total time
		int cost = 0; //Total cost
		int h; 
		int g = 0; 
		int currentPosition; //Position of empty tile
		String moves = "";
		String newPuzzle = "";
		PriorityQueue<Node> frontier = new PriorityQueue<Node>(); //Nodes that can be explored
		Map<String, Node> explored = new HashMap<String, Node>(); //Nodes already explored
		Node parent = null; //Previous node
		Node selected = null; //Expanded node
		
		//Records starting time
		startTime = System.nanoTime();
		
		//Computing h value and expanding root node
		h = chooseHFunction(puzzle, functionChoice);
		explored.put(puzzle, new Node(parent, h, g, puzzle));
		parent = explored.get(puzzle); //next node will be a child of root node
		
		//Adds cost of adding root node to frontier
		if(h > 0)
			cost++;
		
		//Repeat until h value is 0
		while(h > 0) {
			//Locates blank tile and determines possible moves
			currentPosition = findPosition(puzzle, 0);
			moves = possibleMoves(currentPosition);
			
			//Computing h and g values and recording the states in frontier
			for (int i = 0; i < moves.length(); i++) {
				newPuzzle = moveTiles(puzzle, currentPosition, Character.getNumericValue(moves.charAt(i)));
				h = chooseHFunction(newPuzzle, functionChoice);
				if (!explored.containsKey(newPuzzle)) {
					frontier.add(new Node(parent, h, parent.getG() + 1, newPuzzle));
					cost++;
				}
				currentPosition = findPosition(puzzle, 0); //revert position back for next iteration
			}
			
			//Expands lowest cost node in frontier
			selected = frontier.poll();
			puzzle = selected.getPuzzle();
			parent = selected.getParent();
			h = selected.getH();
			g = selected.getG();
			explored.put(puzzle, new Node(parent, h, g, puzzle));
			
			//Sets next node as a child of currently selected node
			parent = explored.get(puzzle);
		}
		
		//Recording end time and computing total time
		endTime = System.nanoTime();
		time = endTime - startTime;
		time /= (double)1000000; //converting from nanoseconds(ns) to milliseconds (ms)
		
		//Starts from goal state and adds each parent to beginning of linked list
		LinkedList<Node> path = new LinkedList<Node>();
		while (selected != null) {
			path.addFirst(selected);
			selected = selected.getParent();
		}
		
		//Prints the original puzzle (root)
		if (!path.isEmpty()) {
			selected = path.poll();
			System.out.println("-------------------------------------------------------");
			System.out.println("Puzzle:");
			printPuzzle(selected.getPuzzle());
			System.out.println();
		}
		
		//Prints the optimal steps to solve the puzzle
		while (!path.isEmpty()) {
			count++;
			System.out.println("Step " + count + ":");
			selected = path.poll();
			printPuzzle(selected.getPuzzle());
			System.out.println();
		}
		
		//Prints the results
		System.out.println("Puzzle Solved!");
		System.out.println("-------------------------------------------------------");
		System.out.println("Time: " + time + " ms");
		System.out.println("Cost: " + cost);
		System.out.println();
		
	}
	
	//Counts the number of misplaced tiles in the puzzle for the h1 function
	public static int h1Counter(String puzzle) {
		int misplacedTiles = 0;
		
		for (int i = 0; i < 9; i++) {
			if (Character.getNumericValue(puzzle.charAt(i)) != i && Character.getNumericValue(puzzle.charAt(i)) != 0)
				misplacedTiles++;
		}
		
		return misplacedTiles;
	}
	
	//Computes to total distance from the goal state of the the puzzle for the h2 function
	public static int h2Distance(String puzzle) {
		int totalDistance = 0;
		int horiDistance = 0;
		int vertDistance = 0;
		int valuePosition = 0;
		
		//Iterate from 1-8, since 0 is not part of calculation
		for (int i = 1; i < 9; i++) {
			//Find position of value in puzzle
			valuePosition = findPosition(puzzle, i);
			
			//Calculate horizontal distance
			if (i % 3 == valuePosition % 3)
				horiDistance += 0;
			else
				horiDistance += Math.abs((i % 3) - valuePosition % 3);
			
			//Calculate vertical distance
			if (i / 3 == valuePosition / 3)
				vertDistance += 0;
			else
				vertDistance += Math.abs((i / 3) - valuePosition / 3);
		}
		
		//Compute sum of distances
		totalDistance = horiDistance + vertDistance;
		
		return totalDistance;
	}
	
	//Determines which h function to use and computes accordingly
	public static int chooseHFunction(String puzzle, int functionChoice) {
		if (functionChoice == 1) {
			return h1Counter(puzzle);
		}
		else {
			return h2Distance(puzzle);
		}
	}
	
	//Searches for the position of the empty tile and returns the position as an int
	public static int findPosition(String puzzle, int target) {	
		int position = 0;
		for (int i = 0; i < 9; i++) {
			if (Character.getNumericValue(puzzle.charAt(i)) == target) {
				position = i;
				break;
			}
		}
		return position;
	}
	
	//Returns the possible moves from the current position
	//Assumes that positions are labeled like below:
	// 0 1 2
	// 3 4 5
	// 6 7 8
	//Ex. - Position 0 will have moves "13", meaning it can swap with positions 1 or 3.
	public static String possibleMoves(int position) {
		String moves = "";
		
		switch(position) {
			case 0:
				moves = "13";
				break;
			case 1:
				moves = "024";
				break;
			case 2:
				moves = "15";
				break;
			case 3:
				moves = "046";
				break;
			case 4:
				moves = "1357";
				break;
			case 5:
				moves = "248";
				break;
			case 6:
				moves = "37";
				break;
			case 7:
				moves = "468";
				break;
			default:
				moves = "57";
				break;
		}
		
		return moves;
	}
	
	//Prints 8 puzzle in a formatted manner
	public static void printPuzzle(String puzzle) {
		for (int i = 0; i < 9; i++) {
			System.out.print(puzzle.charAt(i));
			if (i < 8) {
				System.out.print(" ");
				if ((i + 1) % 3 == 0 && i != 0) {
					System.out.println();
				}
			}
		}
		System.out.println();
	}
}
