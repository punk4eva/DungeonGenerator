
package generation.corridors;

import components.Area;
import generation.PostCorridorPlacer;
import gui.questions.CorridorSpecifier;

/**
 * This cellular automata fills all of the Area with wall and then erodes
 * connections between the rooms.
 * @author Adam Whittaker
 */
public class WormholeCaveGrower extends PavedCaveGrower implements PostCorridorPlacer{

    
    /**
     * Creates a new instance.
     * @param a The Area.
     * @param iterationNumber The number of iterations.
     * @param starvationLimit The minimum number of adjacent living cells needed 
     * before the cell "starves".
     * @param windyness How windy the paths are.
     */
    public WormholeCaveGrower(Area a, int iterationNumber, int starvationLimit, double windyness){
        super(a, 1.00, iterationNumber,  starvationLimit, 9,  9, -1, windyness);
        addCheck = (from, to) -> to.isCorridor;
    }

    
    @Override
    public void generate(){
        initialize();
        corridorFloodFill(getFreePoint());
        pavePaths();
        for(;iterNum>0;iterNum--) iterate();
        convertGraphToArea();
    }
    
    /**
     * Creates a pathway from each pathfinding valid door to the free point.
     * This ensures that all rooms are connected.
     */
    private void pavePaths(){
        area.graph.doors.forEach((p) -> {
            while(p.cameFrom!=p && p.cameFrom!=null){
                p = p.cameFrom;
                area.graph.map[p.y][p.x].isCorridor = false;
            }
        });
    }
    
    
    public static final CorridorSpecifier<WormholeCaveGrower> WORMHOLE_CAVE_SPECIFIER;
    static{
        try{
            WORMHOLE_CAVE_SPECIFIER = new CorridorSpecifier<>(
                    WormholeCaveGrower.class.getConstructor(Area.class, 
                            int.class, int.class, double.class),
                    "Wormhole Cave Grower", 
                    "Design the wormhole growing algorithm");
        }catch(NoSuchMethodException e){
            throw new IllegalStateException(e);
        }
    }
    
}
