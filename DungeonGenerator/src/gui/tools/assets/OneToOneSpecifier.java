
package gui.tools.assets;

import static gui.core.DungeonViewer.WIDTH;
import gui.pages.SelectionScreen;

/**
 *
 * @author Adam Whittaker
 */
public class OneToOneSpecifier extends CorridorSpecifier{

    public OneToOneSpecifier(SelectionScreen screen){
        super("One to One Corridor Builder", "Design the corridors", WIDTH/3);
    }

}
