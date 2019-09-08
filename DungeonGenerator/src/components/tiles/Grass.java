
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
public class Grass extends Tile{

    private final boolean tall;

    public Grass(Trap tr, boolean t){
        super(t ? "high grass" : "low grass", "@Unfinished", Type.FLOOR, null, tr);
        tall = t;
    }
    
    @Override
    public void buildImage(Area area, int x, int y){
        generateFloorImage(area, x, y);
        if(tall) constructTallGrass(area.info.grassColor, image.getGraphics());
        else constructLowGrass(area.info.grassColor, image.getGraphics());
    }
    
    private void constructTallGrass(Color col, Graphics g){
        g.setColor(col.darker());
        for(int n=0;n<23;n++)
            g.fillRect(R.nextInt(15), R.nextInt(15), 2, 2);
        g.setColor(col);
        for(int n=0;n<23;n++)
            g.fillRect(R.nextInt(16), R.nextInt(14), 1, 3);
        g.setColor(col.brighter());
        for(int n=0;n<23;n++)
            g.fillRect(R.nextInt(16), R.nextInt(16), 1, 1);
    }
    
    private void constructLowGrass(Color col, Graphics g){
        g.setColor(col.darker());
        for(int n=0;n<15;n++)
            g.fillRect(R.nextInt(16), R.nextInt(16), 1, 1);
        g.setColor(col);
        for(int n=0;n<22;n++)
            g.fillRect(R.nextInt(16), R.nextInt(15), 1, 2);
        g.setColor(col.brighter());
        for(int n=0;n<15;n++)
            g.fillRect(R.nextInt(16), R.nextInt(16), 1, 1);
    }

}
