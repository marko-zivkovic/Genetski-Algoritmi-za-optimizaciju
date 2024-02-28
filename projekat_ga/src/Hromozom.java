import java.util.ArrayList;
import java.util.Objects;

public class Hromozom implements Comparable<Hromozom>{
    private ArrayList<Double> d_codes;
    private ArrayList<ArrayList> b_codes;
    private double trosak;
    private int tro;
    public Hromozom(){
        b_codes = new ArrayList<>();
        d_codes = new ArrayList<>();
        trosak = 0.0;
    }

    public ArrayList<Double> getD_codes() {return d_codes;}
    public void setD_codes(ArrayList<Double> d_codes) {this.d_codes = d_codes;}
    public ArrayList<ArrayList> getB_codes() {return b_codes;}
    public void setB_codes(ArrayList<ArrayList> b_codes) {this.b_codes = b_codes;}
    public double getTrosak() {return trosak;}
    public void setTrosak(double trosak) {
        this.trosak = Double.parseDouble(String.format("%.2f", trosak));
        this.tro = (int) trosak;
    }
    public int getTro() {
        return tro;
    }
    @Override
    public int compareTo(Hromozom o) {
        return this.tro - o.getTro();
    }
    public void setNewBit(ArrayList<Integer> mutant, int br){
        this.b_codes.remove(br);
        this.b_codes.add(mutant);
    }
}
