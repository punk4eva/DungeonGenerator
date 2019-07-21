
package components;

/**
 *
 * @author Adam Whittaker
 */
public class RoomDesc{

    public final String name;
    public String description;
    public final int width, height;
    public int orientation;
    
    public RoomDesc(String n, int w, int h){
        name = n;
        width = w;
        height = h;
    }
    
}
