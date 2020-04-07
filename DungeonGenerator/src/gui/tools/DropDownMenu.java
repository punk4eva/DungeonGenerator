
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
 *A menu for selecting from multiple choices.
 * @author Adam Whittaker
 * @param <T> The type of the drop down menu (must have a user-potable 
 * toString() method).
 */
@Unfinished("Might not need the predicate")
public abstract class DropDownMenu<T extends Object> extends QuestionBox implements KeyListener{
    
    
    /**
     * title: The user friendly text to display above the menu.
     * items: The items to choose from.
     * selection: The currently selected item.
     * selectionNum: The index of the selected item within the list.
     * open: Whether the drop-down menu is in the expanded state.
     */
    protected final String title;
    protected final LinkedList<T> items = new LinkedList<>();
    
    protected T selection;
    protected int selectionNum = 0;
    protected boolean open = false;
    
    
    /**
     * Creates a new instance.
     * @param na The title.
     * @param width The width.
     */
    public DropDownMenu(String na, int width){
        super((WIDTH-width)/2, (HEIGHT-MENU_HEIGHT)/3, width, MENU_HEIGHT);
        title = na;
    }
    
    
    @Override
    public void click(int mx, int my){
        //Iterates through each item to see if it was clicked.
        if(withinBounds(mx, my)) open = !open;
        else if(x<=mx && mx<=x+width){
            int pos = y + height;
            for(int n=0;n<items.size();n++){
                if(pos<=my && my<pos+height){
                    setSelection(n);
                    return;
                }else pos += height;
            }
        }
    }
    
    
    @Override
    public void paint(Graphics2D g){
        //Paints the box
        paintBox(g, x, y, PADDING);
        //Paints the minimization triangle
        paintTriangle(g);
        //Paints the title.
        paintTitle(g);
        //Paints the name of the currently selected item.
        if(selection!=null) paintText(g, selection.toString(), x, y, 
                width, height, TITLE_FONT, HIGHLIGHT_COLOR.brighter());
        //Paints the options available.
        if(open) paintOptions(g);
    }
    
    /**
     * Paints the minimization triangle.
     * @param g The graphics.
     */
    protected void paintTriangle(Graphics2D g){
        g.setColor(UIPanel.BUTTON_TEXT_COLOR);
        //Decides which direction the triangle will face.
        if(open) g.fillPolygon(
                new int[]{x+width-2*height/3, x+width-height/3, x+width-height/2},
                new int[]{y+height/2, y+height/2, y+2*height/3}, 3);
        else g.fillPolygon(
                new int[]{x+width-height/2, x+width-height/2, x+width-height*2/3},
                new int[]{y+height/3, y+2*height/3, y+height/2}, 3);
    }
    
    /**
     * Paints the title.
     * @param g The graphics.
     */
    protected void paintTitle(Graphics2D g){
        paintText(g, title, x, y-height, width, height, TITLE_FONT, TITLE_COLOR);
    }
    
    /**
     * Paints the currently available item options.
     * @param g The graphics.
     */
    protected void paintOptions(Graphics2D g){
        int pos = y+height;
        for(T item : items){
            paintBox(g, x, pos, PADDING/2);
            paintText(g, item.toString(), x, pos, width, height, 
                BUTTON_TEXT_FONT, item.equals(selection) ? HIGHLIGHT_COLOR : TEXT_COLOR);
            pos += height;
        }
    }
    
    /**
     * Sets the item currently selected.
     * @param n The index of that item.
     */
    protected void setSelection(int n){
        selectionNum = n;
        selection = items.get(n);
    }
    
    
    /**
     * Retrieves the currently selected item.
     * @return
     */
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
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_UP) 
            setSelection((selectionNum + items.size() - 1) % items.size());
        else if(e.getKeyCode() == KeyEvent.VK_DOWN)
            setSelection((selectionNum + 1) % items.size());
    }

    @Override
    public void keyReleased(KeyEvent e){}

}
