/*
 * Copyright 2019 Adam Whittaker.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package generation.corridors;

import components.Area;
import components.tiles.Tile;
import generation.Searcher;
import graph.Point;
import graph.Point.*;
import utils.PriorityQueue;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 * 
 * Builds corridors using the spider corridor algorithm.
 */
public class SpiderCorridorBuilder{
    
    
    private final static int DECAY_LIMIT = 2;
    
    private final Area area;
    private final int windyness;
    private final boolean decayActive;
    
    private final class SpiderCorridorAlgorithm extends Searcher{
        
        SpiderCorridorAlgorithm(Area a, int windyness){
            super(a);
            addCheck = (from, to) -> /*to.currentCost > from.currentCost + to.movementCost &&*/ to.cameFrom==null&&(to.checked==null||!to.checked);
            frontier = new PriorityQueue<>(p -> (double)R.nextInt(windyness) + p.currentCost);
        }
        
        @Override
        public void floodfill(Point start){
            area.graph.reset();
            frontier.clear();
            start.currentCost = 0;
            frontier.add(start);
            start.checked = true;
            start.cameFrom = start;
            int nx, ny;
            System.out.println("Starting point: " + start.x + ", " + start.y);
            while(!frontier.isEmpty()){
                Point p = frontier.removeFirst();
                for(Direction dir : Direction.values()){
                    nx = p.x+dir.x;
                    ny = p.y+dir.y;
                    area.graph.map[ny][nx].currentCost = p.currentCost + 1;
                    if(area.withinBounds(nx-1, ny-1)&&area.withinBounds(nx+1, ny+1)){
                        if(area.map[ny][nx]==null&&addCheck.apply(p, area.graph.map[ny][nx])){
                            area.graph.map[ny][nx].checked = true;
                            area.graph.map[ny][nx].cameFrom = p;
                            frontier.add(area.graph.map[ny][nx]);
                        }else if(area.map[ny][nx]!=null&&area.map[ny][nx].equals(Type.DOOR)){
                            area.graph.map[ny][nx].checked = true;
                            area.graph.map[ny][nx].cameFrom = p;
                        }
                    }
                }
            }
        }
        
    }
    
    /**
     * Creates a new instance.
     * @param a The Area.
     * @param w The windyness factor of the corridors.
     * @param decay Whether to decay walls into cavities.
     */
    public SpiderCorridorBuilder(Area a, int w, boolean decay){
        area = a;
        windyness = w;
        decayActive = decay;
    }
    
    private void extend(Point p, Point a, Point b){
        area.map[b.y][b.x] = Tile.genFloor(area);
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
    
    private void growCavities(){
        for(int x=1;x<area.info.width-1;x++) for(int y=1;y<area.info.height-1;y++)
            if(canDecayWall(x, y)) area.map[y][x] = Tile.genFloor(area);
    }
    
    private boolean canDecayWall(int x, int y){
        if(area.map[y][x] == null || !area.map[y][x].equals(Type.WALL) || area.graph.map[y][x].isCorridor) return false;
        int floors = 0;
        if(area.map[y-1][x-1]==null) return false;
        if(area.map[y-1][x-1].isTraversable()) floors++;
        if(area.map[y-1][x+1]==null) return false;
        if(area.map[y-1][x+1].isTraversable()) floors++;
        if(area.map[y+1][x-1]==null) return false;
        if(area.map[y+1][x-1].isTraversable()) floors++;
        if(area.map[y+1][x+1]==null) return false;
        if(area.map[y+1][x+1].isTraversable()) floors++;
        if(area.map[y-1][x]==null) return false;
        if(area.map[y-1][x].isTraversable()) floors++;
        if(area.map[y+1][x]==null) return false;
        if(area.map[y+1][x].isTraversable()) floors++;
        if(area.map[y][x-1]==null) return false;
        if(area.map[y][x-1].isTraversable()) floors++;
        if(area.map[y][x+1]==null) return false;
        if(area.map[y][x+1].isTraversable()) floors++;
        return floors>DECAY_LIMIT;
    }
    
    /**
     * Generates and builds all corridors in the Area.
     */
    public void build(){
        SpiderCorridorAlgorithm sca = new SpiderCorridorAlgorithm(area, windyness);
        Point p = getFreePoint();
        sca.floodfill(p);
        if(area.map[p.y-1][p.x-1]==null) area.map[p.y-1][p.x-1] = Tile.genWall(area);
        if(area.map[p.y-1][p.x+1]==null) area.map[p.y-1][p.x+1] = Tile.genWall(area);
        if(area.map[p.y+1][p.x-1]==null) area.map[p.y+1][p.x-1] = Tile.genWall(area);
        if(area.map[p.y+1][p.x+1]==null) area.map[p.y+1][p.x+1] = Tile.genWall(area);
        area.graph.doors.forEach((door) -> {
            buildCorridor(door);
        });
        if(decayActive) growCavities();
    }
    
    /**
     * Builds a corridor out of a singular Path.
     * @param path
     */
    private void buildCorridor(Point p){
        Point np, pp = null;
        while(p.cameFrom!=p&&p.cameFrom!=null){
            np = p.cameFrom;
            extend(pp, p, np);
            pp = p;
            p = np;
        }
    }
    
    private Point getFreePoint(){
        int x, y;
        for(Point p : area.graph.doors){
            for(Direction dir : Direction.values()){
                x = p.x+dir.x;
                y = p.y+dir.y;
                try{
                    if(area.map[y][x] == null) return area.graph.map[y][x];
                }catch(ArrayIndexOutOfBoundsException e){}
            }
        }
        throw new IllegalStateException("No point found!");
    }
    
}
