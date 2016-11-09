package clueGame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ClueGame extends JFrame {
	private DetectiveGUI detectiveNotes;
	private boolean toggle;
	
	public ClueGame(Board board) {
		toggle = false;
		//basic defaults
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue Game");
		setSize(700, 700);
		//additions
		add(board, BorderLayout.CENTER);
		JMenuBar file = new JMenuBar();
		setJMenuBar(file);
		file.add(createMenu());
		//let there be a game
		setVisible(true);
		detectiveNotes = new DetectiveGUI(board);
	}
	
	public JMenu createMenu() {
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(createClose());
		fileMenu.add(createNotes());
		return fileMenu;
	}
	
	public JMenuItem createClose() {
		JMenuItem close = new JMenuItem("CLose");
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
		Board board = Board.getInstance();
		board.setConfigFiles("TCJPClueLayout.csv", "TCJPClueLayoutLegend.txt", "ClueCards.txt", "Players.txt");
		board.initialize();
		ClueGame boardGUI = new ClueGame(board);

	}

}
