
package gui.tools;

/**
 * A button that can not be clicked repeatedly and must be reset to be used 
 * again.
 * @author Adam Whittaker
 */
public abstract class ResettableButton extends Button{

    
    /**
     * Creates a new instance.
     * @param x The x coordinate of the box.
     * @param y The y coordinate of the box.
     * @param w The width of the box.
     * @param h The height of the box.
     */
    public ResettableButton(int x, int y, int w, int h){
        super(x, y, w, h);
    }
    
    
    @Override
    public void testClick(int mx, int my){
        if(withinBounds(mx, my) && !selected){
            selected = true;
            click(mx, my);
        }
    }

}
