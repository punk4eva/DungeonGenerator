
package gui.pages;

import gui.core.DungeonViewer;
import gui.core.DungeonViewer.State;
import gui.core.Window;
import gui.tools.InputBox;
import gui.tools.InputCollector;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

/**
 *
 * @author Adam Whittaker
 */
public class SelectionScreen extends MouseAdapter implements Screen{
    
    
    private InputBox input;
    private final InputCollector collector = new InputCollector();
    private final LinkedList<InputBox> boxList = new LinkedList<>();
    
    
    public SelectionScreen(DungeonViewer v){
        v.addMouseListener(this);
    }

    
    @Override
    public void paint(Graphics2D g, int frames){
        g.setColor(DungeonViewer.BACKGROUND_COLOR);
        input.paint(g);
    }
    
    public final void setInputBox(InputBox inp){
        input = inp;
    }
    
    public final InputCollector getInputCollector(){
        return collector;
    }
    
    
    @Override
    public void mouseClicked(MouseEvent me){
        if(Window.VIEWER.getState().equals(State.CHOOSING))
            input.click(me.getX(), me.getY());
    }

}
