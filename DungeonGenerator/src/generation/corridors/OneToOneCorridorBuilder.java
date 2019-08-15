
package generation.corridors;

import components.Area;
import graph.Point;
import graph.Point.Direction;
import java.util.Collections;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 */
public class OneToOneCorridorBuilder extends CorridorBuilder{

    
    private final double windyness;
    
    public OneToOneCorridorBuilder(Area a, double w){
        super(a);
        addCheck = (from, to) -> /*to.currentCost > from.currentCost + to.movementCost &&*/ to.cameFrom==null && !to.type.equals(Point.Type.DOOR) && to.roomNum==-1;
        windyness = w;
    }

    
    @Override
    public void build(){
        Collections.shuffle(area.graph.doors);
        System.out.println(area.graph.doors.size());
        for(int n=1;n<area.graph.doors.size();n++)
            generateCorridor(area.graph.doors.get(n-1), area.graph.doors.get(n));
        generateCorridor(area.graph.doors.get(area.graph.doors.size()-1), area.graph.doors.get(0));
    }
    
    private void generateCorridor(Point a, Point b){
        corridorFloodFill(a, b);
        buildCorridor(b);
    }
    
    /**
     * Assumptions: 
     *      Point.cameFrom is null throughout the graph. (1)
     *      A tile is part of a room if its roomNum is not -1. (2)
     */
    private void corridorFloodFill(Point start, Point end){
        area.graph.reset(); //Verifies assumption (1)
        frontier.clear();
        frontier.setFunction(p -> R.nextDouble()*2D*windyness - windyness + manhattanDist(p, end));
        frontier.add(start);
        start.cameFrom = start;
        int nx, ny;
        System.out.println("Starting point: " + start.x + ", " + start.y);
        while(!frontier.isEmpty()){
            Point p = frontier.removeFirst();
            for(Direction dir : Direction.values()){
                nx = p.x+dir.x;
                ny = p.y+dir.y;
                if(area.withinBounds(nx-1, ny-1)&&area.withinBounds(nx+1, ny+1)){
                    if(addCheck.apply(p, area.graph.map[ny][nx])){
                        area.graph.map[ny][nx].cameFrom = p;
                        frontier.add(area.graph.map[ny][nx]);
                    }else if(nx==end.x && ny==end.y){
                        System.out.println("Found end!");
                        area.graph.map[ny][nx].cameFrom = p;
                        return;
                    }
                }
            }
        }
    }

}
