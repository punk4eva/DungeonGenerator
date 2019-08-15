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
import graph.Point;
import graph.Point.*;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 * 
 * Builds corridors using the spider corridor algorithm.
 */
public class SpiderCorridorBuilder extends CorridorBuilder{
    
    
    private final static int DECAY_LIMIT = 2;
    
    private final int windyness;
    private final boolean decayActive;
    
    /**
     * Creates a new instance.
     * @param a The Area.
     * @param w The windyness factor of the corridors.
     * @param decay Whether to decay walls into cavities.
     */
    public SpiderCorridorBuilder(Area a, int w, boolean decay){
        super(a);
        windyness = w;
        decayActive = decay;
        addCheck = (from, to) -> /*to.currentCost > from.currentCost + to.movementCost &&*/ to.cameFrom==null&&(to.checked==null||!to.checked);
        frontier.setFunction(p -> (double)R.nextInt(windyness) + p.currentCost);
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
    
    @Override
    public void build(){
        Point p = getFreePoint();
        floodfill(p);
        if(area.map[p.y-1][p.x-1]==null) area.map[p.y-1][p.x-1] = Tile.genWall(area);
        if(area.map[p.y-1][p.x+1]==null) area.map[p.y-1][p.x+1] = Tile.genWall(area);
        if(area.map[p.y+1][p.x-1]==null) area.map[p.y+1][p.x-1] = Tile.genWall(area);
        if(area.map[p.y+1][p.x+1]==null) area.map[p.y+1][p.x+1] = Tile.genWall(area);
        area.graph.doors.forEach((door) -> {
            buildCorridor(door);
        });
        if(decayActive) growCavities();
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
                    if(addCheck.apply(p, area.graph.map[ny][nx])){
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
