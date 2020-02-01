
package generation.corridors;

import components.Area;
import components.tiles.SpecialFloor;
import components.tiles.Tile;
import generation.PostCorridorPlacer;

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
     * @param sC
     * @param miL
     * @param maL
     * @param bMi
     * @param bMa
     * @param it
     * @param p Whether or not to use special floor for the paths.
     * @param windyness How curvy the paths are.
     */
    public BurrowCaveGrower(Area a, double sC, int miL, int maL, int bMi, int bMa, int it, boolean p, double windyness){
        super(a, sC, miL, maL, bMi, bMa, it, windyness);
        specialFloorPaths = p;
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
    
}
