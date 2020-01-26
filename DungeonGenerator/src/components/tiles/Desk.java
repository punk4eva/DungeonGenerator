
package components.tiles;

import components.Area;
import components.mementoes.AreaInfo;
import filterGeneration.ImageBuilder;
import graph.Point.Type;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import materials.Material;

/**
 * A table.
 * @author Adam Whittaker
 */
public class Desk extends OverFloorTile{
    
    
    /**
     * material: The material that this desk is built from.
     * filterPath: The file path to the filter image.
     */
    private final Material material;
    private final String filterPath;

    
    /**
     * Creates a new instance.
     * @param name The name of this desk.
     * @param desc The description.
     * @param info The information of the Area.
     * @param specFloor Whether it is on a special floor.
     */
    public Desk(String name, String desc, AreaInfo info, boolean specFloor){
        super(name, desc, Type.FLOOR, null, null, specFloor);
        material = info.architecture.furnitureMaterial;
        filterPath = "desks/desk0.png"; //@Unfinished placeholder
    }

    
    @Override
    public void buildImage(Area area, int x, int y){
        super.buildImage(area, x, y);
        
        overlayDeskTop(area, x, y);
    }
    
    /**
     * Paints the top of this desk to the image.
     * @param area The area.
     * @param _x The x coordinate of the tile.
     * @param _y The y coordinate of the tile.
     */
    private void overlayDeskTop(Area area, int _x, int _y){
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
