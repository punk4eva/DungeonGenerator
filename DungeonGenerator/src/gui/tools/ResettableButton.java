
package gui.tools;

/**
 *
 * @author Adam Whittaker
 */
public abstract class ResettableButton extends Button{

    
    public ResettableButton(int _x, int _y, int w, int h){
        super(_x, _y, w, h);
    }
    
    
    @Override
    public void testClick(int mx, int my){
        if(withinBounds(mx, my) && !selected){
            selected = true;
            click(mx, my);
        }
    }

}
