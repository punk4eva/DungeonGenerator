
package components.tiles;

import components.Area;
import components.decorations.Decoration;
import graph.Point.Type;

/**
 * A tile with the extra functionality for rendering the floor beneath it.
 * @author Adam Whittaker
 */
public abstract class OverFloorTile extends Tile{

    
    private static final long serialVersionUID = 67389321967319356L;
    
    
    /**
     * specialFloor: Whether this Desk is on top of a special floor.
     */
    private final boolean specialFloor;

    
    /**
     * Creates a new instance by forwarding the arguments to the Tile 
     * constructor.
     * @param na
     * @param desc
     * @param t
     * @param al
     * @param tr
     * @param specFloor Wether this tile should be rendered on special floor.
     */
    public OverFloorTile(String na, String desc, Type t, Tile al, Decoration tr, boolean specFloor){
        super(na, desc, t, al, tr);
        specialFloor = specFloor;
    }
    
    
    @Override
    public void buildImage(Area area, int x, int y){
        if(specialFloor) generateSpecFloorImage(area, x, y);
        else generateFloorImage(area, x, y);
    }

}
