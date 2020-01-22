
package biomes;

/**
 *
 * @author Adam Whittaker
 * This class is an abstraction of a society and contains all the factors that 
 * govern a society.
 */
public class Society{

    
    /**
     * technology: The technology level (see Biome).
     */
    public final int technology;
    
    
    /**
     * Creates a new instance by simply initializing the fields.
     * @param tech
     */
    public Society(int tech){
        technology = tech;
    }
    
}
