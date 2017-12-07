package Games.Subtraction;

public class SubSolver {
    public static void main(String[] args) {
        if (args.length != 2){
            System.out.println("Usage: Games.Subtraction.SubSolver stackSize \"subValue1 subValue2 ... \"");
            System.exit(1);
        }

        int stackSize = Integer.parseInt(args[0]);
        String[] stringVals = args[1].split(" ");
        int[] intVals = new int[stringVals.length];
        for (int i = 0; i < stringVals.length; i++){
            intVals[i] = Integer.parseInt(stringVals[i]);
        }
        CombinatorialGame game = new SubtractionGame(stackSize, intVals);
        CombinatorialGameTree tree = new CombinatorialGameTree(game);
        System.out.println(tree.getOutcomeClass());
    }
}
