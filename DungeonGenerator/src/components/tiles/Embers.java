
package components.tiles;

import components.Area;
import graph.Point.Type;
import java.awt.Color;
import java.awt.Graphics2D;
import static utils.Utils.R;

/**
 * The residue of a fire.
 * @author Adam Whittaker
 */
public class Embers extends OverFloorTile{

    
    private static final long serialVersionUID = 47193L;
    
    
    /**
     * Creates a new instance.
     * @param specFloor Whether these embers are on a special tile.
     */
    public Embers(boolean specFloor){
        super("embers", "@Unfinished", Type.FLOOR, null, null, specFloor);
    }
    
    
    @Override
    public void buildImage(Area area, int x, int y){
        super.buildImage(area, x, y);
        
        image.addInstruction(img -> {
            Graphics2D g = (Graphics2D) img.getGraphics();
            g.setColor(Color.BLACK);
            for(int n=0;n<23;n++)
                g.fillRect(R.nextInt(15), R.nextInt(15), 2, 2);
            g.setColor(Color.RED.darker());
            for(int n=0;n<8;n++)
                g.fillRect(R.nextInt(16), R.nextInt(16), 1, 1);
            g.setColor(Color.ORANGE.darker());
            for(int n=0;n<8;n++)
                g.fillRect(R.nextInt(16), R.nextInt(16), 1, 1);
        });
    }
    
}
