
package biomes;

/**
 *
 * @author Adam Whittaker
 */
public enum Biome{
    
    PLAINS(20, 100, 10, 0, 0), SHALLOW_CAVE(10, 20, 30, -25, 35), MEDIUM_CAVE(-5, 20, 35, -50, 45),
    DEEP_CAVE(27, 27, 55, -75, 55), BURIED_OASIS(23, 35, 25, -60, 20), HELL(50, 40, 85, -100, 95),
    LOWLANDS(20, 80, 25, 10, 10), MIDLANDS(16, 65, 20, 20, 15), HIGHLANDS(10, 57, 28, 30, 30),
    MOUNTAIN(-10, 25, 43, 50, 43), FLOATING_ISLAND(-5, 45, 15, 40, 20), SUMMIT(-25, 5, 50, 60, 90),
    MARSH(20, 0, 20, 0, 40), DESERT(40, 9, 39, 0, 15), JUNGLE(30, 45, 45, 0, 50),
    TUNDRA(-15, 30, 30, 20, 30), LIGHT_SNOW(-25, 35, 38, 20, 30), HEAVY_SNOW(-35, 27, 45, 25, 39);

    
    public final double temperature; //in celsius.
    
    /**
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
     * 0: No danger.
     * 20: Predators capable of hunting unprepared people.
     * 40: Occasional modest natural disaster capable of destroying basic settlements.
     * 60: Predators can break wooden doors, natural disasters on scale of tsunamis wipe out towns.
     * 80: Frequent powerful natural disasters prevent people from building permanent settlements.
     * 100: Land is uncolonizable. Only ruins are here.
     */
    public final double hostility;
    
    /**
     * -100: hell
     * 0: surface
     * 100: very high
     */
    public final double height;
    
    /**
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
    
    
    private Biome(double temp, double a, double ho, double he, double tech){
        temperature = temp;
        accommodation = a;
        hostility = ho;
        height = he;
        technology = tech;
    }
    
}
