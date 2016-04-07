package clueGUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotes extends JDialog {

	public DetectiveNotes() {
		setLayout(new GridLayout(3,2));
		JPanel panel = createPeoplePanel();
		add(createPeoplePanel());
		add(createPersonGuessPanel());
		add(createRoomPanel());
		add(createRoomGuessPanel());		
		add(createWeaponsPanel());
		add(createWeaponGuessPanel());
		setSize(800, 600);
		setVisible(true);
	}
	
	/*public static void main(String[] args) {
		// Create a layout with 2 rows
		//JFrame frame = new JFrame();
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setSize(750, 200);	
		// Create the JPanel and add it to the JFrame
		DetectiveNotes gui = new DetectiveNotes();		
		gui.setVisible(true);
		//frame.add(gui, BorderLayout.CENTER);
		// Now let's view it
	}*/
	
	private JPanel createPersonGuessPanel() {
		JPanel panel = new JPanel();
		JComboBox guess = new JComboBox();
		guess.addItem("Miss Scarlett");		
		guess.addItem("Colonel Mustard");
		guess.addItem("Mr. Green");
		guess.addItem("Mrs. White");
		guess.addItem("Mrs. Peacock");
		guess.addItem("Professor Plum");
		panel.add(guess);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess a Person"));
		return panel;		
	}
	private JPanel createRoomGuessPanel() {
		JPanel panel = new JPanel();
		JComboBox guess = new JComboBox();
		guess.addItem("Kitchen");
		guess.addItem("Dining Room");
		guess.addItem("Lounge");
		guess.addItem("Ballroom");
		guess.addItem("Conservatory");
		guess.addItem("Hall");
		guess.addItem("Study");
		guess.addItem("Library");
		guess.addItem("Billiard Room");		
		panel.add(guess);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess a Room"));
		return panel;		
	}
	
	private JPanel createWeaponGuessPanel() {
		JPanel panel = new JPanel();
		JComboBox guess = new JComboBox();
		guess.addItem("Candlestick");
		guess.addItem("Knife");
		guess.addItem("Lead Pipe");
		guess.addItem("Revolver");
		guess.addItem("Rope");
		guess.addItem("Wrench");
		panel.add(guess);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess a Weapon"));
		return panel;		
	}

	 private JPanel createPeoplePanel() {
		 	JPanel panel = new JPanel();
		 	panel.setLayout(new GridLayout(3, 3));			
		 	JCheckBox checkbox = new JCheckBox("Miss Scarlett");		 	
			panel.add(checkbox);			
			checkbox = new JCheckBox("Colonel Mustard");
			panel.add(checkbox);
			checkbox = new JCheckBox("Mr. Green");
			panel.add(checkbox);
			checkbox = new JCheckBox("Mrs. Peacock");
			panel.add(checkbox);
			checkbox = new JCheckBox("Mrs. White");
			panel.add(checkbox);
			checkbox = new JCheckBox("Professor Plum");
			panel.add(checkbox);
			panel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
			return panel;
	}
	 
	 private JPanel createRoomPanel() {
		 	JPanel panel = new JPanel();
		 	panel.setLayout(new GridLayout(3, 3));			
		 	JCheckBox checkbox = new JCheckBox("Kitchen");		 	
			panel.add(checkbox);			
			checkbox = new JCheckBox("Dining Room");
			panel.add(checkbox);
			checkbox = new JCheckBox("Lounge");
			panel.add(checkbox);
			checkbox = new JCheckBox("Ballroom");
			panel.add(checkbox);
			checkbox = new JCheckBox("Conservatory");
			panel.add(checkbox);
			checkbox = new JCheckBox("Hall");
			panel.add(checkbox);
			checkbox = new JCheckBox("Study");
			panel.add(checkbox);
			checkbox = new JCheckBox("Library");
			panel.add(checkbox);
			checkbox = new JCheckBox("Billiard Room");
			panel.add(checkbox);
			checkbox = new JCheckBox("Closet");
			panel.add(checkbox);
			panel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
			return panel;
	}
	 
	 private JPanel createWeaponsPanel() {
		 	JPanel panel = new JPanel();
		 	panel.setLayout(new GridLayout(3, 3));			
		 	JCheckBox checkbox = new JCheckBox("Candlestick");		 	
			panel.add(checkbox);			
			checkbox = new JCheckBox("Knife");
			panel.add(checkbox);
			checkbox = new JCheckBox("Lead Pipe");
			panel.add(checkbox);
			checkbox = new JCheckBox("Revolver");
			panel.add(checkbox);
			checkbox = new JCheckBox("Rope");
			panel.add(checkbox);
			checkbox = new JCheckBox("Wrench");
			panel.add(checkbox);
			panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
			return panel;
	}
}
