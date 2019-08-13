
package filterGeneration;

import components.Area;
import components.tiles.Tile;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.imageio.ImageIO;
import static utils.Utils.R;
import static utils.Utils.exceptionStream;

/**
 *
 * @author Adam Whittaker
 */
public class ImageBuilder{
    
    public static interface SerSupplier extends Supplier<BufferedImage>, Serializable{}
    
    public static interface SerInstruction extends Serializable, Consumer<BufferedImage>{}
    
    
    public static void applyAlphaNoise(BufferedImage img, int midPoint, int jitter){
        WritableRaster raster = img.getAlphaRaster();
        int[] pixel = new int[4];
        for(int y=0;y<img.getHeight();y++){
            for(int x=0;x<img.getWidth();x++){
                pixel = raster.getPixel(x, y, pixel);
                if(pixel[3]==0){
                    pixel[3] = midPoint + 2*R.nextInt(jitter) - jitter;
                    pixel[3] = pixel[3]>255 ? 255 : pixel[3];
                    pixel[3] = pixel[3]<0 ? 0 : pixel[3];
                    raster.setPixel(x, y, pixel);
                }
            }
        }
    }
    
    public static BufferedImage getImageFromFile(String filepath){
        try{
            BufferedImage img = ImageIO.read(new File("graphics/tiles/" + filepath));
            System.out.println("Type: " + img.getType());
            return img;
        }catch(IOException ex){
            ex.printStackTrace(System.err);
            ex.printStackTrace(exceptionStream);
            throw new IllegalStateException();
        }
    }

    /*@Unfinished("Only used for debugging. PARAMETER CHANGE")
    public static BufferedImage constructImage(Tile tile, Area area, int tx, int ty){
        BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        if(tile.equals(Type.WALL) || tile.equals(Type.FLOOR)){
            double[][] map = area.info.getNoiseMap(tile);
            WritableRaster raster = img.getRaster();
            int[] pixel = new int[3];
            for(int x=0;x<img.getWidth();x++){
                for(int y=0;y<img.getHeight();y++){
                    pixel[0] = (int)map[y+ty*16][x+tx*16];
                    pixel[1] = (int)map[y+ty*16][x+tx*16];
                    pixel[2] = (int)map[y+ty*16][x+tx*16];
                    raster.setPixel(x, y, pixel);
                }
            }
        }else if(tile.equals(Type.DOOR)){
            Graphics g = img.getGraphics();
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, 16, 16);
            g.dispose();
        }
        return img;
    }*/
    
    public static BufferedImage constructImage(Tile tile, Area area, int x, int y){
        switch(tile.type){
            case FLOOR: return area.info.feeling.filters.floor.generateImage(x, y, area.info.floorNoise);
            case WALL: return area.info.feeling.filters.wall.generateImage(x, y, area.info.wallNoise);
            default: 
                BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
                Graphics g = img.getGraphics();
                g.setColor(Color.BLUE);
                g.fillRect(0, 0, 16, 16);
                g.dispose();
                return img;
        }
    }
    
    /*@Unfinished("Debugging only. PARAMETER CHANGE")
    public static BufferedImage constructImage(Tile tile, Area area, int tx, int ty){
        BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();
        if(area.graph.map[ty][tx].isCorridor) g.setColor(Color.RED);
        else g.setColor(Color.YELLOW);
        g.fillRect(0, 0, 16, 16);
        return img;
    }*/

}
