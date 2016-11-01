package experiment;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PlayerInterface extends JFrame{

	public PlayerInterface() {
		setSize(new Dimension(700,100));
		setTitle("");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createLayout();
	}
	
	private void createLayout() {
		JPanel panelTop = panelTop();
		add(panelTop, BorderLayout.NORTH);
		JPanel panelBottom = panelBottom();
		add(panelBottom, BorderLayout.SOUTH);
	}
	
	private JPanel panelTop(){
		JPanel panel = new JPanel();
		JLabel turn = new JLabel("Who's Turn?");
		JTextField name = new JTextField(20);
		panel.add(turn);
		panel.add(name);
		JButton nextPlayer = new JButton("Next Player");
		JButton accusation = new JButton("Make an Accusation");
		panel.add(nextPlayer);
		panel.add(accusation);
		//nextPlayer.actionListener();
		
		return panel;
	}
	
	private JPanel panelBottom() {
		JPanel panel = new JPanel();
		JLabel die = new JLabel("Die");
		JTextField move = new JTextField(3);
		panel.add(die);
		panel.add(move);
		JLabel guess = new JLabel("Guess");
		JTextField dropdown = new JTextField(22);
		panel.add(guess);
		panel.add(dropdown);
		JLabel resultTitle = new JLabel("Guess Result");
		JTextField result = new JTextField(20);
		panel.add(resultTitle);
		panel.add(result);
		return panel;
	}
	
	
	public static void main(String[] args) {
		PlayerInterface gui = new PlayerInterface();
		gui.setVisible(true);
	}

}
