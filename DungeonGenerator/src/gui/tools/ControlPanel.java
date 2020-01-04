
package gui.tools;

/**
 *
 * @author Adam Whittaker
 */
public class ControlPanel extends UIPanel{

    
    public ControlPanel(){
        super(0, new Button[]{null});
        buttons[0] = new MinimizeButton(width, 0, 32, true);
    }

}
