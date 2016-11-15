package experiment;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Board;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;

public class PlayerInterface extends JPanel{
	//Items in layout
	private JButton nextPlayer;
	private JButton makeAccusation;
	private JTextField whoseTurn;
	private JTextField rollField;
	private JTextField guessField;
	private JTextField responseField;
	
	//private vars for players
	private int playerTracker;
	private Player player;
	private HumanPlayer humanPlayer;
	private ComputerPlayer[] computerPlayers;
	private Board board;
	
	//Die
	private Random dice = new Random();
	
	public PlayerInterface(Board board) {
		this.board = board;
		humanPlayer = board.gethumanPlayer();
		computerPlayers = board.getComputerPlayers();
		playerTracker = computerPlayers.length - 1;
		
		setLayout(new GridLayout(2,0));
		createLayout();
	}
	
	private void createLayout() {
		JPanel panel = new JPanel();
		panel.add(panelTop());
		panel.add(panelBottom());
		
		add(panel);
	}

	private JPanel panelBottom() {
		//Roll
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Roll");
		rollField = new JTextField(3);
		rollField.setEditable(false);
		JPanel rollPanel = new JPanel();
		rollPanel.setLayout(new GridLayout(2, 0));
		rollPanel.add(label);
		rollPanel.add(rollField);
		rollPanel.setBorder(new TitledBorder(new EtchedBorder(), "Die"));
		panel.add(rollPanel);
		//Guess
		label = new JLabel("Guess");
		guessField = new JTextField(28);
		guessField.setEditable(false);
		JPanel guessPanel = new JPanel();
		guessPanel.setLayout(new GridLayout(2, 0));
		guessPanel.add(label);
		guessPanel.add(guessField);
		guessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		panel.add(guessPanel);
		//Response
		label = new JLabel("Guess Result");
		responseField = new JTextField(12);
		responseField.setEditable(false);
		JPanel responsePanel = new JPanel();
		responsePanel.setLayout(new GridLayout(2, 0));
		responsePanel.add(label);
		responsePanel.add(responseField);
		responsePanel.setBorder(new TitledBorder(new EtchedBorder(), "Response"));
		panel.add(responsePanel);
		return panel;
	}

	private JPanel panelTop() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,0));
		//ButtonListener listener = new ButtonListener(null);
		//Whose Turn Things
		JPanel whoseTurnPanel = new JPanel();
		JLabel whoseLabel = new JLabel("Whose Turn?");
		whoseTurnPanel.add(whoseLabel);
		whoseTurn = new JTextField(16);
		whoseTurnPanel.add(whoseTurn);
		panel.add(whoseTurnPanel);
		//buttons
		nextPlayer = new JButton("Next Player");
		nextPlayer.addActionListener(new ButtonListener());
		panel.add(nextPlayer);
		makeAccusation = new JButton("Make an accusation");
		panel.add(makeAccusation);
		//return it
		return panel;
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == PlayerInterface.this.nextPlayer) {
				nextClicked();
			} else if (e.getSource() == PlayerInterface.this.makeAccusation) {
				makeClicked();
			}
		}
		private ButtonListener() {
		}
	}

	public void nextClicked() {
		String emptyInitial = "";
		guessField.setText(emptyInitial);
		responseField.setText(emptyInitial);
		//setting playerTracker
		if(playerTracker < computerPlayers.length) {
			playerTracker++;
		} else {
			if(humanPlayer.stillTakingTurn()) {
				JOptionPane.showMessageDialog(null, "Please Finish Your Turn!");
				return;
			}
			playerTracker = 0;
			
		}
		//set player
		if(playerTracker == computerPlayers.length) {
			player = humanPlayer;
		} else {
			player = computerPlayers[playerTracker];
		}
		//dicing it up
		whoseTurn.setText(player.getPlayerName());
		int diceRoll = dice.nextInt(5) + 1;
		rollField.setText(String.valueOf(diceRoll));
		
		board.unHighlight();
		board.calcTargets(player.getRow(), player.getColumn(), diceRoll);
		player.move(board);
		
		
		
		
	}
	//unhighlight
	//move
	public void makeClicked() {

	}
	
	
	
	

}
