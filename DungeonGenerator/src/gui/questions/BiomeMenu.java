
package gui.questions;

import biomes.Biome;
import gui.core.DungeonViewer;
import static gui.core.DungeonViewer.WIDTH;
import gui.pages.SelectionScreen;
import gui.tools.DropDownMenu;
import static gui.tools.UIPanel.BUTTON_TEXT_FONT;
import java.awt.Graphics2D;
import java.util.Map.Entry;

/**
 *
 * @author Adam Whittaker
 */
public class BiomeMenu extends DropDownMenu<Biome>{
    
    
    private final static int COLUMN_WIDTH = WIDTH/8, BIOMES_PER_COLUMN = 7;
    

    public BiomeMenu(){
        super("Choose the climate", b -> true, COLUMN_WIDTH * 
                (Biome.values().length/BIOMES_PER_COLUMN + 1));
        for(Biome b : Biome.values()){
            map.put(b, () -> {});
        }
    }
    
    
    @Override
    public void click(int mx, int my){
        if(withinBounds(mx, my)) open = !open;
        else if(x<=mx && mx<=x+width){
            int n = 0;
            for(Entry<Biome, Runnable> entry : map.entrySet()){
                if(withinBounds(getX(n), getY(n), COLUMN_WIDTH, height, mx, my)){
                    selection = entry;
                    return;
                }else n++;
            }
        }
    }
    
    @Override    
    protected void paintOptions(Graphics2D g){
        int n = 0;
        for(Entry<Biome, Runnable> entry : map.entrySet()){
            if(predicate.test(entry.getKey())){
                paintBox(g, getX(n), getY(n), COLUMN_WIDTH, height, PADDING/2);
                paintText(g, entry.getKey().toString(), getX(n), getY(n), COLUMN_WIDTH, height, 
                    BUTTON_TEXT_FONT, entry.equals(selection) ? HIGHLIGHT_COLOR : TEXT_COLOR);
                n++;
            }
        }
    }
    
    protected int getX(int n){
        return x + (n/BIOMES_PER_COLUMN) * COLUMN_WIDTH;
    }
    
    protected int getY(int n){
        return y + height * ((n % BIOMES_PER_COLUMN) + 1);
    }

}
