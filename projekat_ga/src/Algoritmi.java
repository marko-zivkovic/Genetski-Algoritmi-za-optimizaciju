import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Algoritmi {

    String exe; String rez;
    public Algoritmi(String e, String r){
        this.exe = e; this.rez=r;
    }
    ///ISPISIVANJE REZULTATA
    public void IspisRez(String s, int flag){
        try (FileWriter f = new FileWriter(rez, true);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter p = new PrintWriter(b);)
        {
            if(flag == 0){p.println(s);}
            else if (flag != 0){p.println(s + " pop-> " + flag);}
        }
        catch (IOException i)
        { i.printStackTrace(); }

    }
    public void OcistiRez()
        {
            try{
                FileWriter fw = new FileWriter(rez, false);
                PrintWriter pw = new PrintWriter(fw, false);
                pw.flush();
                pw.close();
                fw.close();
            }catch(Exception exception){
                System.out.println("Exception have been caught");
            }
    }
    ///PRAVLJENJE HROZOMA i JEDNE POPULACIJE
    public ArrayList<Hromozom> Populacija(int x, int duzina){
        ArrayList<Hromozom> xs = new ArrayList<>();
        Random randomNum = new Random(); int y = 0;
        while(y < x){///broj hromozoma
            Hromozom hromozom = new Hromozom(); int yy = 0;
            while(yy < 33){///nizovi bitova 33 keficijenta
                ArrayList<Integer> bitovi = new ArrayList<Integer>();
                for(int i = 0; i<duzina; i++){
                    int bit = randomNum.nextInt(2);
                    bitovi.add(bit);
                }
                hromozom.getB_codes().add(bitovi);
                yy++;
            }
            xs.add(hromozom); y++;
        }
        return xs;
    }
    ///DEKODOVANJE HROMOZOMA
    public void Dekodovanje(ArrayList<Hromozom> xs,int duzina_hromozoma){
        int n = duzina_hromozoma;
        for(Hromozom h : xs){
           for(ArrayList bitovi: h.getB_codes()){//33
               double res = 0;
               double p2 = 1;
               for(int m = 1; m != n + 1; m++){
                   p2/=2.0;
                   //res += (bitovi.indexOf(m-1) * (2^(1/m)) ) + 2^(1/(n+1));
                   res += bitovi.indexOf(m-1) * p2;
               }
               p2/= 2.0;
               res+=p2;
               h.getD_codes().add(res);
           }
        }

    }
    ///CITANJE EXE FAJLA - FUNKCIJA TROSKA
    public void FunTroska(Hromozom x) throws IOException, InterruptedException {
        // Command to run the executable with arguments
        ArrayList<String> d = new ArrayList<>();
        for(Double br : x.getD_codes()){
            d.add(String.valueOf(br));
        }
        String[] command = {exe,
                d.get(0), d.get(1), d.get(2), d.get(3), d.get(4), d.get(5), d.get(6), d.get(7), d.get(8), d.get(9), d.get(10),
                d.get(11), d.get(12), d.get(13), d.get(14), d.get(15), d.get(16), d.get(17), d.get(18), d.get(19), d.get(20),
                d.get(21), d.get(22), d.get(23), d.get(24), d.get(25), d.get(26), d.get(27), d.get(28), d.get(29), d.get(30),
                d.get(31), d.get(32)};
        // Create a ProcessBuilder object with the command
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        // Start the process and wait for it to complete
        Process process = processBuilder.start();
        int exitCode = process.waitFor();
        try {
            File myObj = new File("trosak.txt");
            Scanner myReader = new Scanner(myObj);
            String data = myReader.nextLine();
            x.setTrosak(Double.parseDouble(data));
            //System.out.println(x.getTrosak() + " " + x.getTro());
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();}

    }
    ///TURNIR SELEKCIJA
    public Hromozom TurnirSel(ArrayList<Hromozom> xs, int br, int brx){
        ArrayList<Hromozom> randumxs = new ArrayList<>();
        Random randomNum = new Random();
        for(int i = 0; i < br; i++){randumxs.add(xs.get(randomNum.nextInt(brx)));}
        Hromozom najbolji = randumxs.get(0);
        double min_trosak = randumxs.get(0).getTrosak();
        for(Hromozom h : randumxs){
            if(min_trosak > h.getTrosak()){
                min_trosak = h.getTrosak();
                najbolji = h;
            }
        }
        return najbolji;
    }
    ///UKRSTANJE - JEDNOTACKASTO UKRSTANJE
    public ArrayList<Hromozom> Ukrstanje(Hromozom h1, Hromozom h2, int duzina) throws IOException, InterruptedException {
        Hromozom h3  = new Hromozom();
        Hromozom h4  = new Hromozom();
        ArrayList<Hromozom> novixs = new ArrayList<>();
        Random randomNum = new Random();
        for(int i = 0; i<33; i++){
            ArrayList<Integer> bit1 = h1.getB_codes().get(i);
            ArrayList<Integer> bit2 = h2.getB_codes().get(i);
            int tacka = randomNum.nextInt(duzina-2) + 1;
            ArrayList<Integer> bit3 = new ArrayList<>();
            ArrayList<Integer> bit4 = new ArrayList<>();
            for(int j = 0; j<duzina; j++){
                if(j<tacka){
                    bit3.add(bit1.get(j));
                    bit4.add(bit2.get(j));
                }
                else if(j>=tacka){
                    bit3.add(bit2.get(j));
                    bit4.add(bit1.get(j));
                }
            }
            h3.getB_codes().add(bit3);
            h4.getB_codes().add(bit4);
            //System.out.println(bit1 +"bit1 i bit2"+ bit2 + tacka);
           // System.out.println(bit3 +"bit3 i bit4"+ bit4);
            //System.out.println("-----------------------");
        }
        novixs.add(h3); novixs.add(h4);
        return novixs;
    }
    ///MUTACIJA - INVERZIJA
    public void Mutacija (Hromozom h, double verovatnoca,int duzina){
        Random randomNum = new Random();
        int ver = (int)verovatnoca;
        for(int i = 0; i < 33; i++){
            int br = randomNum.nextInt(100);
            if(br <= ver){
                ArrayList<Integer> bit = h.getB_codes().get(i);
                ArrayList<Integer> revbit = new ArrayList<>();
                ArrayList<Integer> mutant = new ArrayList<>();
                for(int b = duzina; b > 0; b--){revbit.add(bit.get(b-1));}
                int tacka1 = randomNum.nextInt(duzina/2);
                int tacka2 = randomNum.nextInt(duzina/2) + tacka1;
                for(int j = 0; j < duzina; j++){
                    if(j<tacka1){
                        mutant.add(bit.get(j));
                    }
                    else if(j>=tacka1 && j<tacka2){
                        mutant.add(revbit.get(j));
                    }
                    else if(j>=tacka2){
                        mutant.add(bit.get(j));
                    }
                }
                //System.out.println(bit + "  " + revbit +"  "+mutant);
                h.setNewBit(mutant, i);
            }
        }
    }
    ///SELEKCIJA ZA NOVU POPULACIJU
    public void PotomakSel(ArrayList<Hromozom> pop, ArrayList<Hromozom> tmp,int brx){
        ArrayList<Hromozom> newx = new ArrayList<>();
        for(int i = 0; i<brx; i++){
            newx.add(tmp.get(i));
        }
        pop.clear();
        for(int j = 0; j<brx; j++){
            pop.add(newx.get(j));
        }
    }





}
