
package generation.corridors;

import components.Area;
import generation.PostCorridorPlacer;
import gui.questions.CorridorSpecifier;
import utils.Utils.Unfinished;

/**
 * Uses cellular automata to grow caves. This implementation searches a wider
 * radius.
 * @author Adam Whittaker
 */
@Unfinished("Flesh out with variable search radius.")
public class RadialCaveGrower extends CaveGrower implements PostCorridorPlacer{

    
    /**
     * Creates an instance by passing through the arguments to the 
     * super-constructor.
     * @param a
     * @param startChance
     * @param iterationNumber
     */
    public RadialCaveGrower(Area a, double startChance, int iterationNumber){
        super(a, startChance, iterationNumber);
    }

    
    @Override
    protected void iterate(){
        for(int x=1;x<area.info.width-1;x++){
            for(int y=1;y<area.info.height-1;y++){
                if(area.graph.map[y][x].roomNum!=-1) continue;
                area.graph.map[y][x].checked = getNeighborNum(x, y, 1)>=5 && 
                        getNeighborNum(x, y, 2)>0;
            }
        }
        flashChecked();
    }
    
    
    public static final CorridorSpecifier<RadialCaveGrower> RADIAL_CAVE_SPECIFIER;
    static{
        try{
            RADIAL_CAVE_SPECIFIER = new CorridorSpecifier<>(
                    RadialCaveGrower.class.getConstructor(Area.class, 
                            double.class, int.class),
                    "Radial Cave Grower", 
                    "Design the cave growing algorithm");
        }catch(NoSuchMethodException e){
            throw new IllegalStateException(e);
        }
    }

}
