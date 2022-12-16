
public class CA {
	public CellList ruletable;
	public int neighborhoodSize = 3;
	
	public CA(int neighborhoodSize ) {
		this.neighborhoodSize = neighborhoodSize;
        ruletable = new CellList((int) Math.pow(2, 2*neighborhoodSize+1)); /*length = 2^(2n+1)*/
	}
    
	 public CA(CellList table) {
	        this.ruletable = table;
	    }
	 
	 public int length() {
	        return ruletable.cells.length;
	    }
	 
	 public CellList makeNextState(CellList oldState) {
		 int neigh;
		 CellList nextState = new CellList(oldState.cells.length);
		 for (int i = 0;i < oldState.cells.length;i++) {
			 int neighborhood = 0;
			 int mask = 0;
			 int n =1;
			 int neighborhoodLength = 2 * neighborhoodSize + 1;
			 for (int e= i-neighborhoodSize; e < i + neighborhoodSize+1; e++) {
				 mask = oldState.get(e) << (neighborhoodLength - n);
				 neighborhood = neighborhood | mask;
				 n ++;
				 
			 }
			 neigh = neighborhood;
	         nextState.set(i, ruletable.get(neigh));
		 }
		 return nextState;
	 }
	 
	 public CellList run(CellList initialState, int maxIter, boolean printing) {
	        CellList state = initialState;
	        if (printing) 
	            System.out.println(initialState);
	        for (int i = 0; i < maxIter; i++) {
	            state = makeNextState(state);
	            if (printing)
	                System.out.println(state);
	            if (state.isAllOne() || state.isAllZero()) {
	                break;
	            }
	        }
	        return state;
	    }
	 
	 public CA crossover(CA other) {
	       
	        return new CA(this.ruletable.crossover(other.ruletable));
	    }
	 @Override
	    public String toString() {
	        return ruletable.toString();
	    }
	 
}
