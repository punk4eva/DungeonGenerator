
package gui.userInterface;

import gui.core.DungeonViewer;
import static gui.core.Window.VIEWER;
import gui.tools.CalibrationPanel;
import gui.tools.ControlPanel;
import java.awt.Graphics2D;

/**
 *
 * @author Adam Whittaker
 */
public class GUI{
    
    
    private final ControlPanel control;
    private CalibrationPanel calibration;
    
    
    public GUI(DungeonViewer v){
        control = new ControlPanel(this);
        v.addMouseListener(control);
    }
    
    
    public void render(Graphics2D g){
        control.render(g);
        //calibration.render(g);
    }
    
    public void setCalibrationPanel(CalibrationPanel p){
        if(calibration!=null) VIEWER.removeMouseListener(calibration);
        calibration = p;
        VIEWER.addMouseListener(calibration);
    }
    
    public String getCalibrationPanelName(){
        return calibration == null ? "null" : calibration.getClass().getSimpleName();
    }
    
}
