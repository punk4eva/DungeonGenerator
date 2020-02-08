
package gui.tools.assets;

import static gui.core.DungeonViewer.WIDTH;
import gui.pages.SelectionScreen;

/**
 *
 * @author Adam Whittaker
 */
public class MazeSpecifier extends CorridorSpecifier{

    public MazeSpecifier(SelectionScreen screen){
        super("Maze Builder", "Design the maze", WIDTH/3);
    }

}
