
package animation.assets;

import filterGeneration.Filter;
import generation.noise.PerlinNoiseGenerator;
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
    
    private final static long DELAY = 110;
    
    
    public WaterPainter(Color waterColor, int width, int height){
        waterImage = new BufferedImage(width*16, height*16, BufferedImage.TYPE_INT_RGB);
        generateWaterImage(waterColor, width, height);
        this.height = height*16;
    }
    
    
    private void generateWaterImage(Color waterColor, int width, int height){
        double[][] waterNoise = new double[height*16][width*16];
        new PerlinNoiseGenerator(width*16, height*16, 100, 6, 0.55, 0.9).apply(waterNoise);
        WritableRaster raster = waterImage.getRaster();
        Color br = waterColor.brighter(), da = waterColor.darker();
        int[] pixel = new int[3];
        
        for(int y=0;y<waterImage.getHeight();y++){
            for(int x=0;x<waterImage.getWidth();x++){
                pixel = Filter.getColorAverage(pixel, br, da, waterNoise[y][x]);
                raster.setPixel(x, y, pixel);
            }
        }
    }
    
    public void paint(Graphics2D g, int x, int y){
        try{
            g.drawImage(waterImage.getSubimage((x-focusX), (y-focusY-frame+height)%(height-16), 16, 16), x, y, null);
        }catch(RasterFormatException e){/*Skip frame*/}
    }
    
    public void checkFrameUpdate(){
        long now = System.currentTimeMillis();
        if(now-lastFrameUpdate>DELAY){
            lastFrameUpdate = now;
            frame = (frame+1) % height;
        }
    }
    
}
