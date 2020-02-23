
package generation;

import gui.questions.CorridorMenu;

/**
 * Marks an algorithm that generates corridors after a different algorithm has
 * placed Rooms into the Area.
 * @author Adam Whittaker
 */
public interface PostCorridorPlacer extends Algorithm{

    public final static CorridorMenu CORRIDOR_MENU = new CorridorMenu();
    
}
