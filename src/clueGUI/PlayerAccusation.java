package clueGUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import clueGUI.MakingGuessPanel.ButtonListenerCancel;
import clueGUI.MakingGuessPanel.ButtonListenerSubmit;
import clueGUI.MakingGuessPanel.ComboListenerPerson;
import clueGUI.MakingGuessPanel.ComboListenerWeapon;
import clueGame.Board;
import clueGame.Solution;

public class PlayerAccusation extends JDialog {
	Board board;
	JComboBox<String> guessP;
	JComboBox<String> guessW;
	JComboBox<String> guessR;
	boolean personGuessed;
	boolean weaponGuessed;
	boolean roomGuessed;
	
	public PlayerAccusation (Board board) {
		personGuessed = false;
		weaponGuessed = false;
		this.board = board;
		setLayout(new GridLayout(4,2));
		JLabel roomLabel = new JLabel("Room");
		JLabel person = new JLabel("Person");
		JLabel weapon = new JLabel("Weapon");
		
		guessR = new JComboBox<String>();
		guessR.addItem("");
		guessR.addItem("Kitchen");
		guessR.addItem("Dining Room");
		guessR.addItem("Lounge");
		guessR.addItem("Ballroom");
		guessR.addItem("Conservatory");
		guessR.addItem("Hall");
		guessR.addItem("Study");
		guessR.addItem("Library");
		guessR.addItem("Billiard Room");
		ComboListenerRoom Rlistener = new ComboListenerRoom();
		guessR.addActionListener(Rlistener);
		
		guessP = new JComboBox<String>();
		guessP.addItem("");
		guessP.addItem("Miss Scarlett");		
		guessP.addItem("Colonel Mustard");
		guessP.addItem("Mr. Green");
		guessP.addItem("Mrs. White");
		guessP.addItem("Mrs. Peacock");
		guessP.addItem("Professor Plum");
		ComboListenerPerson Plistener = new ComboListenerPerson();
		guessP.addActionListener(Plistener);
		
		guessW = new JComboBox<String>();
		guessW.addItem("");
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
		
		add(roomLabel);
		add(guessR);
		add(person);
		add(guessP);
		add(weapon);
		add(guessW);
		add(submit);
		add(cancel);
		
		setTitle("Accusation");
		setSize(300, 300);
		setVisible(true);
	}

	private class ComboListenerPerson implements ActionListener {
		  public void actionPerformed(ActionEvent e)
		  {
			  personGuessed = true;
			  board.getHumanPlayer().setPersonGuess(guessP.getSelectedItem().toString());
		  }
	}

	private class ComboListenerWeapon implements ActionListener {
		  public void actionPerformed(ActionEvent e)
		  {
			  weaponGuessed = true;
			  board.getHumanPlayer().setWeaponGuess(guessW.getSelectedItem().toString());
		  }
	}
	
	private class ComboListenerRoom implements ActionListener {
		  public void actionPerformed(ActionEvent e)
		  {
			  roomGuessed = true;
			  board.getHumanPlayer().setRoomGuess(guessR.getSelectedItem().toString());
		  }
	}
	
	class ButtonListenerSubmit implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (personGuessed && weaponGuessed && roomGuessed) {
				setVisible(false);
				Solution accusation = new Solution(board.getHumanPlayer().getPersonGuess(), board.getHumanPlayer().getRoomGuess(), board.getHumanPlayer().getWeaponGuess());
				board.checkAccusation(accusation);
			}
		}
	}
	
	class ButtonListenerCancel implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
		}
	}
}
