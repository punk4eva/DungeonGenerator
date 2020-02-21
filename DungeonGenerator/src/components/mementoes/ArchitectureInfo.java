
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
    
    public final BiomeProcessor biome = new BiomeProcessor(Biome.PLAINS, new Society(50, 50, 50));
    
    
    /**
     * Creates a new instance.
     * @param info The information of the Area.
     * @param f The general ethos of the level.
     */
    public ArchitectureInfo(AreaInfo info, LevelFeeling f){
        doorGenerator = new DoorIconGenerator(info, () -> 
                DoorIconGenerator.getRandomDoorBackground(
                        new Distribution(new double[]{1, 1})));
        
        LinkedList<Material> banned = new LinkedList<>();
        
        doorMaterial = biome.getMaterial(m -> m.door);
        banned.add(doorMaterial);
        
        floorMaterial = biome.getMaterial(m -> m.floor && !banned.contains(m));
        banned.add(floorMaterial);
        
        wallMaterial = biome.getMaterial(m -> m.wall && !banned.contains(m));
        banned.add(wallMaterial);
        
        specFloorMaterial = biome.getMaterial(m -> m.floor && !banned.contains(m));
        furnitureMaterial = biome.getMaterial(m -> m.furniture && !banned.contains(m));
    }
    
}
