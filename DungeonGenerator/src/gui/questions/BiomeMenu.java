
package gui.questions;

import biomes.Biome;
import static gui.core.DungeonViewer.WIDTH;
import gui.tools.DropDownMenu;
import static gui.tools.UIPanel.BUTTON_TEXT_FONT;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import static java.lang.Math.ceil;
import java.util.Arrays;

/**
 *
 * @author Adam Whittaker
 */
public class BiomeMenu extends DropDownMenu<Biome>{
    
    
    private final static int COLUMN_WIDTH = WIDTH/8, BIOMES_PER_COLUMN = 7,
            TOTAL_COLUMNS = (int)ceil((double)
                    Biome.values().length/BIOMES_PER_COLUMN);    
    

    public BiomeMenu(){
        super("Choose the climate", COLUMN_WIDTH * 
                (Biome.values().length/BIOMES_PER_COLUMN + 1));
        items.addAll(Arrays.asList(Biome.values()));
    }
    
    
    @Override
    public void click(int mx, int my){
        if(withinBounds(mx, my)) open = !open;
        else if(x<=mx && mx<=x+width){
            int n = 0;
            for(int i=0;i<items.size();i++){
                if(withinBounds(getX(n), getY(n), COLUMN_WIDTH, height, mx, my)){
                    setSelection(i);
                    return;
                }else n++;
            }
        }
    }
    
    @Override    
    protected void paintOptions(Graphics2D g){
        int n = 0;
        for(Biome b : items){
            paintBox(g, getX(n), getY(n), COLUMN_WIDTH, height, PADDING/2);
            paintText(g, b.toString(), getX(n), getY(n), COLUMN_WIDTH, height, 
                BUTTON_TEXT_FONT, b.equals(selection) ? HIGHLIGHT_COLOR : TEXT_COLOR);
            n++;
        }
    }
    
    protected int getX(int n){
        return x + (n/BIOMES_PER_COLUMN) * COLUMN_WIDTH;
    }
    
    protected int getY(int n){
        return y + height * ((n % BIOMES_PER_COLUMN) + 1);
    }
    
    @Override
    public void keyPressed(KeyEvent e){
        switch(e.getKeyCode()){
            case KeyEvent.VK_UP:
                setSelection(getUpIndex(selectionNum, BIOMES_PER_COLUMN, items.size()));
                break;
            case KeyEvent.VK_DOWN:
                setSelection(getDownIndex(selectionNum, BIOMES_PER_COLUMN, items.size()));
                break;
            case KeyEvent.VK_LEFT:
                setSelection(getLeftIndex(selectionNum, BIOMES_PER_COLUMN, items.size()));
                break;
            case KeyEvent.VK_RIGHT:
                setSelection(getRightIndex(selectionNum, BIOMES_PER_COLUMN, items.size()));
                break;
        }
    }
    
    private static int getDownIndex(int n, int bpc, int size){
        if((1 + n/bpc)*bpc>size)
            bpc = size%bpc;
        return n - (n%bpc) + ((n+1)%bpc);
    }
    
    private static int getUpIndex(int n, int bpc, int size){
        if(n%bpc == 0){
            if((1 + n/bpc)*bpc>size) return n + size%bpc - 1;
            return n + bpc - 1;
        }
        else return n-1;
    }
    
    private static int getRightIndex(int n, int bpc, int size){
        if(n%bpc >= size%bpc && size%bpc != 0){
            return ((1 + n/bpc)%(TOTAL_COLUMNS-1))*bpc + (n%bpc);
        }else{
            return ((1 + n/bpc)%(TOTAL_COLUMNS))*bpc + (n%bpc);
        }
    }
    
    private static int getLeftIndex(int n, int bpc, int size){
        if(n%bpc >= size%bpc && size%bpc != 0){
            return ((TOTAL_COLUMNS - 2 + n/bpc)%(TOTAL_COLUMNS-1))*bpc + (n%bpc);
        }else{
            return ((TOTAL_COLUMNS - 1 + n/bpc)%(TOTAL_COLUMNS))*bpc + (n%bpc);
        }
    }

}
