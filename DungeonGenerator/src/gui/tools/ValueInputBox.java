
package gui.tools;

import java.awt.Graphics2D;

/**
 *
 * @author Adam Whittaker
 */
public interface ValueInputBox{
    
    
    public abstract Object getValue();
    
    public abstract void click(int mx, int my);
    
    public abstract void paint(Graphics2D g);
    
    public abstract int getY();
    
}
