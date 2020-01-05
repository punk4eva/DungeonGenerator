
package gui.tools;

import animation.Animation;
import static gui.core.DungeonViewer.ANIMATOR;
import static gui.tools.UIPanel.BUTTON_TEXT_COLOR;
import static gui.tools.UIPanel.BUTTON_TEXT_FONT;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author Adam Whittaker
 */
public abstract class Button{
    
    
    private static final int BUTTON_DURATION = 50;
    
    protected int x, y, width, height;
    private boolean selected = false; //for aesthetic purposes only.
    
    
    public Button(int _x, int _y, int w, int h){
        x = _x;
        y = _y;
        width = w;
        height = h;
    }
    
    
    /**
     * Renders the background of this Button. Assumes that the color has already
     * been set.
     * @param g
     */
    public void render(Graphics g){
        g.setColor(UIPanel.BUTTON_COLOR);
        g.fill3DRect(x, y, width, height, !selected);
        paint(g);
    }
    
    /**
     * Checks if the mouse coordinates would have clicked the button, and runs
     * the click function if it was.
     * @param mx the mouse x
     * @param my the mouse y
     */
    public void testClick(int mx, int my){
        if(x<mx && mx<x+width && y<my && my<y+height){
            selected = true;
            registerClickAnimation();
            click();
        }
    }
    
    public abstract void click();
    
    public abstract void paint(Graphics g);
    
    public void setSelected(boolean s){
        selected = s;
    }
    
    private void registerClickAnimation(){
        ANIMATOR.add(new Animation(){
            
            private int duration = BUTTON_DURATION;
            
            @Override
            public void animate(Graphics2D g, int focusX, int focusY, int frames){
                duration -= frames;
                if(duration<=0){
                    setSelected(false);
                    done = true;
                }
            }
            
        });
    }
    
    protected void setXY(int _x, int _y){
        x = _x;
        y = _y;
    }
    
    protected void resize(int w, int h){
        width = w;
        height = h;
    }
    
    protected void paintText(Graphics g, String str){
        g.setFont(BUTTON_TEXT_FONT);
        g.setColor(BUTTON_TEXT_COLOR);
        FontMetrics f = g.getFontMetrics();
        g.drawString(str, x+(width - f.stringWidth(str))/2, y + width/2 + f.getDescent());
    }
    
}
