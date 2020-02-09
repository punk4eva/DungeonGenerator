
package gui.tools;

import static java.lang.Character.isDigit;
import static java.lang.Math.min;
import static utils.Utils.isDouble;

/**
 *
 * @author Adam Whittaker
 */
public class DoubleBox extends NumberBox{
    
    
    private double value;
    

    public DoubleBox(int x, int y, int w, int h, double min, double max){
        super(x, y, w, h, min, max);
        setDefaultValue();
    }
    
    
    @Override
    protected final void setDefaultValue(){
        value = (maximum+minimum)/2D;
        String str = "" + value;
        string = str.substring(0, min(PRECISION, str.length()));
    }
    
    @Override
    protected void validate(){
        if(!isDouble(string)) setDefaultValue();
        else value = putWithinBounds(Double.parseDouble(string));
        string = (""+value);
    }
    
    private double putWithinBounds(double d){
        if(d>maximum) return maximum;
        else if(d<minimum) return minimum;
        else return d;
    }

    @Override
    protected void type(char c){
        if(isDigit(c) || c == '.' || c == '-') string += c;
    }
    
    @Override
    public Double getValue(){
        return value;
    }

}
