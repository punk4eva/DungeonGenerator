
package gui.tools;

import static java.lang.Character.isDigit;
import static java.lang.Math.min;
import static utils.Utils.isDouble;

/**
 * An input box for numerical input to double precision.
 * @author Adam Whittaker
 */
public class DoubleBox extends NumberBox{
    
    
    /**
     * value: The value stored.
     * defaultValue: The value to default to in case the user inputted an
     * erroneous value.
     */
    private double value;
    private final double defaultValue;
    
    
    /**
     * Creates a new instance.
     * @param x The x coordinate of the box.
     * @param y The y coordinate of the box.
     * @param w The width of the box.
     * @param h The height of the box.
     * @param min The minimum value the box can take.
     * @param max The maximum value the box can take.
     * @param defaultVal The default value the box can take.
     */
    public DoubleBox(int x, int y, int w, int h, double min, double max, 
            double defaultVal){
        super(x, y, w, h, min, max);
        defaultValue = defaultVal;
        setToDefaultValue();
    }
    
    /**
     * Creates a new instance by setting the default value to the average of the
     * max and min.
     * @param x The x coordinate of the box.
     * @param y The y coordinate of the box.
     * @param w The width of the box.
     * @param h The height of the box.
     * @param min The minimum value the box can take.
     * @param max The maximum value the box can take.
     */
    public DoubleBox(int x, int y, int w, int h, double min, double max){
        this(x, y, w, h, min, max, (min+max)/2D);
    }
    
    
    @Override
    protected final void setToDefaultValue(){
        value = defaultValue;
        String str = "" + value;
        string = str.substring(0, min(PRECISION, str.length()));
    }
    
    @Override
    protected void validate(){
        if(!isDouble(string)) setToDefaultValue();
        else value = getWithinBounds(Double.parseDouble(string));
        string = (""+value);
    }
    
    /**
     * Gets a version of the double within the range of this box.
     * @param d The input double.
     * @return The closest possible value to the input within the range.
     */
    private double getWithinBounds(double d){
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
