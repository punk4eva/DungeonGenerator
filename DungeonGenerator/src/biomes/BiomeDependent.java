
package biomes;

import static biomes.BiomeProcessor.probFunction;
import utils.Distribution;

/**
 * Represents a probability distribution function over Biomes and Society.
 * The function shares properties with (1+x^2)^(-1). The class is meant to be
 * extended by an entity which has "preference" for different biomes and 
 * societies.
 * @author Adam Whittaker
 */
public class BiomeDependent{

    
    //The "V" parameters are related to the 'variance' in the distribution for 
    //each variable that factors into the function. Values of 0 mean the 
    //function is independent of that variable and values of 1 mean that the
    //function gains a massive boost if the given variable matches the value 
    //stored in the variable.
    private final int temperature, accommodation, hostility, height, technology;
    private final double tempV, accV, hostV, heightV, techV;
    
    private final int ruination, aggressiveness;
    private final double ruinV, warV;

    public double probability;

    
    /**
     * Creates an instance.
     * @param temp The optimal temperature.
     * @param tV
     * @param accommod The optimal accommodation.
     * @param aV
     * @param hostil The optimal hostility.
     * @param hV
     * @param _height The optimal height.
     * @param heV
     * @param tech The optimal technology level.
     * @param _techV
     * @param ruin The optimal ruination level.
     * @param ruV
     * @param aggro The optimal aggressiveness of the society.
     * @param aggV
     */
    public BiomeDependent(int temp, double tV, int accommod, double aV, int hostil, double hV,
            int _height, double heV, int tech, double _techV, int ruin, double ruV, 
            int aggro, double aggV){
        temperature = temp;
        accommodation = accommod;
        hostility = hostil;
        height = _height;
        technology = tech;
        tempV = tV;
        accV = aV;
        hostV = hV;
        heightV = heV;
        techV = _techV;
        ruination = ruin;
        ruinV = ruV;
        aggressiveness = aggro;
        warV = aggV;
    }

    
    /**
     * Evaluates the score of the given biome-society combo according to this
     * distribution.
     * @param b The Biome.
     * @param s The Society.
     * @return A higher score if the distribution "likes" the input.
     */
    public double evaluateProbability(Biome b, Society s){
        probability = probFunction(b.temperature, tempV, 80, temperature);
        probability += probFunction(b.accommodation, accV, 50, accommodation);
        probability += probFunction(b.hostility, hostV, 50, hostility);
        probability += probFunction(b.height, heightV, 100, height);
        probability += probFunction(s.technology, techV, 50, technology);
        probability += probFunction(s.ruination, ruinV, 50, ruination);
        probability += probFunction(s.aggressiveness, warV, 50, aggressiveness);
        return probability;
    }
    
    
    /**
     * Generates a distribution for the possible biome dependent objects. 
     * @param ary The array of biome dependent entities.
     * @param b The Biome.
     * @param s The Society.
     * @return
     */
    public static Distribution selectFromBiome(BiomeDependent[] ary, Biome b, Society s){
        double[] cha = new double[ary.length];
        for(int n=0;n<ary.length;n++) cha[n] = ary[n].evaluateProbability(b, s);
        return new Distribution(cha);
    }
    
}
