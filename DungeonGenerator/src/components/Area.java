
package components;

import components.rooms.Room;
import components.mementoes.AreaInfo;
import components.tiles.Tile;
import graph.Graph;
import graph.Point;
import static gui.DungeonViewer.HEIGHT;
import static gui.DungeonViewer.WIDTH;
import static gui.MouseInterpreter.zoom;
import java.awt.Graphics2D;
import utils.Utils.ThreadUsed;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a single floor of the dungeon.
 */
public class Area{
    
    /**
     * Map: The 2D array of tiles.
     * Info: The AreaInfo memento to be serialized.
     * Graph: The pathfinding tools.
     * Orientation: The cardinal code for this Area's orientation.
     */
    public final Tile[][] map;
    public final AreaInfo info;
    public transient final Graph graph;
    public int orientation;
    
    /**
     * Creates a new instance.
     * @param w The width
     * @param h The height
     * @param f The general feeling of the Area.
     */
    public Area(int w, int h, LevelFeeling f){
        map = new Tile[h][w];
        info = new AreaInfo(w, h, f);
        graph = new Graph(w, h);
    }
    
    /**
     * Paints the given area on the given graphics.
     * @param fx The focusX
     * @param fy The focusY
     * @param g The image to paint on.
     */
    @ThreadUsed("Render")
    public void paint(Graphics2D g, int fx, int fy){
        for(int y=fy, maxY=fy+info.height*16;y<maxY;y+=16){
            for(int x=fx, maxX=fx+info.width*16;x<maxX;x+=16){
                int tx = (x-fx)/16, ty = (y-fy)/16;
                try{
                    if(x>=-16&&y>=-16&&x*zoom<=WIDTH&&y*zoom<=HEIGHT && map[ty][tx]!=null) g.drawImage(map[ty][tx].image, x, y, null);
                }catch(ArrayIndexOutOfBoundsException e){/*Skip frame*/}
            }
        }
    }
    
    /**
     * Copies the given room onto this area, without any clipping prevention.
     * @param r The room to copy.
     * @param x1 The top left x.
     * @param y1 The top left y.
     */
    public void blitRoom(Room r, int x1, int y1){
        r.ensureGenerated(this);
        int o = getApparentOrientation(r), w = r.width, h = r.height;
        for(int y=0;y<h;y++) for(int x=0;x<w;x++){
            map[yOrient(o,x,y,w,h)+y1][xOrient(o,x,y,w,h)+x1] = r.map[y][x];
        }
    }
    
    /**
     * Gets the orientation of the Room relative to the orientation of this Area.
     * @param r
     */
    private int getApparentOrientation(Room r){
        int o = r.orientation - orientation;
        if(o<0) return o+4;
        return o;
    }
    
    /**
     * Recalculates the values for each point on this Area's Graph.
     */
    public void refreshGraph(){
        for(int y=0;y<info.height;y++){
            for(int x=0;x<info.width;x++){
                if(map[y][x]==null) graph.map[y][x].refresh(Point.Type.NULL);
                else graph.map[y][x].refresh(map[y][x].type);
            }
        }
        graph.recalculateDoors(this);
    }
    
    /**
     * Builds the image of each Tile in the Area.
     */
    public void initializeImages(){
        for(int y=0;y<map.length;y++){
            for(int x=0;x<map[0].length;x++){
                if(map[y][x]!=null) map[y][x].buildImage(this, x*16, y*16);
            }
        }
    }
    
    public boolean withinBounds(int x, int y){
        return x>=0 && x<info.width && y>=0 && y<info.height;
    }
    
    /**
     * Applies the re-centred quadrant rotate matrix to the given x,y coordinates.
     * @param o The number of quadrants rotated.
     * @param x
     * @param y
     * @param w The width
     * @param h The height
     * @return The x coordinate of the image.
     */
    public static int xOrient(int o, int x, int y, int w, int h){
        switch(o){
            case 0: return x;
            case 1: return y;
            case 2: return w-x-1;
            default: return h-y-1;
        }
    }
    
    /**
     * Applies the re-centred quadrant rotate matrix to the given x,y coordinates.
     * @param o The number of quadrants rotated.
     * @param x
     * @param y
     * @param w The width
     * @param h The height
     * @return The y coordinate of the image.
     */
    public static int yOrient(int o, int x, int y, int w, int h){
        switch(o){
            case 0: return y;
            case 1: return w-x-1;
            case 2: return h-y-1;
            default: return x;
        }
    }

}
