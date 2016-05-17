import java.util.ArrayList;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Demo {

    public static void main (String args[]){
        Scanner sc = new Scanner(System.in);
        TTT game = new TTT();
        int value = 5;
        game.printBoard();
        while(!game.isOver()){
            System.out.print("Your Move: ");
            String move = sc.nextLine();
        	String[] moveCoorStr = move.split(" ");
            int x = Integer.parseInt(moveCoorStr[0]);
            int y = Integer.parseInt(moveCoorStr[1]);
            int xy = x*3+y;

            if(game.board[x][y]!=0){
                System.out.println("Invalid Move");
                continue;
            }

            game.move(xy,1);
            game.printBoard();
            if(game.isOver()){
                break;
            }
            xy = game.moveAI();
            System.out.println("AI Move: "+xy/3+" "+xy%3);
            game.move(xy,2);
            game.printBoard();


        }


    }
}
