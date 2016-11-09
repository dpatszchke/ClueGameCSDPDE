package clueGame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.Set;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveGUI extends JDialog {
	private Board board;
	public DetectiveGUI(Board board) {
		this.board = board;
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle("Detective Notes");
		setSize(500, 700);
		setLayout(new GridLayout(3,2));
		
		
		add(getPeoplePanel(board.getCards(CardType.PERSON)));
		add(getPeoplePanelDrop(board.getCards(CardType.PERSON)));
		add(getRoomPanel(board.getCards(CardType.ROOM)));
		add(getRoomPanelDrop(board.getCards(CardType.ROOM)));
		add(getWeaponPanel(board.getCards(CardType.WEAPON)));
		add(getWeaponPanelDrop(board.getCards(CardType.WEAPON)));
		
		
	}

	private JPanel getWeaponPanelDrop(String[] array) {
		JPanel retVal = new JPanel();
		retVal.setLayout(new GridLayout(0,2));
		for(String thing: array) {
			JCheckBox check = new JCheckBox(thing);
			retVal.add(check);
		}
		retVal.setBorder(new TitledBorder(new EtchedBorder(), "Weapon"));
		return retVal;
	}

	private JPanel getRoomPanelDrop(String[] array) {
		JPanel retVal = new JPanel();
		retVal.setLayout(new GridLayout(0,2));
		for(String thing: array) {
			JCheckBox check = new JCheckBox(thing);
			retVal.add(check);
		}
		retVal.setBorder(new TitledBorder(new EtchedBorder(), "Room"));
		return retVal;
	}

	private JPanel getPeoplePanelDrop(String[] array) {
		JPanel retVal = new JPanel();
		retVal.setLayout(new GridLayout(0,2));
		for(String thing: array) {
			JCheckBox check = new JCheckBox(thing);
			retVal.add(check);
		}
		retVal.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		return retVal;
	}
	
	private JPanel getWeaponPanel(String[] array) {
		JPanel retVal = new JPanel();
		JComboBox<String> guessBox = new JComboBox<>(array);
		retVal.add(guessBox);
		retVal.setBorder(new TitledBorder(new EtchedBorder(), "Weapon Guess"));
		return retVal;
	}

	private JPanel getRoomPanel(String[] array) {
		JPanel retVal = new JPanel();
		JComboBox<String> guessBox = new JComboBox<>(array);
		retVal.add(guessBox);
		retVal.setBorder(new TitledBorder(new EtchedBorder(), "Room Guess"));
		return retVal;
	}

	private JPanel getPeoplePanel(String[] array) {
		JPanel retVal = new JPanel();
		JComboBox<String> guessBox = new JComboBox<>(array);
		retVal.add(guessBox);
		retVal.setBorder(new TitledBorder(new EtchedBorder(), "People Guess"));
		return retVal;
	}


	
	
}
