
package components.mementoes;

import biomes.Biome;
import biomes.BiomeProcessor;
import biomes.Society;
import components.LevelFeeling;
import java.util.LinkedList;
import textureGeneration.DoorIconGenerator;
import materials.Material;
import utils.Distribution;

/**
 *
 * @author Adam Whittaker
 *
 * This class holds all the information required to color individual tiles in a location.
 * It holds the materials used to build different parts of the place, the biome, and the door generation algorithm.
 */
public class ArchitectureInfo{

    
    public final Material doorMaterial;
    public final Material floorMaterial;
    public final Material wallMaterial;
    public final Material specFloorMaterial;
    public final Material furnitureMaterial;

    public final DoorIconGenerator doorGenerator;
    
    public final BiomeProcessor biomeProcessor/* = new BiomeProcessor(Biome.PLAINS, new Society(50, 50, 50))*/;
    
    
    /**
     * Creates a new instance.
     * @param info The information of the Area.
     * @param f The general ethos of the level.
     * @param b The biome.
     * @param s The society.
     */
    public ArchitectureInfo(AreaInfo info, LevelFeeling f, Biome b, Society s){
        biomeProcessor = new BiomeProcessor(b, s);
        
        doorGenerator = new DoorIconGenerator(info, () -> 
                DoorIconGenerator.getRandomDoorBackground(
                        new Distribution(new double[]{1, 1})));
        
        LinkedList<Material> banned = new LinkedList<>();
        
        doorMaterial = biomeProcessor.getMaterial(m -> m.door);
        banned.add(doorMaterial);
        
        floorMaterial = biomeProcessor.getMaterial(m -> m.floor && !banned.contains(m));
        banned.add(floorMaterial);
        
        wallMaterial = biomeProcessor.getMaterial(m -> m.wall && !banned.contains(m));
        banned.add(wallMaterial);
        
        specFloorMaterial = biomeProcessor.getMaterial(m -> m.floor && !banned.contains(m));
        furnitureMaterial = biomeProcessor.getMaterial(m -> m.furniture && !banned.contains(m));
    }
    
}
