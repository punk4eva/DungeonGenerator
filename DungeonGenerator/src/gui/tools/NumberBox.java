
package gui.tools;

import static gui.tools.UIPanel.BUTTON_COLOR;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * A question box to get numerical input.
 * @author Adam Whittaker
 */
public abstract class NumberBox extends InputBox 
        implements KeyListener, ValueInputBox{
    
    
    /**
     * BORDER: The number of pixels in the box's border.
     * PRECISION: The number of digits the number can have.
     * minimum, maximum: The range of the data.
     * string: The string representation of the numerical value.
     */
    protected static final int BORDER = 4, PRECISION = 6;
    
    protected final double minimum, maximum;
    private boolean typing = false;
    protected String string;
    
    
    /**
     * Creates a new instance.
     * @param x The x coordinate of the box.
     * @param y The y coordinate of the box.
     * @param w The width of the box.
     * @param h The height of the box.
     * @param min The minimum value the box can take.
     * @param max The maximum value the box can take.
     */
    public NumberBox(int x, int y, int w, int h, double min, double max){
        super(x, y, w, h);
        minimum = min;
        maximum = max;
    }
    
    
    @Override
    public void keyTyped(KeyEvent ke){
        if(typing && string.length() < PRECISION)
            type(ke.getKeyChar());
    }

    @Override
    public void click(int mx, int my){
        if(withinBounds(mx, my)){
            typing = true;
            string = "";
        }else if(typing){
            validate();
            typing = false;
        }
    }

    @Override
    public void paint(Graphics2D g){
        //Highlights the box if needed.
        if(typing) g.setColor(Color.BLUE);
        else g.setColor(Color.BLACK);
        //Paints the button and the text.
        g.fillRect(x, y, width, height);
        g.setColor(BUTTON_COLOR);
        g.fillRect(x+BORDER, y+BORDER, width-2*BORDER, height-2*BORDER);
        paintText(g, string);
    }
    
    /**
     * Ensures the value given by the user is valid and within the range.
     */
    protected abstract void validate();    
    
    /**
     * Sets the value of the box to the default value stored.
     */
    protected abstract void setToDefaultValue();
    
    /**
     * Types a character into the box.
     * @param c The character.
     */
    protected abstract void type(char c);
    
    
    @Override
    public void keyPressed(KeyEvent e){
        if(typing && e.getKeyCode()==KeyEvent.VK_BACK_SPACE && !string.isEmpty())
            string = string.substring(0, string.length()-1);
    }

    @Override
    public void keyReleased(KeyEvent e){/*Ignore*/}

}
