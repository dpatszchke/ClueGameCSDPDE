package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import experiment.PlayerInterface;

public class ClueGame extends JFrame {
	private DetectiveGUI detectiveNotes;
	private boolean toggle;
	private static PlayerInterface playerI;
	
	
	public ClueGame(Board board) {
		toggle = false;
		//basic defaults
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue Game");
		setSize(900, 900);
		//additions
		add(board, BorderLayout.CENTER);
		add(playerCards(board), BorderLayout.EAST);
		playerI = new PlayerInterface(board);
		add(playerI, BorderLayout.SOUTH);
		JMenuBar file = new JMenuBar();
		setJMenuBar(file);
		file.add(createMenu());
		//let there be a game
		setVisible(true);
		detectiveNotes = new DetectiveGUI(board);
		
	}
	
	
	public JPanel playerCards(Board board) {
		HumanPlayer hp = board.gethumanPlayer();
		ArrayList<Card> hand = hp.getPlayerCards();
		
		JPanel cards = new JPanel();
		cards.setLayout(new GridLayout(3, 1));
	    TitledBorder title = BorderFactory.createTitledBorder(
	      BorderFactory.createEtchedBorder(0), "Hand");
	    title.setTitleJustification(3);
	    cards.setBorder(title);
	    
	    JPanel personpanel = new JPanel();
	    personpanel.setLayout(new GridLayout(0, 1));
	    personpanel.setBorder(new TitledBorder(new EtchedBorder(), "Person"));
	    for(Card card : hand) {
	    	if(card.getCardType() == CardType.PERSON) {
	    		JTextField persontf = new JTextField(card.getCardName());
	    	    personpanel.add(persontf, BorderLayout.CENTER);
	    	}
	    }
	    
	    JPanel roompanel = new JPanel();
	    roompanel.setLayout(new GridLayout(0, 1));
	    roompanel.setBorder(new TitledBorder(new EtchedBorder(), "Room"));
	    for(Card card : hand) {
	    	if(card.getCardType() == CardType.ROOM) {
	    		JTextField roomtf = new JTextField(card.getCardName());
	    	    roompanel.add(roomtf, BorderLayout.CENTER);
	    	}
	    }
	    
	    JPanel weaponpanel = new JPanel();
	    weaponpanel.setLayout(new GridLayout(0, 1));
	    weaponpanel.setBorder(new TitledBorder(new EtchedBorder(), "Weapon"));
	    for(Card card : hand) {
	    	if(card.getCardType() == CardType.WEAPON) {
	    		JTextField weapontf = new JTextField(card.getCardName());
	    	    weaponpanel.add(weapontf, BorderLayout.CENTER);
	    	}
	    }
	    
	    cards.add(personpanel);
	    cards.add(weaponpanel);
	    cards.add(roompanel);
		return cards;
	}
	
	public JMenu createMenu() {
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(createClose());
		fileMenu.add(createNotes());
		return fileMenu;
	}
	
	public JMenuItem createClose() {
		JMenuItem close = new JMenuItem("Close");
		class CloseListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		close.addActionListener(new CloseListener());
		return close;
	}
	
	public JMenuItem createNotes() {
		JMenuItem notes = new JMenuItem("Toggle Detective Notes");
		class OpenListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				toggle = !toggle;
				detectiveNotes.setVisible(toggle);
			}
		}
		notes.addActionListener(new OpenListener());
		return notes;
	}
	
	
	public static void main(String[] args) {
		Board board = new Board();
		board.setConfigFiles("TCJPClueLayout.csv", "TCJPClueLayoutLegend.txt", "ClueCards.txt", "Players.txt");
		board.initialize();
		ClueGame boardGUI = new ClueGame(board);
		HumanPlayer hp = board.gethumanPlayer();
		String name = hp.getPlayerName();
		String passName = "You are " + name + ", press OK to begin play!";
		JOptionPane.showMessageDialog(boardGUI, passName, "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);

	}

}
