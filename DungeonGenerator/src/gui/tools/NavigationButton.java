
package gui.tools;

import static gui.core.DungeonViewer.HEIGHT;
import static gui.core.DungeonViewer.WIDTH;
import static gui.tools.InputBox.MENU_HEIGHT;
import java.awt.Graphics2D;

/**
 *
 * @author Adam Whittaker
 */
public abstract class NavigationButton extends Button{

    
    protected final String name;
    
    
    public NavigationButton(int _x, String n){
        super(_x, HEIGHT/5, WIDTH/8, MENU_HEIGHT);
        name = n;
    }
    

    @Override
    public void paint(Graphics2D g){
        paintButtonBox(g);
        paintText(g, name);
    }

}
