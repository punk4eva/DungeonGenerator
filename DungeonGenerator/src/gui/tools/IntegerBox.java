
package gui.tools;

import static gui.tools.NumberBox.PRECISION;
import static java.lang.Character.isDigit;
import static java.lang.Math.min;
import static utils.Utils.isInteger;

/**
 *
 * @author Adam Whittaker
 */
public class IntegerBox extends NumberBox{
    
    
    private int value;
    

    public IntegerBox(int x, int y, int w, int h, double min, double max){
        super(x, y, w, h, min, max);
        setDefaultValue();
    }
    
    
    @Override
    protected final void setDefaultValue(){
        value = (int)((maximum+minimum)/2D);
        String str = "" + value;
        string = str.substring(0, min(PRECISION, str.length()));
    }
    
    @Override
    protected void validate(){
        if(!isInteger(string)) setDefaultValue();
        else value = putWithinBounds(Integer.parseInt(string));
        string = (""+value);
    }
    
    private int putWithinBounds(int d){
        if(d>maximum) return (int)maximum;
        else if(d<minimum) return (int)minimum;
        else return d;
    }

    @Override
    protected void type(char c){
        if(isDigit(c) || c == '-') string += c;
    }
    
    @Override
    public Integer getValue(){
        return value;
    }
}