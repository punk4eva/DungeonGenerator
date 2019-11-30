
package components.tiles;

import components.Area;
import components.mementoes.AreaInfo;
import filterGeneration.ImageBuilder;
import graph.Point.Type;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import materials.Material;

/**
 *
 * @author Adam Whittaker
 */
public class Desk extends Tile{
    
    
    private final boolean specialFloor;
    private final Material material;
    private final String filterPath;

    
    public Desk(String name, String desc, AreaInfo info, boolean specFloor, int price){
        super(name, desc, Type.FLOOR, null, null);
        specialFloor = specFloor;
        material = info.architecture.getFurnitureMaterial(price);
        filterPath = "desks/desk0.png"; //@Unfinished placeholder
    }

    
    @Override
    public void buildImage(Area area, int x, int y){
        if(specialFloor)
            image = area.info.architecture.specFloorMaterial.filter.generateImage(x, y, area.info.floorNoise);
        else generateFloorImage(area, x, y);
        
        overlayTop(area, x, y);
    }
    
    private void overlayTop(Area area, int _x, int _y){
        BufferedImage filter = ImageBuilder.getImageFromFile(filterPath);
        WritableRaster tableRaster = material.filter.generateImage(_x, _y, area.info.floorNoise).getRaster(),
                filterRaster = filter.getRaster();
        int pixel[] = new int[4];
        
        for(int y=0;y<16;y++) for(int x=0;x<16;x++){
            if(filterRaster.getPixel(x, y, pixel)[0]!=0){
                filterRaster.setPixel(x, y, tableRaster.getPixel(x, y, pixel));
            }
        }
        
        image.getGraphics().drawImage(filter, 0, 0, null);
    }

}
