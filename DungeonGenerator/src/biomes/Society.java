
package biomes;

import gui.questions.ClassSpecifier;

/**
 * This class is an abstraction of a society and contains all the factors that 
 * govern a society.
 * @author Adam Whittaker
 */
public class Society{

    
    /**
     * The technology level (see Biome).
     */
    public final int technology;
    
    /**
     * How abandoned the society left the area.
     * 0: The society is using the area in the way it was designed.
     * 20: The area is only half occupied, but there are still permanent 
     * residents.
     * 40: The area is only seasonally occupied and maintenance is slack.
     * 60: The area has been abandoned by the society 10-20 years ago and 
     * vegetation has to taken over part of it. Home to the rare squatters and
     * campers.
     * 80: Geological erosion and weathering has reshaped the area to its whim 
     * and large structural damage (e.g: cave-ins) has occurred.
     * 100: The land has almost completely reclaimed the area and it is 
     * difficult to recognize what the area used to be.
     */
    public final int ruination;
    
    /**
     * The affinity of the society for war and raiding.
     * 0: The society is peaceful and no defensive/offensive structures can be 
     * found within the area.
     * 20: The society has adopted defensive structures to guard against 
     * wildlife. The structures are typically harmless (e.g: wall).
     * 40: The society has built defensive structures to guard against a rival
     * society. The structures can harm an attacker but can only be used 
     * defensively (e.g: machicolations).
     * 60: The society has an affinity for raiding weaker settlements in the 
     * area and has constructed raid camps , etc.
     * 80: The society is actively at war with rivals and has constructed 
     * offensive structures against their enemies (e.g: War rooms, barracks, 
     * ballistas).
     * 100: The society's economy and culture is totally dependent on war.
     */
    public final int aggressiveness;
    
    
    /**
     * Creates a new instance by simply initializing the fields.
     * @param technologyLevel
     * @param ruinationLevel
     * @param warMongering
     */
    public Society(int technologyLevel, int ruinationLevel, int warMongering){
        technology = technologyLevel;
        ruination = ruinationLevel;
        aggressiveness = warMongering;
    }
    
    
    /**
     * The default society for quick, hard-coded running of the program.
     */
    public static final Society DEFAULT_SOCIETY = new Society(90, 40, 50);
    
    
    public final static ClassSpecifier<Society> SOCIETY_SPECIFIER;
    static{
        try{
            SOCIETY_SPECIFIER = new ClassSpecifier<>(
                    Society.class.getConstructor(int.class, int.class, int.class), 
                    "Society", "Design the society");
        }catch(NoSuchMethodException e){
            throw new IllegalStateException(e);
        }
    }
    
}
