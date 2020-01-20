
package animation.assets;

import filterGeneration.Filter;
import generation.noise.PerlinNoiseGenerator;
import static gui.core.DungeonViewer.getSettings;
import static gui.core.MouseInterpreter.focusX;
import static gui.core.MouseInterpreter.focusY;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.awt.image.WritableRaster;

/**
 *
 * @author Adam Whittaker
 */
public class WaterPainter{
    
    private final BufferedImage waterImage;
    
    private int frame = 0;
    private final int height;
    private long lastFrameUpdate;
    
    
    public WaterPainter(Color waterColor, int width, int height){
        waterImage = new BufferedImage(width*16, height*16+16, BufferedImage.TYPE_INT_RGB);
        generateWaterImage(waterColor, width*16, height*16);
        this.height = height*16;
    }
    
    
    private void generateWaterImage(Color waterColor, int width, int height){
        double[][] waterNoise = new double[height][width];
        new PerlinNoiseGenerator(width, height,  100, 5*getSettings().GRAPHICS.value+1,  0.5, 0.9).apply(waterNoise);
        WritableRaster raster = waterImage.getRaster();
        Color br = waterColor.brighter(), da = waterColor.darker();
        int[] pixel = new int[3];
        
        int y;
        for(int x=0;x<waterImage.getWidth();x++){
            for(y=0;y<height;y++){
                pixel = Filter.getColorAverage(pixel, br, da, waterNoise[y][x]);
                raster.setPixel(x, y, pixel);
            }
            for(;y<height+16;y++) raster.setPixel(x, y, raster.getPixel(x, y-height, pixel));
        }
        /*try{
            ImageIO.write(waterImage, "png", new File("saves/water.png"));
        }catch(IOException ex){}*/
    }
    
    public void paint(Graphics2D g, int x, int y){
        try{
            g.drawImage(waterImage.getSubimage((x-focusX), (y-focusY-frame+height)%(height), 16, 16), x, y, null);
        }catch(RasterFormatException e){/*Skip frame*/}
    }
    
    public void checkFrameUpdate(){
        long now = System.currentTimeMillis();
        if(now-lastFrameUpdate>getSettings().WATER_DELAY){
            lastFrameUpdate = now;
            frame = (frame+1) % height;
        }
    }
    
}
