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

public class PlayerAccusation extends JDialog {
	Board board;
	JComboBox guessP;
	JComboBox guessW;
	JComboBox guessR;
	boolean personGuessed;
	boolean weaponGuessed;
	
	public PlayerAccusation (Board board) {
		personGuessed = false;
		weaponGuessed = false;
		this.board = board;
		setLayout(new GridLayout(4,2));
		JPanel panel = new JPanel();
		JLabel roomLabel = new JLabel("Your room");
		JLabel person = new JLabel("Person");
		JLabel weapon = new JLabel("Weapon");
		
		JComboBox Rguess = new JComboBox();
		guessR.addItem("Kitchen");
		guessR.addItem("Dining Room");
		guessR.addItem("Lounge");
		guessR.addItem("Ballroom");
		guessR.addItem("Conservatory");
		guessR.addItem("Hall");
		guessR.addItem("Study");
		guessR.addItem("Library");
		guessR.addItem("Billiard Room");	
		
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
		panel.add(guessR);
		panel.add(guessP);
		panel.add(guessW);
		panel.add(cancel);
		
		setSize(200, 200);
		setVisible(true);
	}

	private class ComboListenerPerson implements ActionListener {
		  public void actionPerformed(ActionEvent e)
		  {
			  personGuessed = true;
			  board.getHumanPlayer().setPersonGuess(e.getSource().toString());
		  }
	}

	private class ComboListenerWeapon implements ActionListener {
		  public void actionPerformed(ActionEvent e)
		  {
			  weaponGuessed = true;
			  board.getHumanPlayer().setWeaponGuess(e.getSource().toString());
		  }
	}
	
	private class ComboListenerRoom implements ActionListener {
		  public void actionPerformed(ActionEvent e)
		  {
			  weaponGuessed = true;
			  board.getHumanPlayer().setRoomGuess(e.getSource().toString());
		  }
	}
	
	class ButtonListenerSubmit implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (personGuessed && weaponGuessed) {
				setVisible(false);
			}
		}
	}
	
	class ButtonListenerCancel implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
		}
	}
}
