package clueGUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Solution;

public class MakingGuessPanel extends JDialog {
	Board board;
	JComboBox guessP;
	JComboBox guessW;
	boolean personGuessed;
	boolean weaponGuessed;
	String room;
	
	public MakingGuessPanel(Board board, char roomInitial) {
		personGuessed = false;
		weaponGuessed = false;
		this.board = board;
		setLayout(new GridLayout(4,2));
		JPanel panel = new JPanel();
		JLabel roomLabel = new JLabel("Your room");
		JLabel person = new JLabel("Person");
		JLabel weapon = new JLabel("Weapon");
		
		room = " ";
		switch (roomInitial) {
		case 'C':
			room = "Conservatory";
			break;
		case 'F':
			room = "Billiards";
			break;
		case 'B':
			room = "Ballroom";
			break;
		case 'A':
			room = "Kitchen";
			break;
		case 'I':
			room = "Library";
			break;
		case 'D':
			room = "Dining";
			break;
		case 'H':
			room = "Hall";
			break;
		case 'J':
			room = "Lounge";
			break;
		case 'G':
			room = "Study";
			break;
		}
		JLabel roomName = new JLabel(room);
		guessP = new JComboBox();
		guessP.addItem("Miss Scarlett");		
		guessP.addItem("Colonel Mustard");
		guessP.addItem("Mr. Green");
		guessP.addItem("Mrs. White");
		guessP.addItem("Mrs. Peacock");
		guessP.addItem("Professor Plum");
		ComboListenerPerson Plistener = new ComboListenerPerson();
		guessP.addActionListener(Plistener);
		
		guessW = new JComboBox();
		guessW.addItem("Candlestick");
		guessW.addItem("Knife");
		guessW.addItem("Lead Pipe");
		guessW.addItem("Revolver");
		guessW.addItem("Rope");
		guessW.addItem("Wrench");
		ComboListenerWeapon Wlistener = new ComboListenerWeapon();
		guessW.addActionListener(Wlistener);
		
		JButton submit = new JButton("Submit");
		JButton cancel = new JButton("Cancel");
		submit.addActionListener(new ButtonListenerSubmit());
		cancel.addActionListener(new ButtonListenerCancel());
		
		panel.add(roomLabel);
		panel.add(person);
		panel.add(weapon);
		panel.add(submit);
		panel.add(roomName);
		panel.add(guessP);
		panel.add(guessW);
		panel.add(cancel);
		
		setSize(200, 200);
		setVisible(true);
	}
	
	public class ComboListenerPerson implements ActionListener {
		  public void actionPerformed(ActionEvent e)
		  {
			  personGuessed = true;
			  board.getHumanPlayer().setPersonGuess(e.getSource().toString());
		  }
	}

	public class ComboListenerWeapon implements ActionListener {
		  public void actionPerformed(ActionEvent e)
		  {
			  weaponGuessed = true;
			  board.getHumanPlayer().setWeaponGuess(e.getSource().toString());
		  }
	}
	
	class ButtonListenerSubmit implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (personGuessed && weaponGuessed) {
				setVisible(false);
				board.handleSuggestion(new Solution(board.getHumanPlayer().getPersonGuess(), board.getHumanPlayer().getWeaponGuess(), room), board.getHumanPlayer().getPlayerName(), new BoardCell(board.getHumanPlayer().getRow(), board.getHumanPlayer().getColumn()));
			}
		}
	}
	
	class ButtonListenerCancel implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
		}
	}
}
