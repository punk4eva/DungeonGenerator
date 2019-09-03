
package components.tiles;

import components.Area;
import components.Trap;
import graph.Point.Type;
import java.awt.Color;
import java.awt.Graphics;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 */
public class Embers extends Tile{
    
    public Embers(Trap tr){
        super("embers", "@Unfinished", Type.FLOOR, null, tr);
    }
    
    @Override
    public void buildImage(Area area, int x, int y){
        super.buildImage(area, x, y);
        Graphics g = image.getGraphics();
        g.setColor(Color.BLACK);
        for(int n=0;n<23;n++)
            g.fillRect(R.nextInt(15), R.nextInt(15), 2, 2);
        g.setColor(Color.RED.darker());
        for(int n=0;n<8;n++)
            g.fillRect(R.nextInt(16), R.nextInt(16), 1, 1);
        g.setColor(Color.ORANGE.darker());
        for(int n=0;n<8;n++)
            g.fillRect(R.nextInt(16), R.nextInt(16), 1, 1);
    }
    
}
