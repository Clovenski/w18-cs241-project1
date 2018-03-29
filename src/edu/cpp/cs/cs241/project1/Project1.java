package edu.cpp.cs.cs241.project1;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

/*
 * This class contains the main method that starts the program. Its main purpose
 * is to implement the class BinarySearchTree. The program itself has the user provide
 * initial values (integers) for the binary search tree. It then provides information
 * for the newly created tree and gives the user the option to insert a new value to
 * the tree, delete a value, find node predecessors and successors, and exit the program.
 */
public class Project1 {
	// Scanner object to retrieve user input.
	private Scanner input;
	// True if and only if the user opts to exit the program, false otherwise.
	private boolean done;
	// Tree that this program will implement.
	private BinarySearchTree tree;
	
	// Default constructor that assures the program is ready to start.
	public Project1() {
		input = new Scanner(System.in);
		tree = new BinarySearchTree();
	}
	
	/*
	 * A call to this method is how the program is started. It handles the
	 * entirety of the interface for the user. Note that this method will
	 * not return unless the user opts to exit the program with a command
	 * of 'E', case-insensitive, when prompted for a command.
	 */
	public void start() {
		// Will contain the entire line of the user's input.
		StringTokenizer tokenizer;
		// Will hold the user's input if it was an integer.
		int integerInput;
		// Will contain the user's command when they were prompted for one.
		String userCommand;
		done = false;
		
		System.out.println("// Binary Search Tree //");
		System.out.println("Please enter the initial values in sequence, separated with spaces:");
		System.out.println("(Non-integers and duplicates will be ignored)");
		System.out.print("> ");
		
		// Get the user's input.
		tokenizer = new StringTokenizer(input.nextLine());
		
		/*
		 * Until there are no more tokens, every token is parsed into
		 * an integer and added into the tree unless an exception or
		 * duplicate is encountered.
		 */
		while(tokenizer.hasMoreTokens()) {
			try {
				integerInput = Integer.parseInt(tokenizer.nextToken());
				tree.addNewNode(integerInput);
			} catch(NumberFormatException nfe) {
				continue;
			} catch(Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
		}
		
		// Newly created tree information.
		System.out.println("Pre-order: " + tree.getPreOrderSequence());
		System.out.println("In-order: " + tree.getInOrderSequence());
		System.out.println("Post-order: " + tree.getPostOrderSequence());
		
		// Initially display the options for the user.
		displayOptions();
		
		// Main program loop to implement those options.
		do {
			System.out.print("Command? ");
			tokenizer = new StringTokenizer(input.nextLine());
			
			// True if the user enters nothing, only pressing [ENTER].
			if(tokenizer.countTokens() == 0)
				continue;
			
			userCommand = tokenizer.nextToken();
			
			try {
				switch(userCommand.toUpperCase()) {
				case "I":
					// Insert a new value into the tree.
					integerInput = Integer.parseInt(tokenizer.nextToken());
					insertCommand(integerInput);
					break;
				case "D":
					// Delete a value from the tree.
					integerInput = Integer.parseInt(tokenizer.nextToken());
					deleteCommand(integerInput);
					break;
				case "P":
					// Find the predecessor to a node in the tree.
					integerInput = Integer.parseInt(tokenizer.nextToken());
					predecessorCommand(integerInput);
					break;
				case "S":
					// Find the successor to a node in the tree.
					integerInput = Integer.parseInt(tokenizer.nextToken());
					successorCommand(integerInput);
					break;
				case "E":
					// Exit the program.
					done = true;
					break;
				case "H":
					// Help the user by displaying their available options.
					displayOptions();
					break;
				default:
					// Unrecognized command given by the user.
					throw new IllegalArgumentException("Invalid command.");
				}
			} catch(NoSuchElementException nsee) {
				System.err.println("Error: Please enter a value for this command.");
			} catch(NumberFormatException nfe) {
				System.err.println("Error: Please enter an integer.");
			} catch(IllegalArgumentException iae) {
				System.err.println("Error: " + iae.getMessage());
			} catch(Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
		} while(!done);
		
		// User opted to exit the program.
		System.out.println("// PROGRAM EXITED //");
	}
	
	// Display the options for the user, with an appropriate description for each of them.
	private void displayOptions() {
		System.out.println("Command List:");
		System.out.printf(" %-5s - %s%n", "I [N]", "Insert the value, N, into the tree");
		System.out.printf(" %-5s - %s%n", "D [N]", "Delete the value, N, from the tree");
		System.out.printf(" %-5s - %s%n", "P [N]", "Find the predecessor to the node with value, N");
		System.out.printf(" %-5s - %s%n", "S [N]", "Find the successor to the node with value, N");
		System.out.printf(" %-5s - %s%n", "E", "Exit the program");
		System.out.printf(" %-5s - %s%n", "H", "Display this list of options");
	}
	
	/*
	 * This method is called when the user opts to insert a value into the tree.
	 * The newValue parameter contains the desired integer value to be added into
	 * the tree. An IllegalArgumentException with an appropriate message is thrown
	 * when the desired value is 0 or the tree already contains the desired value.
	 * Otherwise the new value is added into the tree and the tree's in-order
	 * sequence is then printed.
	 */
	private void insertCommand(int newValue) throws IllegalArgumentException {
		if(newValue == 0)
			throw new IllegalArgumentException("Cannot add a zero value into the tree.");
		
		if(tree.contains(newValue))
			throw new IllegalArgumentException(newValue + " already exists in this tree.");
		
		tree.addNewNode(newValue);
		System.out.println("In-order: " + tree.getInOrderSequence());
	}
	
	/*
	 * This method is called when the user opts to delete a value from the tree.
	 * An IllegalArgumentException with an appropriate message is thrown when the
	 * target value does not exist within the tree or the tree itself is empty.
	 * Otherwise the value is removed from the tree and the tree's in-order
	 * sequence is then printed.
	 */
	private void deleteCommand(int targetValue) throws IllegalArgumentException {
		if(tree.isEmpty())
			throw new IllegalArgumentException("This tree is empty.");
		
		if(tree.contains(targetValue)) {
			tree.removeNode(targetValue);
			System.out.println("In-order: " + tree.getInOrderSequence());
		} else
			throw new IllegalArgumentException(targetValue + " does not exist in this tree.");
	}
	
	/*
	 * This method is called when the user opts to find the predecessor node of some other
	 * node within the tree with the given value according to the tree's in-order sequence.
	 * An IllegalArgumentException with an appropriate message is thrown when either the target
	 * value does not exist in the tree, the tree itself is empty or the node containing the
	 * target value has no predecessor. Otherwise the predecessor node's value is printed.
	 */
	private void predecessorCommand(int targetValue) throws IllegalArgumentException {
		int predecessorData;
		
		if(tree.isEmpty())
			throw new IllegalArgumentException("This tree is empty.");
		
		if(tree.contains(targetValue)) {
			predecessorData = tree.getNodePredecessor(targetValue);
			if(predecessorData == 0)
				System.out.println(targetValue + " has no predecessor.");
			else
				System.out.println(predecessorData);
		} else
			throw new IllegalArgumentException(targetValue + " does not exist in this tree.");
	}
	
	/*
	 * This method is called when the user opts to find the successor node of some other
	 * node within the tree with the given value according to the tree's in-order sequence.
	 * An IllegalArgumentException with an appropriate message is thrown when either the
	 * target value does not exist in the tree, the tree itself is empty or the node containing
	 * the target value has no successor. Otherwise the successor node's value is printed.
	 */
	private void successorCommand(int targetValue) throws IllegalArgumentException {
		int successorData;
		
		if(tree.isEmpty())
			throw new IllegalArgumentException("This tree is empty.");
		
		if(tree.contains(targetValue)) {
			successorData = tree.getNodeSuccessor(targetValue);
			if(successorData == 0)
				System.out.println(targetValue + " has no successor.");
			else
				System.out.println(successorData);
		} else
			throw new IllegalArgumentException(targetValue + " does not exist in this tree.");
	}
	
	// Main method for the program. Creates a new Project1 object and calls its start method.
	public static void main(String[] args) {
		Project1 program = new Project1();
		program.start();
	}

}
