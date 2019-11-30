
package components.tiles;

import components.Area;
import java.awt.Color;
import java.awt.Graphics2D;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 */
public class Grass extends Tile{

    private final boolean tall;
    private final Tile underTile;

    
    public Grass(boolean t, Tile tile){
        super(t ? "high grass" : "low grass", "@Unfinished", tile.type, null, tile.decoration);
        tall = t;
        underTile = tile;
    }
    
    
    @Override
    public void buildImage(Area area, int x, int y){
        if(underTile.image==null) underTile.buildImage(area, x, y);
        image = underTile.image;
        if(tall) constructTallGrass(area.info.grassColor, 
                (Graphics2D) image.getGraphics());
        else{
            Graphics2D g = (Graphics2D) image.getGraphics();
            constructLowGrass(area.info.grassColor, g);
            if(underTile.decoration!=null && underTile.decoration.aboveWater){
                underTile.decoration.drawImage((Graphics2D) image.getGraphics(), 0, 0);
            }
        }
    }
    
    private void constructTallGrass(Color col, Graphics2D g){
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
    
    private void constructLowGrass(Color col, Graphics2D g){
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
