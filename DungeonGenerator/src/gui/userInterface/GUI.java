
package gui.userInterface;

import gui.core.DungeonViewer;
import static gui.core.Window.VIEWER;
import gui.tools.CalibrationPanel;
import gui.tools.ControlPanel;
import java.awt.Graphics;

/**
 *
 * @author Adam Whittaker
 */
public class GUI{
    
    
    private final ControlPanel control = new ControlPanel();
    private CalibrationPanel calibration;
    
    
    public GUI(DungeonViewer v){
        v.addMouseListener(control);
    }
    
    
    public void setCalibration(CalibrationPanel p){
        calibration = p;
    }
    
    
    public void render(Graphics g){
        control.render(g);
        //calibration.render(g);
    }
    
    public void setCalibrationPanel(CalibrationPanel p){
        if(calibration!=null) VIEWER.removeMouseListener(calibration);
        calibration = p;
        VIEWER.addMouseListener(calibration);
    }
    
}
