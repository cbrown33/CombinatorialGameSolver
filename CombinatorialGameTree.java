import java.util.HashSet;

/**
 * The CombinatorialGameTree implementation.  Combinatorial Games can be solved
 * by implementing the CombinatorialGame interface in a representation of the game in Java.
 * Then, a call to the constructor will create and reduce the tree to one outcome class.
 * Then, a call to the getOutcomeClass will return the outcome class of the entire game.
 * @author Conor Brown
 */
public class CombinatorialGameTree {
    private HashSet<CombinatorialGameTree> left;
    private HashSet<CombinatorialGameTree> right;
    private CombinatorialGame game;
    private OutcomeClass treeClass;

    /**
     * The only public method for the CombinatorialGameTree.  Takes in a CombinatorialGame and
     * creates all possible left moves and right moves for each game state, then reduces
     * them through outcome class reduction until one
     * @param game A CombinatorialGame
     */
    public CombinatorialGameTree(CombinatorialGame game){
        this.game = game;
        left = new HashSet<>();
        right = new HashSet<>();
        buildTree(game);
        resolveTree();
    }

    /**
     * Method called in the constructor to create all possible game states from a game state
     * @param game the game passed in from the tree constructor
     */
    private void buildTree(CombinatorialGame game){
        HashSet<CombinatorialGame> leftMoves = game.getLeftMoves();
        if (leftMoves.size() == 0){                                         // base case
            return;
        }
        for( CombinatorialGame move : leftMoves){                               // else
            CombinatorialGameTree moveTree = new CombinatorialGameTree(move);
                left.add(moveTree);
            }

        HashSet<CombinatorialGame> rightMoves = game.getRightMoves();
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
        if (left.size() > 0 || right.size() > 0) {
            HashSet<OutcomeClass> leftOutcomes = new HashSet<>();
            HashSet<OutcomeClass> rightOutcomes = new HashSet<>();
            for (CombinatorialGameTree lTree : left) {
                leftOutcomes.add(lTree.resolveTree());
                if(leftOutcomes.size()==4){                     // all 4 outcome classes
                    break;
                }
            }
            for (CombinatorialGameTree rTree : right) {
                rightOutcomes.add(rTree.resolveTree());
                if(leftOutcomes.size()==4){                     // all 4 outcome classes
                    break;
                }
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
        treeClass = OutcomeClass.P;
        return treeClass;                                                  // previous wins if no move available
    }

    /**
     * toString for the entire CombinatorialGameTree
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
     * gets the outcome class of the resolved tree
     * @return an OutcomeClass
     */
    public OutcomeClass getOutcomeClass() {
        return treeClass;
    }
}
