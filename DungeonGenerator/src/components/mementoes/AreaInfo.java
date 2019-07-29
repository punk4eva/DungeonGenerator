
package components.mementoes;

import components.LevelFeeling;
import generation.noise.PerlinNoiseGenerator;
import generation.noise.MidpointDisplacer;
import components.LevelFeeling.NoiseType;
import components.tiles.Tile;
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
    public final LevelFeeling feeling;
    
    public transient double[][] perlinNoise;
    public transient double[][] midpointNoise;
    public transient double[][] tileNoise;
    
    /**
     * Creates a random instance based on a given level feeling.
     * @param w The width.
     * @param h The height.
     * @param f The ethos of the level.
     */
    public AreaInfo(int w, int h, LevelFeeling f){
        width = w;
        height = h;
        seed = SEED_MAKER.nextLong();
        initialJitter = f.initialJitter.next(0,120);
        jitterDecay = f.jitterDecay.next(0.7, 0.95); 
        amplitude = f.amplitude.next(30, 120);
        octaves = f.octaves.next(); 
        lacunarity = f.lacunarity.next(0.3, 1.0);
        persistence = f.persistence.next(0.3, 1.0);
        feeling = f;
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
                " P: " + persistence + "\niJ: " + initialJitter + " jD: " + jitterDecay + " alt: " + feeling.alternateTiles);
        new MidpointDisplacer(125, initialJitter, jitterDecay, 255, false, false).apply(midpointNoise);
        new PerlinNoiseGenerator(width*16, height*16, amplitude, octaves, lacunarity, persistence).apply(perlinNoise);
        new MidpointDisplacer(125, initialJitter, jitterDecay, 255, feeling.alternateTiles, true).apply(tileNoise);
    }
    
    /**
     * Gets the noise map of the given Tile.
     * @param tile
     * @return
     */
    public double[][] getNoiseMap(Tile tile){
        if(tile.type.equals(Type.WALL)) return getNoiseFromType(feeling.wallNoise);
        else return getNoiseFromType(feeling.floorNoise);
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
