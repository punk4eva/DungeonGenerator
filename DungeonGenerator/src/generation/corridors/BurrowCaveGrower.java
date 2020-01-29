
package generation.corridors;

import components.Area;
import components.tiles.SpecialFloor;
import components.tiles.Tile;
import graph.Point;
import graph.Point.Direction;
import static utils.Utils.R;

/**
 * This class uses cellular automata to grow caves in the shape of burrows.
 * @author Adam Whittaker
 */
public class BurrowCaveGrower extends ConwayCaveGrower{
    
    
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
     */
    public BurrowCaveGrower(Area a, double sC, int miL, int maL, int bMi, int bMa, int it, boolean p){
        super(a, sC, miL, maL, bMi, bMa, it);
        specialFloorPaths = p;
    }
    
    
    /**
     * Ensures all doors are connected via a pathway.
     * Assumptions:
     *      A tile is not part of a room if its roomNum is -1. (1)
     */
    public void buildCorridors(){
        corridorFloodFill(getFreePoint());
        pavePaths();
    }
    
    /**
     * Returns a free point in the Area which is not part of a room.
     * Assumptions: a point which is not part of a room has a roomNum of -1.
     * @return
     */
    private Point getFreePoint(){
        while(true){
            Integer[] c = new Integer[]{3+R.nextInt(area.info.width-12),
                    3+R.nextInt(area.info.height-12)};
            if(area.graph.map[c[1]][c[0]].roomNum==-1) return area.graph.map[c[1]][c[0]];
        }
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
    
    /**
     * Flood fills all the corridors from the given free point until all doors
     * are reached.
     * @param start The free point.
     */
    private void corridorFloodFill(Point start){
        area.graph.reset();
        frontier.clear();
        start.cameFrom = start;
        frontier.add(start);
        int nx, ny;
        while(!frontier.isEmpty()){
            Point p = frontier.poll();
            for(Direction dir : Direction.values()){
                nx = p.x+dir.x;
                ny = p.y+dir.y;
                try{ 
                    if(area.withinBounds(nx-1, ny-1) && area.withinBounds(nx+1, ny+1) && area.graph.map[ny][nx].cameFrom==null){
                        if(area.graph.map[ny][nx].roomNum==-1){
                            area.graph.map[ny][nx].cameFrom = p;
                            frontier.add(area.graph.map[ny][nx]);
                        }else if(area.map[ny][nx].equals(Point.Type.DOOR))
                            area.graph.map[ny][nx].cameFrom = p;
                    }
                }catch(ArrayIndexOutOfBoundsException | NullPointerException e){}
            }
        }
    }
    
}
