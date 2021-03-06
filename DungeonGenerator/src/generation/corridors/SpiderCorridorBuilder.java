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
import generation.PostCorridorPlacer;
import graph.Point;
import graph.Point.Direction;
import graph.Point.Type;
import gui.questions.CorridorSpecifier;
import java.util.function.Function;
import static utils.Utils.R;
import utils.Utils.Unfinished;

/**
 * Builds corridors using the spider corridor algorithm:
 * 1) Choose a random door as the "hub" and flood-fill to all other doors from 
 * it.
 * 2) Build all the corridors.
 * 3) Decay some walls into floors to widen the corridors.
 * @author Adam Whittaker
 */
@Unfinished("Perhaps use multiple starting points? Also figure out whether to serialize windyness")
public class SpiderCorridorBuilder extends CorridorBuilder implements PostCorridorPlacer{
    
    
    /**
     * decayLimit: The minimum number number of adjacent floors needed for a 
     * wall to be able to decay into a floor. Set to 9 if no decay is wanted.
     * windyness: How windy the corridors are.
     */
    private final int decayLimit;
    
    
    /**
     * Creates a new instance.
     * @param a The Area.
     * @param windyness The windyness factor of the corridors.
     * @param decayLimit The minimum number of adjacent floor tiles needed for a wall
     * to decay into a floor. 0 corresponds to maximum decay and 9 corresponds
     * to no decay.
     */
    public SpiderCorridorBuilder(Area a, double windyness, int decayLimit){
        this(a, windyness, decayLimit, null);
    }
    
    /**
     * Creates a new instance.
     * @param a The Area.
     * @param windyness The windyness factor of the corridors.
     * @param decayLimit The minimum number of adjacent floor tiles needed for a wall
     * to decay into a floor. 0 corresponds to maximum decay and 9 corresponds
     * to no decay.
     * @param prioritySkew A user-specified function used to skew the priority
     * queue of the corridor flood fill algorithm in order to get paths with
     * desirable characteristics.
     */
    public SpiderCorridorBuilder(Area a, double windyness, int decayLimit, Function<Point, Double> prioritySkew){
        super(a);
        this.decayLimit = decayLimit;
        addCheck = (from, to) -> to.cameFrom==null && (to.checked==null||!to.checked) && to.roomNum==-1;
        if(prioritySkew!=null) frontier.setFunction(p -> R.nextDouble()*2D*windyness - windyness + p.currentCost + prioritySkew.apply(p));
        else frontier.setFunction(p -> R.nextDouble()*2D*windyness - windyness + p.currentCost);
    }
    
    
    /**
     * Iterates through the map and decays walls into floors if they are 
     * eligible for decay.
     */
    private void growCavities(){
        for(int x=1;x<area.info.width-1;x++) for(int y=1;y<area.info.height-1;y++)
            if(canDecayWall(x, y)) area.map[y][x] = Tile.genFloor(area);
    }
    
    /**
     * Checks whether a wall at the given coordinates can decay into a floor.
     * @param _x
     * @param _y
     * @return
     */
    private boolean canDecayWall(int _x, int _y){
        if(area.map[_y][_x] == null || !area.map[_y][_x].equals(Type.WALL) 
                || !area.graph.map[_y][_x].isCorridor) return false;
        
        int floors = 0;
        for(int y=_y-1;y<=_y+1;y++) for(int x=_x-1;x<=_x+1;x++){
            if(area.map[y][x]==null) return false;
            else if(area.map[y][x].isTraversable()) floors++;
        }
        return floors>=decayLimit;
    }
    
    @Override
    public void generate(){
        //Floodfills around a door.
        Point p = getFreePoint();
        floodfill(p);
        //Builds the corridor aroung the first door.
        if(area.map[p.y-1][p.x-1]==null) area.map[p.y-1][p.x-1] = Tile.genWall(area);
        if(area.map[p.y-1][p.x+1]==null) area.map[p.y-1][p.x+1] = Tile.genWall(area);
        if(area.map[p.y+1][p.x-1]==null) area.map[p.y+1][p.x-1] = Tile.genWall(area);
        if(area.map[p.y+1][p.x+1]==null) area.map[p.y+1][p.x+1] = Tile.genWall(area);
        area.graph.doors.forEach((door) -> {
            //Builds all corridors.
            buildCorridor(door);
        });
        //Makes the corridors more cavernous.
        if(decayLimit<9) growCavities();
    }
    
    /**
     * Gets a random free point adjacent to a door to serve as a starting point 
     * for the pathfinding.
     * @return
     */
    private Point getFreePoint(){
        int x, y;
        for(Point p : area.graph.doors){
            for(Direction dir : Direction.values()){
                x = p.x+dir.x;
                y = p.y+dir.y;
                //If the tile is not initialized then it is free.
                if(area.map[y][x] == null) return area.graph.map[y][x];
            }
        }
        throw new IllegalStateException("No point found!");
    }
    
    
    /**
     * Assumptions: 
     *      Point.cameFrom is null throughout the graph at the beginning. (1)
     *      A tile is part of a room if its roomNum is not -1. (2)
     */
    @Override
    public void floodfill(Point start){
        //Prepares the graph for the flood fill.
        area.graph.reset(); //Verifies assumption (1)
        frontier.clear();
        //Initializes the first point of the search.
        start.currentCost = 0;
        frontier.add(start);
        start.checked = true;
        start.cameFrom = start;
        int nx, ny;
        //Loops until there are no tiles left to be searched.
        while(!frontier.isEmpty()){
            //Gets the highest value point in the frontier.
            Point p = frontier.removeFirst();
            //Checks the adjacent points.
            for(Direction dir : Direction.values()){
                nx = p.x+dir.x;
                ny = p.y+dir.y;
                //If the point is eligable to be added to the frontier, it
                //is added and connected to the previous point.
                if(area.withinBounds(nx-1, ny-1)&&area.withinBounds(nx+1, ny+1)){
                    if(addCheck.test(p, area.graph.map[ny][nx])){
                        area.graph.map[ny][nx].currentCost = p.currentCost + 1;
                        area.graph.map[ny][nx].checked = true;
                        area.graph.map[ny][nx].cameFrom = p;
                        frontier.add(area.graph.map[ny][nx]);
                    }else if(area.map[ny][nx]!=null && area.map[ny][nx].equals(Type.DOOR)
                            && area.graph.map[ny][nx].cameFrom == null){
                        area.graph.map[ny][nx].checked = true;
                        area.graph.map[ny][nx].cameFrom = p;
                    }
                }
            }
        }
    }
    
    
    public static final CorridorSpecifier<SpiderCorridorBuilder> SPIDER_SPECIFIER;
    static{
        try{
            SPIDER_SPECIFIER = new CorridorSpecifier<>(
                    SpiderCorridorBuilder.class.getConstructor(Area.class, 
                            double.class, int.class),
                    "Spider Corridor Builder", 
                    "Design the corridor generation algorithm");
        }catch(NoSuchMethodException e){
            throw new IllegalStateException(e);
        }
    }
    
}
