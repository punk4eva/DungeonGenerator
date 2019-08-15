
package generation;

import components.Area;
import graph.Point;
import graph.Point.Direction;
import java.io.Serializable;
import java.util.function.BiFunction;
import java.util.function.Function;
import utils.PriorityQueue;

/**
 *
 * @author Adam Whittaker
 */
public class Searcher{
    
    public BiFunction<Point, Point, Boolean> addCheck = ((Serializable & BiFunction<Point, Point, Boolean>) ((from, to) -> to.checked!=null && !to.checked));
    public PriorityQueue<Point> frontier = new PriorityQueue<>((Serializable & Function<Point, Double>)p -> p.currentCost);
    protected Area area;
    
    public Searcher(Area a){
        area = a;
    }
    
    /**
     * Flood fills the Searcher's graph using a search prioritizing based on the
     * Searcher's comparator in PriorityQueue. Checks stuff.
     * @param start The starting point.
     */
    public void floodfill(Point start){
        area.graph.reset();
        frontier.clear();
        start.currentCost = 0;
        frontier.add(start);
        int nx, ny;
        while(!frontier.isEmpty()){
            Point p = frontier.poll();
            for(Direction dir : Direction.values()){
                nx = p.x+dir.x;
                ny = p.y+dir.y;
                try{ 
                    if(addCheck.apply(p, area.graph.map[ny][nx])){
                        area.graph.map[ny][nx].checked = true;
                        area.graph.map[ny][nx].cameFrom = p;
                        area.graph.map[ny][nx].currentCost = p.currentCost + 1;
                        frontier.add(area.graph.map[ny][nx]);
                    }
                }catch(ArrayIndexOutOfBoundsException | NullPointerException e){}
            }
        }
    }
    
    public static int manhattanDist(Point a, Point b){
        return Math.abs(a.x-b.x) + Math.abs(a.y-b.y);
    }
    
    public static double euclideanDist(Point a, Point b){
        return Math.sqrt((a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y));
    }

}
