import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class GUI implements ActionListener{
	JFrame mframe;
	JFrame gframe;

	JPanel title_holder;
	JPanel button_holder;
	JPanel main_menu;

	JPanel tile_holder;
	JPanel status_holder;
	JPanel game_holder;

	JButton pvc;
	JButton cvc;
	JButton tiles[][];

	JLabel title;
	JLabel status;
	JLabel winner;

	boolean pva;
	boolean ava;
	int turn; //1- Player is first    2- AI1 is first	3- AI2 is first

	public GUI(){

		pva = false;
		ava = false;

		pvc = new JButton("Player vs. AI");
		pvc.setBackground(Color.WHITE);
		pvc.addActionListener(this);

		cvc = new JButton("AI vs. AI");
		cvc.setBackground(Color.WHITE);
		cvc.addActionListener(this);

		title = new JLabel("TIC-TAC-TOE",SwingConstants.CENTER);

		mframe = new JFrame("K E K");
		mframe.setPreferredSize(new Dimension(480,320));

		button_holder = new JPanel();
		button_holder.setLayout(new GridLayout(0,1));
		button_holder.add(pvc);
		pvc.setPreferredSize(new Dimension(40,30));
		button_holder.add(cvc);
		cvc.setPreferredSize(new Dimension(40,30));
		button_holder.setPreferredSize(new Dimension(200,100));
		
		title_holder = new JPanel();
		title_holder.setLayout(new BorderLayout());
		title_holder.add(title,BorderLayout.CENTER);

		main_menu = new JPanel();
		main_menu.setLayout(new GridLayout(2,1));
		main_menu.add(title_holder);
		main_menu.add(button_holder);
		main_menu.setPreferredSize(new Dimension(440,220));

		mframe.setLayout(new BorderLayout());
		mframe.add(main_menu,BorderLayout.CENTER);
		mframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mframe.pack();
		mframe.setVisible(true);
		//frame.setResizeable(false);
	}

	public void initBoard(){
		gframe  = new JFrame("G A M E");

		tiles = new JButton[3][3];
		tile_holder = new JPanel();
		tile_holder.setLayout(new GridLayout(3,3));

		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				tiles[i][j] = new JButton();
				tiles[i][j].setBackground(Color.WHITE);
				tiles[i][j].setPreferredSize(new Dimension(30,30));
				tiles[i][j].addActionListener(this);
				tile_holder.add(tiles[i][j]);
			}
		}

		winner = new JLabel();
		status = new JLabel();

		status_holder = new JPanel();
		status_holder.setLayout(new BorderLayout());
		status_holder.add(winner,BorderLayout.WEST);
		status_holder.add(status,BorderLayout.EAST);
		status_holder.setPreferredSize(new Dimension(150,300));

		game_holder = new JPanel();
		game_holder.setLayout(new GridLayout(0,1));
		game_holder.setPreferredSize(new Dimension(400,300));
		game_holder.add(tile_holder);
		game_holder.add(status_holder);

		gframe.add(game_holder);
		gframe.pack();
		gframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void actionPerformed(ActionEvent e){
		if(e.getSource() == pvc){
			mframe.setVisible(false);
			pva = true;
			turn = 1;
			initBoard();
			gframe.setVisible(true);
		}else if(e.getSource() == cvc){
			mframe.setVisible(false);
			ava = true;
			turn = 3;
			initBoard();
			gframe.setVisible(true);
		}

		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				if(e.getSource() == tiles[i][j]){
					if(tiles[i][j].getText() == ""){
						tiles[i][j].setText("x");	
					}else{
						System.out.println("Invalid Move");
					}
				}		
			}
		}
		
	}

	public static void main(String args[]){
		GUI g = new GUI();
	}

}