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
import components.tiles.SpecialFloor;
import components.tiles.Tile;
import generation.Searcher;
import graph.Point;
import graph.Point.Direction;
import static utils.Utils.R;

/**
 *
 * @author punk4eva
 * 
 * This class uses cellular automata to grow Areas.
 */
public class CaveGrower extends Searcher{
    
    private final double startChance;
    private final int minLimit, maxLimit, birthMax, birthMin;
    private int iterNum;
    private final boolean aliveEdges, paths;
    
    /**
     * Creates a new instance.
     * @param a The Area.
     * @param sC The chance for a tile to start alive.
     * @param miL The starvation limit (inclusive).
     * @param maL The overpopulation limit (inclusive).
     * @param bMi The lower birth limit (inclusive).
     * @param bMa The upper birth limit (inclusive).
     * @param it The amount of iteration.
     * @param aE Whether to treat edges as alive.
     * @param p
     */
    public CaveGrower(Area a, double sC, int miL, int maL, int bMi, int bMa, int it, boolean aE, boolean p){
        super(a);
        startChance = sC;
        minLimit = miL;
        iterNum = it;
        maxLimit = maL;
        birthMax = bMa;
        birthMin = bMi;
        aliveEdges = aE;
        paths = p;
    }
    
    private void initialize(){
        for(int x=0;x<area.info.width;x++) for(Point[] row : area.graph.map){
            if(R.nextDouble()<startChance)
                row[x].isCorridor = true;
        }
    }
    
    private int getNeighborNum(int x, int y){
        int n = 0;
        try{
            if(area.graph.map[y-1][x-1].isCorridor) n++;
        }catch(ArrayIndexOutOfBoundsException e){if(aliveEdges) n++;}
        try{
            if(area.graph.map[y-1][x].isCorridor) n++;
        }catch(ArrayIndexOutOfBoundsException e){if(aliveEdges) n++;}
        try{
            if(area.graph.map[y-1][x+1].isCorridor) n++;
        }catch(ArrayIndexOutOfBoundsException e){if(aliveEdges) n++;}
        try{
            if(area.graph.map[y][x+1].isCorridor) n++;
        }catch(ArrayIndexOutOfBoundsException e){if(aliveEdges) n++;}
        try{
            if(area.graph.map[y][x-1].isCorridor) n++;
        }catch(ArrayIndexOutOfBoundsException e){if(aliveEdges) n++;}
        try{
            if(area.graph.map[y+1][x-1].isCorridor) n++;
        }catch(ArrayIndexOutOfBoundsException e){if(aliveEdges) n++;}
        try{
            if(area.graph.map[y+1][x].isCorridor) n++;
        }catch(ArrayIndexOutOfBoundsException e){if(aliveEdges) n++;}
        try{
            if(area.graph.map[y+1][x+1].isCorridor) n++;
        }catch(ArrayIndexOutOfBoundsException e){if(aliveEdges) n++;}
        return n;
    }
    
    private void iterate(){
        int n;
        for(int x=0;x<area.info.width;x++){
            for(int y=0;y<area.info.height;y++){
                n = getNeighborNum(x, y);
                if(n<=minLimit||n>=maxLimit) area.graph.map[y][x].checked = false;
                else if(n>=birthMin&&n<=birthMax) area.graph.map[y][x].checked = true;
                else area.graph.map[y][x].checked = area.graph.map[y][x].isCorridor;
            }
        }
        for(int x=0;x<area.info.width;x++) for(Point[] row : area.graph.map){
            row[x].isCorridor = row[x].checked;
    }
    
}
    
    /**
     * Runs the cellular automata and generates an Area.
     * @return
     */
    public Area build(){
        initialize();
        for(;iterNum>0;iterNum--) iterate();
        convertGraphToArea();
        return area;
    }
    
    private void convertGraphToArea(){
        for(int x=0;x<area.info.width;x++) for(int y=0;y<area.info.height;y++)
            if(area.graph.map[y][x].isCorridor) area.map[y][x] = Tile.genWall(area);
            else area.map[y][x] = Tile.genFloor(area);
    }
    
    private Point getFreePoint(){
        while(true){
            Integer[] c = new Integer[]{3+R.nextInt(area.info.width-12),
                    3+R.nextInt(area.info.height-12)};
            if(!area.graph.map[c[1]][c[0]].isCorridor) return area.graph.map[c[1]][c[0]];
        }
    }

    private void buildPaths(){
        System.out.println("Doors: " + area.graph.doors);
        area.graph.doors.forEach((p) -> {
            while(p.cameFrom!=null){
                p = p.cameFrom;
                if(paths) area.map[p.y][p.x] = new SpecialFloor();
                else area.map[p.y][p.x] = Tile.genFloor(area);
            }
        });
    }
    
    private void corridorFloodFill(Point start){
        area.graph.reset();
        frontier.clear();
        start.isCorridor = true;
        frontier.add(start);
        int nx, ny;
        while(!frontier.isEmpty()){
            Point p = frontier.poll();
            for(Direction dir : Direction.values()){
                nx = p.x+dir.x;
                ny = p.y+dir.y;
                try{ if(area.graph.map[ny][nx].cameFrom==null&&!area.graph.map[ny][nx].isCorridor){
                    area.graph.map[ny][nx].cameFrom = p;
                    frontier.add(area.graph.map[ny][nx]);
                }}catch(ArrayIndexOutOfBoundsException | NullPointerException e){}
            }
        }
    }
    
    public void ensureConnectedness(){
        Point start = getFreePoint();
        corridorFloodFill(start);
        buildPaths();
    }
    
}
