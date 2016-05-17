import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class Board extends JFrame implements MouseListener, ActionListener{
// Board class that takes an AI and a Player 
	private static final long serialVersionUID = 1L;
	
	
	ArrayList<ArrayList<JLabel>> boardState;// 2d state of the board
	ArrayList<ArrayList<Integer>> state;
	protected JPanel boardHolder;
	protected JPanel menuHolder;
	protected JPanel mainHolder;
	protected JButton Reset;
	protected JButton changeType;
	boolean isAIFirst;
	
	Player p;
	AI a;
	
	public Board(Player p, AI a, boolean flag){
		isAIFirst = flag;// flag that determines if the AI moves first or not
		this.p = new Player();
		this.p = p;
		this.a = new AI();
		this.a = a;
		boardState = new ArrayList<ArrayList<JLabel>>();
		ArrayList<JLabel> dummyList;
		JLabel dummyLabel;
		int i;
		int j;
		
		
		//set UI for the board
		for(i=0;i<3;i++){
			dummyList = new ArrayList<JLabel>();
			for(j=0;j<3;j++){
				dummyLabel = new JLabel(" ",SwingConstants.CENTER);				
				dummyLabel.setFont(new Font("KG Second Chances Sketch", Font.PLAIN, 120));
				dummyLabel.setPreferredSize(new Dimension(150,150));
				dummyLabel.setBackground(new Color(25,71,25));
				dummyLabel.setForeground(Color.WHITE);
				dummyLabel.setOpaque(true);
				dummyLabel.addMouseListener(this);
				
				dummyList.add(dummyLabel);
			}
			boardState.add(dummyList);
		}
		
		boardHolder = new JPanel();
		boardHolder.setPreferredSize(new Dimension(450,450));
		boardHolder.setLayout(new GridLayout(3,3,3,3));
		
		for(i=0; i<3; i++){
			for(j=0; j<3; j++){
				boardHolder.add(boardState.get(i).get(j));
			}			
		}
		
		// set UI for the menu bar
		menuHolder = new JPanel();
		menuHolder.setLayout(new GridLayout(1,2));
		menuHolder.setPreferredSize(new Dimension(450,80));
		
		
		changeType = new JButton("change type");
		changeType.setFont(new Font("KG Second Chances Sketch", Font.PLAIN, 25));
		changeType.setBackground(new Color(1,20,25));
		changeType.setForeground(Color.WHITE);
		changeType.addActionListener(this);
		
		Reset = new JButton("Reset");
		Reset.setFont(new Font("KG Second Chances Sketch", Font.PLAIN, 25));
		Reset.setBackground(new Color(1,20,25));
		Reset.setForeground(Color.WHITE);
		Reset.addActionListener(this);
		
		menuHolder.add(Reset);
		menuHolder.add(changeType);
		// position UI on main Panel
		mainHolder = new JPanel();
		mainHolder.setLayout(new BorderLayout());
		mainHolder.setPreferredSize(new Dimension(450,530));
		
		mainHolder.add(boardHolder, BorderLayout.NORTH);
		mainHolder.add(menuHolder, BorderLayout.CENTER);
		
		if(isAIFirst){
			boardState = a.move(boardState);
		}
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(mainHolder);
		this.pack();
		this.setVisible(true);
		this.setResizable(false);
		
	}

	public boolean isGameOver(){// checks if the board is full, ( game is over )
		int i;
		int j;
		for(i=0; i<3; i++){
			for(j=0; j<3; j++){
				if(this.boardState.get(i).get(j).getText().equals(" ")){
					return false;
					
				}
			}			
		}return true;
	}
	
	// Board class implement Mouse listener to interact with the clickable JLabels
	@Override
	public void mouseClicked(MouseEvent arg0) {// on click , text change as a move of the player
		// TODO Auto-generated method stub
		int i;
		int j;
		for(i=0; i<3; i++){
			for(j=0; j<3; j++){
				
				if(arg0.getSource() == boardState.get(i).get(j) && boardState.get(i).get(j).getForeground().equals(Color.RED)){// checks if move is valid
					
					boardState.get(i).get(j).setForeground(Color.WHITE);
					
						boardState = p.move(boardState, i, j);// move of player
						if(Game.isWinPlayer(boardState)){// checks if after the move player has won
							
							System.out.println("Player Wins");
							JOptionPane.showMessageDialog(null,"Player Wins");
							this.resetBoard();
							
						}else{// else ai is let to move , consequently, is checked if AI has won as well
							boardState = a.move(boardState);
							if(Game.isWinAI(boardState)){
								
								System.out.println("A.I. Wins");
								JOptionPane.showMessageDialog(null,"A.I. Wins");
								this.resetBoard();
								
							}
						}
				}
			}			
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {// add on ui , shows a red "x" signaling a possible move 
		// TODO Auto-generated method stub
		int i;
		int j;
	
		for(i=0;i<3;i++){
			
			for(j=0;j<3;j++){
				if(arg0.getSource() == boardState.get(i).get(j) && boardState.get(i).get(j).getText().equals(" ") ){
					boardState.get(i).get(j).setForeground(Color.RED);
					boardState.get(i).get(j).setText("X");
					boardState.get(i).get(j).getForeground().equals(Color.RED);
				}
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {// and once, exited, return to original state 
		// TODO Auto-generated method stub
		int i;
		int j;
	
		for(i=0;i<3;i++){
			
			for(j=0;j<3;j++){
				if(arg0.getSource() == boardState.get(i).get(j) &&  boardState.get(i).get(j).getForeground().equals(Color.RED)){
					boardState.get(i).get(j).setForeground(Color.WHITE);
					boardState.get(i).get(j).setText(" ");
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {// action listener implemented by Board class to interact with  the Menu bar  
		// TODO Auto-generated method stub
		
		if(arg0.getSource() == Reset){
			System.out.println("yo");
			this.resetBoard();
		}
		else if(arg0.getSource() == changeType){
			if(isAIFirst){
				isAIFirst = false;
			}else{
				isAIFirst = true;
			}
			
			this.resetBoard();
		}else{
			
		}
		
	}
	
	public void resetBoard(){ // function that reset the board 
		
		int i;
		int j;
		
		for(i=0;i<3;i++){
			
			for(j=0;j<3;j++){
				boardState.get(i).get(j).setText(" ");
			}
		}
		if(isAIFirst){// if the AI is set as the first to move , immediately allow move after reseting 
			boardState = a.move(boardState);
		}
	}


}


