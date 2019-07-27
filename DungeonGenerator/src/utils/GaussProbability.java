
package utils;

import java.io.Serializable;
import static utils.Utils.R;

/**
 * This class captures a normal distribution with an adjustable mean and
 * standard deviation.
 */
public class GaussProbability implements Serializable{

    private static final long serialVersionUID = 56734892217432L;

    public double mean, sDev;

    /**
     * Constructs a normal distribution with the given mean and standard
     * deviation.
     * @param m
     * @param s
     */
    public GaussProbability(double m, double s){
        mean = m;
        sDev = s;
    }

    /**
     * Returns a normally distributed double above 0.
     * Will get stuck in an infinite loop if the mean is too far below zero and 
     * the standard deviation is too low.
     * @return
     */
    public double nextAboveZero(){
        return getGaussianAboveZero(mean, sDev);
    }

    /**
     * Returns a normally distributed double.
     * @return
     */
    public double next(){
        return getGaussian(mean, sDev);
    }
    
    /**
     * Returns a normally distributed double between two bounds.
     * @param from
     * @param to
     * @return
     */
    public double next(double from, double to){
        return getGaussianBetween(mean, sDev, from, to);
    }

    /**
     * Returns true if the Gaussian was above 0.
     * @return
     */
    public boolean check(){
        return getGaussian(mean, sDev)>0;
    }
    
    /**
     * Generates a normally distributed double with the given mean and standard
     * deviation.
     * @param mean
     * @param sDev
     * @return
     */
    public static double getGaussian(double mean, double sDev){
        return mean + sDev*R.nextGaussian();
    }
    
    /**
     * Returns a normally distributed double above 0.
     * Will get stuck in an infinite loop if the mean is too far below zero and 
     * the standard deviation is too low.
     * @param mean
     * @param sDev
     * @return
     */
    public static double getGaussianAboveZero(double mean, double sDev){
        double ret;
        do ret = getGaussian(mean, sDev);
        while(ret<0);
        return ret;
    }
    
    /**
     * Returns a normally distributed double between the two given values.
     * Will get stuck in an infinite loop if the mean is too far away from the 
     * bounds and the standard deviation is too low.
     * @param mean
     * @param sDev
     * @param from
     * @param to
     * @return
     */
    public static double getGaussianBetween(double mean, double sDev, double from, double to){
        double ret;
        do ret = getGaussian(mean, sDev);
        while(ret<from||ret>to);
        return ret;
    }

}
