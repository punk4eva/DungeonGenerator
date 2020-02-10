
package gui.tools;

import static gui.core.DungeonViewer.HEIGHT;
import static gui.tools.UIPanel.BUTTON_TEXT_COLOR;
import static gui.tools.UIPanel.BUTTON_TEXT_FONT;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

/**
 *
 * @author Adam Whittaker
 */
public abstract class InputBox{
    
    
    protected static final int MENU_HEIGHT = HEIGHT*64/1080;
    protected static final int PADDING = 6;
    protected static final Color TITLE_COLOR = Color.YELLOW,
            HIGHLIGHT_COLOR = new Color(98, 96, 147),
            TEXT_COLOR = Color.YELLOW.darker();
    protected static final Font TITLE_FONT = new Font(Font.MONOSPACED, Font.BOLD, 36);
    
    
    protected int x, y, width, height;
    
    
    public InputBox(int _x, int _y, int w, int h){
        x = _x;
        y = _y;
        width = w;
        height = h;
    }
    
    
    public abstract void click(int mx, int my);
    
    public abstract void paint(Graphics2D g);
    
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    protected void setXY(int _x, int _y){
        x = _x;
        y = _y;
    }
    
    protected void resize(int w, int h){
        width = w;
        height = h;
    }
    
    protected void paintBox(Graphics2D g, int x, int y, int padding){
        g.setColor(UIPanel.BUTTON_COLOR);
        g.fill3DRect(x-padding/2, y-padding/2, width+padding, height+padding, true);
        g.setColor(UIPanel.UI_COLOR);
        g.fill3DRect(x+padding/2, y+padding/2, width-padding, height-padding, false);
    }
    
    protected void paintText(Graphics2D g, String str){
        paintText(g, str, x, y, width, height, 
                BUTTON_TEXT_FONT, BUTTON_TEXT_COLOR);
    }
    
    protected void paintText(Graphics2D g, String str, int x, int y, 
            int width, int height, Font font, Color col){
        g.setFont(font);
        g.setColor(col);
        FontMetrics f = g.getFontMetrics();
        g.drawString(str, x+(width - f.stringWidth(str))/2, y + height/2 + f.getDescent());
    }
    
    protected boolean withinBounds(int x, int y, int width, int height, int mx, int my){
        return x<mx && mx<x+width && y<my && my<y+height;
    }
    
    public boolean withinBounds(int mx, int my){
        return withinBounds(x, y, width, height, mx, my);
    }
    
}
