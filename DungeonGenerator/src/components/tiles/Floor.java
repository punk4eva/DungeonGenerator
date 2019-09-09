
package components.tiles;

import components.Area;
import components.traps.Trap;
import graph.Point.Type;
import java.awt.Color;
import java.awt.Graphics2D;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 */
public class Floor extends Tile{

    public Floor(Trap tr){
        super("floor", "@Unfinished", Type.FLOOR, null, tr);
    }
    
    protected Floor(String name, String desc, Trap tr){
        super(name, desc, Type.FLOOR, null, tr);
    }
    
    @Override
    public void buildImage(Area area, int x, int y){
        generateFloorImage(area, x, y);
        
        Graphics2D g = (Graphics2D) image.getGraphics();
        Color wood = area.info.architecture.biome.getRandomTreeType().color;
        g.setColor(wood.darker());
        for(int n=0;n<18;n++)
            g.fillRect(R.nextInt(12), R.nextInt(15), 5+R.nextInt(5), 2);
        g.setColor(wood);
        for(int n=0;n<18;n++)
            g.fillRect(R.nextInt(13), R.nextInt(16), 4+R.nextInt(4), 1);
        g.setColor(wood.brighter());
        for(int n=0;n<18;n++)
            g.fillRect(R.nextInt(13), R.nextInt(16), 4+R.nextInt(4), 1);
    }

}
