/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statistikberechnung;

import java.util.Arrays;
import java.util.Random;

/**
 * @author drmilde
 */
public class StatistikBerechnung
{
    
    Random rg = new Random();
    
    /**
     * @param args
     *         the command line arguments
     */
    public static void main(String[] args)
    {
        StatistikBerechnung sbr         = new StatistikBerechnung();
        int[]               werte       = sbr.generate(1000000);
        double[]            werteNormal = sbr.generateGauss(1000000);
        //Das Array wird nie verwendet
        
        
        double sd = sbr.sd(werteNormal);
        System.out.println("Standardabweichung = " + sd);

        double percent = sbr.checkConfidence (100.0, 3*42.0, werteNormal);
        System.out.println("Anteil Konfidenzinterval sd*1 = " + percent);
        


        // gleichverteilte Beispiele
        int median = sbr.median(werte);
        System.out.println("Median = " + median);
                
        int spannweite = sbr.weite(werte);
        System.out.println("Spannweite = " + spannweite);
        
        int zaehlen = 0; // hier zaehlen wir die Anzahl für einen wert
        
        for (int i = 0; i < werte.length; i++)
        {
            int wert = werte[i];
            if (wert == 10)
            {
                zaehlen++;
            }
            
        }
        
        double arithemtischesMittel = sbr.arithMittel(werte);
        
        System.out.println("Gezaehlter Wert = " + zaehlen);
        System.out.println("Arithmetischer Mittelwert = " + arithemtischesMittel);
        
    }
    
    private int median(int[] werte)
    {
        Arrays.sort(werte);
        
        return (werte[werte.length / 2]);
    }
    
    private int[] generate(int anzahl)
    {
        int[] result = new int[anzahl];
        for (int i = 0; i < result.length; i++)
        {
            result[i] = rg.nextInt(100) + 1;
        }
        return result;
    }
    
    private double arithMittel(int[] werte)
    {
        double summe = 0;
        for (int i = 0; i < werte.length; i++)
        {
            int wert = werte[i];
            summe += wert;
        }
        
        return (summe / werte.length);
    }
    
    private int weite(int[] werte)
    {
        Arrays.sort(werte);
        return (werte[werte.length - 1] - werte[0]);
    }
    
    private double[] generateGauss(int anzahl)
    {
        double[] result = new double[anzahl];
        
        final double sd  = 42;
        final double mju = 100;
        
        for (int i = 0; i < result.length; i++)
        {
            double val1 = Math.sqrt(-2 * Math.log(rg.nextDouble()));
            double val2 = Math.sin(2 * Math.PI * rg.nextDouble());
            result[i] = // z
                    sd * // standard deviation
                    val1 * // value 1
                    val2 + // value 2
                    mju; // mean
        }
        return result;
    }

    private double sd(double[] werteNormal) {
        double sum = 0;
        for (int i = 0; i < werteNormal.length; i++) {
            double d = werteNormal[i];
            sum += d;            
        }
        double mju = sum / werteNormal.length;        
        System.out.println("Erwartungswert = " + mju);   
        
        sum = 0;
        for (int i = 0; i < werteNormal.length; i++) {
            double d = werteNormal[i];
            sum += (d - mju) * (d - mju);
            
        }
        
        sum = sum * (1.0 / (werteNormal.length - 1));
        sum = Math.sqrt(sum);
       
        return sum;
    }

    private double checkConfidence(double mju, double sd, double[] werteNormal) {
        int count = 0;
        for (int i = 0; i < werteNormal.length; i++) {
            double x = werteNormal[i];
            if ( ((mju-sd) <= x) && ((mju+sd)>= x) ) {
                count++;
            }
        }
        
        return ((count * 1.0) / werteNormal.length) * 100;
    }
    
}
