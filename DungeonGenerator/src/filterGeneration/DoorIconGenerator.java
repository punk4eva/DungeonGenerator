
package filterGeneration;

import components.mementoes.AreaInfo;
import components.tiles.Door;
import filterGeneration.ImageBuilder.SerSupplier;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import javax.swing.ImageIcon;
import utils.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 */
public class DoorIconGenerator{
    
    private final static int[] WALL_REGEX = new int[]{95, 24, 86}, 
            FLOOR_REGEX = new int[]{0,0,0,0};
    
    public final AreaInfo info;
    public final SerSupplier doorShapeSupplier;
    
    public DoorIconGenerator(AreaInfo i, SerSupplier door){
        info = i;
        doorShapeSupplier = door;
    }
    

    public void generateAllImages(Door door, int x, int y){
        door.setClosedImage(generateClosed(x, y));
        door.setOpenImage(generateOpen(x, y));
        door.setLockedImage(generateLocked(door.closedImage));
        if(door.open) door.image = door.openImage;
        else if(door.locked) door.image = door.lockedImage;
        else door.image = door.closedImage;
    }
    
    private BufferedImage generateOpen(int tx, int ty){
        BufferedImage img = doorShapeSupplier.get();
        fillWall(img, tx, ty);
        fillFloor(img, tx, ty);
        return img;
    }
    
    private BufferedImage generateClosed(int tx, int ty){
        BufferedImage img = doorShapeSupplier.get();
        fillWall(img, tx, ty);
        fillDoor(img, tx, ty);
        return img;
    }
    
    private BufferedImage generateLocked(BufferedImage closed){
        BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();
        g.drawImage(closed, 0, 0, null);
        g.drawImage(new ImageIcon("graphics/tiles/lock.png").getImage(), 0, 0, null);
        return img;
    }
    
    private void fillWall(BufferedImage img, int tx, int ty){
        BufferedImage wall = info.feeling.filters.wall.generateImage(tx, ty, info.wallNoise);
        WritableRaster raster = img.getRaster(), wallRaster = wall.getRaster();
        int[] pixel = new int[3];
        for(int y=0;y<img.getHeight();y++){
            for(int x=0;x<img.getWidth();x++){
                if(Filter.RGBPixelEquals(WALL_REGEX, raster.getPixel(x, y, pixel)))
                    raster.setPixel(x, y, wallRaster.getPixel(x, y, pixel));
            }
        }
    }
    
    private void fillFloor(BufferedImage img, int tx, int ty){
        BufferedImage floor = info.feeling.filters.floor.generateImage(tx, ty, info.floorNoise);
        WritableRaster raster = img.getAlphaRaster(), floorRaster = floor.getRaster();
        int[] pixel = new int[4];
        for(int y=0;y<img.getHeight();y++)
            for(int x=0;x<img.getWidth();x++)
                if(Filter.ARGBPixelEquals(raster.getPixel(x, y, pixel), FLOOR_REGEX)){
            raster.setPixel(x, y, Filter.overlayColor(floorRaster.getPixel(x, y, pixel), 
                    raster.getPixel(x, y, pixel)));
        }
    }
    
    @Unfinished
    private void fillDoor(BufferedImage img, int tx, int ty){}
    
}
