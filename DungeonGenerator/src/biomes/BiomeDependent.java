
package biomes;

import static biomes.BiomeProcessor.probFunction;
import utils.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class BiomeDependent{

    
    private final int temperature, accommodation, hostility, height, technology;
    private final double tempV, accV, hostV, heightV, techV;
    
    private final int ruination, aggressiveness;
    private final double ruinV, warV;

    public double probability;


    public BiomeDependent(int tem, double tV, int a, double aV, int ho, double hV,
            int he, double heV, int tec, double tecV, int ruin, double ruV, 
            int aggro, double aggV){
        temperature = tem;
        accommodation = a;
        hostility = ho;
        height = he;
        technology = tec;
        tempV = tV;
        accV = aV;
        hostV = hV;
        heightV = heV;
        techV = tecV;
        ruination = ruin;
        ruinV = ruV;
        aggressiveness = aggro;
        warV = aggV;
    }


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
    
    
    public static Distribution selectFromBiome(BiomeDependent[] ary, Biome b, Society s){
        double[] cha = new double[ary.length];
        for(int n=0;n<ary.length;n++) cha[n] = ary[n].evaluateProbability(b, s);
        return new Distribution(cha);
    }
    
}
