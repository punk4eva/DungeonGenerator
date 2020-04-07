
package gui.tools;

import static gui.core.DungeonViewer.WIDTH;
import java.util.Arrays;

/**
 * The panel providing fine tuning options for the currently selected control
 * option.
 * @author Adam Whittaker
 */
public class CalibrationPanel extends UIPanel{

    
    /**
     * Creates a new instance.
     * @param butt The buttons of the calibration panel.
     */
    public CalibrationPanel(Button[] butt){
        super(WIDTH-PANEL_WIDTH, Arrays.copyOf(butt, butt.length+1));
        buttons[buttons.length-1] = new MinimizeButton(WIDTH-PANEL_WIDTH-32, 
                0, 32*WIDTH/1920, false);
    }

}
