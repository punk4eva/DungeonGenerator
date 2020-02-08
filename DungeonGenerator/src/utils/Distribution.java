
package utils;

import java.io.Serializable;
import static utils.Utils.R;

/**
 * Models a discrete, finite distribution of integer outputs with distinct
 * possibilities.
 * @author Adam Whittaker
 */
public class Distribution implements Serializable{
    
    
    private final static long serialVersionUID = -1416387932;
    
    /**
     * outputs: The possible outputs of the distributions.
     * chances: The relative chances of each of the above outputs being 
     * selected.
     */
    protected int[] outputs;
    protected double[] chances;
    
    
    /**
     * Creates a new instance.
     * @param out The possible outputs.
     * @param cha The relative chances of those outputs.
     */
    public Distribution(int[] out, double[] cha){
        outputs = out;
        chances = convertToCumulative(cha);
    }
    
    /**
     * Creates a new instance with outputs ranging from 0 to the length of the 
     * chances array.
     * @param cha The relative chances of those outputs.
     */
    public Distribution(double[] cha){
        outputs = new int[cha.length];
        for(int n=0;n<outputs.length;n++) outputs[n] = n;
        chances = convertToCumulative(cha);
    }
    
    
    /**
     * Gives a random output from this Distribution's output array based on its
     * chances.
     * @return An output from the array.
     */
    public int next(){
        return outputs[chanceToInt(R.nextDouble()*chances[chances.length-1])];
    }
    
    
    /**
     * Converts an array of normal chances to an array of cumulative chances. 
     * @param ary The array.
     * @return The modified array.
     */
    private static double[] convertToCumulative(double[] ary){
        double cumulative = 0;
        for(int n=0;n<ary.length;n++){
            cumulative += ary[n];
            ary[n] = cumulative;
        }
        return ary;
    }
    
    /**
     * Gets the index of output which the given chance value obtains.
     * @param i The chance value.
     * @return The index of the output.
     */
    private int chanceToInt(double i){
        for(int n=0;n<chances.length;n++) if(i<=chances[n]) return n;
        throw new IllegalStateException();
    }
    
}
