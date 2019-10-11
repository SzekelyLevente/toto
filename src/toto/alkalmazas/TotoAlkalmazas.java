/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toto.alkalmazas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import szolgaltatas.TotoSzolgaltatas;
import tarolo.Eredmeny;

/**
 *
 * @author szeke
 */
public class TotoAlkalmazas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        TotoSzolgaltatas t=new TotoSzolgaltatas("toto.csv");
        System.out.println("A legnagyobb nyeremeny amit rogzitettek: "+t.legnagyobbNyeremeny()+" Ft");
        System.out.println("Statisztika: #1 csapat nyert: "+t.nyeresiArany(Eredmeny._1)+" %, #2 csapat nyert: "+t.nyeresiArany(Eredmeny._2)+" %, döntetlen: "+t.nyeresiArany(Eredmeny.X)+" %");
        System.out.print("Kérem adjon meg egy dátumot: ");
        String datum=sc.nextLine();
        System.out.print("Kérem adjon meg egy tippet: ");
        String tipp=sc.nextLine();
        List<Eredmeny> elista=new ArrayList<>();
        for (int i = 0; i < tipp.length(); i++) {
            switch(tipp.charAt(i))
            {
                case '1':
                    elista.add(Eredmeny._1);
                    break;
                case '2':
                    elista.add(Eredmeny._2);
                    break;
                case 'X':
                case 'x':
                    elista.add(Eredmeny.X);
                    break;
            }
        }
        String[] d=datum.split("-");
        LocalDate l=LocalDate.of(Integer.parseInt(d[0]), Integer.parseInt(d[1]), Integer.parseInt(d[2]));
        System.out.println(t.tippeles(l, elista));
    }
    
}
