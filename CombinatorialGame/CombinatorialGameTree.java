package CombinatorialGame;

import java.util.HashMap;
import java.util.HashSet;

/**
 * The CombinatorialGame.CombinatorialGameTree implementation.  Combinatorial Games can be solved
 * by implementing the CombinatorialGame.CombinatorialGame interface in a representation of the game in Java.
 * Then, a call to the constructor will create and reduce the tree to one outcome class.
 * Then, a call to the getOutcomeClass will return the outcome class of the entire game.
 * @author Conor Brown
 */
public class CombinatorialGameTree {
    private HashSet<CombinatorialGameTree> left;
    private HashSet<CombinatorialGameTree> right;
    private CombinatorialGame game;
    private OutcomeClass treeClass;
    // if a game has been solved, store it in the cache to reduce memory and compute time
    private static HashMap<CombinatorialGame, OutcomeClass> gameCache = new HashMap<>();
    // if a game has been created, store the move options in the cache
    private static HashMap<CombinatorialGame, HashSet<CombinatorialGame>> leftMoveCache = new HashMap<>();
    private static HashMap<CombinatorialGame, HashSet<CombinatorialGame>> rightMoveCache = new HashMap<>();
    private static int cacheCounter = 0;
    private static int treeCounter = 0;

    private static int usingLeftCache = 0;
    private static int notUsingLeftCache = 0;


    /**
     * The only public method for the CombinatorialGame.CombinatorialGameTree.  Takes in a CombinatorialGame.CombinatorialGame and
     * creates all possible left moves and right moves for each game state, then reduces
     * them through outcome class reduction until one
     * @param game A CombinatorialGame.CombinatorialGame
     */
    public CombinatorialGameTree(CombinatorialGame game){
        treeCounter++;
        this.game = game;
        left = new HashSet<>();
        right = new HashSet<>();
        buildTree(game);
        resolveTree();
    }

    public int getCacheCounter() {
        return cacheCounter;
    }

    public int getTreeCounter() {
        return treeCounter;
    }

    public int getCacheSize() {
        return gameCache.size();
    }

    public CombinatorialGameTree(OutcomeClass treeClass){
        this.treeClass = treeClass;
    }

    /**
     * Method called in the constructor to create all possible game states from a game state
     * @param game the game passed in from the tree constructor
     */
    private void buildTree(CombinatorialGame game){
        if (treeClass != null){     // for outcomeClass constructed trees
            return;
        }
        HashSet<CombinatorialGame> leftMoves;
        if (leftMoveCache.containsKey(game)){
            leftMoves = leftMoveCache.get(game);

            usingLeftCache++;
            if (usingLeftCache % 100 == 0){
                System.out.println("Using: " + usingLeftCache);
            }
        } else {
            leftMoves = game.getLeftMoves();
            leftMoveCache.put(game, leftMoves);
            notUsingLeftCache++;
            if (notUsingLeftCache % 100 == 0){
                System.out.println("Not using: " + notUsingLeftCache);
            }
        }
        if (leftMoves.size() == 0){                                         // base case
            return;
        }
        for( CombinatorialGame move : leftMoves){                               // else
            CombinatorialGameTree moveTree = new CombinatorialGameTree(move);
                left.add(moveTree);
            }

        HashSet<CombinatorialGame> rightMoves;
        if (rightMoveCache.containsKey(game)){
            rightMoves = rightMoveCache.get(game);
        } else {
            rightMoves = game.getRightMoves();
            rightMoveCache.put(game, rightMoves);
        }
        if (rightMoves.size() == 0){                                         // base case
            return;
        }
        for( CombinatorialGame move : rightMoves){                               // else
            CombinatorialGameTree moveTree = new CombinatorialGameTree(move);
                right.add(moveTree);
            }

    }

    /**
     * Method called in the constructor to resolve all created leaf nodes
     * @return the outcome class of the leaf games
     */
    private OutcomeClass resolveTree(){
        if (treeClass != null){
            return treeClass;
        }
        HashSet<OutcomeClass> leftOutcomes = new HashSet<>();
        HashSet<OutcomeClass> rightOutcomes = new HashSet<>();
        if (left.size() > 0) {
            leftOutcomes = resolveTreeSide(left);
        }
        if (right.size() > 0) {
            rightOutcomes = resolveTreeSide(right);
        }
        boolean leftLP = false;         // L union P for left games
        boolean rightRP = false;        // R union P for right games

        // initialize boolean values for truth table
        if (leftOutcomes.contains(OutcomeClass.L) || leftOutcomes.contains(OutcomeClass.P)){
            leftLP = true;
        }
        if (rightOutcomes.contains(OutcomeClass.R) || rightOutcomes.contains(OutcomeClass.P)){
            rightRP = true;
        }

        // based on boolean values resolve tree to one outcome class
        if (leftLP && rightRP){
            treeClass = OutcomeClass.N;
        }
        if (leftLP && !rightRP){
            treeClass = OutcomeClass.L;
        }
        if (!leftLP && rightRP){
            treeClass = OutcomeClass.R;
        }
        if (!leftLP && !rightRP){
            treeClass = OutcomeClass.P;
        }
        return treeClass;
    }

    private HashSet<OutcomeClass> resolveTreeSide(HashSet<CombinatorialGameTree> treeSide){
        HashSet<OutcomeClass> sideOutcomes = new HashSet<>();
        for(CombinatorialGameTree tree : treeSide) {
            if (gameCache.containsKey(tree.getGame())) {            // check if the game has been solved already
                sideOutcomes.add(gameCache.get(tree.getGame()));    // use constructor for
                cacheCounter++;
            }

            OutcomeClass outcomeClass = tree.resolveTree();         // resolve tree to get outcomeClass
            gameCache.put(tree.getGame(), outcomeClass);            // put in the cache
            sideOutcomes.add(outcomeClass);                         // continue with tree building

            if (sideOutcomes.size() == 4) {                         // all 4 outcome classes
                break;
            }
        }
        return sideOutcomes;
    }

    /**
     * toString for the entire CombinatorialGame.CombinatorialGameTree
     * useful foe debug purposes
     * @return the string representation of the entire tree
     */
    public String toString(){
        String out = "";
        if (game == null){
            return out;
        }
        out = out + "This: " + game.toString();
        for (CombinatorialGameTree gameTree : left) {
            out = out + "Left: " + gameTree.toString() + "\n";
        }
        for (CombinatorialGameTree gameTree : right){
            out = out + "Right: " + gameTree.toString() + "\n";
        }
        return out;
    }

    /**
     * Allows the gameCache to compare games of a tree
     * @return the CombinatorialGame that is the root of a tree
     */
    public CombinatorialGame getGame() {
        return game;
    }

    /**
     * gets the outcome class of the resolved tree
     * @return an CombinatorialGame.OutcomeClass
     */
    public OutcomeClass getOutcomeClass() {
        return treeClass;
    }
}
