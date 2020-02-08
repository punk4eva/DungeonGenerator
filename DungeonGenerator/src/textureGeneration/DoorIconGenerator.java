
package textureGeneration;

import components.mementoes.AreaInfo;
import components.tiles.Door;
import textureGeneration.ImageBuilder.SerSupplier;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 * Generates the image for a door.
 * @author Adam Whittaker
 */
public class DoorIconGenerator{
    
    
    /**
     * WALL_REGEX: the color code to replace in the filter.
     * info: The information about the Area.
     * doorShapeSupplier: The supplier for the door filter.
     */
    private final static int[] WALL_REGEX = new int[]{242, 61, 219};
    
    public final AreaInfo info;
    public final SerSupplier doorShapeSupplier;
    
    
    /**
     * Creates a new instance.
     * @param i The area info.
     * @param door The image supplier for the door.
     */
    public DoorIconGenerator(AreaInfo i, SerSupplier door){
        info = i;
        doorShapeSupplier = door;
    }
    
    
    /**
     * Generates all the images for the door.
     * @param door The Door.
     * @param x The tile x
     * @param y The tile y
     */
    public void generateAllImages(Door door, int x, int y){
        door.setClosedImage(generateClosed(x, y));
        door.setOpenImage(generateOpen(x, y));
        door.setLockedImage(generateLocked(door.getClosedImage()));
    }
    
    
    /**
     * Generates the image for a open door.
     * @param tx The tile x.
     * @param ty The tile y.
     * @return
     */
    private BufferedImage generateOpen(int tx, int ty){
        BufferedImage img = doorShapeSupplier.get();
        fillWall(img, tx, ty);
        fillFloor(img, tx, ty);
        return img;
    }
    
    /**
     * Generates the image for a closed door.
     * @param tx The tile x.
     * @param ty The tile y.
     * @return
     */
    private BufferedImage generateClosed(int tx, int ty){
        BufferedImage img = doorShapeSupplier.get();
        fillWall(img, tx, ty);
        fillDoor(img, tx, ty);
        return img;
    }
    
    /**
     * Generates the image for a locked door.
     * @param closed The image for the closed door.
     * @return
     */
    private BufferedImage generateLocked(BufferedImage closed){
        BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();
        g.drawImage(closed, 0, 0, null);
        g.drawImage(ImageBuilder.getImageFromFile("tiles/lock.png"), 0, 0, null);
        g.dispose();
        return img;
    }
    
    
    /**
     * Fills in the wall part of the door image.
     * @param img The image.
     * @param tx The tile x.
     * @param ty The tile y.
     */
    private void fillWall(BufferedImage img, int tx, int ty){
        //Construct a full wall image
        BufferedImage wall = info.architecture.wallMaterial.texture.generateImage(tx, ty, info.wallNoise);
        //Initialize raster and pixel objects.
        WritableRaster raster = img.getRaster(), wallRaster = wall.getRaster();
        int[] pixel = new int[4];
        //Loop through all pixels.
        for(int y=0;y<img.getHeight();y++){
            for(int x=0;x<img.getWidth();x++){
                if(Texture.rgbPixelEquals(WALL_REGEX, raster.getPixel(x, y, pixel)))
                    raster.setPixel(x, y, wallRaster.getPixel(x, y, pixel));
            }
        }
    }
    
    /**
     * Fills in the floor part of the open door image.
     * @param img The image.
     * @param tx The tile x.
     * @param ty The tile y.
     */
    private void fillFloor(BufferedImage img, int tx, int ty){
        BufferedImage floor = info.architecture.floorMaterial.texture.generateImage(tx, ty, info.floorNoise);
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
    
    /**
     * Fills in the door part of the closed/locked door image.
     * @param img The image.
     * @param tx The tile x.
     * @param ty The tile y.
     */
    private void fillDoor(BufferedImage img, int tx, int ty){
        BufferedImage door = info.architecture.doorMaterial.texture.generateImage(tx, ty, info.wallNoise);
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
        
        img.getGraphics().drawImage(ImageBuilder.getImageFromFile("tiles/handle.png"), 0, 0, null);
    }
    
}
