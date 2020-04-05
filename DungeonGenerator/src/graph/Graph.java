
package graph;

import static generation.Searcher.euclideanDist;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.max;
import static java.lang.Integer.min;
import java.util.List;
import static utils.Utils.R;
import utils.Utils.Unfinished;

/**
 * A triangular adjacency matrix implementation of the graph data structure.
 * The costs are based on proximity of the selected points to each other.
 * @author Adam Whittaker
 */
@Unfinished("To implement")
public class Graph{

    
    /**
     * map: The adjacency matrix.
     * retention: The proportion of "redundant nodes" retained, i.e: 0 equals
     * minimum spanning tree, 1 equals a Kn graph.
     */
    private double[][] map;
    private final double retention; //0 -> 1
    
    
    /**
     * Creates an instance.
     * @param points The list of points in the graph.
     * @param retention The retention factor.
     */
    public Graph(List<Point> points, double retention){
        map = initializeAdjacencyMatrix(points.size());
        calculateCost(points);
        this.retention = retention;
    }
    
    
    /**
     * Creates the adjacency matrix for a graph.
     * @param len The number of points in the graph.
     * @return a blank adjacency matrix.
     */
    private static double[][] initializeAdjacencyMatrix(int len){
        double[][] m = new double[len-1][];
        for(int n=0;n<m.length;n++){
            m[n] = new double[len-1-n];
        }
        return m;
    }
    
    /**
     * Creates a minimum spanning tree of the graph. Does not mutate the 
     * original matrix. Uses Prim's algorithm.
     * @return A minimum spanning tree adjacency matrix.
     */
    public double[][] minimumSpanningTree(){
        //Creates a blank adjacency matrix.
        double[][] tree = initializeAdjacencyMatrix(map.length);
        boolean[] inTree = new boolean[map.length];
        //Adds the first node to the tree.
        inTree[0] = true;
        //Adds edges while the tree is not connected.
        while(!connected(inTree)){
            int mFrom = -1, mTo = -1;
            double minCost = MAX_VALUE;
            //Loops over the nodes in the tree.        
            for(int from=0;from<map.length;from++) if(inTree[from]){
                //Finds the edge of least cost to an unconnected node.
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
            //Adds the edge to the tree.
            setDistance(mFrom, mTo, tree, minCost);
            inTree[mTo] = true;
        }
        
        return tree;
    }
    
    /**
     * Adds some redundant edges to the given tree that are part of the full 
     * Kn graph.
     * @param tree The minimum spanning tree.
     */
    public void retainEdges(double[][] tree){
        for(int from = 0;from<map.length;from++){
            for(int to = 0;to<map[from].length;to++){
                if(tree[from][to]==0 && R.nextDouble() < retention)
                    tree[from][to] = map[from][to];
            }
        }
    }
    
    /**
     * Gets the distance between two points in a given adjacency matrix.
     * @param a The index of one point.
     * @param b The index of the other.
     * @param map The matrix.
     * @return The cost of the edge between those two points (0 if they are not
     * directly connected).
     */
    public static double getDistance(int a, int b, double[][] map){
        return map[min(a, b)][max(a, b)-1];
    }
    
    /**
     * Sets the cost between two nodes in an adjacency matrix.
     * @param a The index of one point.
     * @param b The index of the other.
     * @param map The matrix.
     * @param dist The cost.
     */
    public static void setDistance(int a, int b, double[][] map, double dist){
        map[min(a, b)][max(a, b)-1] = dist;
    }
    
    /**
     * Checks whether the given tree connects all nodes.
     * @param inTree The boolean array for which nodes are in the tree.
     * @return true if it is connected.
     */
    private boolean connected(boolean[] inTree){
        for(boolean node : inTree) if(!node) return false;
        return true;
    }
    
    /**
     * Assigns the costs to travel between each node to the adjacency matrix. 
     * @param points The list of points in the graph.
     */
    private void calculateCost(List<Point> points){
        for(int from = 0;from<map.length;from++){
            for(int to = 0;to<map[from].length;to++){
                map[from][to] = euclideanDist(points.get(from), points.get(to+1));
            }
        }
    }
    
}
