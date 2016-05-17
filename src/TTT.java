import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class TTT {
    int[][] board ;
    ArrayList<Integer> moves;
    ArrayList<Integer> scores;

    private JButton bird[][];
    public TTT() {
        board = new int[3][3];
        clearBoard();
    }

    //clone constructor
    public TTT(TTT cloneSource) {
        board = new int[3][3];
        for(int i=0;i<9;i++) {
            this.board[i/3][i%3] = cloneSource.board[i/3][i%3];
        }
    }

    //evaluates if the game is over
    public boolean isOver() {
        //horizontals and verticals
        for(int i=0; i<3; i++) {
            if( (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != 0) || (board[0][i] == board[1][i] && board[1][i] == board[2][i]&& board[0][i] != 0) ) {
                return true;
            }
        }
        //diagonals
        if( board[1][1] != 0 && ((board[0][0] == board[1][1] && board[1][1] == board[2][2]) || (board[2][0] == board[1][1] && board[1][1] == board[0][2])) ) {
            return true;
        }

        //check for zero
        for(int i=0;i<9;i++) {
            if(this.board[i/3][i%3]==0) {
                return false;
            }
        }
        return true;
    }

    public int getValue(TTT curr, int depth, int alpha, int beta, int player){
        int value=0;
        if( curr.isOver() || depth == 8 ) {
            //System.out.println("end at:");
            //curr.printBoard();
            value = curr.getScore();
            return value;
        }
        if( player == 1 ) {
            value = curr.getMaxValue(curr,depth,alpha,beta,player);
            return value;
        }
        value = curr.getMinValue(curr,depth,alpha,beta,player);
        return value;

    }

    public int getMaxValue(TTT curr, int depth, int alpha, int beta, int player) {
        moves = curr.getPossibleMoves();
        scores = new ArrayList<Integer>();
        int value = Integer.MIN_VALUE;

        for ( Integer possibleMove : moves) {
            TTT childNode = new TTT(curr);
            //System.out.println("Max:"+possibleMove);
            //curr.printBoard();
            childNode.move(possibleMove,1);
            int successorVal = getValue(childNode, depth+1, alpha, beta, 2);
            value = value > successorVal ? value : successorVal;
            if(value>=beta){
                return value;
            }
            scores.add(successorVal);
            alpha = alpha > value ? alpha : value;

        }
        return value;
    }

    public int getMinValue(TTT curr, int depth, int alpha, int beta, int player) {
        moves = curr.getPossibleMoves();
        scores = new ArrayList<Integer>();
        int value = Integer.MAX_VALUE;

        for ( Integer possibleMove : moves) {
            TTT childNode = new TTT(curr);
            //System.out.println("Min:"+possibleMove);
            //curr.printBoard();
            childNode.move(possibleMove,2);
            int successorVal = getValue(childNode, depth+1, alpha, beta, 1);
            value = value < successorVal ? value : successorVal;
            if(value<=alpha){
                return value;
            }
            scores.add(successorVal);
            beta = beta > value ? beta : value;
        }
        return value;

    }


    //returns a list of all possible moves by checking all board tiles with 0 value
    public ArrayList<Integer> getPossibleMoves() {
        ArrayList<Integer> array= new ArrayList<Integer>();
        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                if(board[i][j] == 0){
                    array.add(i*3+j);
                }
            }
        }
        return array;
    }

    public void move(int xy, int turn) {
        this.board[xy/3][xy%3] = turn;
    }


    //returns the outcome
    // 10 == x wins
    //-10 == o wins
    //  0 == draw
    public int getScore() {
        for(int i=0; i<3; i++) {
            if( (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] == 1) || (board[0][i] == board[1][i] && board[1][i] == board[2][i]&& board[0][i] == 1) ) {
                return 10;
            }
            if( (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] == 2) || (board[0][i] == board[1][i] && board[1][i] == board[2][i]&& board[0][i] == 2) ) {
                return -10;
            }
        }
        if( board[1][1] == 1 && ((board[0][0] == board[1][1] && board[1][1] == board[2][2]) || (board[2][0] == board[1][1] && board[1][1] == board[0][2])) ) {
            return 10;
        }
        if( board[1][1] == 2 && ((board[0][0] == board[1][1] && board[1][1] == board[2][2]) || (board[2][0] == board[1][1] && board[1][1] == board[0][2])) ) {
            return -10;
        }
        return 0;
    }

    //evaluate the value of the node
    public int evaluate(int depth) {
        int score = this.getScore();

        //if(score == 10){
            //return score;// - depth;
        //}
        //if(score == -10){
            return score;// + depth;
        //}
        //return 0;
    }

    //calling function of the AI on the AI's turn
    public int moveAI() {
        int bestMove = 0;
        int bestMoveVal = this.getValue(this, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 2);
        //
        for ( Integer possibleMove : this.getPossibleMoves()) {
            System.out.println("Possible Move:" + possibleMove);
            TTT childNode = new TTT(this);
            childNode.move(possibleMove,2);
            int successorVal = this.getValue(childNode, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
            System.out.println("bestMoveVal:" + bestMoveVal);
            System.out.println("successorVal:" + successorVal);
            if(successorVal == bestMoveVal){
                System.out.println("mOVE fOUND");
                bestMove = possibleMove;
                break;
            }
        }

        return bestMove;
    }


    //Print the Current State of the Game
    public void printBoard() {
        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                System.out.print(this.board[i][j]+" ");
            }
            System.out.println("");
        }
    }

    //clears the board
    public void clearBoard() {
        for(int i=0;i<9;i++) {
            board[i/3][i%3] = 0;
        }
    }
}
