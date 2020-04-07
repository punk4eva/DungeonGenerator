
package gui.tools;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * A boolean toggle switch.
 * @author Adam Whittaker
 */
public class Toggle extends Button implements ValueInputBox{
    
    
    /**
     * TOGGLE_COLOR: The color of the toggle's "active" mode.
     */
    private static final Color TOGGLE_COLOR = new Color(165, 105, 0);
    
    
    /**
     * Creates a new instance.
     * @param x The x coordinate of the box.
     * @param y The y coordinate of the box.
     * @param w The width of the box.
     * @param h The height of the box.
     */
    public Toggle(int x, int y, int w, int h){
        super(x, y, w, h);
    }
    

    @Override
    public void testClick(int mx, int my){
        click(mx, my);
    }
    
    @Override
    public void click(int mx, int my){
        if(withinBounds(mx, my)) selected = !selected;
    }

    @Override
    public void paint(Graphics2D g){
        paintBox(g, x, y, PADDING);
        if(selected){
            g.setColor(TOGGLE_COLOR);
            g.fill3DRect(x+PADDING*3/2, y+PADDING*3/2, width-PADDING*3, 
                    width-PADDING*3, true);
        }
    }
    
    
    @Override
    public Boolean getValue(){
        return selected;
    }

}
