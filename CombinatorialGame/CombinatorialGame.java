package CombinatorialGame;

import java.util.HashSet;

/**
 * An interface needed to have a combinatorial game work with the tree class.
 * @author Conor Brown
 */
public interface CombinatorialGame {
    /**
     * A string representation of an impartial game.
     * Used for printing out the path to get to a solution
     * @return String representation of the game
     */
    String toString();

    /**
     * Gets all legal left moves from a game position
     * @return a HashSet of left moves, moves do not need to be repeated
     */
    HashSet<CombinatorialGame> getLeftMoves();

    /**
     * Gets all legal right moves from a game position
     * @return a HashSet of left moves, moves do not need to be repeated
     */
    HashSet<CombinatorialGame> getRightMoves();
}
