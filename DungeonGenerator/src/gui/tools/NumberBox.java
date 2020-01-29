
package gui.tools;

import static gui.tools.UIPanel.BUTTON_COLOR;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static java.lang.Character.isDigit;

/**
 *
 * @author Adam Whittaker
 */
public class NumberBox extends InputBox implements KeyListener{
    
    
    private static final int BORDER = 4, PRECISION = 6;
    
    private final double minimum, maximum;
    private double value;
    private boolean typing = false;
    private String string;
    
    
    public NumberBox(int x, int y, int w, int h, double min, double max){
        super(x, y, w, h);
        minimum = min;
        maximum = max;
        setDefaultValue();
    }
    
    
    @Override
    public void keyTyped(KeyEvent ke){
        if(typing && string.length() < PRECISION){
            char c = ke.getKeyChar();
            if(isDigit(c) || c == '.') string += c;
        }
    }

    @Override
    public void click(int mx, int my){
        if(withinBounds(mx, my)){
            typing = true;
        }else{
            if(typing) validate();
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
    
    private void validate(){
        while(string.endsWith(".")) 
            string = string.substring(0, string.length()-1);
        while(string.startsWith("."))
            string = string.substring(1);
        if(string.isEmpty()) setDefaultValue();
        else value = Double.parseDouble(string);
    }
    
    private void setDefaultValue(){
        value = (maximum+minimum)/2D;
        string = ("" + value).substring(0, PRECISION);
    }
    
    
    @Override
    public void keyPressed(KeyEvent e){/*Ignore*/}

    @Override
    public void keyReleased(KeyEvent e){/*Ignore*/}

}
