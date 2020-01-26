
package filterGeneration;

import components.mementoes.AreaInfo;
import components.tiles.Door;
import filterGeneration.ImageBuilder.SerSupplier;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import utils.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 */
public class DoorIconGenerator{
    
    private final static int[] WALL_REGEX = new int[]{242, 61, 219};
    
    public final AreaInfo info;
    public final SerSupplier doorShapeSupplier;
    
    public DoorIconGenerator(AreaInfo i, SerSupplier door){
        info = i;
        doorShapeSupplier = door;
    }
    

    public void generateAllImages(Door door, int x, int y){
        door.setClosedImage(generateClosed(x, y));
        door.setOpenImage(generateOpen(x, y));
        door.setLockedImage(generateLocked(door.getClosedImage()));
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
        g.drawImage(ImageBuilder.getImageFromFile("lock.png"), 0, 0, null);
        g.dispose();
        return img;
    }
    
    private void fillWall(BufferedImage img, int tx, int ty){
        //Construct a full wall image
        BufferedImage wall = info.architecture.wallMaterial.filter.generateImage(tx, ty, info.wallNoise);
        //Initialize raster and pixel objects.
        WritableRaster raster = img.getRaster(), wallRaster = wall.getRaster();
        int[] pixel = new int[4];
        //Loop through all pixels.
        for(int y=0;y<img.getHeight();y++){
            for(int x=0;x<img.getWidth();x++){
                if(Filter.rgbPixelEquals(WALL_REGEX, raster.getPixel(x, y, pixel)))
                    raster.setPixel(x, y, wallRaster.getPixel(x, y, pixel));
            }
        }
    }
    
    private void fillFloor(BufferedImage img, int tx, int ty){
        BufferedImage floor = info.architecture.floorMaterial.filter.generateImage(tx, ty, info.floorNoise);
        WritableRaster raster = img.getRaster(), floorRaster = floor.getRaster();
        int[] pixel = new int[4];
        int temp;
        
        for(int y=0;y<img.getHeight();y++)
            for(int x=0;x<img.getWidth();x++){
                pixel = raster.getPixel(x, y, pixel);
                if(pixel[0]==0 && pixel[1]==0 && pixel[2]==0){
                    temp = pixel[3];
                    pixel = floorRaster.getPixel(x, y, pixel);
                    pixel[3] = 255 - temp;
                    raster.setPixel(x, y, pixel);
                }
        }
    }
    
    @Unfinished
    private void fillDoor(BufferedImage img, int tx, int ty){
        BufferedImage door = info.architecture.doorMaterial.filter.generateImage(tx, ty, info.wallNoise);
        WritableRaster raster = img.getRaster(), doorRaster = door.getRaster();
        int[] pixel = new int[4];
        int temp;
        
        for(int y=0;y<img.getHeight();y++)
            for(int x=0;x<img.getWidth();x++){
                pixel = raster.getPixel(x, y, pixel);
                if(pixel[0]==0 && pixel[1]==0 && pixel[2]==0){
                    temp = pixel[3];
                    pixel = doorRaster.getPixel(y, x, pixel); //intentional reflection in line y = x
                    pixel[3] = 255 - temp;
                    raster.setPixel(x, y, pixel);
                }
        }
        
        img.getGraphics().drawImage(ImageBuilder.getImageFromFile("handle.png"), 0, 0, null);
    }
    
}
