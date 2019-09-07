
package components.mementoes;

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
 */
@Unfinished("All placeholders")
public class ArchitectureInfo{

    public final Material doorMaterial = new Ebony();
    public final Material floorMaterial = new Oak();
    public final Material wallMaterial = new CaveStone();
    public final Material specFloorMaterial = new Birch();
    public final Material furnitureMaterial = new Mahogany();

    public final DoorIconGenerator doorGenerator;
    
    public ArchitectureInfo(AreaInfo info, LevelFeeling f){
        doorGenerator = new DoorIconGenerator(info, () -> ImageBuilder.getImageFromFile("rectDoor.png"));
    }
    
}
