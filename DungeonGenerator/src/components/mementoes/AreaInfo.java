
package components.mementoes;

import components.LevelFeeling.NoiseType;
import components.tiles.Tile;
import generation.*;
import graph.Point.Type;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Random;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 * 
 * The information about an Area to be serialized.
 */
public class AreaInfo implements Serializable{
    
    private static final long serialVersionUID = 42748394236345L;
    
    private static final Random SEED_MAKER = new Random();

    private final long seed;
    
    /**
     * The elemental fields that need to be retained upon saving on the Area.
     */
    public final int width, height;
    private final double initialJitter, jitterDecay, 
            amplitude, lacunarity, persistence;
    private final int octaves;
    private final boolean alteredTiles;
    public final NoiseType wallNoise, floorNoise;
    
    public transient double[][] perlinNoise;
    public transient double[][] midpointNoise;
    public transient double[][] tileNoise;
    
    /**
     * Creates an instance.
     * @param w
     * @param h
     * @param iJ
     * @param jD
     * @param altT
     * @param amp
     * @param oc
     * @param l
     * @param p
     * @param wNoise
     * @param fNoise
     */
    public AreaInfo(int w, int h, double iJ, double jD, boolean altT, double amp, int oc, double l, double p, NoiseType wNoise, NoiseType fNoise){
        width = w;
        height = h;
        seed = SEED_MAKER.nextLong();
        initialJitter = iJ;
        jitterDecay = jD;
        amplitude = amp;
        wallNoise = wNoise;
        floorNoise = fNoise;
        octaves = oc;
        lacunarity = l;
        persistence = p;
        alteredTiles = altT;
        initializeNoise();
    }
    
    /**
     * Initializes the noise maps during instantiation or deserialization.
     */
    private void initializeNoise(){
        R.setSeed(seed);
        midpointNoise = new double[height*16][width*16];
        perlinNoise = new double[height*16][width*16];
        tileNoise = new double[height*16][width*16];
        System.out.println("Amp: " + amplitude + " Oc: " + octaves + "  L: " + lacunarity + 
                " P: " + persistence + "\niJ: " + initialJitter + " jD: " + jitterDecay + " alt: " + alteredTiles);
        new MidpointDisplacer(125, initialJitter, jitterDecay, 255, false, false).apply(midpointNoise);
        new PerlinNoiseGenerator(width*16, height*16, amplitude, octaves, lacunarity, persistence).apply(perlinNoise);
        new MidpointDisplacer(125, initialJitter, jitterDecay, 255, alteredTiles, true).apply(tileNoise);
    }
    
    /**
     * Gets the noise map of the given Tile.
     * @param tile
     * @return
     */
    public double[][] getNoiseMap(Tile tile){
        if(tile.type.equals(Type.WALL)) return getNoiseFromType(wallNoise);
        else return getNoiseFromType(floorNoise);
    }
    
    /**
     * Gets the noise map from the given NoiseType.
     * @param tile
     * @return
     */
    private double[][] getNoiseFromType(NoiseType type){
        switch(type){
            case MIDPOINT: return midpointNoise;
            case PERLIN: return perlinNoise;
            case TILE: return tileNoise;
        }
        throw new IllegalStateException();
    }
    
    
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        initializeNoise();
    }
    
}
