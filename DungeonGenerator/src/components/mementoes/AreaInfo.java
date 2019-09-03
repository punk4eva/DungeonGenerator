
package components.mementoes;

import components.LevelFeeling;
import generation.noise.PerlinNoiseGenerator;
import generation.noise.MidpointDisplacer;
import components.tiles.Tile;
import filterGeneration.DoorIconGenerator;
import filterGeneration.ImageBuilder;
import graph.Point.Type;
import java.awt.Color;
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
    public final Color grassColor;
    
    public final ArchitectureInfo architecture;
    
    public transient double[][] wallNoise;
    public transient double[][] floorNoise;
    
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
        grassColor = Color.decode("#16560d");
        initializeNoise();
        architecture = new ArchitectureInfo(this, f);
    }
    
    /**
     * Initializes the noise maps during instantiation or deserialization.
     */
    private void initializeNoise(){
        R.setSeed(seed);
        wallNoise = new double[height*16][width*16];
        floorNoise = new double[height*16][width*16];
        System.out.println("Amp: " + amplitude + " Oc: " + octaves + "  L: " + lacunarity + 
                " P: " + persistence + "\niJ: " + initialJitter + " jD: " + jitterDecay + " alt: " + feeling.alternateWallTiles);
        switch(feeling.wallNoiseType){
            case MIDPOINT: new MidpointDisplacer(125, initialJitter, jitterDecay, 255, false, false).apply(wallNoise);
                break;
            case PERLIN: new PerlinNoiseGenerator(width*16, height*16, amplitude, octaves, lacunarity, persistence).apply(wallNoise);
                break;
            case TILE: new MidpointDisplacer(125, initialJitter, jitterDecay, 255, feeling.alternateWallTiles, true).apply(wallNoise);
                break;
        }
        switch(feeling.floorNoiseType){
            case MIDPOINT: new MidpointDisplacer(125, initialJitter, jitterDecay, 255, false, false).apply(floorNoise);
                break;
            case PERLIN: new PerlinNoiseGenerator(width*16, height*16, amplitude, octaves, lacunarity, persistence).apply(floorNoise);
                break;
            case TILE: new MidpointDisplacer(125, initialJitter, jitterDecay, 255, feeling.alternateFloorTiles, true).apply(floorNoise);
                break;
        }
    }
    
    /**
     * Gets the noise map of the given Tile.
     * @param tile
     * @return
     */
    public double[][] getNoiseMap(Tile tile){
        if(tile.type.equals(Type.WALL)) return wallNoise;
        else return floorNoise;
    }
    
    
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        initializeNoise();
    }
    
}
