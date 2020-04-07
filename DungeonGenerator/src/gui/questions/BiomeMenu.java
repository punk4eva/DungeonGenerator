
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
 * User selects the Biome.
 * @author Adam Whittaker
 */
public class BiomeMenu extends DropDownMenu<Biome>{
    
    
    /**
     * COLUMN_WIDTH: The width of each of the columns in the table.
     * BIOMES_PER_COLUMN: The number of rows in the table.
     * TOTAL_COLUMNS: The total number of columns in the table.
     */
    private final static int COLUMN_WIDTH = WIDTH/8, BIOMES_PER_COLUMN = 7,
            TOTAL_COLUMNS = (int)ceil((double)
                    Biome.values().length/BIOMES_PER_COLUMN);    
    

    /**
     * Creates a new instance.
     */
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
            //Loops through the biomes to figure out which was clicked.
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
        //Paints all biome boxes in a loop.
        for(Biome b : items){
            paintBox(g, getX(n), getY(n), COLUMN_WIDTH, height, PADDING/2);
            paintText(g, b.toString(), getX(n), getY(n), COLUMN_WIDTH, height, 
                BUTTON_TEXT_FONT, b.equals(selection) ? HIGHLIGHT_COLOR : TEXT_COLOR);
            n++;
        }
    }
    
    /**
     * Gets the top-left x coordinate of the biome with the given number.
     * @param biomeNum
     * @return
     */
    protected int getX(int biomeNum){
        return x + (biomeNum/BIOMES_PER_COLUMN) * COLUMN_WIDTH;
    }
    
    /**
     * Gets the top-left y coordinate of the biome with the given number.
     * @param biomeNum
     * @return
     */
    protected int getY(int biomeNum){
        return y + height * ((biomeNum % BIOMES_PER_COLUMN) + 1);
    }
    
    @Override
    public void keyPressed(KeyEvent e){
        switch(e.getKeyCode()){
            //Navigates the selected biome.
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
    
    /**
     * Calculates the index of the biome underneath the given one in the table
     * @param n The biome index.
     * @param bpc The number of biomes per column.
     * @param size The total number of biomes.
     * @return
     */
    private static int getDownIndex(int n, int bpc, int size){
        if((1 + n/bpc)*bpc>size)
            bpc = size%bpc;
        return n - (n%bpc) + ((n+1)%bpc);
    }
    
    /**
     * Calculates the index of the biome above the given one in the table
     * @param n The biome index.
     * @param bpc The number of biomes per column.
     * @param size The total number of biomes.
     * @return
     */
    private static int getUpIndex(int n, int bpc, int size){
        if(n%bpc == 0){
            if((1 + n/bpc)*bpc>size) return n + size%bpc - 1;
            return n + bpc - 1;
        }
        else return n-1;
    }
    
    /**
     * Calculates the index of the biome to the right of the given one in the 
     * table.
     * @param n The biome index.
     * @param bpc The number of biomes per column.
     * @param size The total number of biomes.
     * @return
     */
    private static int getRightIndex(int n, int bpc, int size){
        if(n%bpc >= size%bpc && size%bpc != 0){
            return ((1 + n/bpc)%(TOTAL_COLUMNS-1))*bpc + (n%bpc);
        }else{
            return ((1 + n/bpc)%(TOTAL_COLUMNS))*bpc + (n%bpc);
        }
    }
    
    /**
     * Calculates the index of the biome to the left of the given one in the 
     * table.
     * @param n The biome index.
     * @param bpc The number of biomes per column.
     * @param size The total number of biomes.
     * @return
     */
    private static int getLeftIndex(int n, int bpc, int size){
        if(n%bpc >= size%bpc && size%bpc != 0){
            return ((TOTAL_COLUMNS - 2 + n/bpc)%(TOTAL_COLUMNS-1))*bpc + (n%bpc);
        }else{
            return ((TOTAL_COLUMNS - 1 + n/bpc)%(TOTAL_COLUMNS))*bpc + (n%bpc);
        }
    }

}
