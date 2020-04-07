
package gui.tools;

import static gui.core.DungeonViewer.HEIGHT;
import static gui.core.DungeonViewer.WIDTH;
import static gui.tools.InputBox.MENU_HEIGHT;
import java.awt.Graphics2D;

/**
 * A button that navigates between different panels in the selection screen.
 * @author Adam Whittaker
 */
public abstract class NavigationButton extends Button{

    
    /**
     * name: The name of the button.
     */
    protected final String name;
    
    
    /**
     * Creates an instance.
     * @param x The x coordinate of the button
     * @param name The name of the button.
     */
    public NavigationButton(int x, String name){
        super(x, HEIGHT/5, WIDTH/8, MENU_HEIGHT);
        this.name = name;
    }
    

    @Override
    public void paint(Graphics2D g){
        paintButtonBox(g);
        paintText(g, name);
    }

}
