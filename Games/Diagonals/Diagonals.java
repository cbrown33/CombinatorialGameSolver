package Games.Diagonals;

import CombinatorialGame.*;
import java.util.HashSet;

public class Diagonals implements CombinatorialGame {
    private pieceType[][] board;

    public Diagonals(int n, int m){
        board = new pieceType[n][m];
        for(int i = 0; i < m; i++){
            board[0][i] = pieceType.left;       //bottom row is left
            board[n-1][i] = pieceType.right;    //top row is right
        }
    }

    public Diagonals(pieceType[][] board){
        this.board = new pieceType[board.length][board[0].length];
        for(int i = 0; i < board.length; i++){
            System.arraycopy(board[i], 0, this.board[i], 0, board[0].length);
        }
    }

    @Override
    public HashSet<CombinatorialGame> getLeftMoves() {
        HashSet<CombinatorialGame> moves = new HashSet<>();
        HashSet<CombinatorialGame> captures = new HashSet<>();
        for(int i = 0; i < board.length-1; i++){                // first dimension, vertical
            for(int j = 0; j < board[0].length; j++){           // second dimension, horizontal
                if (board[i][j] == pieceType.left){
                    if (j > 0){                                 // can we move left horizontally?
                        if (board[i+1][j-1] == null) {            // non cap
                            Diagonals newBoard = new Diagonals(board);
                            newBoard.movePiece(i, j, i+1, j-1);         // move up and left
                            moves.add(newBoard);
                        } else if (board[i+1][j-1] == pieceType.right){              // capture
                            Diagonals newBoard = new Diagonals(board);
                            newBoard.movePiece(i, j, i+1, j-1);         // move up and left with a cap
                            captures.add(newBoard);
                        }
                    }
                    if (j < (board[0].length - 1)){                 // can we move right?
                        if (board[i+1][j+1] == null) {              // non cap
                            Diagonals newBoard = new Diagonals(board);
                            newBoard.movePiece(i, j, i+1, j+1);         // move up and right
                            moves.add(newBoard);
                        } else if (board[i+1][j+1] == pieceType.right){              // capture
                            Diagonals newBoard = new Diagonals(board);
                            newBoard.movePiece(i, j, i+1, j+1);         // move up and right with a cap
                            captures.add(newBoard);
                        }
                    }
                }
            }
        }
        if (captures.isEmpty()){
            // captures are mandatory, if they no not exist you can simply return list of normal moves
            return moves;
        }
        return captures;
    }

    @Override
    public HashSet<CombinatorialGame> getRightMoves() {
        HashSet<CombinatorialGame> moves = new HashSet<>();
        HashSet<CombinatorialGame> captures = new HashSet<>();
        for(int i = 1; i < board.length; i++){                  // first dimension, vertical
            for(int j = 0; j < board[0].length; j++){           // second dimension, horizontal
                if (board[i][j] == pieceType.right){
                    if (j > 0){                                 // can we move left?
                        if (board[i-1][j-1] == null) {            // non capture
                            Diagonals newBoard = new Diagonals(board);
                            newBoard.movePiece(i, j, i-1, j-1);             // move down and left
                            moves.add(newBoard);
                        } else if (board[i-1][j-1] == pieceType.left){                  // capture
                            Diagonals newBoard = new Diagonals(board);
                            newBoard.movePiece(i, j, i-1, j-1);             // move down and left with cap
                            captures.add(newBoard);
                        }
                    }
                    if (j < (board[0].length - 1)){             // can we move right?
                        if (board[i-1][j+1] == null) {            // non cap
                            Diagonals newBoard = new Diagonals(board);
                            newBoard.movePiece(i, j, i-1, j+1);             // move down and right
                            moves.add(newBoard);
                        } else if (board[i-1][j+1] == pieceType.left){                    // capture
                            Diagonals newBoard = new Diagonals(board);
                            newBoard.movePiece(i, j, i-1, j+1);             // move down and right with cap
                            captures.add(newBoard);
                        }
                    }
                }
            }
        }
        if (captures.isEmpty()){
            return moves;
        }
        return captures;
    }

    public void movePiece(int srcX, int srcY, int destX, int destY){
        pieceType type = board[srcX][srcY];
        board[srcX][srcY] = null;
        board[destX][destY] = type;
    }

    @Override
    public int hashCode() {
        int result = 37;
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if (board[i][j] == null){
                    result = 37 * result + 7;
                } else if (board[i][j] == pieceType.left) {
                    result = 37 * result + 9;
                } else if (board[i][j] == pieceType.right) {
                    result = 37 * result + 11;
                }
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Diagonals)) {
            return false;
        } else {
            pieceType[][] compBoard = ((Diagonals) obj).board;
            for(int i = 0; i < board.length; i++){
                for(int j = 0; j < board[0].length; j++){
                    if (board[i][j] != compBoard[i][j]){
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
