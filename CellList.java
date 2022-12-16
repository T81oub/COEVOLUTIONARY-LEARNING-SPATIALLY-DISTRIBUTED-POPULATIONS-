import java.util.Random;

public class CellList {
    public boolean[] cells;
    
    public CellList(int CellList_length) {
    	cells = new boolean[CellList_length];
    	for (int i = 0; i < CellList_length; i++) {
    		cells[adjustIndex(i)] = false;
    	}
    }
    
    public CellList(CellList cellsl) {
        
        for (int i = 0; i < cellsl.cells.length; i++) {
            this.set(i, cellsl.get(i));
        }
    }
    
    public int get(int i) {
        
        if (cells[adjustIndex(i)]) {
            return 1;
        }
        return 0;
    }
    
    public void set(int i, int bool) {
        if (bool == 1) {
        	cells[adjustIndex(i)] = true;
        }
        else {
        	cells[adjustIndex(i)] = false;
        }
    }
    
    public void setAllOne() {
        for (int i = 0; i < cells.length; i++) {
        	cells[adjustIndex(i)] = true;
        }
    }
    
    public void setAllZero() {
        for (int i = 0; i < cells.length; i++) {
        	cells[adjustIndex(i)] = false;
        }
    }
    
    public void swap(int i, int j) {
        int n = this.get(i);
        this.set(i, this.get(j));
        this.set(j, n);
    }

    
    public void init() {
        
    	this.setAllOne();
    	for (int i=0; i < cells.length; i++) {
            if (Math.random() < 0.5) {
                this.spin(i);
            }
        }
        
    }
    
   
    public int adjustIndex(int i) {
        return (((i % cells.length) + cells.length) % cells.length);  // Periodic boundary conditions
    }
    
    public boolean isAllZero() {
    	for (int i = 0; i < cells.length; i++) {
    		if(cells[adjustIndex(i)]) return false;
    	}
    	return true;
    }
    
    public boolean isAllOne() {
    	for (int i = 0; i < cells.length; i++) {
    		if(cells[adjustIndex(i)]==false) return false;
    	}
    	return true;
    }
    
    public int numofOnes() {
    	int n = 0;
    	for (int i = 0; i < cells.length; i++) {
    		if(cells[adjustIndex(i)]) n++;
    	}
    	return n;
    }
    
    public double density() {
    	return (double) this.numofOnes()/cells.length;
    }
	
    public CellList crossover(CellList Cell2) {
    	
    		Random random = new Random();
    		CellList Child1 = new CellList(cells.length);
    		CellList Child2 = new CellList(cells.length);
    		int crossover_length = random.nextInt(cells.length);
    		for(int i = 0;i<crossover_length;i++) {
    			
    	            Child1.set(i, this.get(i));
    	            Child2.set(i, Cell2.get(i));
    	        
    		}
    		for (int i = crossover_length; i < cells.length; i++) {
                Child1.set(i, Cell2.get(i));
                Child2.set(i, this.get(i));
            }
    		int flipCoin = random.nextInt(2);
    	        if (flipCoin == 0) {
    	            return Child1;
    	        }
    	        return Child2;
    	
    }
    
    public void spin(int i) {
        
        cells[adjustIndex(i)] = !cells[adjustIndex(i)];
    }
    
    public void mutate(double prob) {
        for (int i = 0; i < cells.length; i++) {
            if (Math.random() < prob) {
                spin(i);
            }
        }
    }
    
    public String toString() {
        String cellString = "";
        for (int i = 0; i < cells.length; i++) {
            cellString += this.get(i);
        }
       
        return cellString;
    }
    

}
