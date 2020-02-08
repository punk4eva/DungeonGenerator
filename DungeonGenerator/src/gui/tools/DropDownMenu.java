
package gui.tools;

import static gui.core.DungeonViewer.HEIGHT;
import static gui.core.DungeonViewer.WIDTH;
import static gui.tools.UIPanel.BUTTON_TEXT_FONT;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.function.Predicate;
import utils.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 * @param <T> The type of the drop down menu (must have a user-potable 
 * toString() method).
 */
@Unfinished("Might not need the predicate")
public abstract class DropDownMenu<T extends Object> extends InputBox{
    
    
    private static final int MENU_HEIGHT = 64;
    private static final int PADDING = 6;
    private static final Color TITLE_COLOR = Color.YELLOW,
            HIGHLIGHT_COLOR = new Color(98, 96, 147),
            TEXT_COLOR = Color.YELLOW.darker();
    private static final Font TITLE_FONT = new Font(Font.MONOSPACED, Font.BOLD, 36);
    
    
    protected final String title;
    protected final HashMap<T, Runnable> map = new HashMap<>();
    protected final Predicate<T> predicate;
    
    protected Entry<T, Runnable> selection;
    private boolean open = false;
    
    
    public DropDownMenu(String na, Predicate<T> p, int width){
        super((WIDTH-width)/2, (HEIGHT-MENU_HEIGHT)/3, width, MENU_HEIGHT);
        predicate = p;
        title = na;
    }
    
    
    @Override
    public void click(int mx, int my){
        if(withinBounds(mx, my)) open = !open;
        else if(x<=mx && mx<=x+width){
            int pos = y + height;
            for(Entry<T, Runnable> entry : map.entrySet()){
                if(pos<=my && my<pos+height){
                    selection = entry;
                    return;
                }else pos += height;
            }
        }
    }
    
    
    @Override
    public void paint(Graphics2D g){
        paintBox(g, x, y, PADDING);
        paintTriangle(g);
        paintName(g);
        if(selection!=null) paintText(g, selection.getKey().toString(), x, y, 
                width, height, TITLE_FONT, HIGHLIGHT_COLOR.brighter());
        if(open) paintOptions(g);
    }
    
    private void paintBox(Graphics2D g, int x, int y, int padding){
        g.setColor(UIPanel.BUTTON_COLOR);
        g.fill3DRect(x-padding/2, y-padding/2, width+padding, height+padding, true);
        g.setColor(UIPanel.UI_COLOR);
        g.fill3DRect(x+padding/2, y+padding/2, width-padding, height-padding, false);
    }
    
    private void paintTriangle(Graphics2D g){
        g.setColor(UIPanel.BUTTON_TEXT_COLOR);
        if(open) g.fillPolygon(
                new int[]{x+width-2*height/3, x+width-height/3, x+width-height/2},
                new int[]{y+height/2, y+height/2, y+2*height/3}, 3);
        else g.fillPolygon(
                new int[]{x+width-height/2, x+width-height/2, x+width-height*2/3},
                new int[]{y+height/3, y+2*height/3, y+height/2}, 3);
    }
    
    private void paintName(Graphics2D g){
        paintText(g, title, x, y-height, width, height, TITLE_FONT, TITLE_COLOR);
    }
    
    private void paintOptions(Graphics2D g){
        int pos = y+height;
        for(Entry<T, Runnable> entry : map.entrySet()){
            if(predicate.test(entry.getKey())){
                paintBox(g, x, pos, PADDING/2);
                paintText(g, entry.getKey().toString(), x, pos, width, height, 
                    BUTTON_TEXT_FONT, entry.equals(selection) ? HIGHLIGHT_COLOR : TEXT_COLOR);
                pos += height;
            }
        }
    }

}
