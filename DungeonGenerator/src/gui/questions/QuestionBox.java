
package gui.questions;

import gui.core.DungeonViewer;
import gui.pages.SelectionScreen;
import gui.tools.InputBox;

/**
 * A question to give to the user.
 * @author Adam Whittaker
 */
public abstract class QuestionBox extends InputBox{

    
    /**
     * Creates an instance.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param w The width.
     * @param h The height.
     */
    public QuestionBox(int x, int y, int w, int h){
        super(x, y, w, h);
    }
    
    
    /**
     * Does something with the information acquired from the user.
     * @param sc The selection screen.
     */
    public abstract void process(SelectionScreen sc);
    
    /**
     * Registers a key listener (if one is needed) with the dungeon viewer.
     * @param v The viewer.
     */
    public abstract void registerKeys(DungeonViewer v);
    
    /**
     * De-registers a key listener (if one was used) from the dungeon viewer.
     * @param v The viewer.
     */
    public abstract void deregisterKeys(DungeonViewer v);

}
