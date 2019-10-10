/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szolgaltatas;

import java.io.File;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import tarolo.Eredmeny;
import tarolo.Fordulo;
import tarolo.Talalat;

/**
 *
 * @author szeke
 */
public class TotoSzolgaltatas {
    public List<Fordulo> forduloLista;
    
    public TotoSzolgaltatas(String File)
    {
        forduloLista=new ArrayList<>();
        Scanner s=new Scanner(System.in);
        try
        {
            s=new Scanner(new File(File));
        }
        catch(Exception e)
        {
            System.out.println("Hiba!!"); 
        }
        while(s.hasNext())
        {
            String sor=s.nextLine();
            String[] a=sor.split(";");
            Fordulo f=new Fordulo();
            f.setEv(Integer.parseInt(a[0]));
            f.setHet(Integer.parseInt(a[1]));
            int fordulo=0;
            if(!a[2].equals(""))
            {
                fordulo=Integer.parseInt(a[2]);
            }
            else
            {
                fordulo=1;
            }
            f.setForduloAHeten(fordulo);
            String d="";
            if(!a[3].equals(""))
            {
                d=a[3];
            }
            else
            {
                d=getDatum(Integer.parseInt(a[0]),Integer.parseInt(a[1]),fordulo);
            }
            String[] datum=d.split("\\.");
            f.setDatum(LocalDate.of(Integer.parseInt(datum[0]),Integer.parseInt(datum[1]),Integer.parseInt(datum[2])));
            List<Talalat> tlista=new ArrayList<>();
            for (int i = 4; i < 14; i+=2) {
                String szam="";
                for (int j = 0; j < a[i+1].indexOf("Ft"); j++) {
                    if(a[i+1].substring(j,j+1).hashCode()>=48 &&a[i+1].substring(j,j+1).hashCode()<=57)
                    {
                        szam+=a[i+1].charAt(j);
                    }
                }
                tlista.add(new Talalat(Integer.parseInt(a[i]),Integer.parseInt(szam)));
            }
            f.setTalalatok(tlista);
            List<Eredmeny> elista=new ArrayList<>();
            for (int i = 14; i < 28; i++) {
                if(a[i].length()==2)
                {
                    a[i]=a[i].substring(1, 2);
                }
                switch(a[i])
                {
                    case "1":
                        elista.add(Eredmeny._1);
                        break;
                    case "2":
                        elista.add(Eredmeny._2);
                        break;
                    case "X":
                    case "x":
                        elista.add(Eredmeny.X);
                        break;
                }
            }
            f.setEredmenyek(elista);
            forduloLista.add(f);
        }
    }
    
    @Override
    public String toString()
    {
        String s="";
        for (Fordulo f : forduloLista) {
            s+=f.toString()+"\n";
        }
        return s;
    }
    
    public String getDatum(int ev, int het, int nap)
    {
        LocalDate elso=LocalDate.of(ev, 1, 1);
        int elsoHetNapja=0;
        int n=1;
        if(elso.getDayOfWeek().getValue()<=4)
        {
            n-=(elso.getDayOfWeek().getValue()-1);
            if(n<1)
            {
                n+=31;
            }
            ev--;
            n=LocalDate.of(ev, 12, n).getDayOfYear();
        }
        else
        {
            n+=(8-elso.getDayOfWeek().getValue());
            
        }
        int evNapok=n+(7*(het-1));
        if(evNapok>365 && ev%4!=0)
        {
            ev++;
            evNapok-=365;
        }
        else if(evNapok>366 && ev%4==0)
        {
            ev++;
            evNapok-=366;
        }
        int h=0;
        int hn=0;
        while(evNapok>0)
        {
            h++;
            switch(h)
            {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    evNapok-=31;
                    hn=31;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    evNapok-=30;
                    hn=30;
                    break;
                case 2:
                    if(ev%4==0)
                    {
                       evNapok-=29; 
                       hn=29;
                    }
                    else
                    {
                        evNapok-=28;
                        hn=28;
                    }
                    break;
            }
        }
        evNapok+=hn;
        return ev+"."+h+"."+evNapok+".";
    }
    
    public int legnagyobbNyeremeny()
    {
        int max=this.forduloLista.get(0).getTalalatok().get(0).getNyeremeny();
        for (int i = 0; i < this.forduloLista.size(); i++) {
            for (int j = 0; j < this.forduloLista.get(i).getTalalatok().size(); j++) {
                int aktNyeremeny=this.forduloLista.get(i).getTalalatok().get(j).getNyeremeny();
                if(aktNyeremeny>max)
                {
                    max=aktNyeremeny;
                }
            }
        }
        return max;
    }
    
    public double nyeresiArany(Eredmeny e)
    {
        int db=0;
        int ind=0;
        for (int i = 0; i < this.forduloLista.size(); i++) {
            for (int j = 0; j < this.forduloLista.get(i).getEredmenyek().size(); j++) {
                if(this.forduloLista.get(i).getEredmenyek().get(j)==e)
                {
                    db++;
                }
            }
        }
        double valos=this.forduloLista.size()*14;
        return db/valos*100;
    }
    
    public String tippeles(LocalDate datum, List<Eredmeny> elista)
    {
        int talalat=0;
        int find=-1;
        int ii=0;
        boolean l=false;
        while((ii<this.forduloLista.size())&&(!l))
        {
            if(this.forduloLista.get(ii).getDatum().equals(datum))
            {
                l=true;
                find=ii;
            }
            ii++;
        }
        for (int i = 0; i < 14; i++) {
            if(this.forduloLista.get(find).getEredmenyek().get(i)==elista.get(i))
            {
                talalat++;
            }
        }
        int nyeremeny=0;
        if(talalat>=10)
        {
            nyeremeny=this.forduloLista.get(find).getTalalatok().get(14-talalat).getNyeremeny();
        }
        return "Eredmeny: talalat: "+talalat+", nyeremeny: "+nyeremeny+" Ft";
    }
}
