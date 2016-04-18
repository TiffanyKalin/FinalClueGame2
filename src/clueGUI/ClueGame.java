package clueGUI;

import clueGame.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClueGame extends JFrame {
	public static boolean humanMustFinish;
	public Clue_GUI control;
	Board board;
	Player currentPlayer;
	int currentPlayerNum;
	int dieRoll;
	
	public ClueGame() {
		//setLayout(new GridLayout(1, 2));
		board = new Board();
		board.initialize();
		Set<Card> humanCards = new HashSet<Card>();
		humanCards = board.getHumanPlayer().getMyCards();
		JPanel cardPanel = new JPanel();
		cardPanel = this.createCards(humanCards);
		JOptionPane.showMessageDialog(this, "You are Miss Scarlet, press Next Player to begin play", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		add(board, BorderLayout.CENTER);
		add(cardPanel, BorderLayout.EAST);
		control = new Clue_GUI(this);
		add(control, BorderLayout.SOUTH);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		humanMustFinish = false;
		currentPlayer = board.getPlayers().get(0);
		currentPlayerNum = 0;
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
					createDetectiveNotes();
				}
	
			}
		item.addActionListener(new MenuItemListener());
		
			return item;
		}
		
	private void createDetectiveNotes() {
		//JFrame frame = new JFrame();		
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setSize(750, 200);	
		// Create the JPanel and add it to the JFrame
		DetectiveNotes gui = new DetectiveNotes();		
		gui.setVisible(true);		
		//frame.add(gui, BorderLayout.CENTER);
		//frame.setVisible(true);
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
	
	private JPanel createCards(Set<Card> humanCards){
		JPanel cardPanel = new JPanel();
		cardPanel.setSize(200, 100);
		cardPanel.setLayout(new GridLayout(6, 1));
		JLabel peopleLabel = new JLabel("People");
		JLabel roomLabel = new JLabel("Rooms");
		JLabel weaponLabel = new JLabel("Weapons");
		
		JTextField peopleField = new JTextField();
		JTextField RoomsField = new JTextField();
		JTextField WeaponsField = new JTextField();
		for (Card c : humanCards) {
			if (c.getCardType() == CardType.PERSON) {
				peopleField = new JTextField(c.getCardName());
				peopleField.setBorder(new TitledBorder (new EtchedBorder(), "People"));
				cardPanel.add(peopleField);
			}
			if (c.getCardType() == CardType.ROOM) {
				RoomsField = new JTextField(c.getCardName());
				RoomsField.setBorder(new TitledBorder (new EtchedBorder(), "Room"));
				cardPanel.add(RoomsField);
			}
			if (c.getCardType() == CardType.WEAPON) {
				WeaponsField = new JTextField(c.getCardName());
				WeaponsField.setBorder(new TitledBorder (new EtchedBorder(), "Weapon"));
				cardPanel.add(WeaponsField);
			}
		}

		cardPanel.setBorder(new TitledBorder (new EtchedBorder(), "Cards"));
		return cardPanel;
		
		
	}
	
	public void NextPlayer() {
		
		if (board.isHumanMustFinish()) {
			JOptionPane.showMessageDialog(this, "Stop! Human Player Must Finish!", "Clue", JOptionPane.INFORMATION_MESSAGE);
			return; 
		}
		currentPlayer = board.getPlayers().get(currentPlayerNum);
		Random rand = new Random();
		dieRoll = rand.nextInt(6) + 1;
		control.dieText.setText(Integer.toString(dieRoll));
		control.turnText.setText(currentPlayer.getPlayerName());
		board.calcTargets(currentPlayer.getColumn(), currentPlayer.getRow(), dieRoll);
		
		/*System.out.println(currentPlayer.getRow() + " " +  currentPlayer.getColumn() + " " + dieRoll);
		
		for (BoardCell b : board.getTargets()) {
			//System.out.println(b.getRow() + " " + b.getColumn());
		}*/
		if (currentPlayer.getMakeAccu() && currentPlayer != board.getHumanPlayer()) {
			Solution accu = new Solution(currentPlayer.getSolution().person, currentPlayer.getSolution().room, currentPlayer.getSolution().weapon);
			board.checkAccusation(accu);
		}
		
		board.getPlayers().get(currentPlayerNum).makeMove(board, board.getTargets(), this);
		
		board.repaint();

		currentPlayerNum++;
		if (currentPlayerNum == 6) {
			currentPlayerNum = 0;
		}
		
	}
	
	public void MakeAccu() {
		if (currentPlayer == board.getHumanPlayer()) {
			PlayerAccusation pacc = new PlayerAccusation(board);
			pacc.setVisible(true);
		}
		else {
			JOptionPane.showMessageDialog(this, "It is not the human player's turn", "Clue", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}

	public void GameHandleSuggestion(Solution soln, Player playerCalling, BoardCell playerLoc) {
		Card card = board.handleSuggestion(soln, playerCalling.getPlayerName(), playerLoc);
		control.guessText.setText(soln.toString());
		if (card != null) 
			control.resultText.setText(card.toString());
		else 
			control.resultText.setText("no new card");
	}

	public static void main(String[] args) {
		ClueGame clue = new ClueGame();

		clue.setVisible(true);
		clue.setSize(800, 850);
		clue.setTitle("Clue Game");
		
		
	}

	public Player getCurrentPlayer() {
		// TODO Auto-generated method stub
		return currentPlayer;
	}


}
