
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
     * TrapGenChance: The chance to generate a trap.
     * WaterGenChance: The chance to generate water.
     * GrassGenChance: The chance to generate grass.
     * GrassUpgradeChance: The chance to upgrade low grass into high grass.
     * WallDecoChance: The chance to convert a wall into a decorated wall.
     * FloorDecoChance: The chance to convert a floor into a decorated floor.
     * DoorHideChance: The chance to hide a door.
     * WallNoise: The noise to use when building a wall image.
     * FloorNoise: The noise to use when building a floor image.
     * WaterBeforeGrass: Whether to generate water before grass.
     * AlternateTiles: Whether to preserve artefacts for the tile noise.
     * InitialJitter: The distribution for initial jitter for midpoint noise.
     * JitterDecay: The distribution for jitter decay for midpoint noise.
     * Amplitude: The distribution for amplitude for Perlin noise.
     * Lacunarity: The distribution for lacunarity for Perlin noise.
     * Persistence: The distribution for persistence for Perlin noise.
     */
    public final String description;
    public final Distribution roomDist, trapVisibleChance, trapChance, octaves;
    public final double trapGenChance, waterGenChance, grassGenChance, 
            grassUpgradeChance, wallDecoChance, floorDecoChance, doorHideChance;
    public final List<Room> forcedRooms;
    public final NoiseType wallNoise, floorNoise;
    public final boolean waterBeforeGrass, alternateTiles;
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
     * @param trCh
     * @param wC
     * @param gC
     * @param guc
     * @param wallCh
     * @param floorCh
     * @param dhc
     * @param wall
     * @param floor
     * @param wbg
     * @param altT
     * @param iJ
     * @param jD
     * @param amp
     * @param lac
     * @param per
     * @param fRooms
     */
    protected LevelFeeling(String desc, Distribution room,
            Distribution trapVis, Distribution trap, Distribution oct,
            double trCh, double wC, double gC, double guc, double wallCh, 
            double floorCh, double dhc, NoiseType wall, NoiseType floor, 
            boolean wbg, boolean altT, GaussProbability iJ, GaussProbability jD,
            GaussProbability amp, GaussProbability lac, GaussProbability per, 
            List<Room>... fRooms){
        description = desc;
        lacunarity = lac;
        persistence = per;
        wallNoise = wall;
        floorNoise = floor;
        initialJitter = iJ;
        jitterDecay = jD;
        amplitude = amp;
        alternateTiles = altT;
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
        waterBeforeGrass = wbg;
        grassUpgradeChance = guc;
        grassGenChance = gC;
        trapGenChance = trCh;
    }
    
    public static final LevelFeeling DEFAULT_FEELING = new LevelFeeling("Default", null, null, null, new Distribution(new int[]{2,3,4,5,6}, new int[]{1,3,5,3,2}), 
            0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, NoiseType.MIDPOINT, NoiseType.TILE, true, false, 
            new GaussProbability(80, 25), new GaussProbability(0.65, 0.25), new GaussProbability(80, 25), new GaussProbability(0.6, 0.2), new GaussProbability(0.8, 0.2));
    
}
