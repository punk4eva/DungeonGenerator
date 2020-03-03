
package gui.tools;

import static gui.core.DungeonViewer.HEIGHT;
import static gui.core.DungeonViewer.WIDTH;
import gui.userInterface.*;
import java.awt.Graphics2D;

/**
 *
 * @author Adam Whittaker
 */
public class ControlPanel extends UIPanel{

    
    public ControlPanel(GUI gui){
        super(0, new Button[8]);
        
        buttons[0] = new MinimizeButton(PANEL_WIDTH, 0, 32*WIDTH/1920, true);
        buttons[1] = genNewButton(gui, 0, 0, PANEL_WIDTH/3, PANEL_WIDTH/3);
        buttons[2] = genSaveButton(gui, PANEL_WIDTH/3, 0, PANEL_WIDTH/3, PANEL_WIDTH/3);
        buttons[3] = genLoadButton(gui, 2*PANEL_WIDTH/3, 0, PANEL_WIDTH/3, PANEL_WIDTH/3);
        
        int buffer = (HEIGHT-7*PANEL_WIDTH/3)/5;
        int y = PANEL_WIDTH/3 + buffer;
        buffer += PANEL_WIDTH/2;
        buttons[4] = genLookButton(gui, PANEL_WIDTH/4, y, PANEL_WIDTH/2, PANEL_WIDTH/2);
        y += buffer;
        buttons[5] = genSelectButton(gui, PANEL_WIDTH/4, y, PANEL_WIDTH/2, PANEL_WIDTH/2);
        y += buffer;
        buttons[6] = genRoomsButton(gui, PANEL_WIDTH/4, y, PANEL_WIDTH/2, PANEL_WIDTH/2);
        y += buffer;
        buttons[7] = genEffectButton(gui, PANEL_WIDTH/4, y, PANEL_WIDTH/2, PANEL_WIDTH/2);
    }
    
    
    private Button genButton(GUI gui, CalibrationPanel p, 
            String name, int x, int y, int w, int h){
        return new Button(x, y, w, h){
            
            @Override
            public void click(int mx, int my){
                gui.setCalibrationPanel(p);
            }

            @Override
            public void paint(Graphics2D g){
                paintButtonBox(g);
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
