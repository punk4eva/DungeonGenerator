
package components.tiles;

import components.Area;

/**
 * This class represents a Tile that needs to run extra code after being created
 * initially.
 * @author Adam Whittaker
 */
public interface PostProcessingTile{
    
    /**
     * Edits the Area after all tiles have been laid down and rotated.
     * @param area The Area.
     * @param x The tile x.
     * @param y The tile y.
     */
    public void postProcessing(Area area, int x, int y);
    
}
