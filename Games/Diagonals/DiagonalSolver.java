package Games.Diagonals;

import CombinatorialGame.*;

public class DiagonalSolver {
    public static void main(String[] args) {
        int i = 2;
        for(int j = 2; j < 5; j++){
            Diagonals d = new Diagonals(i, j);
            CombinatorialGameTree tree = new CombinatorialGameTree(d);
            System.out.println("Diagonals(" + i + "," + j + ") is in outcome class: " + tree.getOutcomeClass());
            System.out.println("Size of the game cache: " + tree.getCacheSize());
            System.out.println("Times the cache was used: " + tree.getCacheCounter());
            System.out.println("Total game states created: " + tree.getTreeCounter());
        }
//        int j = 2;
//        Diagonals d = new Diagonals(j, i);
//        CombinatorialGameTree tree = new CombinatorialGameTree(d);
//        System.out.println("Diagonals(" + j + "," + i + ") is in outcome class: " + tree.getOutcomeClass());
//        System.out.println("Size of the game cache: " + tree.getCacheSize());
//        System.out.println("Times the cache was used: " + tree.getCacheCounter());
//        System.out.println("Total game states created: " + tree.getTreeCounter());
//        System.out.println(tree.getTreeCounter() - tree.getCacheCounter() == tree.getCacheSize()+1 ?
//                "Math works!" : "Math doesn't work :(");
    }
}
