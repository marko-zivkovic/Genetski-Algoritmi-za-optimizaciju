import com.sun.security.auth.login.ConfigFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static int broj_hromozoma;
    public static int broj_iteracija; ///broj generacija
    static ArrayList<String> config;

    public static void main (String [] args) throws IOException, InterruptedException {
        int input;
        boolean flag = true;
        config = new ArrayList<>();
        ///////////citamo config.txt
        try {
            File myObj = new File("projekat_ga/config.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataS = data.split(" ");
                config.add(dataS[1]);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();}
        ///////////

        Scanner myObj = new Scanner(System.in);
        while(flag){
            System.out.println("UNESITE ODREDJENI BROJ");
            System.out.println("Broj hromozoma = 10:-->  1" + "\n" + "Broj hromozoma = 50:-->  2" + "\n"
                    +"Broj hromozoma = 90:-->  3" + "\n" + "Izadji iz programa:--->  0");
            input = myObj.nextInt();
            if(input == 0){break;}
            else if (input == 1){
                broj_hromozoma = Integer.parseInt(config.get(1));
            }
            else if (input == 2){
                broj_hromozoma = Integer.parseInt(config.get(2));
            }
            else if (input == 3){
                broj_hromozoma = Integer.parseInt(config.get(3));
            }

            broj_iteracija = Integer.parseInt(config.get(4));

            System.out.println("VASI ODGOVOR x = " + broj_hromozoma + ", g = " + broj_iteracija);
            Analiza analiza = new Analiza(broj_hromozoma,broj_iteracija,config);

        }

    }
        /**
         * 0 - Broj-uzastopnih-ponovnih-pokretan
         * 1 - Veličinu-populacije1: 10
         * 2 - Veličinu-populacije2: 50
         * 3 - Veličinu-populacije3: 90
         * 4 - Veličinu-populacija-itr: 150
         * 5 - Broj-jedinki-za-parenje:
         * 6 - Broj-jedinki-za-mutaciju:
         * 7 - Broj-jedinki-koje-se-izbacuju:
         * 8 - Random-seed:
         * 9 - param_left_bound: -3
         * 10 - param_right_bound: 3
         * 11 - duzina-hromozmoa:
         * 12 - exe_location:
         * 13 - Duzina-populacije-parenja: 10
         * 14 - rez_locatin: rez.txt
         **/

}
