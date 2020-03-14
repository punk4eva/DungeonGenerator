
package components.rooms;

import components.Area;
import components.tiles.Chasm;
import components.tiles.Tile;
import static utils.Utils.R;
import static utils.Utils.interpolate;

/**
 * A room where part of the floor has caved in.
 * @author Adam Whittaker
 */
public class CaveInRoom extends PlainRoom{

    
    /**
     * The minimum and maximum chance of a Tile being a Chasm.
     */
    private final double MIN_CHASM_CHANCE = 0.2, MAX_CHASM_CHANCE = 0.7;
    
    
    /**
     * Creates an instance.
     * @param w The width.
     * @param h The height.
     */
    public CaveInRoom(int w, int h){
        super(DoorStyle.ANY, "Collapsed Room", w, h);
        assertDimensions(w, h, 7, 7);
    }
    
    
    @Override
    public void generate(Area area){
        buildWalls(area);
        double chance = getChasmChance(area);
        
        //Generates the Chasms
        for(int x=1;x<width-1;x++){
            for(int y=1;y<height-1;y++){
                if(y!=1 && y!=height-2 && x!=1 && x!=width-2 && 
                        R.nextDouble()<chance) map[y][x] = new Chasm();
                else map[y][x] = Tile.genFloor(area);
            } 
        }
    }
    
    /**
     * Calculates the chance of a Tile being a Chasm based on the ruination
     * level.
     * @param area The area.
     * @return A 0 -> 1 probability.
     */
    private double getChasmChance(Area area){
        return interpolate(MIN_CHASM_CHANCE, MAX_CHASM_CHANCE, 
                area.info.architecture.biomeProcessor.society.ruination/100D);
    }
    
}
