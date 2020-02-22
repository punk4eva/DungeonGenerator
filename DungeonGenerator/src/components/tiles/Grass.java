
package components.tiles;

import static biomes.GrassColorer.getGrassColor;
import components.Area;
import java.awt.Color;
import java.awt.Graphics2D;
import static utils.Utils.R;

/**
 * A patch of vegetation.
 * @author Adam Whittaker
 */
public class Grass extends Tile{

    
    /**
     * tall: Whether this grass is high or not.
     * underTile: The tile underneath this grass.
     */
    private final boolean tall;
    private final Tile underTile;

    
    /**
     * Creates a new grass object.
     * @param t Whether it is tall.
     * @param tile The tile underneath.
     */
    public Grass(boolean t, Tile tile){
        super(t ? "high grass" : "low grass", "@Unfinished", tile.type, null, tile.decoration);
        tall = t;
        underTile = tile;
    }
    
    
    @Override
    public void buildImage(Area area, int x, int y){
        Color color = getGrassColor(area.info.architecture.biomeProcessor.biome);
        if(underTile.image==null) underTile.buildImage(area, x, y);
        image = underTile.image;
        if(tall) constructTallGrassImage(color, 
                (Graphics2D) image.getGraphics());
        else{
            Graphics2D g = (Graphics2D) image.getGraphics();
            constructLowGrassImage(color, g);
            if(underTile.decoration!=null && underTile.decoration.aboveWater){
                underTile.decoration.drawImage((Graphics2D) image.getGraphics(), 0, 0);
            }
        }
    }
    
    /**
     * Constructs the image of tall grass.
     * @param col The color.
     * @param g The graphics.
     */
    private void constructTallGrassImage(Color col, Graphics2D g){
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
    
    /**
     * Constructs the image of short grass.
     * @param col The color.
     * @param g The graphics.
     */
    private void constructLowGrassImage(Color col, Graphics2D g){
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
