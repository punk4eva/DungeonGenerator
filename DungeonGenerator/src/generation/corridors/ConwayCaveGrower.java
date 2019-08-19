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

/**
 *
 * @author punk4eva
 * 
 * This class uses cellular automata to grow Areas.
 */
public class ConwayCaveGrower extends CaveGrower{
    
    private final int minLimit, maxLimit, birthMax, birthMin;
    
    /**
     * Creates a new instance.
     * @param a The Area.
     * @param sC The chance for a tile to start alive.
     * @param miL The starvation limit (inclusive).
     * @param maL The overpopulation limit (inclusive).
     * @param bMi The lower birth limit (inclusive).
     * @param bMa The upper birth limit (inclusive).
     * @param it The amount of iterations.
     */
    public ConwayCaveGrower(Area a, double sC, int it, int miL, int maL, int bMi, int bMa){
        super(a, sC, it);
        minLimit = miL;
        maxLimit = maL;
        birthMax = bMa;
        birthMin = bMi;
    }
    
    @Override
    protected void iterate(){
        int n;
        for(int x=1;x<area.info.width-1;x++){
            for(int y=1;y<area.info.height-1;y++){
                n = getNeighborNum(x, y, 1);
                if(n<=minLimit||n>=maxLimit) area.graph.map[y][x].checked = false;
                else if(n>=birthMin&&n<=birthMax) area.graph.map[y][x].checked = true;
                else area.graph.map[y][x].checked = area.graph.map[y][x].isCorridor;
            }
        }
        for(int x=1;x<area.info.width-1;x++) 
            for(int y=1;y<area.info.height-1;y++)
            area.graph.map[y][x].isCorridor = area.graph.map[y][x].checked;
    }
    
}
