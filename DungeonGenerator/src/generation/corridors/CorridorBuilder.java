
package generation.corridors;

import components.Area;
import components.tiles.Tile;
import generation.Searcher;
import graph.Point;

/**
 *
 * @author Adam Whittaker
 */
public abstract class CorridorBuilder extends Searcher{
    
    
    public CorridorBuilder(Area a){
        super(a);
    }
    
    
    /**
     * Generates and builds all corridors in the Area.
     */
    public abstract void build();
    
    protected void extend(Point p, Point a, Point b){
        if(!(area.map[b.y][b.x]!=null && area.map[b.y][b.x].equals(Point.Type.DOOR))) area.map[b.y][b.x] = Tile.genFloor(area);
        if(a.x!=b.x){
            if(area.map[b.y-1][b.x]==null){
                area.map[b.y-1][b.x] = Tile.genWall(area);
                area.graph.map[b.y-1][b.x].isCorridor = true;
            }
            if(area.map[b.y+1][b.x]==null){
                area.map[b.y+1][b.x] = Tile.genWall(area);
                area.graph.map[b.y+1][b.x].isCorridor = true;
            }
            if(area.map[a.y-1][a.x]==null){
                area.map[a.y-1][a.x] = Tile.genWall(area);
                area.graph.map[a.y-1][a.x].isCorridor = true;
            }
            if(area.map[a.y+1][a.x]==null){
                area.map[a.y+1][a.x] = Tile.genWall(area);
                area.graph.map[a.y+1][a.x].isCorridor = true;
            }
            if(p==null||a.x==p.x){
                if(area.map[a.y-1][a.x-1]==null){
                    area.map[a.y-1][a.x-1] = Tile.genWall(area);
                    area.graph.map[a.y-1][a.x-1].isCorridor = true;
                }
                if(area.map[a.y-1][a.x+1]==null){
                    area.map[a.y-1][a.x+1] = Tile.genWall(area);
                    area.graph.map[a.y-1][a.x+1].isCorridor = true;
                }
                if(area.map[a.y+1][a.x-1]==null){
                    area.map[a.y+1][a.x-1] = Tile.genWall(area);
                    area.graph.map[a.y+1][a.x-1].isCorridor = true;
                }
                if(area.map[a.y+1][a.x+1]==null){
                    area.map[a.y+1][a.x+1] = Tile.genWall(area);
                    area.graph.map[a.y+1][a.x+1].isCorridor = true;
                }
            }
        }else{
            if(area.map[b.y][b.x-1]==null){
                area.map[b.y][b.x-1] = Tile.genWall(area);
                area.graph.map[b.y][b.x-1].isCorridor = true;
            }
            if(area.map[b.y][b.x+1]==null){
                area.map[b.y][b.x+1] = Tile.genWall(area);
                area.graph.map[b.y][b.x+1].isCorridor = true;
            }
            if(area.map[a.y][a.x-1]==null){
                area.map[a.y][a.x-1] = Tile.genWall(area);
                area.graph.map[a.y][a.x-1].isCorridor = true;
            }
            if(area.map[a.y][a.x+1]==null){
                area.map[a.y][a.x+1] = Tile.genWall(area);
                area.graph.map[a.y][a.x+1].isCorridor = true;
            }
            if(p==null||a.x!=p.x){
                if(area.map[a.y-1][a.x-1]==null){
                    area.map[a.y-1][a.x-1] = Tile.genWall(area);
                    area.graph.map[a.y-1][a.x-1].isCorridor = true;
                }
                if(area.map[a.y-1][a.x+1]==null){
                    area.map[a.y-1][a.x+1] = Tile.genWall(area);
                    area.graph.map[a.y-1][a.x+1].isCorridor = true;
                }
                if(area.map[a.y+1][a.x-1]==null){
                    area.map[a.y+1][a.x-1] = Tile.genWall(area);
                    area.graph.map[a.y+1][a.x-1].isCorridor = true;
                }
                if(area.map[a.y+1][a.x+1]==null){
                    area.map[a.y+1][a.x+1] = Tile.genWall(area);
                    area.graph.map[a.y+1][a.x+1].isCorridor = true;
                }
            }
        }
    }
    
    /**
     * Builds a corridor from a singular Point after a flood fill.
     * @param p
     */
    protected void buildCorridor(Point p){
        Point np, pp = null;
        while(p.cameFrom!=p&&p.cameFrom!=null){
            np = p.cameFrom;
            extend(pp, p, np);
            pp = p;
            p = np;
        }
    }
    
}
