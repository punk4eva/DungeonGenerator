
package components;

import biomes.Biome;
import biomes.Society;
import utils.Distribution;
import utils.GaussProbability;
import utils.Utils.Unfinished;
import static utils.Utils.triangulate;

/**
 * This class contains all of the probabilities for each customisable value used
 * in the dungeon generation.
 * @author Adam Whittaker
 */
public class LevelFeeling{
    
    
    /**
     * This Enum represents the different possible types of noise.
     */
    public enum NoiseType{
        MIDPOINT, PERLIN, TILE;
    }

    
    /**
     * These fields are the characteristics of the generation algorithm.
     * Description: A summary text for an Area generated with this feeling.
     * RoomDist: Relative chances to generate each Room.
     * TrapVisibleChance: The chance for a Trap to be visible.
     * TrapChance: Relative chances to generate each Trap.
     * WaterGenChance: The chance to generate water.
     * GrassGenChance: The chance to generate grass.
     * GrassUpgradeChance: The chance to upgrade low grass into high grass.
     * WallDecoChance: The chance to convert a wall into a decorated wall.
     * FloorDecoChance: The chance to convert a floor into a decorated floor.
     * DoorHideChance: The chance to hide a door.
     * DoorTrapChance: The chance to make a Door trapped.
     * FloorTrapChance: The chance to generate a trap on a floor.
     * WallTrapChance: The chance to generate a trap on a wall.
     * WallNoise: The noise to use when building a wall image.
     * FloorNoise: The noise to use when building a floor image.
     * WaterBeforeGrass: Whether to generate water before grass.
     * InitialJitter: The distribution for initial jitter for midpoint noise.
     * JitterDecay: The distribution for jitter decay for midpoint noise.
     * Amplitude: The distribution for amplitude for Perlin noise.
     * Lacunarity: The distribution for lacunarity for Perlin noise.
     * Persistence: The distribution for persistence for Perlin noise.
     */
    public final String description;
    @Unfinished("Implement trap chances")
    public final Distribution /*trapVisibleChance, trapChance,*/ octaves;
    public final double waterGenChance, grassGenChance, 
            grassUpgradeChance, wallDecoChance, floorDecoChance, doorHideChance,
            doorTrapChance, floorTrapChance, wallTrapChance;
    public final NoiseType wallNoiseType, floorNoiseType;
    public final GaussProbability initialJitter, jitterDecay, amplitude, 
            lacunarity, persistence;
    
    
    /**
     * Creates an instance, the parameters are in the same order as their 
     * respective fields are declared.
     * @param b
     * @param s
     * @param desc
     * @param room
     * @param trapVis
     * @param trap
     * @param oct
     * @param wC
     * @param gC
     * @param guc
     * @param wallCh
     * @param floorCh
     * @param dhc
     * @param doorTrap
     * @param floorTrap
     * @param wallTrap
     * @param wNoise
     * @param fNoise
     * @param wBeforeG
     * @param iJ
     * @param jD
     * @param amp
     * @param lac
     * @param per
     * @param fRooms
     */
    protected LevelFeeling(Biome b, Society s, String desc, Distribution oct,
            NoiseType wNoise, NoiseType fNoise, 
            GaussProbability iJ, GaussProbability jD,
            GaussProbability amp, GaussProbability lac, GaussProbability per){
        description = desc;
        lacunarity = lac;
        persistence = per;
        wallNoiseType = wNoise;
        floorNoiseType = fNoise;
        initialJitter = iJ;
        jitterDecay = jD;
        amplitude = amp;
        octaves = oct;
        wallDecoChance = 0.05*(1D - s.ruination/100D);
        floorDecoChance = 0.06*(1D - s.ruination/100D);
        doorHideChance = triangulate(s.aggressiveness, 0, 80, 100,  0, 0.3, 0.1);
        waterGenChance = waterGenChance(b);
        grassUpgradeChance = grassUpgradeChance(b);
        grassGenChance = grassGenChance(b);
        doorTrapChance = triangulate(s.aggressiveness, 0, 70, 100,  0, 0.06, 0.08);
        floorTrapChance = triangulate(s.aggressiveness, 0, 70, 100,  0, 0.04, 0.06);
        wallTrapChance = triangulate(s.aggressiveness, 0, 70, 100,  0, 0.02, 0.03);
    }
    
    
    /**
     * Calculates the grass generation chance from the biome.
     * @param b The biome.
     * @return A 0 -> 1 chance of a given tile starting off as grass (before 
     * spread).
     */
    private static double grassGenChance(Biome b){
        return 0.2*(triangulate(b.temperature, -50,20,60, 0,0.7,0) +
                0.85*b.accommodation/100D)/2D;
    }
    
    /**
     * Calculates the chance of a grass being tall from the biome.
     * @param b The biome.
     * @return A 0 -> certain chance of a given grass tile being tall.
     */
    private static double grassUpgradeChance(Biome b){
        return (triangulate(b.temperature, -50,22,60, 0,1.3,0) +
                b.accommodation/100D)/2D;
    }
    
    /**
     * Calculates the water generation chance from the biome.
     * @param b The biome.
     * @return A 0 -> 1 chance of a given tile starting off as water (before 
     * spread).
     */
    private static double waterGenChance(Biome b){
        return 0.2*(triangulate(b.temperature, -50,20,60, 0.2,0.7,0) +
                triangulate(b.height, -50,-7,60, 0.2,0.7,0.05) +
                0.5*b.accommodation/100D)/3D;
    }
    
    
    /**
     * Gets the default level feeling using the biome and the society.
     * @param b The biome.
     * @param s The society.
     * @return
     */
    public static LevelFeeling getDefaultFeeling(Biome b, Society s){
        return new LevelFeeling(b, s, "Default", 
                new Distribution(new int[]{2,3,4,5,6}, new double[]{1,3,5,3,2}), 
                NoiseType.PERLIN, NoiseType.TILE, new GaussProbability(80, 25),
                new GaussProbability(0.65, 0.25), new GaussProbability(80, 25), 
                new GaussProbability(0.5, 0.2), new GaussProbability(0.8, 0.2));
    }
    
}
