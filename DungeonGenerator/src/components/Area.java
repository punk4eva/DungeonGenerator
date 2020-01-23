
package components;

import components.rooms.Room;
import components.mementoes.AreaInfo;
import components.tiles.Floor;
import components.tiles.Grass;
import components.tiles.Tile;
import components.tiles.Water;
import graph.Graph;
import graph.Point;
import graph.Point.Type;
import static gui.core.DungeonViewer.ANIMATOR;
import static gui.core.DungeonViewer.HEIGHT;
import static gui.core.DungeonViewer.WIDTH;
import static gui.core.MouseInterpreter.zoom;
import java.awt.Color;
import java.awt.Graphics2D;
import static utils.Utils.R;
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
    public transient Graph graph;
    public int orientation;
    
    private static final Color BORDER_COLOR = new Color(0, 0, 0, 100);
    
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
     * @param focusX The focusX
     * @param focusY The focusY
     * @param g The image to paint on.
     */
    @ThreadUsed("Render")
    public void paint(Graphics2D g, int focusX, int focusY){
        int tileX, tileY;
        for(int y=focusY, maxY=focusY+info.height*16;y<maxY;y+=16){
            for(int x=focusX, maxX=focusX+info.width*16;x<maxX;x+=16){
                tileX = (x-focusX)/16;
                tileY = (y-focusY)/16;
                try{
                    if(x>=-16 && x*zoom<=WIDTH &&
                            y >=-16 && y*zoom<=HEIGHT && map[tileY][tileX]!=null){
                        map[tileY][tileX].draw(g, x, y);
                    }
                }catch(ArrayIndexOutOfBoundsException e){/*Skip frame*/}
            }
        }
        g.setColor(BORDER_COLOR);
        paintOutsideBorder(g, focusX, focusY);
        for(tileY=0;tileY<info.height;tileY++){
            for(tileX=0;tileX<info.width;tileX++){
                if(map[tileY][tileX] != null && map[tileY][tileX].type.equals(Type.FLOOR)){
                    paintInsideBorder(tileX, tileY, g, focusX, focusY);
                }
            }
        }
        
        info.waterPainter.checkFrameUpdate();
    }
    
    /**
     * Paints a border on the outside edge of the Area.
     */
    private void paintOutsideBorder(Graphics2D g, int focusX, int focusY){
        g.drawLine(focusX, focusY, 16*info.width+focusX - 1, focusY);
        g.drawLine(focusX, focusY, focusX, focusY + 16*info.height - 1);
        g.drawLine(focusX, focusY + 16*info.height - 1, 16*info.width + focusX - 1, focusY + 16*info.height - 1);
        g.drawLine(focusX + 16*info.width - 1, focusY, focusX + 16*info.width - 1, focusY + 16*info.height-1);
    }
    
    /**
     * Paints an alpha border at the interface between walls and floors.
     */
    private void paintInsideBorder(int tx, int ty, Graphics2D g, int focusX, int focusY){
        if(map[ty-1][tx].type.equals(Type.WALL)) g.fillRect(tx*16+focusX, ty*16+focusY-1, 16, 2);
        if(map[ty+1][tx].type.equals(Type.WALL)) g.fillRect(tx*16+focusX, ty*16+focusY+15, 16, 2);
        if(map[ty][tx-1].type.equals(Type.WALL)) g.fillRect(tx*16+focusX-1, ty*16+focusY, 2, 16);
        if(map[ty][tx+1].type.equals(Type.WALL)) g.fillRect(tx*16+focusX+15, ty*16+focusY, 2, 16);
    }
    
    
    /**
     * Copies the given room onto this area, without any clipping prevention.
     * @param r The room to copy.
     * @param x1 The top left x.
     * @param y1 The top left y.
     */
    public void blitRoom(Room r, int x1, int y1){
        r.setCoords(x1, y1);
        r.ensureGenerated(this);
        int o = getApparentOrientation(r), w = r.width, h = r.height;
        for(int y=0;y<h;y++) for(int x=0;x<w;x++)
            map[yOrient(o,x,y,w,h)+y1][xOrient(o,x,y,w,h)+x1] = r.map[y][x];
    }
    
    /**
     * Gets the orientation of the Room relative to the orientation of this Area.
     * @param r
     */
    private int getApparentOrientation(Room r){
        int o = r.orientation - orientation;
        if(o<0) return o+4;
        else return o;
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
                if(map[y][x]!=null){
                    map[y][x].buildImage(this, x*16, y*16);
                    if(map[y][x].decoration!=null && map[y][x].decoration.animation!=null)
                        ANIMATOR.add(map[y][x].decoration.animation.apply(x, y));
                }
            }
        }
    }
    
    /**
     * Tests whether the given tile coordinates are within the Area's bounds.
     * @param x
     * @param y
     * @return true if they are, false if not.
     */
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
    
    
    /**
     * Tests whether the tile at the given coordinates has a tile of the given
     * class next to it.
     * @param x
     * @param y
     * @param clazz
     * @return
     */
    public boolean hasAdjacentTile(int x, int y, Class clazz){
        if(y>0 && clazz.isInstance(map[y-1][x])) return true;
        else if(y<map.length-1 && clazz.isInstance(map[y+1][x])) return true;
        else if(x>0 && clazz.isInstance(map[y][x-1])) return true;
        return x<map.length-1 && clazz.isInstance(map[y][x+1]);
    }
    
    /**
     * Fills the area with grass.
     */
    public void growGrass(){
        for(int y=1;y<map.length-1;y++){
            for(int x=1;x<map[0].length-1;x++){
                if(map[y][x] instanceof Floor && R.nextDouble()<info.feeling.grassGenChance){
                    map[y][x] = new Grass(R.nextDouble()<info.feeling.grassUpgradeChance, map[y][x]);
                }
            }
        }
        
        for(int c=3;c<12;c++){
            for(int y=1;y<map.length-1;y++){
                for(int x=1;x<map[0].length-1;x++){
                    if(map[y][x] instanceof Grass && c*R.nextDouble()<1){
                        if(map[y-1][x] instanceof Floor){
                            map[y-1][x] = new Grass(R.nextDouble()<info.feeling.grassUpgradeChance, map[y-1][x]);
                        }
                        if(map[y+1][x] instanceof Floor){
                            map[y+1][x] = new Grass(R.nextDouble()<info.feeling.grassUpgradeChance, map[y+1][x]);
                        }
                        if(map[y][x-1] instanceof Floor){
                            map[y][x-1] = new Grass(R.nextDouble()<info.feeling.grassUpgradeChance, map[y][x-1]);
                        }
                        if(map[y][x+1] instanceof Floor){
                            map[y][x+1] = new Grass(R.nextDouble()<info.feeling.grassUpgradeChance, map[y][x+1]);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Fills the area with water.
     */
    public void spillWater(){
        for(int y=1;y<map.length-1;y++){
            for(int x=1;x<map[0].length-1;x++){
                if(map[y][x] instanceof Floor && R.nextDouble()<info.feeling.waterGenChance){
                    map[y][x] = new Water(info, map[y][x]);
                }
            }
        }
        
        for(int c=3;c<12;c++){
            for(int y=1;y<map.length-1;y++){
                for(int x=1;x<map[0].length-1;x++){
                    if(map[y][x] instanceof Water && c*R.nextDouble()<1){
                        if(map[y-1][x] instanceof Floor){
                            map[y-1][x] = new Water(info, map[y-1][x]);
                        }
                        if(map[y+1][x] instanceof Floor){
                            map[y+1][x] = new Water(info, map[y+1][x]);
                        }
                        if(map[y][x-1] instanceof Floor){
                            map[y][x-1] = new Water(info, map[y][x-1]);
                        }
                        if(map[y][x+1] instanceof Floor){
                            map[y][x+1] = new Water(info, map[y][x+1]);
                        }
                    }
                }
            }
        }
    }

}
