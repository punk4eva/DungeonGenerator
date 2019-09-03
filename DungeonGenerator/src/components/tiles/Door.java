
package components.tiles;

import components.Trap;
import graph.Point.Type;
import java.awt.image.BufferedImage;

/**
 *
 * @author Adam Whittaker
 */
public class Door extends Tile{
    
    public transient BufferedImage openImage, closedImage, lockedImage;
    public boolean open = false, locked;

    public Door(Tile al, Trap tr, boolean lo){
        super("Door", "@Unfinished", Type.DOOR, al, tr);
        locked = lo;
    }
    
    public void setOpenImage(BufferedImage img){
        openImage = img;
    }
    
    public void setClosedImage(BufferedImage img){
        closedImage = img;
    }
    
    public void setLockedImage(BufferedImage img){
        lockedImage = img;
    }
    
    
    public void open(){
        image = openImage;
        open = true;
    }
    
    public void close(){
        image = closedImage;
        open = false;
    }
    
    public void unlock(){
        image = closedImage;
        locked = false;
    }
    
    public void lock(){
        image = lockedImage;
        locked = true;
        open = false;
    }

}
