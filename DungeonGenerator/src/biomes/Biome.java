
package biomes;

/**
 *
 * @author Adam Whittaker
 * This class contains the attributes of different climate zones.
 */
public enum Biome{
    
    
    /**
     * All the different possible biomes.
     * temperature
     * accommodation
     * hostility
     * height
     * technology
     */
    //Underground
    SHALLOW_CAVE(10, 20, 30, -25, 35), MEDIUM_CAVE(-5, 20, 35, -50, 45),
    DEEP_CAVE(27, 27, 55, -75, 55), BURIED_OASIS(23, 35, 25, -60, 20), 
    HELL(60, 40, 85, -100, 95), THE_DEEP(37, 32, 70, -85, 75),
    
    //Plain
    PLAINS(16, 100, 10, 0, 0), LOWLANDS(12, 80, 25, 10, 10), 
    MIDLANDS(8, 65, 20, 20, 15), HIGHLANDS(5, 57, 28, 30, 30), 
    TEMPERATE_FOREST(13, 75, 32, 5, 20), MARSH(20, 0, 45, 0, 35),
    
    //High altitude
    MOUNTAIN(-10, 25, 43, 50, 43), FLOATING_ISLAND(-5, 45, 15, 40, 20), 
    SUMMIT(-25, 5, 50, 60, 90), CLIFF(0, 30, 41, 35, 30), 
    BASALTIC_VOLCANO(8, 30, 41, 28, 20), RHYOLITIC_VOLCANO(15, 40, 65, 38, 30), 
    SPRING(3, 45, 35, 38, 22),
    
    //High temp, humid
    JUNGLE(30, 45, 45, 0, 34), MANGROVE_SWAMP(25, 27, 38, 0, 35), 
    TROPICAL_RAINFOREST(30, 48, 36, 0, 36),
    
    //High temp, arid
    DESERT(40, 9, 39, 0, 15), SAVANNA(30, 28, 46, 3, 32), 
    BEACH(27, 52, 40, 0, 35),
    
    //Low temp
    TUNDRA(-15, 30, 30, 20, 30), LIGHT_SNOW(-25, 35, 38, 20, 30), 
    HEAVY_SNOW(-35, 27, 45, 25, 39), BADLAND(-15, 0, 30, 32, 52);

    
    /**
     * The average yearly temperature of the biome in celsius.
     * Ranges roughly -80C to 80C.
     */
    public final double temperature;
    
    /**
     * A scale for how high the food production capabilities of the biome are.
     * Used proportional to the size of the society that can be accommodated.
     * 0: No food.
     * 10: Enough prey for small nomadic group (20 or less people).
     * 20: Enough prey for nomadic tribe (~100 people).
     * 30: Enough prey for small permanent settlement (30-70 people).
     * 40: Soil fertility high enough for subsistence farming for a village (70-300 people).
     * 50: Soil fertility high enough for subsistence farming for a small town (300-2000 people).
     * 60: Soil fertility high enough for cash crops (2000-10000 people).
     * 70: Soil fertility and pastoral farming high enough for cash crops to be the major export (10000-50000 people).
     * 80: Enough food to support a large city (50000-4mil people).
     * 90: Enough food to support a country (4mil-20mil people).
     * 100: Food quality high enough to store food for years (20mil-1bil).
     */
    public final double accommodation;
    
    /**
     * The danger to societies trying to build here.
     * 0: No danger.
     * 20: Predators capable of hunting unprepared people.
     * 40: Occasional modest natural disaster capable of destroying basic settlements.
     * 60: Predators can break wooden doors, natural disasters on scale of tsunamis wipe out towns.
     * 80: Frequent powerful natural disasters prevent people from building permanent settlements.
     * 100: Land is uncolonizable. Only ruins are here.
     */
    public final double hostility;
    
    /**
     * The altitude of the biome.
     * -100: hell
     * 0: surface
     * 100: very high
     */
    public final double height;
    
    /**
     * The minimum technology requirements to build here.
     * 0: No technological advancement required to build here.
     * 10: Moderate shelter construction skills required.
     * 20: Light hand-held tools required (to cut trees, hunt, etc).
     * 30: Fire.
     * 40: Tools for mining cobblestone.
     * 50: Forging bronze and brass.
     * 60: Forging things in a refined manner, i.e: glass.
     * 70: Means of surviving medium natural disasters.
     * 80: Industry.
     * 90: Means of surviving harsh conditions for prolonged times.
     * 100: Means of surviving in space for prolonged times.
     */
    public final double technology;
    
    
    /**
     * Creates a new instance.
     * @param temp The average yearly temperature in Celsius.
     * @param a The accommodation level on the 0-100 scale (see above).
     * @param ho The danger level on the 0-100 scale (see above).
     * @param he The height on the -100 -> 100 scale (see above).
     * @param tech The minimum technology level on the 0-100 scale (see above)
     * required to build in this biome.
     */
    private Biome(double temp, double a, double ho, double he, double tech){
        temperature = temp;
        accommodation = a;
        hostility = ho;
        height = he;
        technology = tech;
    }
    
}
