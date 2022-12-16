import java.util.*;
public class main {

	public static final int GRID_SIZE = 20;
    public static final int MAX_ITER = 10;  // It takes about 2 minutes to run 10 iterations. -> about 300 iterations per hour.

    // By default, the CAs and ICs are initialized using a uniform distribution
    
    
    public static final double CROSSOVER_PROB = 0.2;  // Probability a parent CA will crossover each generation
    public static final double MUTATE_PROB = 0.01;  // Probability each bit of a CA or IC will mutate each generation

    
    public static void main(String[] args) {
    	Grid coev = new Grid(GRID_SIZE, true);
        Grid nextGen;
        Pair bestCell;
        coev.init();
        System.out.println("Initial grid initialized.");
        for (int i = 1; i <= MAX_ITER; i++) {
            System.out.printf("Iteration %d of %d, computing fitnesses:\n", i, MAX_ITER);
            coev.computeFitnesses();
            coev.printCAFitnesses();
            coev.printICFitnesses();
            System.out.println();
            bestCell = coev.getBestCell();
            //
            CellList ic = new CellList(149);
            ic.init();
            System.out.println("Sample run with random initial condition:");
            System.out.println(ic);
            System.out.printf("Density: %.2f\n", ic.density());
            CellList finalState = bestCell.ca.run(ic, 300,true);
            if (Grid.isCorrect(ic, finalState)) {
                System.out.println("Correct");
            }
            else {
                System.out.println("Incorrect");
            }
           
            System.out.println();

            nextGen = coev.selectParents();
            nextGen = nextGen.crossoverCAs();
            nextGen.mutate();
            coev = nextGen;
        }
        
    }

}
