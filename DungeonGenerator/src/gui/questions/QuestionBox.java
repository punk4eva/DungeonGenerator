
package gui.questions;

import gui.core.DungeonViewer;
import gui.pages.SelectionScreen;
import gui.tools.InputBox;

/**
 *
 * @author Adam Whittaker
 */
public abstract class QuestionBox extends InputBox{

    
    public QuestionBox(int _x, int _y, int w, int h){
        super(_x, _y, w, h);
    }
    
    
    public abstract void process(SelectionScreen sc);
    
    public abstract void registerKeys(DungeonViewer v);
    
    public abstract void deregisterKeys(DungeonViewer v);

}
