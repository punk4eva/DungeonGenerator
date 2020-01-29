
package gui.tools;

import static gui.tools.UIPanel.BUTTON_TEXT_COLOR;
import static gui.tools.UIPanel.BUTTON_TEXT_FONT;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

/**
 *
 * @author Adam Whittaker
 */
public abstract class InputBox{
    
    
    protected int x, y, width, height;
    
    
    public InputBox(int _x, int _y, int w, int h){
        x = _x;
        y = _y;
        width = w;
        height = h;
    }
    
    
    public abstract void click(int mx, int my);
    
    public abstract void paint(Graphics2D g);
    
    
    protected void setXY(int _x, int _y){
        x = _x;
        y = _y;
    }
    
    protected void resize(int w, int h){
        width = w;
        height = h;
    }
    
    protected void paintText(Graphics2D g, String str){
        g.setFont(BUTTON_TEXT_FONT);
        g.setColor(BUTTON_TEXT_COLOR);
        FontMetrics f = g.getFontMetrics();
        g.drawString(str, x+(width - f.stringWidth(str))/2, y + width/2 + f.getDescent());
    }
    
    protected boolean withinBounds(int mx, int my){
        return x<mx && mx<x+width && y<my && my<y+height;
    }
    
}
