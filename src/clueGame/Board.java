package clueGame;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.AncestorListener;

import com.sun.glass.events.MouseEvent;
import com.sun.xml.internal.ws.api.Component;

import clueGUI.MakingGuessPanel;

import java.util.Random;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

public class Board extends JPanel implements MouseListener{

	public static final int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board;
	public static Map<Character, String> rooms;
	private int rows;
	private int columns;
	private String boardConfigFile;
	private String roomConfigFile;
	//adjacency lists
	private Map<BoardCell, LinkedList<BoardCell>> adjList;
	//visited
	private Set<BoardCell> visitedCells;
	//target
	private Set<BoardCell> targetCells;
	private Vector<Player> players;
	private Solution theAnswer;
	private Set<Card> playingCards;
	private Vector<Card> weaponCards;
	private Vector<Card> playerCards;
	private Vector<Card> roomCards;
	private HumanPlayer humanPlayer;
	Vector<Card> undealt;
	boolean humanMustFinish;
	boolean compAccustation;
	clueGUI.ClueGame clueGamePlayed;

	public boolean isHumanMustFinish() {
		return humanMustFinish;
	}

	public Board() {
		boardConfigFile = "ClueConfigFile.csv";
		roomConfigFile = "Legend.txt";
		
	}

	public Board(int numRows, int numColumns){
		rows = numRows;
		columns = numColumns;
	}

	public Board(String boardfilename, String legendfilename) {
		boardConfigFile = boardfilename;
		roomConfigFile = legendfilename;
	}

