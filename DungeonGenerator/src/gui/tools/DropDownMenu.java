
package gui.tools;

import gui.core.DungeonViewer;
import static gui.core.DungeonViewer.HEIGHT;
import static gui.core.DungeonViewer.WIDTH;
import gui.pages.SelectionScreen;
import gui.questions.QuestionBox;
import static gui.tools.UIPanel.BUTTON_TEXT_FONT;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import utils.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 * @param <T> The type of the drop down menu (must have a user-potable 
 * toString() method).
 */
@Unfinished("Might not need the predicate")
public abstract class DropDownMenu<T extends Object> extends QuestionBox implements KeyListener{
    
    
    protected final String title;
    protected final LinkedList<T> items = new LinkedList<>();
    
    protected T selection;
    protected boolean open = false;
    
    
    public DropDownMenu(String na, int width){
        super((WIDTH-width)/2, (HEIGHT-MENU_HEIGHT)/3, width, MENU_HEIGHT);
        title = na;
    }
    
    
    @Override
    public void click(int mx, int my){
        if(withinBounds(mx, my)) open = !open;
        else if(x<=mx && mx<=x+width){
            int pos = y + height;
            for(T item : items){
                if(pos<=my && my<pos+height){
                    selection = item;
                    return;
                }else pos += height;
            }
        }
    }
    
    
    @Override
    public void paint(Graphics2D g){
        paintBox(g, x, y, PADDING);
        paintTriangle(g);
        paintTitle(g);
        if(selection!=null) paintText(g, selection.toString(), x, y, 
                width, height, TITLE_FONT, HIGHLIGHT_COLOR.brighter());
        if(open) paintOptions(g);
    }
    
    protected void paintTriangle(Graphics2D g){
        g.setColor(UIPanel.BUTTON_TEXT_COLOR);
        if(open) g.fillPolygon(
                new int[]{x+width-2*height/3, x+width-height/3, x+width-height/2},
                new int[]{y+height/2, y+height/2, y+2*height/3}, 3);
        else g.fillPolygon(
                new int[]{x+width-height/2, x+width-height/2, x+width-height*2/3},
                new int[]{y+height/3, y+2*height/3, y+height/2}, 3);
    }
    
    protected void paintTitle(Graphics2D g){
        paintText(g, title, x, y-height, width, height, TITLE_FONT, TITLE_COLOR);
    }
    
    protected void paintOptions(Graphics2D g){
        int pos = y+height;
        for(T item : items){
            paintBox(g, x, pos, PADDING/2);
            paintText(g, item.toString(), x, pos, width, height, 
                BUTTON_TEXT_FONT, item.equals(selection) ? HIGHLIGHT_COLOR : TEXT_COLOR);
            pos += height;
        }
    }
    
    
    public T get(){
        return selection;
    }
    
    
    @Override
    public void process(SelectionScreen sc){
        sc.getInputCollector().collect(get());
    }

    @Override
    public void registerKeys(DungeonViewer v){
        v.addKeyListener(this);
    }

    @Override
    public void deregisterKeys(DungeonViewer v){
        v.removeKeyListener(this);
    }
    
    
    @Override
    public void keyTyped(KeyEvent e){}

    @Override
    public void keyPressed(KeyEvent e){}

    @Override
    public void keyReleased(KeyEvent e){}

}
