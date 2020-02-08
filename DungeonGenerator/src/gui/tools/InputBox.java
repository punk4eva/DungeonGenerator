
package gui.tools;

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
    
    protected boolean withinBounds(int mx, int my){
        return withinBounds(x, y, width, height, mx, my);
    }
    
}