	public void initialize(){
		humanMustFinish = false;
		compAccustation = false;
		playingCards = new HashSet<Card>();
		//load room config file
		try {
			loadRoomConfig(roomConfigFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}

		//load board config file
		try {
			loadBoardConfig(boardConfigFile);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		//load people and weapon config files
		players = new Vector<Player>();
		loadConfigFiles();
		
		//deal cards
		dealCards();
	}



	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException{
		loadRoomConfig(roomConfigFile);
	}

	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException{
		loadBoardConfig(boardConfigFile);
	}

	public void loadRoomConfig(String roomConfigFile) throws BadConfigFormatException, FileNotFoundException{
		FileReader reader = new FileReader(roomConfigFile);
		Scanner in = new Scanner(reader);
		rooms = new HashMap<Character, String>();

		while(in.hasNextLine()){
			String line = in.nextLine();
			String[] legendIn = line.split(", ");
			if (legendIn[0].length() > 1) {throw new BadConfigFormatException("Room abbrev. is in improper format");}
			if (legendIn.length < 3){throw new BadConfigFormatException("NO ROOM TYPE");}
			rooms.put(legendIn[0].charAt(0), legendIn[1]);
		}
		in.close();

	}

	public void loadBoardConfig(String filename)throws BadConfigFormatException, FileNotFoundException{
		FileReader reader = new FileReader(filename);
		Scanner in = new Scanner(reader);
		int row = 0;
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];	
		// Doing these outside the for loop for exception purposes

		// Setting Board private variable columns. Should be this value forever.
		// It gets reset everytime the while loop loops, but still should be constant.


		while(in.hasNextLine()) {
			String line = in.nextLine();
			String[] rowarr = line.split("\\s*,\\s*");

			for(int i = 0; i < rowarr.length; i++) {
				board[row][i] = new BoardCell(row, i);
				board[row][i].setInitial(rowarr[i].charAt(0));
				if (rowarr[i].charAt(0) == 'W')
					board[row][i].setWalkway(true);
				if(rowarr[i].length() > 1) {
					board[row][i].setDoorway(true);
					switch(rowarr[i].charAt(1)) {
					case 'U': board[row][i].setDoorDirection(DoorDirection.UP);
					break;
					case 'D': board[row][i].setDoorDirection(DoorDirection.DOWN);
					break;
					case 'L': board[row][i].setDoorDirection(DoorDirection.LEFT);
					break;
					case 'R': board[row][i].setDoorDirection(DoorDirection.RIGHT);
					break;
					case 'N':
						board[row][i].setDoorDirection(DoorDirection.NONE);						
					break;
					case 'X':
						board[row][i].setNameCell(true);
						board[row][i].setDoorway(false);
						break;
					default: {throw new BadConfigFormatException("Invalid DoorDirection");}
					}
				}
			}

			if(row == 0){
				columns = rowarr.length;
			}
			else if (rowarr.length != columns) {throw new BadConfigFormatException("Number of columns not constant");}

			row++;
		}
		// Setting Baord private variable rows.
		rows = row;
		calcAdjacencies();


	}

	public boolean checkNeighbor(int row, int col, DoorDirection direction){
		if(row < 0 || row > rows - 1 ||  col < 0 || col > columns - 1) {
			return false;
		}
		BoardCell neighbor = board[row][col];
		if (neighbor.getInitial() == 'W'){
			return true;
		}
		if (neighbor.isDoorway() && (neighbor.getDoorDirection().equals(direction))){
			return true;
		}
		else return false;
	}

	public void calcAdjacencies(){
		adjList = new HashMap<BoardCell, LinkedList<BoardCell>>();

		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				LinkedList<BoardCell> toadd = new LinkedList<BoardCell>();
				if(board[i][j].getInitial() == 'W' || board[i][j].isDoorway()){

					if(checkNeighbor(i-1,j, DoorDirection.DOWN)){
						toadd.add(board[i - 1][j]);
					}
					if (checkNeighbor(i+1,j, DoorDirection.UP)) {
						toadd.add(board[i + 1][j]);
					}
					if (checkNeighbor(i,j-1, DoorDirection.RIGHT)) {
						toadd.add(board[i][j - 1]);
					}
					if (checkNeighbor(i,j+1, DoorDirection.LEFT)) {
						toadd.add(board[i][j + 1]);
					}

				}

				adjList.put(board[i][j], toadd);
				//System.out.println(adjList.size());
			}
		}

	}


	public void findAllTargets(BoardCell cell, int numSteps){
		LinkedList<BoardCell> adjacents = new LinkedList<BoardCell>();
		visitedCells.add(cell);

		adjacents = adjList.get(cell);


		for(BoardCell adjCell : adjacents){
			if(visitedCells.contains(adjCell)){
				continue;
			}
			if(numSteps == 1 || adjCell.isDoorway()){
				targetCells.add(adjCell);
			}
			else{
				findAllTargets(adjCell,numSteps-1);

			}
			visitedCells.remove(adjCell);
		}

	}

	public void calcTargets(int row, int column, int pathLength){
		BoardCell startCell = board[row][column];

		visitedCells = new HashSet<BoardCell>();
		targetCells = new HashSet<BoardCell>();

		visitedCells.add(startCell);

		if(startCell != null){
			findAllTargets(startCell,pathLength);
		}
	}
	
	public void loadConfigFiles()  {
		playerCards = new Vector<Card>();
		weaponCards = new Vector<Card>();
		roomCards = new Vector<Card>();
		try {
			FileReader reader = new FileReader("Players.txt");	
			BufferedReader buffered = new BufferedReader(reader);
			String line = "";	
			while ((line = buffered.readLine()) != null) {
				String name = "";
				String color;
				int row;
				int col;
				name = line.split(", ")[0];
				color = line.split(", ")[1];	
				row = Integer.parseInt(line.split(", ")[2]);	
				col = Integer.parseInt(line.split(", ")[3]);
				if (players.size() < 1) {
					players.add(new HumanPlayer(name, color, row, col));
				}
				else 
					players.add(new ComputerPlayer(name, color, row, col));
				playerCards.add(new Card(name.trim(), CardType.PERSON));
				playingCards.add(new Card(name.trim(), CardType.PERSON));
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			FileReader reader = new FileReader("Weapons.txt");	
			BufferedReader buffered = new BufferedReader(reader);
			String line = "";	
			while ((line = buffered.readLine()) != null) {
				String name = line;
				weaponCards.add(new Card(name.trim(), CardType.WEAPON));
				playingCards.add(new Card(name.trim(), CardType.WEAPON));
			}
			setHumanPlayer(players.get(0));
			
			this.setHumanPlayer(humanPlayer);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			FileReader reader = new FileReader("Legend.txt");	
			BufferedReader buffered = new BufferedReader(reader);
			String line = "";	
			while ((line = buffered.readLine()) != null) {
				if (line.split(", ")[2].equals("Card")) {
					String name = line.split(", ")[1];
					roomCards.add(new Card(name.trim(), CardType.ROOM));
					playingCards.add(new Card(name.trim(), CardType.ROOM));
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void dealCards() {
		String[] criteria = new String[3];
		boolean solutionFound = false;
		Random rand = new Random();
		int randomNum = rand.nextInt(roomCards.size());
		String roomSolution = roomCards.get(randomNum).getCardName();
		randomNum = rand.nextInt(weaponCards.size());
		String weaponSolution = weaponCards.get(randomNum).getCardName();
		randomNum = rand.nextInt(playerCards.size());
		String playerSolution = playerCards.get(randomNum).getCardName();
		Card roomCard = null, playerCard = null, weaponCard = null;
		for (Card c: playingCards) {
			if (c.getCardName().equals(roomSolution))
				roomCard = c;
			if (c.getCardName().equals(weaponSolution))
				weaponCard = c;
			if (c.getCardName().equals(playerSolution))
				playerCard = c;
		}

		theAnswer = new Solution(playerSolution, roomSolution, weaponSolution);

		undealt = new Vector<Card>();
		undealt.addAll(playingCards);
		undealt.remove(roomCard);
		undealt.remove(playerCard);
		undealt.remove(weaponCard);
		int player = 0;
		while (undealt.size() > 0) {
			int randomCard = rand.nextInt(undealt.size());
			players.get(player).addCardToHand(undealt.get(randomCard));
			undealt.remove(randomCard);
			player++;
			if (player >= players.size()) player = 0;
		}
	}
	
	public Card handleSuggestion(Solution suggestion, String accusingPlayer, BoardCell clicked) {
		Card card = null;
		boolean started = false;
		int chosen = 0;
		
		for (Player p: players) {
			if (p.getPlayerName() == suggestion.person) {
				p.setRow(clicked.getColumn());
				p.setColumn(clicked.getRow());
			}
		}
		
		while (true) {
			if (players.get(chosen).getPlayerName() == accusingPlayer) {
				if (started)					
					break;
				else
					started = true;
			}
			else if (started) {
				 card = players.get(chosen).disproveSuggestion(suggestion);
				 if (card != null)
					 return card;
			}
			chosen++;
			if (chosen >= players.size())
				chosen = 0;
		}
		
		if (card == null) {
			compAccustation = true;
		}
		return card;
	}
	
	public boolean checkAccusation(Solution accusation) {
		JOptionPane.showMessageDialog(this, "Accusation: " + accusation.person + " with the " + accusation.weapon + " in the " + accusation.room 
				+ "\nThis accusation is " + accusation.equals(theAnswer), "Clue", JOptionPane.INFORMATION_MESSAGE);
		return (accusation.equals(theAnswer));
	}
	
	public void paintComponent(Graphics g) {		
		super.paintComponent(g);
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {
				board[row][col].draw(g);				
			}
		}
		for (Player p : players) {
			int x = p.getColumn();
			int y = p.getRow();
			Color c = p.getColor();
			board[x][y].drawPlayer(g, c);
		}
	}

	public void highlightTargets(Set<BoardCell> targets, clueGUI.ClueGame game) {
		clueGamePlayed = game;
		if (targets != null) {
			for (BoardCell b: targets) {
				b.setHighlight(true);
			}
		}
		repaint();
		humanMustFinish = true;
		//System.out.println(humanPlayer.getRow() + humanPlayer.getColumn());
		addMouseListener(this);
		//System.out.println(humanPlayer.getRow() + humanPlayer.getColumn());
		//removeMouseListener(this);

	}
	
	public Set<BoardCell> getTargets(){
		return targetCells;
	}


	public LinkedList<BoardCell> getAdjList(BoardCell cell){
		return adjList.get(cell);

	}

	public Map<Character, String> getRooms() {

		return rooms;
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public Vector<Player> getPlayers() {
		return players;
	}

	public Solution getTheAnswer() {
		// TODO Auto-generated method stub
		return theAnswer;
	}
	
	public void setPlayers(Vector<Player> people) {
		players.clear();
		players.addAll(people);
	}
	
	public void setHumanPlayer(Player humanPlayer) {
		//HumanPlayer hPlayer = new HumanPlayer(humanPlayer.getPlayerName(), humanPlayer.getColor().toString(), humanPlayer.getRow(), humanPlayer.getColumn());
		this.humanPlayer = (HumanPlayer) humanPlayer;
	}

	public Vector<Card> getUndealt() {
		return undealt;
	}

	public HumanPlayer getHumanPlayer() {
		return humanPlayer;
	}
	
	public BoardCell getCellAt(int row, int column){
		BoardCell cellTest = board[row][column];		
		return cellTest;
	}
	
	public LinkedList<BoardCell> getAdjList(int row, int column) {
		return adjList.get(board[row][column]);
	}
	
	public Set<Card> getPlayingCards() {
		return playingCards;
	}

	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {}
	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {}
	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {}
	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		BoardCell whichBox = null;
		for (BoardCell b: targetCells) {
			if (b.containsClick(e.getX(), e.getY())) {
				whichBox = b;
				break;
			}
		}
		
		// display some information just to show whether a box was clicked
		if (whichBox != null){
			humanPlayer.setColumn(whichBox.getRow());
			humanPlayer.setRow(whichBox.getColumn());
				for (BoardCell b: targetCells) {
					b.setHighlight(false);
				}
				repaint();
			humanMustFinish = false;
			if (whichBox.isRoom()) {
				MakingGuessPanel mgp = new MakingGuessPanel(this, whichBox.getInitial(), clueGamePlayed);
				mgp.setVisible(true);
				}
			}
		
		else{
			JOptionPane.showMessageDialog(this, "Not a valid target selected. Please choose a valid target.", "Clue", JOptionPane.INFORMATION_MESSAGE);	
			((java.awt.Component) e.getSource()).addMouseListener(this);
		}
		((java.awt.Component) e.getSource()).removeMouseListener(this);
	}
	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {}
}
