package clueGUI;

import clueGame.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame {
	
	public ClueGame() {
		Board board = new Board();
		board.initialize();
		JOptionPane.showMessageDialog(this, "You are Miss Scarlet, press Next Player to begin play", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		add(board);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
	}

	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File"); 
		menu.add(createNotesItem());
		menu.add(createFileExitItem());

		return menu;

	}

	private JMenuItem createNotesItem() {
		JMenuItem item = new JMenuItem("Show Detective Notes");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {	
					System.out.println("alskdjfalksd");
					createDetectiveNotes();
				}
	
			}
		item.addActionListener(new MenuItemListener());
		
			return item;
		}
		
	private void createDetectiveNotes() {
		JFrame frame = new JFrame();		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(750, 200);	
		// Create the JPanel and add it to the JFrame
		DetectiveNotes gui = new DetectiveNotes();		
		gui.setVisible(true);		
		frame.add(gui, BorderLayout.CENTER);
		frame.setVisible(true);
		// Now let's view it
	}
	private JMenuItem createFileExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}

		}
		item.addActionListener(new MenuItemListener());

		return item;
	}

	public static void main(String[] args) {
		ClueGame clue = new ClueGame();

		clue.setVisible(true);
		clue.setSize(600, 600);
		clue.setTitle("Clue Game");
		
		
	}
}
