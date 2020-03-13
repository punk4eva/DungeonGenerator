
package animation.assets;

import generation.noise.PerlinNoiseGenerator;
import static gui.core.MouseInterpreter.focusX;
import static gui.core.MouseInterpreter.focusY;
import static gui.pages.DungeonScreen.getSettings;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.awt.image.WritableRaster;
import textureGeneration.Texture;

/**
 * Generates and paints the water animation onto the screen.
 * @author Adam Whittaker
 */
public class WaterPainter{
    
    
    /**
     * waterImage: The image of the water.
     * frame: The current progress through the rendering.
     * height: The height of the waterImage.
     * lastFrameUpdate: The time of the last frame change.
     */
    private final BufferedImage waterImage;
    
    private int frame = 0;
    private final int height;
    private long lastFrameUpdate;
    
    
    /**
     * Creates a new instance.
     * @param waterColor The general color of the water.
     * @param width The amount of tiles the Area is wide.
     * @param height The amount of tiles the Area is high.
     */
    public WaterPainter(Color waterColor, int width, int height){
        waterImage = new BufferedImage(width*16, height*16+16, 
                BufferedImage.TYPE_INT_RGB);
        generateWaterImage(waterColor, width*16, height*16);
        this.height = height*16;
    }
    
    
    /**
     * Creates the water image.
     * @param waterColor The general color of the water.
     * @param width The pixel width of the water image.
     * @param height The pixel height of the water image.
     */
    private void generateWaterImage(Color waterColor, int width, int height){
        //Generates a Perlin noise height map and initializes the color of the
        //water.
        double[][] waterNoise = new double[height][width];
        new PerlinNoiseGenerator(width, height,  100, 5*getSettings().GRAPHICS.code+1,  0.5, 0.9).apply(waterNoise);
        WritableRaster raster = waterImage.getRaster();
        Color br = waterColor.brighter(), da = waterColor.darker();
        int[] pixel = new int[3];
        
        //Loops through the water image and colors it according to the noise 
        //map.
        int y;
        for(int x=0;x<waterImage.getWidth();x++){
            for(y=0;y<height;y++){
                pixel = Texture.getColorAverage(pixel, br, da, waterNoise[y][x]);
                raster.setPixel(x, y, pixel);
            }
            for(;y<height+16;y++) raster.setPixel(x, y, raster.getPixel(x, y-height, pixel));
        }
    }
    
    /**
     * Paints the water image onto the given graphics.
     * @param g
     * @param x The tile x coordinate.
     * @param y The tile y coordinate.
     */
    public void paint(Graphics2D g, int x, int y){
        try{
            g.drawImage(waterImage.getSubimage((x-focusX), (y-focusY-frame+height)%(height), 16, 16), x, y, null);
        }catch(RasterFormatException e){/*Skip frame*/}
    }
    
    /**
     * Checks whether to update the frame of the animation.
     */
    public void checkFrameUpdate(){
        long now = System.currentTimeMillis();
        if(now-lastFrameUpdate>getSettings().WATER_DELAY){
            lastFrameUpdate = now;
            frame = (frame+1) % height;
        }
    }
    
}
