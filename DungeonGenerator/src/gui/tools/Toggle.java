
package gui.tools;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Adam Whittaker
 */
public class Toggle extends Button implements ValueInputBox{
    
    
    private static final Color TOGGLE_COLOR = new Color(165, 105, 0);
    
    
    public Toggle(int _x, int _y, int w, int h){
        super(_x, _y, w, h);
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
