
package gui.tools;

import static gui.core.DungeonViewer.WIDTH;
import java.util.Arrays;

/**
 *
 * @author Adam Whittaker
 */
public class CalibrationPanel extends UIPanel{

    
    public CalibrationPanel(Button[] butt){
        super(WIDTH-PANEL_WIDTH/*-VIEWER.getRightLostSpace()*/, Arrays.copyOf(butt, butt.length+1));
        buttons[buttons.length-1] = new MinimizeButton(WIDTH-PANEL_WIDTH-32, 0, 32*WIDTH/1920, false);
    }

}
