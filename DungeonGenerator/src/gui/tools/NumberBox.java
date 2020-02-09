
package gui.tools;

import static gui.tools.UIPanel.BUTTON_COLOR;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Adam Whittaker
 */
public abstract class NumberBox extends InputBox implements KeyListener{
    
    
    protected static final int BORDER = 4, PRECISION = 6;
    
    protected final double minimum, maximum;
    private boolean typing = false;
    protected String string;
    
    
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
        }else if(typing){
            validate();
            typing = false;
        }
    }

    @Override
    public void paint(Graphics2D g){
        if(typing) g.setColor(Color.BLUE);
        else g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);
        g.setColor(BUTTON_COLOR);
        g.fillRect(x+BORDER, y+BORDER, width-2*BORDER, height-2*BORDER);
        paintText(g, string);
    }
    
    protected abstract void validate();    
    
    protected abstract void setDefaultValue();
    
    protected abstract void type(char c);
    
    
    @Override
    public void keyPressed(KeyEvent e){
        if(typing && e.getKeyCode()==KeyEvent.VK_BACK_SPACE && !string.isEmpty())
            string = string.substring(0, string.length()-1);
    }

    @Override
    public void keyReleased(KeyEvent e){/*Ignore*/}

}
