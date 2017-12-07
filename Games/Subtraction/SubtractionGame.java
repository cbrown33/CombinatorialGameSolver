package Games.Subtraction;
import CombinatorialGame.*;

import java.util.HashSet;

public class SubtractionGame implements CombinatorialGame{
    private int stackSize;
    private int[] values;

    public SubtractionGame(int stackSize, int[] values){
        this.stackSize = stackSize;
        this.values = values;
    }

    @Override
    public HashSet<CombinatorialGame> getLeftMoves() {
        return getMoves();
    }

    @Override
    public HashSet<CombinatorialGame> getRightMoves() {
        return getMoves();
    }

    /**
     * This can be used for right and left because this is an impartial game
     */
    private HashSet<CombinatorialGame> getMoves() {
        HashSet<CombinatorialGame> moves = new HashSet<>();
        for (int i : values){
            if (i > stackSize) {
                moves.add(new SubtractionGame(stackSize - i, values));
            }
        }
        return moves;
    }
}
