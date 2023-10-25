//Author: Parth Nimbalkar
//Date:9/26/2023
//Game:TicTacToe

import java.util.Scanner;

public class TicTacToe {

	int[][] board = new int[3][3];
	final int BLANK = 0;
	final int X_MOVE = 1;
	final int O_MOVE = 2;
	final int X_TURN = 0;
	final int O_TURN = 1;
	int turn = X_TURN;
	
	Scanner scanner;
	String input=""; 
	
	public TicTacToe() {
		scanner = new Scanner(System.in);
		
		int NO_X_MOVE = 0;
		int NO_O_MOVE = 0;
		int Tie = 0;
		
		boolean stillPlaying = true;
		while (stillPlaying == true) {
		while ((checkWin(X_MOVE) == false) && (checkWin(O_MOVE)== false) && (checkTie()== false)) {		//runs the game if x or o didn't win or didn't tie
			printBoard(); 
			input = scanner.nextLine();
		
		
		
		if (input.length()!= 2) {					// If the row and column is not within a,b,c and 1,2,3 the it will print out a string saying it should one of the 3 letters and numbers combination
			System.out.println("Enter a letter followed by a number");
		}
		else if (input.charAt(0)!= 'a' && 		//sets up the rows 
				input.charAt(0)!= 'b' && 
				input.charAt(0)!= 'c' ) {
			System.out.println("Row must be an a, b, or c.");
		}
		else if (input.charAt(1)!='1' && 		//sets up the columns
				input.charAt(1)!= '2' && 
				input.charAt(1)!= '3' ) {
			System.out.println("Column must be an 1, 2, or 3.");
		}
		else {
			int row = input.charAt(0) - 'a';
			int column = input.charAt(1) - '1';
			if (board[row][column]==BLANK) {
				if (turn ==X_TURN) {
				board[row][column] = X_MOVE;
				turn = O_TURN;
			}
				else {
					board [row][column] = O_MOVE;
					turn = X_TURN;
				}
			}
			else {
				System.out.println("There is a piece there!");
			}
	    }	
	}
			
		if (checkWin(X_MOVE)==true) {		// this is the code for tracking our wins. If X or O wins then it there will be and increment of +1 to the variable that shows our wins
			printBoard();
			System.out.println("X wins!	");
			NO_X_MOVE += 1;
			System.out.println("Number of wins for X:"); 
			System.out.println(NO_X_MOVE);
			System.out.println("Do better O !");
			
		}
		else if (checkWin(O_MOVE)==true) {
			printBoard();
			System.out.println("O wins!	");
			NO_O_MOVE += 1;
			System.out.println("Number of wins for O:");
			System.out.println(NO_O_MOVE);
			System.out.println("Do better X !");
				
		}
		else if (checkTie()== true) {		// this is for checking the tie. If the game ties then the Tie variable will show the numbers of ties
			printBoard();
			System.out.println("It is a tie !");
			Tie += 1;
			System.out.println("Number of ties: ");
			System.out.println(Tie);
			System.out.println("Finally... A worthy opponent...Our battle will be legendary!!");
		}
		afterwin();
		}
}
	public static void main(String[] args) {
		new TicTacToe();
	}
	
	public void printBoard() {		//prints the board at the end and after someone's win or tie
		System.out.println(" \t1\t2\t3");
		for(int row= 0; row < board.length; row++) {
			String output = (char)('a'+row)+"\t";
			for (int column = 0; column < board[0].length; column++ ) {
				if (board[row][column]==BLANK) {
					output += " \t";
				}
				else if (board[row][column]== X_MOVE){
					output += "X\t";
				
				}
				else if (board[row][column]==O_MOVE) {
					output += "O\t";
				}
			}
				
			System.out.println(output);
		}
	}
	
	public boolean checkWin(int player) {		// this are all the possible sequences of a win in the game. If none these sequences were used then the game will tie. 
		if (board[0][0] == player && board[0][1] == player && board[0][2] == player) {
			return true;
		}
		if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
			return true;
		}
		if (board[2][0] == player && board[2][1] == player && board[2][2] == player) {
			return true;
		}
		if (board[2][0] == player && board[1][1] == player && board[0][2] == player) {
			return true;
		}
		if (board[0][1] == player && board[1][1] == player && board[2][1] == player) {
			return true;
		}
		if (board[0][2] == player && board[1][2] == player && board[2][2] == player) {
			return true;
		}
		if (board[0][0] == player && board[1][0] == player && board[2][0] == player) {
			return true;
		}
		if (board[1][0] == player && board[1][1] == player && board[1][2] == player) {
			return true;
		}
		return false;
	}
	
	public boolean checkTie() { //checks if it ties
		for (int row = 0; row < board.length; row++) {
			for (int column = 0; column < board.length; column++) {
				if (board[row][column] == BLANK) {
					return false;
				}
			}
		}
		return true;
	}

	public void afterwin() { //checks if someone wins
		for (int row = 0; row < board.length; row++) {
			for (int column = 0; column < board[0].length; column++) {
				board[row][column] = BLANK;
			}
		}
	}
}	
