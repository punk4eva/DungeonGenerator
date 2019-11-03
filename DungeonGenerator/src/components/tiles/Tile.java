
package components.tiles;

import builders.TrapBuilder;
import components.Area;
import components.decorations.Decoration;
import components.traps.FloorTrap;
import components.traps.WallTrap;
import graph.Point.Type;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a metre-squared of dungeon.
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
    
    
    public void draw(Graphics2D g, int _x, int _y){
        g.drawImage(image, _x, _y, null);
    }
    
    
    public abstract void buildImage(Area area, int x, int y);
    
    public final boolean equals(Class clazz){
        return clazz.getSimpleName().equals(getClass().getSimpleName());
    }
    
    public final boolean equals(Type t){
        return t.equals(type);
    }
    
    public final boolean isTraversable(){
        return type.equals(Type.FLOOR) || type.equals(Type.DOOR);
    }
    
    
    public static Door genDoor(Area area, boolean path){
        return new Door(R.nextDouble() < area.info.feeling.doorHideChance ? 
                genWall(area) : null, R.nextDouble() < area.info.feeling.doorTrapChance ?
                        TrapBuilder.getDoorTrap(area) : null, false, path);
    }
    
    public static Floor genFloor(Area area){
        if(R.nextDouble() < area.info.feeling.floorDecoChance)
            return new DecoFloor(floorTrap(area));
        else return new Floor(floorTrap(area));
    }
    
    public static Wall genWall(Area area){
        if(R.nextDouble() < area.info.feeling.wallDecoChance)
            return new DecoWall(wallTrap(area));
        else return new Wall(wallTrap(area));
    }
    
    
    public static FloorTrap floorTrap(Area area){
        return R.nextDouble() < area.info.feeling.floorTrapChance ?
                        TrapBuilder.getFloorTrap(area) : null;
    }
    
    public static WallTrap wallTrap(Area area){
        return R.nextDouble() < area.info.feeling.wallTrapChance ?
                        TrapBuilder.getWallTrap(area) : null;
    }
    
    
    protected void generateWallImage(Area area, int x, int y){
        image = area.info.architecture.wallMaterial.filter.generateImage(x, y, area.info.wallNoise);
    }
    
    protected void generateFloorImage(Area area, int x, int y){
        image = area.info.architecture.floorMaterial.filter.generateImage(x, y, area.info.floorNoise);
    }
    
}
