
package components.mementoes;

import animation.assets.WaterPainter;
import biomes.Biome;
import biomes.Society;
import components.LevelFeeling;
import static components.LevelFeeling.getDefaultFeeling;
import components.tiles.Tile;
import generation.noise.MidpointDisplacmentNoise;
import generation.noise.PerlinNoiseGenerator;
import graph.Point.Type;
import gui.core.Settings;
import gui.pages.DungeonScreen;
import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import static utils.Utils.PERFORMANCE_LOG;
import static utils.Utils.R;
import static utils.Utils.SPEED_TESTER;
import utils.Utils.Unfinished;

/**
 * The information about an Area to be serialized.
 * @author Adam Whittaker
 */
public class AreaInfo implements Serializable{
    
    private static final long serialVersionUID = 42748394236345L;

    
    /**
     * The elemental fields that need to be retained upon saving on the Area.
     * seed: The seed of the randomizer.
     * width, height: The dimensions of the Area.
     * initialJitter: The initial noise amplitude for midpoint displacement.
     * jitterDecay: The persistence of noise amplitude for midpoint displacement.
     * amplitude: The amplitude of noise for Perlin noise.
     * lacunarity: The rate of decrease of size of the Perlin noise vector grids.
     * persistence: The rate of decrease of amplitude of the Perlin noise.
     * octaves: The number of iterations of the Perlin noise algorithm.
     * feeling: The overall ethos of the level.
     * grassColor, waterColor: The average color of grass and water.
     * architecture: The information about which material tiles are made of.
     */
    private final long seed;
    
    public final int width, height;
    private final double initialJitter, jitterDecay, 
            amplitude, lacunarity, persistence;
    private final int octaves;
    public final LevelFeeling feeling;
    public final Color waterColor;
    
    public final ArchitectureInfo architecture;
    private Settings settings;
    
    public transient double[][] wallNoise;
    public transient double[][] floorNoise;
    public transient WaterPainter waterPainter;
    
    
    /**
     * Creates a random instance based on a given level feeling.
     * @param w The width.
     * @param h The height.
     * @param b The biome.
     * @param s The society.
     */
    @Unfinished("Remove temporary declarations")
    public AreaInfo(int w, int h, Biome b, Society s){
        width = w;
        height = h;
        seed = R.nextLong();
        feeling = getDefaultFeeling(b, s);
        initialJitter = feeling.initialJitter.next(0,120);
        jitterDecay = feeling.jitterDecay.next(0.7, 0.95); 
        amplitude = feeling.amplitude.next(30, 120);
        octaves = feeling.octaves.next(); 
        lacunarity = feeling.lacunarity.next(0.3, 1.0);
        persistence = feeling.persistence.next(0.3, 1.0);
        waterColor = Color.decode("#0f7e9c").darker();
        initializeNoise();
        architecture = new ArchitectureInfo(this, feeling, b, s);
        SPEED_TESTER.test("Architechture created");
    }
    
    /**
     * Initializes the noise maps during instantiation or deserialization.
     */
    private void initializeNoise(){
        R.setSeed(seed);
        wallNoise = new double[height*16][width*16];
        floorNoise = new double[height*16][width*16];
        SPEED_TESTER.test("Area information initialized");
        switch(feeling.wallNoiseType){
            case MIDPOINT: new MidpointDisplacmentNoise(125, initialJitter, jitterDecay, 255, false).apply(wallNoise);
                break;
            case PERLIN: new PerlinNoiseGenerator(width*16, height*16, amplitude, octaves, lacunarity, persistence).apply(wallNoise);
                break;
            case TILE: new MidpointDisplacmentNoise(125, initialJitter, jitterDecay, 255, true).apply(wallNoise);
                break;
        }
        SPEED_TESTER.test("Wall noise created");
        switch(feeling.floorNoiseType){
            case MIDPOINT: new MidpointDisplacmentNoise(125, initialJitter, jitterDecay, 255, false).apply(floorNoise);
                break;
            case PERLIN: new PerlinNoiseGenerator(width*16, height*16, amplitude, octaves, lacunarity, persistence).apply(floorNoise);
                break;
            case TILE: new MidpointDisplacmentNoise(125, initialJitter, jitterDecay, 255, true).apply(floorNoise);
                break;
        }
        SPEED_TESTER.test("Floor noise created");
        waterPainter = new WaterPainter(waterColor, width, height);
        SPEED_TESTER.test("Water image created");
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
    
    /**
     * Gets the Random seed used for generating the noise.
     * @return
     */
    public long getSeed(){
        return seed;
    }
    
    /**
     * Prints the relevant debug info to the PerformanceLog.
     */
    public void printInfo(){
        PERFORMANCE_LOG.println("      ---- Area ----");
        PERFORMANCE_LOG.println(" -    Area width, height: " + width + ", " + height);
        PERFORMANCE_LOG.println(" -    initialJitter: " + initialJitter);
        PERFORMANCE_LOG.println(" -    jitterJecay: " + jitterDecay);
        PERFORMANCE_LOG.println(" -    amlpitude: " + amplitude);
        PERFORMANCE_LOG.println(" -    lacunarity: " + lacunarity);
        PERFORMANCE_LOG.println(" -    persistence: " + persistence);
        PERFORMANCE_LOG.println(" -    octaves: " + octaves);
        PERFORMANCE_LOG.println(" -    seed: " + seed);
    }
    
    /**
     * Sets the settings of this Area.
     * @param s The Settings
     */
    public void setSettings(Settings s){
        settings = s;
    }
    
    
    /**
     * Gents the default Area info for quick hard-coded running for debugging
     * purposes.
     * @return
     */
    public static AreaInfo getDefaultAreaInfo(){
        return new AreaInfo(80, 80, Biome.DEFAULT_BIOME, Society.DEFAULT_SOCIETY);
    }
    
    
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        initializeNoise();
        DungeonScreen.setSettings(settings);
    }
    
}
