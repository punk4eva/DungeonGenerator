
package components.mementoes;

import biomes.Biome;
import biomes.BiomeProcessor;
import biomes.Society;
import components.LevelFeeling;
import java.util.LinkedList;
import materials.Material;
import textureGeneration.DoorIconGenerator;
import utils.Distribution;

/**
 * This class holds all the information required to color individual tiles in a 
 * location. It holds the materials used to build different parts of the place, 
 * the biome, and the door generation algorithm.
 * @author Adam Whittaker
 */
public class ArchitectureInfo{

    
    /**
     * Material: The materials to use for the various purposes of the dungeon.
     * doorGenerator: The algorithm to generate the images for the doors.
     * biomeProcessor: The biome and society information.
     */
    public final Material doorMaterial;
    public final Material floorMaterial;
    public final Material wallMaterial;
    public final Material specFloorMaterial;
    public final Material furnitureMaterial;

    public final DoorIconGenerator doorGenerator;
    
    public final BiomeProcessor biomeProcessor;
    
    
    /**
     * Creates a new instance.
     * @param info The information of the Area.
     * @param f The general ethos of the level.
     * @param b The biome.
     * @param s The society.
     */
    public ArchitectureInfo(AreaInfo info, LevelFeeling f, Biome b, Society s){
        //Creates the biome processor and door generator.
        biomeProcessor = new BiomeProcessor(b, s);
        doorGenerator = new DoorIconGenerator(info, () -> 
                DoorIconGenerator.getRandomDoorBackground(
                        new Distribution(new double[]{1, 1})));
        
        //A list of used materials so that the floor and walls are not made out
        //of the same material.
        LinkedList<Material> used = new LinkedList<>();
        
        //Initializes the materials to use in the Area.
        doorMaterial = biomeProcessor.getMaterial(m -> m.door);
        used.add(doorMaterial);
        
        floorMaterial = biomeProcessor.getMaterial(m -> m.floor && !used.contains(m));
        used.add(floorMaterial);
        
        wallMaterial = biomeProcessor.getMaterial(m -> m.wall && !used.contains(m));
        used.add(wallMaterial);
        
        specFloorMaterial = biomeProcessor.getMaterial(m -> m.floor && !used.contains(m));
        furnitureMaterial = biomeProcessor.getMaterial(m -> m.furniture && !used.contains(m));
    }
    
}
