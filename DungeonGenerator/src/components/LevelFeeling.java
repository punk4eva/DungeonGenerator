
package components;

import components.rooms.Room;
import java.util.LinkedList;
import java.util.List;
import utils.Distribution;
import utils.GaussProbability;

/**
 *
 * @author Adam Whittaker
 */
public class LevelFeeling{
    
    public enum NoiseType{
        MIDPOINT, PERLIN, TILE;
    }

    public final String description;
    public final Distribution roomDist, trapVisibleChance, 
            trapChance, waterGenChance, grassGenChance, grassUpgradeChance,
            wallChance, floorChance, doorHideChance, octaves;
    public final List<Room> forcedRooms;
    public final NoiseType wallNoise, floorNoise;
    public final boolean waterBeforeGrass, alternateTiles;
    public final GaussProbability initialJitter, jitterDecay, amplitude, 
            lacunarity, persistence;
    
    
    
    private LevelFeeling(String desc, Distribution room,
            Distribution trapVis, Distribution trap, Distribution wC,
            Distribution gC, Distribution guc, Distribution wallCh,
            Distribution floorCh, Distribution dhc, Distribution oct,
            NoiseType wall, NoiseType floor, boolean wbg, boolean altT, 
            GaussProbability iJ, GaussProbability jD, GaussProbability amp, 
            GaussProbability lac, GaussProbability per, List<Room>... fRooms){
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
        wallChance = wallCh;
        floorChance = floorCh;
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
    }
    
    public static final LevelFeeling DEFAULT_FEELING = new LevelFeeling("Default", null, null, null, null, null, null, null, null, null, new Distribution(new int[]{2,3,4,5,6}, new int[]{1,3,5,3,2}), 
            NoiseType.MIDPOINT, NoiseType.TILE, true, false, 
            new GaussProbability(80, 25), new GaussProbability(0.65, 0.25), new GaussProbability(80, 25), new GaussProbability(0.6, 0.2), new GaussProbability(0.8, 0.2));
    
}
