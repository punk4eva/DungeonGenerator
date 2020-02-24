
package components;

import components.rooms.Room;
import java.util.LinkedList;
import java.util.List;
import utils.Distribution;
import utils.GaussProbability;

/**
 *
 * @author Adam Whittaker
 * 
 * This class contains all of the probabilities for each customisable value used
 * in the dungeon generation.
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
    public final Distribution roomDist, trapVisibleChance, trapChance, octaves;
    public final double waterGenChance, grassGenChance, 
            grassUpgradeChance, wallDecoChance, floorDecoChance, doorHideChance,
            doorTrapChance, floorTrapChance, wallTrapChance;
    public final List<Room> forcedRooms;
    public final NoiseType wallNoiseType, floorNoiseType;
    public final boolean waterBeforeGrass;
    public final GaussProbability initialJitter, jitterDecay, amplitude, 
            lacunarity, persistence;
    
    
    /**
     * Creates an instance, the parameters are in the same order as their 
     * respective fields are declared.
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
    protected LevelFeeling(String desc, Distribution room,
            Distribution trapVis, Distribution trap, Distribution oct,
            double wC, double gC, double guc, double wallCh, 
            double floorCh, double dhc, double doorTrap, double floorTrap, 
            double wallTrap, NoiseType wNoise, NoiseType fNoise, 
            boolean wBeforeG, GaussProbability iJ, GaussProbability jD,
            GaussProbability amp, GaussProbability lac, GaussProbability per, 
            List<Room>... fRooms){
        description = desc;
        lacunarity = lac;
        persistence = per;
        wallNoiseType = wNoise;
        floorNoiseType = fNoise;
        initialJitter = iJ;
        jitterDecay = jD;
        amplitude = amp;
        octaves = oct;
        wallDecoChance = wallCh;
        floorDecoChance = floorCh;
        roomDist = room;
        trapVisibleChance = trapVis;
        doorHideChance = dhc;
        trapChance = trap;
        if(fRooms.length==0) forcedRooms = new LinkedList<>(); 
        else forcedRooms = fRooms[0];
        waterGenChance = wC;
        waterBeforeGrass = wBeforeG;
        grassUpgradeChance = guc;
        grassGenChance = gC;
        doorTrapChance = doorTrap;
        wallTrapChance = wallTrap;
        floorTrapChance = floorTrap;
    }
    
    
    public static final LevelFeeling DEFAULT_FEELING = new LevelFeeling("Default", null, null, null, new Distribution(new int[]{2,3,4,5,6}, new double[]{1,3,5,3,2}), 
            0.1, 0.05, 0.5, 0.03, 0.03, 0.1, 0.04, 0.03, 0.01, NoiseType.PERLIN, NoiseType.TILE, true, 
            new GaussProbability(80, 25), new GaussProbability(0.65, 0.25), new GaussProbability(80, 25), new GaussProbability(0.5, 0.2), new GaussProbability(0.8, 0.2));
    
    public static final LevelFeeling CAVE_FEELING = new LevelFeeling("Cave", null, null, null, new Distribution(new int[]{2,3,4,5,6}, new double[]{1,3,5,3,2}), 
            0.1, 0.05, 0.5, 0.03, 0.03, 0.1, 0.04, 0.03, 0.01, NoiseType.MIDPOINT, NoiseType.MIDPOINT, true, 
            new GaussProbability(90, 25), new GaussProbability(0.8, 0.1), new GaussProbability(80, 25), new GaussProbability(0.5, 0.2), new GaussProbability(0.8, 0.2));
    
}
