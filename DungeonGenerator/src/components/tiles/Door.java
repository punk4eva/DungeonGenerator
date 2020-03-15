
package components.tiles;

import components.Area;
import components.traps.Trap;
import graph.Point.Type;
import java.awt.image.BufferedImage;
import textureGeneration.SerImage;

/**
 * A passage way between two rooms.
 * @author Adam Whittaker
 */
public class Door extends PassageTile{

    
    private static final long serialVersionUID = 1368L;
    
    
    /**
     * image: The images for the various states of the door.
     * open: Whether the door is open.
     * locked: Whether the door is locked.
     */
    protected transient BufferedImage openImage, closedImage, lockedImage;
    private boolean open = false, locked;

    
    /**
     * Creates a new instance.
     * @param al The alias that this tile is masquerading as.
     * @param tr The trap that this tile has.
     * @param lo Whether the door is locked.
     * @param pathfind Whether to consider this door in pathfinding.
     */
    public Door(Tile al, Trap tr, boolean lo, boolean pathfind){
        super("Door", "@Unfinished", Type.DOOR, al, tr, pathfind);
        locked = lo;
    }
    
    
    @Override
    public void generateImage(Area area, int x, int y){
        area.info.architecture.doorGenerator.generateAllImages(this, x, y);
        image = new SerImage(() -> null);
        if(open) image.setImage(openImage, true);
        else if(locked) image.setImage(lockedImage, true);
        else image.setImage(closedImage, true);
    }
    
    
    /**
     * Sets the open image of this door.
     * @param img
     */
    public void setOpenImage(BufferedImage img){
        openImage = img;
    }
    
    /**
     * Sets the closed image of this door.
     * @param img
     */
    public void setClosedImage(BufferedImage img){
        closedImage = img;
    }
    
    /**
     * Sets the locked image of this door.
     * @param img
     */
    public void setLockedImage(BufferedImage img){
        lockedImage = img;
    }
    
    
    /**
     * Opens this door.
     */
    public void open(){
        image.setImage(openImage, true);
        open = true;
    }
    
    /**
     * Closes this door.
     */
    public void close(){
        image.setImage(closedImage, true);
        open = false;
    }
    
    /**
     * Unlocks this door.
     */
    public void unlock(){
        image.setImage(closedImage, true);
        locked = false;
    }
    
    /**
     * Locks this door.
     */
    public void lock(){
        image.setImage(lockedImage, true);
        locked = true;
        open = false;
    }
    
    /**
     * Checks whether this door is locked.
     * @return True if it is, false if it isn't.
     */
    public boolean getLocked(){
        return locked;
    }
    
    /**
     * Retrieves the closed image of this door.
     * @return
     */
    public BufferedImage getClosedImage(){
        return closedImage;
    }

}
