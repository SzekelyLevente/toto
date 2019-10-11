/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarolo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author szeke
 */
public class Fordulo {
    private int ev;
    private int het;
    private int forduloAHeten;
    private LocalDate datum;
    private List<Eredmeny> eredmenyek=new ArrayList<>();
    private List<Talalat> talalatok=new ArrayList<>();
    
    public Fordulo()
    {
        
    }

    public int getEv() {
        return ev;
    }

    public int getHet() {
        return het;
    }

    public int getForduloAHeten() {
        return forduloAHeten;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public List<Eredmeny> getEredmenyek() {
        return eredmenyek;
    }

    public List<Talalat> getTalalatok() {
        return talalatok;
    }

    public void setEv(int ev) {
        this.ev = ev;
    }

    public void setHet(int het) {
        this.het = het;
    }

    public void setForduloAHeten(int forduloAHeten) {
        this.forduloAHeten = forduloAHeten;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public void setEredmenyek(List<Eredmeny> eredmenyek) {
        this.eredmenyek.clear();
        for (int i = 0; i < eredmenyek.size(); i++) {
            this.eredmenyek.add(eredmenyek.get(i));
        }
    }

    public void setTalalatok(List<Talalat> talalatok) {
        this.talalatok.clear();
        for (int i = 0; i < talalatok.size(); i++) {
            this.talalatok.add(talalatok.get(i));
        }
    }
    
    @Override
    public String toString()
    {
        String st="";
        for (int i = 0; i < talalatok.size(); i++) {
            st+=talalatok.get(i).getTalalatokSzama()+";"+talalatok.get(i).getNyeremeny()+" Ft;";
        }
        String se="";
        for (int i = 0; i < eredmenyek.size(); i++) {
            switch(eredmenyek.get(i))
            {
                case _1:
                    se+="1;";
                    break;
                case _2:
                    se+="2;";
                    break;
                case X:
                    se+="X;";
            }
        }
        return ev+";"+het+";"+forduloAHeten+";"+datum+";"+st+""+se;
    }
}
