
package utils;

import components.Area;
import components.tiles.Tile;
import graph.Point.Type;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import utils.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 */
public class ImageBuilder{

    @Unfinished("Need to finish filters.")
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
    }
    
    
    /*@Unfinished("Debug.")
    public static BufferedImage constructImage(Tile tile, Area area, int tx, int ty){
        BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();
        if(area.graph.map[ty][tx].isCorridor) g.setColor(Color.RED);
        else g.setColor(Color.YELLOW);
        g.fillRect(0, 0, 16, 16);
        return img;
    }*/

}
