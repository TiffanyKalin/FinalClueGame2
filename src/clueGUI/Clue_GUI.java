package clueGUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import clueGUI.ClueGame;

public class Clue_GUI extends JPanel {
	public JTextField turnText;
	public JTextField dieText;
	public JTextField guessText;
	public JTextField resultText;
	private ClueGame game;

	public Clue_GUI(ClueGame game)
	{
		// Create a layout with 2 rows
		this.game = game;
		setLayout(new GridLayout(3,2));
		JPanel panel = createTurnPanel();
		add(panel);
		panel = createButtonPanel();
		add(panel);	
		panel = createDiePanel();
		add(panel);
		panel = createGuessPanel();
		add(panel);
		panel = createResultPanel();
		add(panel);
	}

	 public JPanel createTurnPanel() {
		 	JPanel panel = new JPanel();
		 	// Use a grid layout, 1 row, 2 elements (label, text)	
		 	JLabel nameLabel = new JLabel("Whose turn?");
			turnText = new JTextField(20);
			turnText.setEditable(false);
			panel.add(nameLabel, BorderLayout.CENTER);
			panel.add(turnText, BorderLayout.SOUTH);
			
			return panel;
	}
	 
	 public JPanel createDiePanel() {
		 JPanel panel = new JPanel();
		 JLabel nameLabel = new JLabel("Roll");
		 dieText = new JTextField(5);
		 dieText.setEditable(false);
		 panel.add(nameLabel);
		 panel.add(dieText);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		 return panel;
	 }
	 
	public JPanel createButtonPanel() {
		// no layout specified, so this is flow
		JButton nextButton = new JButton("Next Player");
		JButton accusationButton = new JButton("Make Accusation");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 4));
		panel.add(nextButton);
		panel.add(accusationButton);
		nextButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				game.NextPlayer();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) {}
	}); 
		
		accusationButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				game.MakeAccu();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) {}
	}); 
		return panel;
	}

	
	public JPanel createGuessPanel() {
		JPanel panel = new JPanel();
		JLabel nameLabel =  new JLabel("Guess");
		guessText = new JTextField(15);
		panel.add(nameLabel);
		panel.add(guessText);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		return panel;
	}
	
	public JPanel createResultPanel() {
		JPanel panel = new JPanel();
		JLabel guessLabel =  new JLabel("Guess");
		guessLabel = new JLabel("Response");
		resultText = new JTextField(5);
		resultText.setEditable(false);
		panel.add(guessLabel);
		panel.add(resultText);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		return panel;
	}
	
	public void setGuessText(String guess) {
		guessText.setText(guess);
	}
	/*public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(750, 200);	
		// Create the JPanel and add it to the JFrame
		Clue_GUI gui = new Clue_GUI();
		frame.add(gui, BorderLayout.CENTER);
		// Now let's view it
		frame.setVisible(true);
	}*/


}