
package components.tiles;

import builders.TrapBuilder;
import components.Area;
import components.decorations.Decoration;
import components.decorations.FloorDecoration;
import components.decorations.WallDecoration;
import graph.Point.Type;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import static utils.Utils.R;

/**
 * This class represents a metre-squared of dungeon.
 * @author Adam Whittaker
 */
public abstract class Tile{
    
    
    /**
     * Name: Name of this tile.
     * Description: Description of this tile.
     * Alias: The Tile that this Tile is masquerading as.
     * Decoration: The Decoration on this Tile.
     * Type: The Type of tile (for image noise generation).
     * Image: The image of the tile.
     */
    public String name, description;
    public Tile alias;
    public Decoration decoration;
    public Type type;
    protected transient BufferedImage image;
    
    
    /**
     * Creates a new instance.
     * @param na
     * @param desc
     * @param t
     * @param al
     * @param tr
     */
    public Tile(String na, String desc, Type t, Tile al, Decoration tr){
        name = na;
        type = t;
        description = desc;
        alias = al;
        decoration = tr;
    }
    
    
    /**
     * Draws this tile to the given graphics.
     * @param g The graphics.
     * @param _x the pixel x.
     * @param _y the pixel y.
     */
    public void draw(Graphics2D g, int _x, int _y){
        g.drawImage(image, _x, _y, null);
    }
    
    
    /**
     * Generates the image of this tile.
     * @param area The area.
     * @param x The tile x.
     * @param y The pixel y.
     */
    public abstract void buildImage(Area area, int x, int y);
    
    /**
     * Tests whether this Tile has the given class.
     * @param <T> The type of the class.
     * @param clazz The class instance.
     * @return True if it does, false if not.
     */
    public final <T extends Tile> boolean equals(Class<T> clazz){
        return clazz.equals(getClass());
    }
    
    /**
     * Tests if the tile has the given point type.
     * @param t The Point.Type
     * @return true if it does, false if not.
     */
    public final boolean equals(Type t){
        return t.equals(type);
    }
    
    /**
     * Checks whether this tile is traversable.
     * @return
     */
    public final boolean isTraversable(){
        return type.equals(Type.FLOOR) || type.equals(Type.DOOR);
    }
    
    
    /**
     * Generates a door instance.
     * @param area The area.
     * @param path Whether to treat this door as a pathway for pathfinding.
     * @return
     */
    public static Door genDoor(Area area, boolean path){
        return new Door(R.nextDouble() < area.info.feeling.doorHideChance ? 
                new Wall(null) : null, R.nextDouble() < area.info.feeling.doorTrapChance ?
                        TrapBuilder.getDoorTrap(area) : null, false, path);
    }
    
    /**
     * Generates a floor instance.
     * @param area The area.
     * @return
     */
    public static Floor genFloor(Area area){
        return new Floor(floorDeco(area));
    }
    
    /**
     * Generates a wall instance.
     * @param area The area.
     * @return
     */
    public static Wall genWall(Area area){
        return new Wall(wallDeco(area));
    }
    
    
    /**
     * Returns a random floor decoration.
     * @param area The area.
     * @return
     */
    public static Decoration floorDeco(Area area){
        return R.nextDouble() < area.info.feeling.floorDecoChance ?
                FloorDecoration.getFloorDecoration(area) : 
                (R.nextDouble() < area.info.feeling.floorTrapChance ?
                        TrapBuilder.getFloorTrap(area) : null);
    }
    
    /**
     * Returns a random wall decoration.
     * @param area The area.
     * @return
     */
    public static Decoration wallDeco(Area area){
        return R.nextDouble() < area.info.feeling.wallDecoChance ?
                WallDecoration.getWallDecoration(area) : 
                (R.nextDouble() < area.info.feeling.floorTrapChance ?
                        TrapBuilder.getWallTrap(area) : null);
    }
    
    
    /**
     * Generates the image for a wall.
     * @param area The area.
     * @param x The tile x.
     * @param y The tile y.
     */
    protected void generateWallImage(Area area, int x, int y){
        image = area.info.architecture.wallMaterial.filter.generateImage(x, y, area.info.wallNoise);
    }
    
    /**
     * Generates the image for a wall.
     * @param area The area.
     * @param x The tile x.
     * @param y The tile y.
     */
    protected void generateFloorImage(Area area, int x, int y){
        image = area.info.architecture.floorMaterial.filter.generateImage(x, y, area.info.floorNoise);
    }
    
    /**
     * Generates the image for a wall.
     * @param area The area.
     * @param x The tile x.
     * @param y The tile y.
     */
    protected void generateSpecFloorImage(Area area, int x, int y){
        image = area.info.architecture.specFloorMaterial.filter.generateImage(x, y, area.info.floorNoise);
    }
    
}
