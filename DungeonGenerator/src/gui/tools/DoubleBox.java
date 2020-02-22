
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
    private final double defaultValue;
    

    public DoubleBox(int x, int y, int w, int h, double min, double max, 
            double defaultVal){
        super(x, y, w, h, min, max);
        defaultValue = defaultVal;
        setDefaultValue();
    }
    
    public DoubleBox(int x, int y, int w, int h, double min, double max){
        this(x, y, w, h, min, max, (min+max)/2D);
    }
    
    
    @Override
    protected final void setDefaultValue(){
        value = defaultValue;
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
