
package generation.corridors;

import components.Area;
import components.tiles.Tile;
import generation.Searcher;
import graph.Point;
import graph.Point.Type;
import static utils.Utils.R;
import static utils.Utils.SPEED_TESTER;

/**
 * Blueprint for cellular automata based cave generation algorithms.
 * @author Adam Whittaker
 */
public abstract class CaveGrower extends Searcher{
    
    
    /**
     * startAliveChance: The chance for a cell to start alive.
     * iterNum: The number of iterations.
     */
    private final double startAliveChance;
    protected int iterNum;

    
    /**
     * Creates a new instance.
     * @param a The Area.
     * @param startChance The chance for a cell to start alive.
     * @param iterationNumber The number of iterations.
     */
    public CaveGrower(Area a, double startChance, int iterationNumber){
        super(a);
        startAliveChance = startChance;
        iterNum = iterationNumber;
    }
    
    
    /**
     * Iterates through every non-room cell in the Area and initializes them to "dead" or
     * "alive" based on the startAliveChance.
     * Assumptions: A tile's roomNum is -1 if it is not part of a room.
     */
    protected void initialize(){
        for(int x=1;x<area.info.width-1;x++) 
            for(int y=1;y<area.info.height-1;y++)
                if(area.graph.map[y][x].roomNum==-1 && R.nextDouble()<startAliveChance)
                    area.graph.map[y][x].isCorridor = true;
        for(int x=0;x<area.info.width;x++){
            area.graph.map[0][x].isCorridor = true;
            area.graph.map[area.info.height-1][x].isCorridor = true;
        }
        for(int y=1;y<area.info.height-1;y++){
            area.graph.map[y][0].isCorridor = true;
            area.graph.map[y][area.info.width-1].isCorridor = true;
        }
    }
    
    /**
     * Gets the number of living neighbours in the vicinity of the given 
     * coordinates.
     * @param _x The tile x.
     * @param _y The tile y.
     * @param dist The distance away from the tile to search.
     * @return
     */
    protected int getNeighborNum(int _x, int _y, int dist){
        int n = 0;
        for(int y=Math.max(_y-dist, 0);y<=Math.min(_y+dist, area.info.height-1);y++){
            for(int x=Math.max(_x-dist, 0);x<=Math.min(_x+dist, area.info.width-1);x++){
                if(x!=_x||y!=_y){
                    if(area.map[y][x]!=null){
                        if(area.map[y][x].equals(Type.WALL)) n++;
                        else if(area.map[y][x].equals(Type.DOOR)) return 0;
                    }else if(area.graph.map[y][x].isCorridor) n++;
                }
            }
        }
        return n;
    }
    
    /**
     * One iteration of the algorithm. The cells are cycled through and their
     * status is updated based on the number of adjacent cells.
     */
    protected abstract void iterate();
    
    /**
     * Sets the corridor flag of each tile to its "checked" flag. This is so 
     * that the algorithm can use the checked flag to perform a simultaneous
     * update on the corridor state. To be called at end of iterate() 
     * implementation.
     * Assumptions: roomNum == -1 if the tile is not part of a room.
     */
    protected void flashChecked(){
        for(int x=1;x<area.info.width-1;x++){
            for(int y=1;y<area.info.height-1;y++){
                if(area.graph.map[y][x].roomNum!=-1) continue;
                area.graph.map[y][x].isCorridor = area.graph.map[y][x].checked;
            }
        }
    }
    
    /**
     * Runs the cellular automata and generates an Area.
     */
    public void generate(){
        initialize();
        for(;iterNum>0;iterNum--) iterate();
        convertGraphToArea();
    }
    
    /**
     * Converts living cells to walls and dead ones to floor.
     */
    protected void convertGraphToArea(){
        for(int x=0;x<area.info.width;x++) for(int y=0;y<area.info.height;y++) 
            if(area.map[y][x]==null){
                if(area.graph.map[y][x].isCorridor) area.map[y][x] = Tile.genWall(area);
                else area.map[y][x] = Tile.genFloor(area);
        }
        SPEED_TESTER.test("Caves grown");
    }
    
    /**
     * Sets the corridor flag for each tile to false.
     */
    public void flushGraphCorridors(){
        for(int y=0;y<area.info.height;y++){
            for(int x=0;x<area.info.width;x++){
                area.graph.map[y][x].isCorridor = false;
            }
        }
    }
    
    /**
     * Returns a free point in the Area which is not part of a room.
     * Assumptions: a point which is not part of a room has a roomNum of -1.
     * @return
     */
    protected Point getFreePoint(){
        while(true){
            Integer[] c = new Integer[]{3+R.nextInt(area.info.width-6),
                    3+R.nextInt(area.info.height-6)};
            if(area.graph.map[c[1]][c[0]].roomNum==-1) return area.graph.map[c[1]][c[0]];
        }
    }

}
