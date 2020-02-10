
package generation.corridors;

import components.Area;
import graph.Point;
import static utils.Utils.R;

/**
 * This class is a blueprint of cave-growing algorithms which auto-generate 
 * their own corridors.
 * @author Adam Whittaker
 */
public abstract class PavedCaveGrower extends ConwayCaveGrower{

    
    /**
     * Creates an instance by forwarding the arguments to the super-constructor.
     * @param a
     * @param startChance
     * @param iterationNumber
     * @param starvationLimit
     * @param overpopulationLimit
     * @param birthMinimum
     * @param birthMaximum
     * @param windyness How windy the paths are.
     */
    public PavedCaveGrower(Area a, double startChance, int iterationNumber, 
            int starvationLimit, int overpopulationLimit, int birthMinimum, 
            int birthMaximum, double windyness){
        super(a, startChance, iterationNumber, starvationLimit, 
                overpopulationLimit, birthMinimum, birthMaximum);
        frontier.setFunction(point -> R.nextDouble()*windyness);
    }
    
    
    /**
     * Flood fills all the corridors from the given free point until all doors
     * are reached.
     * @param start The free point.
     */
    protected void corridorFloodFill(Point start){
        area.graph.reset();
        frontier.clear();
        start.cameFrom = start;
        frontier.add(start);
        int nx, ny;
        while(!frontier.isEmpty()){
            Point p = frontier.poll();
            for(Point.Direction dir : Point.Direction.values()){
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
