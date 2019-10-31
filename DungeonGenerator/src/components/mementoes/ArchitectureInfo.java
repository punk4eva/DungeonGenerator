
package components.mementoes;

import biomes.Biome;
import components.LevelFeeling;
import filterGeneration.DoorIconGenerator;
import filterGeneration.ImageBuilder;
import materials.Material;
import materials.stone.*;
import materials.wood.*;
import utils.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 *
 * This class holds all the information required to color individual tiles in a location.
 * It holds the materials used to build different parts of the place, the biome, and the door generation algorithm.
 */
@Unfinished("All placeholders")
public class ArchitectureInfo{

    public final Material doorMaterial = new Ebony();
    public final Material floorMaterial = new Oak();
    public final Material wallMaterial = new Marble();
    public final Material specFloorMaterial = new Birch();
    public final Material furnitureMaterial = new Mahogany();

    public final DoorIconGenerator doorGenerator;
    
    public final Biome biome = Biome.MIDLANDS;
    
    public ArchitectureInfo(AreaInfo info, LevelFeeling f){
        doorGenerator = new DoorIconGenerator(info, () -> ImageBuilder.getImageFromFile("rectDoor.png"));
    }

    
    @Unfinished("Placeholder")
    public Material getFurnitureMaterial(int price){
        return new StoneBrick();
    }
    
}
