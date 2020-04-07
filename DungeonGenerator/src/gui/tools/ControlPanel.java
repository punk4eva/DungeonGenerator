
package gui.tools;

import static gui.core.DungeonViewer.HEIGHT;
import static gui.core.DungeonViewer.WIDTH;
import gui.userInterface.*;
import java.awt.Graphics2D;

/**
 * The panel with all the control buttons in it.
 * @author Adam Whittaker
 */
public class ControlPanel extends UIPanel{

    
    /**
     * Creates a new instance.
     * @param gui The GUI.
     */
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
    
    
    /**
     * Creates a new button to activate the given calibration panel.
     * @param gui The GUI
     * @param calPanel The calibration panel.
     * @param name The name of the button to display.
     * @param x The x of the top-left of the button.
     * @param y The y of the top-left of the button.
     * @param w The button's width.
     * @param h The button's height.
     * @return A button.
     */
    private Button genButton(GUI gui, CalibrationPanel calPanel, 
            String name, int x, int y, int w, int h){
        return new Button(x, y, w, h){
            
            @Override
            public void click(int mx, int my){
                gui.setCalibrationPanel(calPanel);
            }

            @Override
            public void paint(Graphics2D g){
                paintButtonBox(g);
                paintText(g, name);
            }
        
        };
    }
    
    
    /**
     * Creates the button to activate the "New" calibration panel.
     * @param gui The GUI
     * @param x The x of the top-left of the button.
     * @param y The y of the top-left of the button.
     * @param w The button's width.
     * @param h The button's height.
     * @return A button.
     */
    private Button genNewButton(GUI gui, int x, int y, int w, int h){
        return genButton(gui, new NewCalPanel(), "New", x, y, w, h);
    }
    
    /**
     * Creates the button to activate the "Save" calibration panel.
     * @param gui The GUI
     * @param x The x of the top-left of the button.
     * @param y The y of the top-left of the button.
     * @param w The button's width.
     * @param h The button's height.
     * @return A button.
     */
    private Button genSaveButton(GUI gui, int x, int y, int w, int h){
        return genButton(gui, new SaveCalPanel(), "Save", x, y, w, h);
    }
    
    /**
     * Creates the button to activate the "Load" calibration panel.
     * @param gui The GUI
     * @param x The x of the top-left of the button.
     * @param y The y of the top-left of the button.
     * @param w The button's width.
     * @param h The button's height.
     * @return A button.
     */
    private Button genLoadButton(GUI gui, int x, int y, int w, int h){
        return genButton(gui, new LoadCalPanel(), "Load", x, y, w, h);
    }
    
    /**
     * Creates the button to activate the "Look" calibration panel.
     * @param gui The GUI
     * @param x The x of the top-left of the button.
     * @param y The y of the top-left of the button.
     * @param w The button's width.
     * @param h The button's height.
     * @return A button.
     */
    private Button genLookButton(GUI gui, int x, int y, int w, int h){
        return genButton(gui, new LookCalPanel(), "Look", x, y, w, h);
    }
    
    /**
     * Creates the button to activate the "Select" calibration panel.
     * @param gui The GUI
     * @param x The x of the top-left of the button.
     * @param y The y of the top-left of the button.
     * @param w The button's width.
     * @param h The button's height.
     * @return A button.
     */
    private Button genSelectButton(GUI gui, int x, int y, int w, int h){
        return genButton(gui, new SelectCalPanel(), "Select", x, y, w, h);
    }
    
    /**
     * Creates the button to activate the "Rooms" calibration panel.
     * @param gui The GUI
     * @param x The x of the top-left of the button.
     * @param y The y of the top-left of the button.
     * @param w The button's width.
     * @param h The button's height.
     * @return A button.
     */
    private Button genRoomsButton(GUI gui, int x, int y, int w, int h){
        return genButton(gui, new RoomsCalPanel(), "Rooms", x, y, w, h);
    }
    
    /**
     * Creates the button to activate the "Effect" calibration panel.
     * @param gui The GUI
     * @param x The x of the top-left of the button.
     * @param y The y of the top-left of the button.
     * @param w The button's width.
     * @param h The button's height.
     * @return A button.
     */
    private Button genEffectButton(GUI gui, int x, int y, int w, int h){
        return genButton(gui, new EffectsCalPanel(), "Effects", x, y, w, h);
    }

}
