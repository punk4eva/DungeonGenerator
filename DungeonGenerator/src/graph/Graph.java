
package graph;

import static generation.Searcher.euclideanDist;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.max;
import static java.lang.Integer.min;
import java.util.List;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 */
public class Graph{

    
    private double[][] map;
    private final double retention; //0 -> 1
    
    
    public Graph(List<Point> points, double retention){
        map = initializeMap(points.size());
        calculateCost(points);
        this.retention = retention;
    }
    
    
    private static double[][] initializeMap(int len){
        double[][] m = new double[len-1][];
        for(int n=0;n<m.length;n++){
            m[n] = new double[len-1-n];
        }
        return m;
    }
    
    public double[][] minimumSpanningTree(){
        double[][] tree = initializeMap(map.length);
        boolean[] inTree = new boolean[map.length];
        inTree[0] = true;
        
        while(!connected(inTree)){
            int mFrom = -1, mTo = -1;
            double minCost = MAX_VALUE;
                    
            for(int from=0;from<map.length;from++) if(inTree[from]){
                for(int to=from+1;to<map.length;to++){
                    if(!inTree[to] && from!=to){
                        double dist = getDistance(from, to, map);
                        if(dist<minCost){
                            mFrom = from;
                            mTo = to;
                            minCost = dist;
                        }
                    }
                }
            }
            
            setDistance(mFrom, mTo, tree, minCost);
        }
        
        return tree;
    }
    
    public void retainNodes(double[][] tree){
        for(int from = 0;from<map.length;from++){
            for(int to = 0;to<map[from].length;to++){
                if(tree[from][to]==0 && R.nextDouble() < retention)
                    tree[from][to] = map[from][to];
            }
        }
    }
    
    public static double getDistance(int a, int b, double[][] map){
        return map[min(a, b)][max(a, b)-1];
    }
    
    public static void setDistance(int a, int b, double[][] map, double dist){
        map[min(a, b)][max(a, b)-1] = dist;
    }
    
    private boolean connected(boolean[] inTree){
        for(boolean node : inTree) if(!node) return false;
        return true;
    }
    
    private void calculateCost(List<Point> points){
        for(int from = 0;from<map.length;from++){
            for(int to = 0;to<map[from].length;to++){
                map[from][to] = euclideanDist(points.get(from), points.get(to+1));
            }
        }
    }
    
}
