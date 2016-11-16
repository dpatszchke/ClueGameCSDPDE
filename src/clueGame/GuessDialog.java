package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JTextField;

import experiment.PlayerInterface;

public class GuessDialog extends JDialog {
	//var decs
	private JComboBox people;
	private JComboBox weapons;
	private JComboBox rooms;
	private JButton submitButton;
	private JButton cancelButton;
	
	
	private boolean submit;
	private String playerRoom;
	
	
	public GuessDialog(Board board, String currentRoom) {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle("Make a Guess");
		setSize(300, 300);
		//add the buttons and the other things, this is a 4*2
		setLayout(new GridLayout(4, 2));
		people = new JComboBox();
		rooms = new JComboBox();
		weapons = new JComboBox();
		
		//split the deck here into sets of cards by cardType()
		int counter;
		String[] weaponsList = board.getCards(CardType.WEAPON);
		String[] roomList = board.getCards(CardType.ROOM);
		String[] peopleList = board.getCards(CardType.PERSON);
		
		
		if(currentRoom != null) {
			//in this conditional, we make a jtextfield for rooms instead of a jcombo
			playerRoom = currentRoom;
			JLabel roomLabel = new JLabel("Room");
			
			JTextField roomTF = new JTextField(playerRoom);
			roomTF.setEditable(false);
			
			add(roomLabel);
			add(roomTF);
			
		} else {
			//jcombobox in here for rooms
			counter = board.getNumberOfRoomCards();
			for (int i = 0; i < counter; i++) {
				rooms.addItem(roomList[i]);
			}
			JLabel roomLabel = new JLabel("Room");
			add(roomLabel);
			add(rooms);
		}
		// Combobox for weapons
		counter = board.getNumberOfWeaponCards();
		for (int i = 0; i < counter; i++) {
			weapons.addItem(weaponsList[i]);
		}
		
		// Combobox for people
		counter = board.getNumberOfPersonCards();
		for (int i = 0; i < counter; i++) {
			people.addItem(peopleList[i]);
		}
		
		JLabel weaponLabel = new JLabel("Weapon");
		JLabel personLabel = new JLabel("Person");
		add(weaponLabel);
		add(weapons);
		add(personLabel);
		add(people);
		
		setButtons();
		
		setVisible(false);
	}
	
	public void setButtons() {
		submitButton = new JButton("Submit");
		cancelButton = new JButton("Cancel");
		
		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == GuessDialog.this.submitButton) {
					submit = true;
				} else if (e.getSource() == GuessDialog.this.cancelButton) {
					submit = false;
				}
				GuessDialog.this.setVisible(false);
			}
			public ButtonListener() {
			}
		}
		
		ButtonListener buttonListener = new ButtonListener();
		submitButton.addActionListener(buttonListener);
		cancelButton.addActionListener(buttonListener);
		add(submitButton);
		add(cancelButton);
	}
	
	public String getRoom() {
		return rooms.getSelectedItem().toString();
	}
	
	public String getWeapon() {
		return weapons.getSelectedItem().toString();
	}
	
	public String getPerson() {
		return people.getSelectedItem().toString();
	}
	
	public boolean getSubmit() {
		return submit;
	}
}
