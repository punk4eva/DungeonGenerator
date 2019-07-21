/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package graph;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.function.BiFunction;
import javax.imageio.ImageIO;

/**
 *
 * @author Adam Whittaker
 */
public class Graph{

    public final Point[][] map;
    
    private final LinkedList<Point> doors = new LinkedList<>();
    
    public Graph(int w, int h){
        map = new Point[h][w];
        for(int y=0;y<h;y++){
            for(int x=0;x<w;x++){
                map[y][x] = new Point(x, y);
            }
        }
    }
    
    public void makePNG(String filepath, BiFunction<Point, int[], int[]> colorGetter) throws IOException{
        BufferedImage img = new BufferedImage(map[0].length, map.length, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = img.getRaster();
        int[] pixel = new int[3];
        for(int y=0;y<map.length;y++){
            for(int x=0;x<map[y].length;x++){
                pixel = colorGetter.apply(map[y][x], pixel);
                raster.setPixel(x, y, pixel);
            }
        }
        ImageIO.write(img, "png", new File(filepath));
    }
    
}
