
package generation.corridors;

import components.Area;
import components.tiles.SpecialFloor;
import components.tiles.Tile;
import generation.PostCorridorPlacer;
import gui.questions.CorridorSpecifier;

/**
 * This class uses cellular automata to grow caves in the shape of burrows after
 * rooms have been placed.
 * @author Adam Whittaker
 */
public class BurrowCaveGrower extends PavedCaveGrower implements PostCorridorPlacer{
    
    
    /**
     * Whether or not to use special floor for the paths.
     */
    private final boolean specialFloorPaths;

    
    /**
     * Creates an instance by forwarding parameters.
     * @param a
     * @param startChance
     * @param iterationNumber
     * @param starvationLimit
     * @param overpopulationLimit
     * @param birthMinimum
     * @param birthMaximum
     * @param specialPaths Whether or not to use special floor for the paths.
     * @param windyness How curvy the paths are.
     */
    public BurrowCaveGrower(Area a, double startChance, int iterationNumber, 
            int starvationLimit, int overpopulationLimit, int birthMinimum, 
            int birthMaximum, double windyness, boolean specialPaths){
        super(a, startChance, iterationNumber, starvationLimit, 
                overpopulationLimit, birthMinimum, birthMaximum, windyness);
        specialFloorPaths = specialPaths;
    }
    
    
    /**
     * Runs the algorithm and builds the paths.
     * Assumptions:
     *      A tile is not part of a room if its roomNum is -1.
     */
    @Override
    public void generate(){
        super.generate();
        corridorFloodFill(getFreePoint());
        pavePaths();
    }

    /**
     * Creates a pathway from each pathfinding valid door to the free point.
     * This ensures that all rooms are connected.
     */
    private void pavePaths(){
        area.graph.doors.forEach((p) -> {
            while(p.cameFrom!=p && p.cameFrom!=null){
                p = p.cameFrom;
                if(specialFloorPaths) area.map[p.y][p.x] = new SpecialFloor("pathway");
                else area.map[p.y][p.x] = Tile.genFloor(area);
            }
        });
    }
    
    
    public static final CorridorSpecifier<BurrowCaveGrower> BURROW_CAVE_SPECIFIER;
    static{
        try{
            BURROW_CAVE_SPECIFIER = new CorridorSpecifier<>(
                    BurrowCaveGrower.class.getConstructor(Area.class, 
                            double.class, int.class, int.class, int.class, 
                            int.class, int.class, double.class, boolean.class),
                    "Burrow Cave Grower", 
                    "Design the burrow growing algorithm");
        }catch(NoSuchMethodException e){
            throw new IllegalStateException(e);
        }
    }
    
}
