import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Analiza {
    public  int broj_hromozoma;
    public  int broj_iteracija;
    private Algoritmi algoritmi;
    private ArrayList<String> config;
    String exefile; String rez;

    public Analiza(int x, int i, ArrayList<String> c) throws IOException, InterruptedException {
        this.broj_hromozoma = x;
        this.broj_iteracija = i;
        this.config = c;
        exefile = config.get(12);
        rez=config.get(14);
        this.algoritmi = new Algoritmi(exefile,rez);
        algoritmi.OcistiRez();
        Funkcija();
    }
    int brParenje; double brMutacija; double selekcija;
    int leftBound; int rightBound; int duzinaHromo; int ciklus; int populacijaplus;
    private void Funkcija() throws IOException, InterruptedException {
        ciklus = Integer.parseInt(config.get(0));
        brParenje = Integer.parseInt(config.get(5));
        brMutacija = Double.parseDouble(config.get(6));
        selekcija = Double.parseDouble(config.get(7));
        leftBound = Integer.parseInt(config.get(9));
        rightBound = Integer.parseInt(config.get(10));
        duzinaHromo = Integer.parseInt(config.get(11));
        populacijaplus = Integer.parseInt(config.get(13));
        int brCiklus = 1;

        while(brCiklus < ciklus+1){///3
            algoritmi.IspisRez("ZADATAK " + brCiklus , 0);
            int brPopulacije = 0;
            ArrayList<Hromozom> populacija = algoritmi.Populacija(broj_hromozoma,duzinaHromo);
            algoritmi.Dekodovanje(populacija,duzinaHromo);
            //for(Hromozom h : populacija){System.out.println(h.getD_codes() + "\n" +  h.getD_codes().size());}
            //for(Hromozom h : populacija){System.out.println(h.getB_codes() + "\n" +  h.getB_codes().size());}
            double min_trosak = 100;
            Hromozom najbolji_hromozom = new Hromozom();

            ///FUNKCIJA TROSKA
            for(Hromozom h : populacija){
                if(h.getTrosak() == 0.0) {algoritmi.FunTroska(h);}
            }
            ///SORT
            Collections.sort(populacija); //for(Hromozom h : populacija){System.out.println(h.getTrosak());}

            while(brPopulacije < broj_iteracija){///////////////150

                double srednji_trosak = 0;
                ArrayList<Hromozom> populacijaTmp = new ArrayList<>();
                for(Hromozom pop: populacija){populacijaTmp.add(pop);}
                while(populacijaTmp.size() <= broj_hromozoma + populacijaplus){
                    Hromozom h1 = algoritmi.TurnirSel(populacija,brParenje,broj_hromozoma);
                    Hromozom h2 = algoritmi.TurnirSel(populacija,brParenje,broj_hromozoma);
                    //System.out.println(h1.getTrosak()+" h1 i h2 "+h2.getTrosak()+"\n");
                    ArrayList<Hromozom> novixs = algoritmi.Ukrstanje(h1,h2,duzinaHromo);
                    Hromozom h3 = novixs.get(0);
                    Hromozom h4 = novixs.get(1);
                    algoritmi.Mutacija(h3,brMutacija,duzinaHromo);
                    algoritmi.Mutacija(h4,brMutacija,duzinaHromo);
                    ArrayList<Hromozom> h3h4 = new ArrayList<>();
                    h3h4.add(h3); h3h4.add(h4);
                    algoritmi.Dekodovanje(h3h4,duzinaHromo);
                    algoritmi.FunTroska(h3);
                    algoritmi.FunTroska(h4);
                    //System.out.println(h3.getTrosak() + "  h3");
                    //System.out.println(h4.getTrosak() + "  h4" + "\n");
                    populacijaTmp.add(h3);
                    populacijaTmp.add(h4);
                }
                Collections.sort(populacijaTmp);
                //for(Hromozom p: populacija){System.out.println(p.getTrosak());}
                algoritmi.PotomakSel(populacija,populacijaTmp,broj_hromozoma);
                //System.out.println("Nova generacija");
                //for(Hromozom p: populacija){System.out.println(p.getTrosak());}

                min_trosak = populacija.get(0).getTrosak();
                if(min_trosak == 100){break;} double suma = 0;
                for(Hromozom h : populacija){suma += h.getTrosak();}
                srednji_trosak = Double.parseDouble(String.format("%.2f", suma/broj_hromozoma));
                brPopulacije++;
                algoritmi.IspisRez("[najmanji trosak] " + min_trosak + " [srednji trosak] " + srednji_trosak,brPopulacije);
                if(brPopulacije == broj_iteracija)
                {najbolji_hromozom = populacija.get(0);}
           }
            algoritmi.IspisRez("NAJBOLJI HROMOZOM - bitovi",0);

            for(int dd = 0; dd<33; dd++){
                ArrayList<Integer> bit =  najbolji_hromozom.getB_codes().get(dd);
                String s = bit.toString();
                Double d = najbolji_hromozom.getD_codes().get(dd);
                String dcode = d.toString();
                algoritmi.IspisRez(s + " d_code " + dcode ,0);
            }

            brCiklus++;

        }


    }
}
