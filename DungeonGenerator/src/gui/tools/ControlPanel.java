
package gui.tools;

import static gui.core.DungeonViewer.HEIGHT;
import gui.userInterface.*;
import java.awt.Graphics;

/**
 *
 * @author Adam Whittaker
 */
public class ControlPanel extends UIPanel{

    
    public ControlPanel(GUI gui){
        super(0, new Button[8]);
        
        buttons[0] = new MinimizeButton(width, 0, 32, true);
        buttons[1] = genNewButton(gui, 0, 0, width/3, width/3);
        buttons[2] = genSaveButton(gui, width/3, 0, width/3, width/3);
        buttons[3] = genLoadButton(gui, 2*width/3, 0, width/3, width/3);
        
        int buffer = (HEIGHT-7*width/3)/5;
        int y = width/3 + buffer;
        buffer += width/2;
        buttons[4] = genLookButton(gui, width/4, y, width/2, width/2);
        y += buffer;
        buttons[5] = genSelectButton(gui, width/4, y, width/2, width/2);
        y += buffer;
        buttons[6] = genRoomsButton(gui, width/4, y, width/2, width/2);
        y += buffer;
        buttons[7] = genEffectButton(gui, width/4, y, width/2, width/2);
    }
    
    
    private Button genButton(GUI gui, CalibrationPanel p, 
            String name, int x, int y, int w, int h){
        return new Button(x, y, w, h){
            
            @Override
            public void click(){
                gui.setCalibrationPanel(p);
            }

            @Override
            public void paint(Graphics g){
                paintText(g, name);
            }
        
        };
    }
    
    
    private Button genNewButton(GUI gui, int x, int y, int w, int h){
        return genButton(gui, new NewCalPanel(), "New", x, y, w, h);
    }
    
    private Button genSaveButton(GUI gui, int x, int y, int w, int h){
        return genButton(gui, new SaveCalPanel(), "Save", x, y, w, h);
    }
    
    private Button genLoadButton(GUI gui, int x, int y, int w, int h){
        return genButton(gui, new LoadCalPanel(), "Load", x, y, w, h);
    }
    
    private Button genLookButton(GUI gui, int x, int y, int w, int h){
        return genButton(gui, new LookCalPanel(), "Look", x, y, w, h);
    }
    
    private Button genSelectButton(GUI gui, int x, int y, int w, int h){
        return genButton(gui, new SelectCalPanel(), "Select", x, y, w, h);
    }
    
    private Button genRoomsButton(GUI gui, int x, int y, int w, int h){
        return genButton(gui, new RoomsCalPanel(), "Rooms", x, y, w, h);
    }
    
    private Button genEffectButton(GUI gui, int x, int y, int w, int h){
        return genButton(gui, new EffectsCalPanel(), "Effects", x, y, w, h);
    }

}
