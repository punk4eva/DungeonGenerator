
package utils;

import components.mementoes.AreaInfo;
import components.tiles.Tile;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import utils.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 */
public class ImageBuilder{

    @Unfinished("Need to finish filters.")
    public static BufferedImage constructImage(Tile tile, AreaInfo info, int tx, int ty){
        BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        double[][] map = info.getNoiseMap(tile);
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
        return img;
    }

}
