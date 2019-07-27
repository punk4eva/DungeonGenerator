
package components;

import components.rooms.Room;
import components.mementoes.AreaInfo;
import components.tiles.Tile;
import graph.Graph;
import graph.Point;
import static gui.DungeonViewer.HEIGHT;
import static gui.DungeonViewer.WIDTH;
import static gui.MouseInterpreter.xOrient;
import static gui.MouseInterpreter.yOrient;
import static gui.MouseInterpreter.zoom;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import utils.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 */
public class Area{
    
    public final Tile[][] map;
    public final AreaInfo info;
    public final Graph graph;
    public int orientation;
    
    public Area(int w, int h, LevelFeeling f){
        map = new Tile[h][w];
        info = new AreaInfo(w, h, f.initialJitter.next(0,120), f.jitterDecay.next(0.7, 0.95), 
                f.alternateTiles, f.amplitude.next(30, 120), f.octaves.next(), 
                f.lacunarity.next(0.3, 1.0), f.persistence.next(0.3, 1.0), f.wallNoise, f.floorNoise);
        graph = new Graph(w, h);
    }
    
    /**
     * Paints the given area on the given graphics.
     * @thread render
     * @param fx The focusX
     * @param fy The focusY
     * @param g The image to paint on.
     */
    public void paint(Graphics2D g, int fx, int fy){
        for(int y=fy, maxY=fy+info.height*16;y<maxY;y+=16){
            for(int x=fx, maxX=fx+info.width*16;x<maxX;x+=16){
                int tx = (x-fx)/16, ty = (y-fy)/16;
                try{
                    if( map[ty][tx]!=null) g.drawImage(map[ty][tx].image, x, y, null);
                }catch(ArrayIndexOutOfBoundsException e){/*Skip frame*/}
            }
        }
    }
    
    /**
     * Copies the given room onto this area, without any calculations.
     * @param r
     * @param x1
     * @param y1
     */
    public void blitDirty(Room r, int x1, int y1){
        int o = getApparentOrientation(r), w = r.width, h = r.height;
        for(int y=0;y<h;y++) for(int x=0;x<w;x++){
            map[yOrient(o,x,y,w,h)+y1][xOrient(o,x,y,w,h)+x1] = r.getMap()[y][x];
        }
    }
    
    private int getApparentOrientation(Room r){
        int o = r.orientation - orientation;
        if(o<0) return o+4;
        return o;
    }
    
    public void refreshGraph(){
        for(int y=0;y<info.height;y++){
            for(int x=0;x<info.width;x++){
                if(map[y][x]==null) graph.map[y][x].refresh(Point.Type.NULL);
                else graph.map[y][x].refresh(Point.Type.valueOf(map[y][x].name.toUpperCase()));
            }
        }
    }
    
    public void initializeImages(){
        for(int y=0;y<map.length;y++){
            for(int x=0;x<map[0].length;x++){
                if(map[y][x]!=null) map[y][x].buildImage(info, x, y);
            }
        }
    }

}
