import java.util.*;

public class Pair  implements Comparable<Pair> {
	public CA ca;
    public CellList ic;
    public double caFitness = 0.0;
    public double icFitness = 0.0;
    public boolean caFitnessComputed = false;
    public boolean icFitnessComputed = false;
    
    public Pair(CA ca, CellList init) {
        this.ca = ca;
        this.ic = init;
    }
    
    public void setCAFitness(double caFitness) {
        this.caFitness = caFitness;
        caFitnessComputed = true;
    }

    public void setICFitness(double icFitness) {
        this.icFitness = icFitness;
        icFitnessComputed = true;
    }
    
    public void clearFitnesses() {
        caFitness = 0.0;
        icFitness = 0.0;
        caFitnessComputed = false;
        icFitnessComputed = false;
    }

    
    public String toString() {
        String s = "(";
        if (caFitnessComputed) {
            s += String.format("%.2f", this.caFitness);
        }
        else {
            s += "None";
        }
        s += ", ";
        if (icFitnessComputed) {
            s += String.format("%.2f", this.icFitness);
        } 
        else {
            s += "None";
        }
        s += ")";
        return s;  
    }
    
    public int compareTo(Pair other) {
        return (int) Math.signum(this.caFitness - other.caFitness);
    }
    
    public static Comparator<Pair> IC_Comparator = new Comparator<Pair>(){
    	  public int compare(Pair cp1, Pair cp2) {
              return (int) Math.signum(cp1.icFitness - cp2.icFitness);
          }
    };
    
    public static Comparator<Pair> CA_Comparator 
    = new Comparator<Pair>() {
        public int compare(Pair cp1, Pair cp2) {
            return (int) Math.signum(cp1.caFitness - cp2.caFitness);
        }
};

}
