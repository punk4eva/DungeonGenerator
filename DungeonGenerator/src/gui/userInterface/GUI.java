
package gui.userInterface;

import gui.core.DungeonViewer;
import static gui.core.Window.VIEWER;
import gui.tools.Button;
import gui.tools.CalibrationPanel;
import gui.tools.ControlPanel;
import java.awt.Graphics2D;

/**
 * The base control class for the GUI.
 * @author Adam Whittaker
 */
public class GUI{
    
    
    /**
     * control: The current control panel.
     * calibration: The current calibration panel.
     */
    private final ControlPanel control;
    private CalibrationPanel calibration;
    
    
    /**
     * Creates a new instance.
     * @param v The viewer.
     */
    public GUI(DungeonViewer v){
        control = new ControlPanel(this);
        v.addMouseListener(control);
    }
    
    
    /**
     * Renders the entire GUI.
     * @param g The graphics.
     */
    public void render(Graphics2D g){
        control.render(g);
        if(calibration != null) calibration.render(g);
    }
    
    /**
     * Sets the current calibration panel.
     * @param p the new panel.
     */
    public void setCalibrationPanel(CalibrationPanel p){
        if(calibration!=null) VIEWER.removeMouseListener(calibration);
        calibration = p;
        VIEWER.addMouseListener(calibration);
    }
    
    /**
     * Gets the calibration panel name for debugging purposes.
     * @return
     */
    public String getCalibrationPanelName(){
        return calibration == null ? "null" : calibration.getClass().getSimpleName();
    }
    
    /**
     * Gets all the buttons of the control panel for debug purposes.
     * @return
     */
    public Button[] getControlPanelButtons(){
        return control.getButtons();
    }
    
}
