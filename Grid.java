import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Grid {
	    public Pair[][] grid;
	    public static final int MAX_ITER = 300;
	    //public static final int ARRAY_SIZE = 149;
	    public double CROSSOVER_PROB = 0.2;
	    public double MUTATE_PROB = 0.01; 
	    public Grid(int n , boolean init) {
	    	grid = new Pair[n][n];
	    	if (init) {
	            for (int i=0; i < grid.length; i++) {
	                for (int j=0; j < grid.length; j++) {
	                    this.set(i, j, new Pair(new CA(3), new CellList(149)));
	                }
	            }
	        }
	    }
	    
	    public void set(int i, int j, Pair pair) {
	        grid[adjustIndex(i)][adjustIndex(j)] = pair;
	    }
	    
	    public int adjustIndex(int i) {
	        return (((i % grid.length) + grid.length) % grid.length);  // Periodic boundary conditions
	    }
	    
	    public void set(int i, int j, CA ca, CellList ic) {
	        this.set(i, j, new Pair(ca, ic));
	    }
	    
	  
	    
	    public void init() {
	        for (int i=0; i < grid.length; i++) {
	            for (int j=0; j < grid.length; j++) {
	                this.set(i, j, new CA(3), new CellList(149));
	                this.grid[adjustIndex(i)][adjustIndex(j)].ca.ruletable.init();
	                this.grid[adjustIndex(i)][adjustIndex(j)].ic.init();
	                
	            }
	        }
	    }
	    
	    /* the final configuration fc correctly classifies the initial configuration ic */
	    public static boolean isCorrect(CellList ic, CellList fc) {
	       
	        if ((double)ic.numofOnes()/ic.cells.length > 0.5) {
	            return fc.isAllOne();
	        }
	        return fc.isAllZero();
	    }
	    
	    public void computeFitnesses() {
	        
	        for (int i = 0; i < grid.length; i++) {
	            for (int j = 0; j < grid.length; j++) {
	            	 CA ca = this.grid[adjustIndex(i)][adjustIndex(j)].ca;
	     	        CellList ic = grid[adjustIndex(i)][adjustIndex(j)].ic;
	     	        CellList finalState = new CellList(ic.cells.length);
	     	        int numCorrect = 0;
	     	        boolean correct = false;
	     	        
	     	        for (int d = i-1; d <= i + 1; d++) {
	     	            for (int e = j-1; e <= j+1; e++) {
	     	                ic = this.grid[adjustIndex(d)][adjustIndex(e)].ic;
	     	                finalState = ca.run(ic, MAX_ITER,false);
	     	                correct = isCorrect(ic, finalState);
	     	                if (correct) {
	     	                    numCorrect++;
	     	                }

	     	                if ((d == i) && (e == j)) {
	     	                	if (correct) {
	     	        	            this.grid[adjustIndex(i)][adjustIndex(j)].setICFitness(0.0);
	     	        	        }
	     	                	else {
	     	                    this.grid[adjustIndex(i)][adjustIndex(j)].setICFitness(Math.abs(ic.density() - 0.5));
	     	                	}
	     	                }
                          }
	     	        }
	                 double caFitness = numCorrect/9.0;  // There are nine cells in the neighborhood  
	     	        this.grid[adjustIndex(i)][adjustIndex(j)].setCAFitness(caFitness);

	            }
	        }
	    }
	    
	    public List<Pair> getNeighbors(int row, int col) {
	        List<Pair> neighbors = new ArrayList<Pair>();
	        for (int i = row-1; i <= row+1; i++) {
	            for (int j = col-1; j <= col+1; j++) {
	                neighbors.add(grid[adjustIndex(i)][adjustIndex(j)]);
	            }
	        }
	        return neighbors;  
	    }
	    
	    public void sortByCAFitness(List<Pair> cells) {
	        Collections.sort(cells, Collections.reverseOrder());
	    }

	    public void sortByICFitness(List<Pair> cells) {
	        Collections.sort(cells, Pair.IC_Comparator.reversed());
	    }
	    
	   
	    
	    public Pair selectByRank(List<Pair> sorted_cells) {
	        int N = sorted_cells.size() - 1;  // Number of partitions
	        double[] probabilities = new double[N];
	        for (int i = 0; i < N; i++) {
	            probabilities[i] = Math.pow(0.5, i+1);  // probability = (0.5)^rank
	        }
	        int n = probabilities.length;
	        double[] partition = new double[n];
	        
	        partition[0] = probabilities[0];
	        for (int i = 1; i < n; i++) {
	      
	            partition[i] = partition[i-1]+probabilities[i];
	        }

	        double randomNum = Math.random();

	        
	        if (randomNum < partition[0]) return sorted_cells.get(0);
	        for (int i = 1; i < n; i++) {
	            if ((randomNum >= partition[i-1]) && (randomNum < partition[i])) {
	                return sorted_cells.get(i);
	            }
	        }
	        return sorted_cells.get(n);
	    }
	       
	    public Grid selectParents() {
	        Grid newGrid = new Grid(grid.length, false);
	        for (int i = 0; i < grid.length; i++) {
	            for (int j = 0;j < grid.length; j++) {
	            	 List<Pair> neighbors = getNeighbors(i, j);
		              
		              // Select Parent CA
		              sortByCAFitness(neighbors);
		              CA parentCA = selectByRank(neighbors).ca;

		              // Select Parent IC
		              sortByICFitness(neighbors);
		              CellList parentIC = selectByRank(neighbors).ic;
	                newGrid.set(i, j, new Pair(parentCA, parentIC));
	            }
	        }
	        return newGrid;
	    }
        
	    
	    public Grid crossoverCAs() {
	        Grid newGrid = new Grid(grid.length, false);
	        CA currentCA;
	        CA otherCA;
	        List<Pair> neighbors;
	        int index;
	        for (int row = 0; row < grid.length; row++) {
	            for (int col = 0; col < grid.length; col++) {
	                newGrid.set(row, col, this.grid[adjustIndex(row)][adjustIndex(col)]);
	                if (Math.random() < CROSSOVER_PROB) {
	                    currentCA = newGrid.grid[adjustIndex(row)][adjustIndex(col)].ca;
	                    neighbors = getNeighbors(row, col);
	                    index = (int) Math.random()*neighbors.size();
	                    otherCA = neighbors.get(index).ca;
	                    newGrid.grid[adjustIndex(row)][adjustIndex(col)].ca=currentCA.crossover(otherCA);
	                }
	            }
	        }
	        return newGrid;
	    }

	    public void mutate() {
	        for (int row = 0; row < grid.length; row++) {
	            for (int col = 0; col < grid.length; col++) {
	            	grid[adjustIndex(row)][adjustIndex(col)].ca.ruletable.mutate(MUTATE_PROB);
	            	grid[adjustIndex(row)][adjustIndex(col)].ic.mutate(MUTATE_PROB);
	            }
	        }
	    }
       
	    
	    public Pair getBestCell() {
	    	// Grid[][] to list()
	        List<Pair> coevList = new ArrayList<Pair>();
	        for (int i = 0; i < grid.length; i++) {
	            for (int j = 0; j < grid.length; j++) {
	                coevList.add(grid[adjustIndex(i)][adjustIndex(j)]);
	            }
	        }
	        sortByCAFitness(coevList);
	        Pair best = coevList.get(0);
	        System.out.println("Best CA: ");
	        System.out.println(best.ca);
	        System.out.println("Fitness: " + best.caFitness);
	        return best;
	    }

	   
	    public void printCAFitnesses() {
	        System.out.println("CA Fitnesses:");
	        for (int i = 0; i < grid.length; i++) {
	            for (int j = 0; j < grid.length; j++) {
	                System.out.printf("%.2f ", grid[adjustIndex(i)][adjustIndex(j)].caFitness);
	            }
	            System.out.println();
	        }
	    }

	    public void printICFitnesses() {
	        System.out.println("IC Fitnesses:");
	        for (int i = 0; i < grid.length; i++) {
	            for (int j = 0; j < grid.length; j++) {
	                System.out.printf("%.2f ", grid[adjustIndex(i)][adjustIndex(j)].icFitness);
	            }
	            System.out.println();
	        }
	    }
        }

        

