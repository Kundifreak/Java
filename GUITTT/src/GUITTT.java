//Author: Parth Nimbalkar


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUITTT implements ActionListener {

	JFrame frame = new JFrame();
	JButton[][] button = new JButton[3][3];
	int[][] board = new int[3][3];
	final int BLANK = 0;
	final int X_MOVE = 1;
	final int O_MOVE = 2;
	final int X_TURN = 0;
	final int O_TURN = 1;
	int turn = X_TURN;
	Container center  = new Container(); 
	JLabel xname = new JLabel("X wins:0");
	JLabel oname = new JLabel("O wins:0");
	JButton xChangeName = new JButton("Change X's Name.");
	JButton oChangeName = new JButton("Change O's Name.");
	JTextField xChangeField = new JTextField();
	JTextField oChangeField = new JTextField();
	Container north = new Container ();
	String xPlayerName = "X";
	String oPlayerName = "O";
	int xwins = 0;
	int owins = 0;
	
	public GUITTT() {
		frame.setSize(400,400);
		//Center grid container
		frame.setLayout(new BorderLayout());
		center.setLayout(new GridLayout(3,3));
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				button[j][i] = new JButton(j+","+i);
				center.add(button[j][i]);
				button[j][i].addActionListener(this);
			}
		}
		frame.add(center, BorderLayout.CENTER);
		//North container
		north.setLayout(new GridLayout(3,2));
		north.add(xname);
		north.add(oname);
		north.add(xChangeName);
		xChangeName.addActionListener(this);
		north.add(oChangeName);
		oChangeName.addActionListener(this);
		north.add(xChangeField);
		north.add(oChangeField);
		frame.add(north, BorderLayout.NORTH);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
	new GUITTT();

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JButton current;
		boolean gridButton = false;
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				if (event.getSource().equals(button[j][i])) {
					gridButton = true;
					current = button[j][i];
					if (board[j][i]==BLANK) {
						if (turn == X_TURN) {
							current.setText("X");
							current.setEnabled(false);
							board[j][i]=X_MOVE;
							turn = O_TURN;
						}
						else {
							current.setText("O");
							current.setEnabled(false);
							board[j][i] = O_MOVE;
							turn = X_TURN;
						}
						//check for wins and ties
						if (checkWin(X_MOVE)== true) {
							//X wins
							xwins++;
							xname.setText(xPlayerName + " wins:" + xwins );
							clearBoard();
						}
						else if (checkWin(O_MOVE)== true) {
							//o wins
							owins++;
							oname.setText(oPlayerName + " wins:" + owins );
							clearBoard();
						}
						else if (checkTie()== true) {
							//tie
							
							System.out.println("It is a tie !");
							clearBoard();
						}
					}
				}	
			}
		}
		if (gridButton == false) {						//If we click the button and their is no number then it will set to default X or O
			if (event.getSource().equals(xChangeName)== true){
				xPlayerName = xChangeField.getText();
				
				if (xPlayerName.equals("")) {
					xPlayerName = "X";
			}
				xname.setText(xPlayerName + " wins:" + xwins );
			}
			
				else if (event.getSource().equals(oChangeName)== true) {
				oPlayerName = oChangeField.getText();
				
				if (oPlayerName.equals("")) {
					oPlayerName = "O";
			}
				oname.setText(oPlayerName + " wins:" + owins );
			}
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
	

	} //checks tie
	public boolean checkTie() {
		for (int row = 0; row < board.length; row++) {
			for (int column = 0; column < board.length; column++) {
				if (board[row][column] == BLANK) {
					return false;
				}
			}
		}
		return true;
		
	}
	public void clearBoard() {						//  reset board and then enables buttons
		for (int a = 0; a < board.length; a++) {
			for (int b = 0; b < board.length; b ++) {
				board[a][b]=BLANK;
				button[a][b].setText("");
				button[a][b].setEnabled(true);
				// enable buttons
			}
		}
		turn = X_TURN;
	}
}