
package generation;

import components.Area;
import graph.Point;
import graph.Point.Direction;
import java.io.Serializable;
import java.util.function.BiPredicate;
import java.util.function.Function;
import utils.PriorityQueue;

/**
 * This class is designed to serve as a super-type to flood-fill algorithms, but
 * can perform a basic flood-fill on its own. It also contains numerous static
 * access methods for flood fills.
 * @author Adam Whittaker
 */
public class Searcher{
    
    
    /**
     * addCheck: The predicate determining whether the flood fill can spread 
     * between two given points.
     * frontier: The priority queue for points to be scanned.
     * area: The Area to be flood filled.
     */
    public BiPredicate<Point, Point> addCheck = ((Serializable & BiPredicate<Point, Point>) ((from, to) -> to.checked!=null && !to.checked));
    public PriorityQueue<Point> frontier = new PriorityQueue<>((Serializable & Function<Point, Double>)p -> p.currentCost);
    protected Area area;
    
    
    /**
     * Creates a new instance.
     * @param a The Area to be searched.
     */
    public Searcher(Area a){
        area = a;
    }
    
    
    /**
     * Flood fills the Searcher's graph using a search prioritizing based on the
     * Searcher's comparator in PriorityQueue. Checks stuff.
     * @param start The starting point.
     */
    public void floodfill(Point start){
        //Prepares the graph for the flood fill.
        area.graph.reset();
        frontier.clear();
        //Initializes the first point of the search.
        start.currentCost = 0;
        frontier.add(start);
        int nx, ny;
        //Loops until there are no tiles left to be searched.
        while(!frontier.isEmpty()){
            //Gets the highest value point in the frontier.
            Point p = frontier.poll();
            //Checks the adjacent points.
            for(Direction dir : Direction.values()){
                nx = p.x+dir.x;
                ny = p.y+dir.y;
                try{
                    //If the point is eligable to be added to the frontier, it
                    //is added and connected to the previous point.
                    if(addCheck.test(p, area.graph.map[ny][nx])){
                        area.graph.map[ny][nx].checked = true;
                        area.graph.map[ny][nx].cameFrom = p;
                        area.graph.map[ny][nx].currentCost = p.currentCost + 1;
                        frontier.add(area.graph.map[ny][nx]);
                    }
                }catch(ArrayIndexOutOfBoundsException | NullPointerException e){}
            }
        }
    }
    
    
    /**
     * Returns the manhattan distance between two given points.
     * @param a
     * @param b
     * @return |a.x-b.x| + |a.y-a.y|
     */
    public static int manhattanDist(Point a, Point b){
        return Math.abs(a.x-b.x) + Math.abs(a.y-b.y);
    }
    
    /**
     * Returns the Euclidean/Pythagoras distance between two points.
     * @param a
     * @param b
     * @return
     */
    public static double euclideanDist(Point a, Point b){
        return Math.sqrt((a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y));
    }

}
