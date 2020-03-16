/*
 * Copyright 2018 punk4eva.
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
import generation.PreCorridorPlacer;

/**
 * This class uses cellular automata to grow Areas in the style of John Conway's
 * Game Of Life. This cave generator assumes that no rooms have been placed yet
 * and that rooms will be generated after the cave (if they will at all).
 * @author punk4eva
 */
public class ConwayCaveGrower extends CaveGrower implements PreCorridorPlacer{
    
    
    /**
     * starveLimit: The minimum number of living adjacent cells needed 
     * before a cell starves.
     * overpopulationLimit: The maximum number of living adjacent cells needed
     * before a cell dies.
     * birthMin: The minimum number of cells needed before a cell can be born.
     * birthMax: The maximum number of cells needed before a cell stops being 
     * born.
     */
    private final int starveLimit, overpopulationLimit, birthMax, birthMin;
    
    
    /**
     * Creates a new instance.
     * @param a The Area.
     * @param startChance The chance for a tile to start alive.
     * @param starvationLimit The starvation limit (inclusive).
     * @param overpopulationLimit The overpopulation limit (inclusive).
     * @param birthMinimum The lower birth limit (inclusive).
     * @param birthMaximum The upper birth limit (inclusive).
     * @param iterationNumber The amount of iterations.
     */
    public ConwayCaveGrower(Area a, double startChance, int iterationNumber, 
            int starvationLimit, int overpopulationLimit, int birthMinimum, 
            int birthMaximum){
        super(a, startChance, iterationNumber);
        starveLimit = starvationLimit;
        this.overpopulationLimit = overpopulationLimit;
        birthMax = birthMaximum;
        birthMin = birthMinimum;
    }
    
    
    @Override
    protected void iterate(){
        int n;
        for(int x=1;x<area.info.width-1;x++){
            for(int y=1;y<area.info.height-1;y++){
                n = getNeighborNum(x, y, 1);
                if(area.graph.map[y][x].isCorridor){
                    //Checks if the cell will die.
                    if(starveLimit >= n || n >= overpopulationLimit)
                        area.graph.map[y][x].checked = false;
                    else area.graph.map[y][x].checked = area.graph.map[y][x].isCorridor;
                }else{
                    //Checks if the cell will be born.
                    if(birthMin <= n && n <= birthMax)
                        area.graph.map[y][x].checked = true;
                    else area.graph.map[y][x].checked = area.graph.map[y][x].isCorridor;
                }
            }
        }
        //Performs the simultaneous update.
        for(int x=1;x<area.info.width-1;x++) 
            for(int y=1;y<area.info.height-1;y++)
            area.graph.map[y][x].isCorridor = area.graph.map[y][x].checked;
    }
    
}
