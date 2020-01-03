
package generation.corridors;

import components.Area;
import components.tiles.Tile;
import generation.Searcher;
import graph.Point.Type;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 */
public class CaveGrower extends Searcher{
    
    private final double startChance;
    private int iterNum;

    public CaveGrower(Area a, double sC, int it){
        super(a);
        startChance = sC;
        iterNum = it;
        frontier.setFunction(point -> R.nextDouble()*100D);
    }
    
    private void initialize(){
        for(int x=1;x<area.info.width-1;x++) 
            for(int y=1;y<area.info.height-1;y++)
                if(area.graph.map[y][x].roomNum==-1 && R.nextDouble()<startChance)
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
    
    protected int getNeighborNum(int _x, int _y, int dist){
        int n = 0;
        for(int y=Math.max(_y-dist, 0);y<=Math.min(_y+dist, area.info.height-1);y++){
            for(int x=Math.max(_x-dist, 0);x<=Math.min(_x+dist, area.info.width-1);x++){
                if(x!=0||y!=0){
                    if(area.map[y][x]!=null){
                        if(area.map[y][x].equals(Type.WALL)) n++;
                        else if(area.map[y][x].equals(Type.DOOR)) return 0;
                    }else if(area.graph.map[y][x].isCorridor) n++;
                }
            }
        }
        return n;
    }
    
    protected void iterate(){
        for(int x=1;x<area.info.width-1;x++){
            for(int y=1;y<area.info.height-1;y++){
                if(area.graph.map[y][x].roomNum!=-1) continue;
                area.graph.map[y][x].checked = getNeighborNum(x, y, 1)>=5 && 
                        getNeighborNum(x, y, 2)>0;
            }
        }
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
    public void build(){
        initialize();
        for(;iterNum>0;iterNum--) iterate();
        convertGraphToArea();
    }
    
    private void convertGraphToArea(){
        for(int x=0;x<area.info.width;x++) for(int y=0;y<area.info.height;y++) 
            if(area.map[y][x]==null){
                if(area.graph.map[y][x].isCorridor) area.map[y][x] = Tile.genWall(area, x, y);
                else area.map[y][x] = Tile.genFloor(area);
        }
    }
    
    public void flushGraphCorridors(){
        for(int y=0;y<area.info.height;y++){
            for(int x=0;x<area.info.width;x++){
                area.graph.map[y][x].isCorridor = false;
            }
        }
    }

}
