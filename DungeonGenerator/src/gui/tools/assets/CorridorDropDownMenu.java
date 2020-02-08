
package gui.tools.assets;

import static gui.core.DungeonViewer.WIDTH;
import gui.pages.SelectionScreen;
import gui.tools.DropDownMenu;
import java.util.function.Predicate;

/**
 *
 * @author Adam Whittaker
 */
public class CorridorDropDownMenu extends DropDownMenu<CorridorSpecifier>{

    
    public CorridorDropDownMenu(Predicate<CorridorSpecifier> p, 
            SelectionScreen screen){
        super("Select corridor generation algorithm", p, WIDTH/3);
        
        CorridorSpecifier burrow = new BurrowCaveSpecifier(screen),
                conway = new ConwayCaveSpecifier(screen),
                maze = new MazeSpecifier(screen),
                otoc = new OneToOneSpecifier(screen),
                radial = new RadialCaveSpecifier(screen),
                spider = new SpiderSpecifier(screen),
                wormhole = new WormholeSpecifier(screen);
        
        map.put(burrow, () -> screen.setInputBox(burrow));
        map.put(conway, () -> screen.setInputBox(conway));
        map.put(maze, () -> screen.setInputBox(maze));
        map.put(otoc, () -> screen.setInputBox(otoc));
        map.put(radial, () -> screen.setInputBox(radial));
        map.put(spider, () -> screen.setInputBox(spider));
        map.put(wormhole, () -> screen.setInputBox(wormhole));
    }

}
