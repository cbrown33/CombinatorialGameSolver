package Games.Diagonals;

import CombinatorialGame.*;

public class DiagonalSolver {
    public static void main(String[] args) {
        Diagonals d = new Diagonals(4, 10);
        CombinatorialGameTree tree = new CombinatorialGameTree(d);
        System.out.println(tree.getOutcomeClass());
    }
}
