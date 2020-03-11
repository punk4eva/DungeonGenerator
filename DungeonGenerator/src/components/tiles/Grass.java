
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

    
    private static final long serialVersionUID = 1756912812L;
    
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
        underTile.decoration = null;
    }
    
    
    @Override
    public void buildImage(Area area, int x, int y){
        Color color = getGrassColor(area.info.architecture.biomeProcessor.biome);

        if(underTile.image==null){
            underTile.buildImage(area, x, y);
        }
        image = underTile.image;
        
        if(decoration == null){
            if(tall) image.addInstruction(img -> constructTallGrassImage(color, 
                    (Graphics2D) img.getGraphics()));
            else{
                image.addInstruction(img -> constructLowGrassImage(color, 
                        (Graphics2D) img.getGraphics()));
            }
        }else{
            if(!tall && decoration.isAboveBackground()){
                image.addInstruction(img -> constructLowGrassImage(color, 
                        (Graphics2D) img.getGraphics()));
                decoration.addDecoration(image);
            }else{
                decoration.addDecoration(image);
                if(tall) image.addInstruction(img -> constructTallGrassImage(color, 
                        (Graphics2D) img.getGraphics()));
                else{
                    image.addInstruction(img -> constructLowGrassImage(color, 
                            (Graphics2D) img.getGraphics()));
                }
            }
        }
    }
    
    @Override
    public void initializeImage(Area area, int x, int y){
        buildImage(area, x, y);
        image.buildImage();
        if(alias != null){
            alias.buildImage(area, x, y);
            alias.image.buildImage();
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
