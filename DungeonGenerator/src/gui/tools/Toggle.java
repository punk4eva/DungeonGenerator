
package gui.tools;

import java.awt.Graphics2D;

/**
 *
 * @author Adam Whittaker
 */
public class Toggle extends InputBox{

    
    private boolean active;
    
    
    public Toggle(int _x, int _y, int w, int h){
        super(_x, _y, w, h);
    }
    

    @Override
    public void click(int mx, int my){
        if(withinBounds(mx, my)) active = !active;
    }

    @Override
    public void paint(Graphics2D g){
        paintBox(g, x, y, PADDING);
        if(active){
            g.setColor(TITLE_COLOR);
            g.fill3DRect(x+PADDING*3/2, y+PADDING*3/2, width-PADDING*3, 
                    width-PADDING*3, true);
        }
    }
    
    
    @Override
    public Boolean getValue(){
        return active;
    }

}
