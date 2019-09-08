
package components.tiles;

import components.Area;
import components.Trap;
import filterGeneration.ImageBuilder;
import static filterGeneration.ImageBuilder.convertToRGB;
import static filterGeneration.ImageBuilder.getImageFromFile;
import graph.Point.Type;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;
import utils.Utils;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 */
public class Bookshelf extends Tile{
    
    public final boolean entrance;

    public Bookshelf(Tile al, Trap tr, final boolean e){
        super("bookshelf", "@Unfinished", Type.WALL, al, tr);
        entrance = e;
    }

    @Override
    public void buildImage(Area area, int x, int y){
        switch(R.nextInt(2)){
            case 0: generateStyle1(area); break;
            default: generateStyle1(area); break;
        }
    }
    
    private void generateStyle1(Area area){
        LinkedList<AbstractMap.SimpleEntry<int[], int[]>> map = new LinkedList<>();
        map.add(new SimpleEntry(new int[]{88, 60, 40}, ImageBuilder.colorToPixelArray(area.info.architecture.furnitureMaterial.color.darker())));
        map.add(new SimpleEntry(new int[]{120, 88, 48}, ImageBuilder.colorToPixelArray(area.info.architecture.furnitureMaterial.color)));
        map.add(new SimpleEntry(new int[]{128, 100, 64}, ImageBuilder.colorToPixelArray(area.info.architecture.furnitureMaterial.color.brighter())));
        map.add(new SimpleEntry(new int[]{112, 56, 112}, ImageBuilder.colorToPixelArray(ImageBuilder.getRandomColor())));
        map.add(new SimpleEntry(new int[]{48,  56, 136}, ImageBuilder.colorToPixelArray(ImageBuilder.getRandomColor())));
        map.add(new SimpleEntry(new int[]{160, 92, 32}, ImageBuilder.colorToPixelArray(ImageBuilder.getRandomColor())));
        map.add(new SimpleEntry(new int[]{80,  100, 128}, ImageBuilder.colorToPixelArray(ImageBuilder.getRandomColor())));
        map.add(new SimpleEntry(new int[]{152, 148, 120}, ImageBuilder.colorToPixelArray(ImageBuilder.getRandomColor())));
        map.add(new SimpleEntry(new int[]{88,  0, 16}, ImageBuilder.colorToPixelArray(ImageBuilder.getRandomColor())));
        map.add(new SimpleEntry(new int[]{152, 24, 32}, ImageBuilder.colorToPixelArray(ImageBuilder.getRandomColor())));
        map.add(new SimpleEntry(new int[]{120, 148, 24}, ImageBuilder.colorToPixelArray(ImageBuilder.getRandomColor())));
        map.add(new SimpleEntry(new int[]{160, 96, 24}, ImageBuilder.colorToPixelArray(ImageBuilder.getRandomColor())));
        map.add(new SimpleEntry(new int[]{72,  132, 56}, ImageBuilder.colorToPixelArray(ImageBuilder.getRandomColor())));
        
        image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = image.getRaster(),
                bookRaster = convertToRGB(getImageFromFile("bookshelf0.png")).getRaster();
        int[] pixel = new int[3];
        for(int y=0;y<image.getHeight();y++){
            for(int x=0;x<image.getWidth();x++){
                pixel = bookRaster.getPixel(x, y, pixel);
                if(Utils.mapContainsArray(map, pixel)){
                    raster.setPixel(x, y, Utils.getValueFromMap(map, pixel));
                }else{
                    raster.setPixel(x, y, pixel);
                }
            }
        }
    }

}
