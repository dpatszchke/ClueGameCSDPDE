package experiment;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Board;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;

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
	private HumanPlayer humanPlayer;
	private ComputerPlayer[] computerPlayers;
	private Board board;
	
	public PlayerInterface(Board board) {
		this.board = board;
		humanPlayer = board.gethumanPlayer();
		computerPlayers = board.getComputerPlayers();
		playerTracker = 0;
		
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
		panel.add(nextPlayer);
		makeAccusation = new JButton("Make an accusation");
		panel.add(makeAccusation);
		//return it
		return panel;
	}

}
