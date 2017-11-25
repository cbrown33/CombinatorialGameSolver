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
        for(int i = 0; i < board.length-1; i++){
            for(int j = 0; j < board[0].length; j++){
                if (board[i][j] == pieceType.left){
                    if (j > 0){
                        if (board[i][j-1] == null) {
                            Diagonals newBoard = new Diagonals(board);
                            newBoard.movePiece(i, j, i+1, j-1);
                            moves.add(newBoard);
                        } else if (board[i][j-1] == pieceType.right){
                            Diagonals newBoard = new Diagonals(board);
                            newBoard.movePiece(i, j, i+1, j-1);
                            captures.add(newBoard);
                        }
                    }
                    if (j < (board[0].length - 1)){
                        if (board[i][j+1] == null) {
                            Diagonals newBoard = new Diagonals(board);
                            newBoard.movePiece(i, j, i+1, j+1);
                            moves.add(newBoard);
                        } else if (board[i][j+1] == pieceType.right){
                            Diagonals newBoard = new Diagonals(board);
                            newBoard.movePiece(i, j, i+1, j+1);
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

    @Override
    public HashSet<CombinatorialGame> getRightMoves() {
        HashSet<CombinatorialGame> moves = new HashSet<>();
        HashSet<CombinatorialGame> captures = new HashSet<>();
        for(int i = 1; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if (board[i][j] == pieceType.right){
                    if (j > 0){
                        if (board[i][j+1] == null) {
                            Diagonals newBoard = new Diagonals(board);
                            newBoard.movePiece(i, j, i-1, j-1);
                            moves.add(newBoard);
                        } else if (board[i][j-1] == pieceType.left){
                            Diagonals newBoard = new Diagonals(board);
                            newBoard.movePiece(i, j, i-1, j-1);
                            captures.add(newBoard);
                        }
                    }
                    if (j < (board[0].length - 1)){
                        if (board[i][j+1] == null) {
                            Diagonals newBoard = new Diagonals(board);
                            newBoard.movePiece(i, j, i-1, j+1);
                            moves.add(newBoard);
                        } else if (board[i][j+1] == pieceType.left){
                            Diagonals newBoard = new Diagonals(board);
                            newBoard.movePiece(i, j, i-1, j+1);
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

}
