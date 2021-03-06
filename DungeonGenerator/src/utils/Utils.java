
package utils;

import java.awt.AWTException;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.Character.isDigit;
import static java.lang.Character.isUpperCase;
import static java.lang.Character.toLowerCase;
import static java.lang.Character.toUpperCase;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Random;
import static textureGeneration.Texture.rgbPixelEquals;
import utils.test.AutoClicker;
import utils.test.PerformanceLog;
import utils.test.SpeedTester;

/**
 * A static library of miscellaneous functions, utilities and a main method for 
 * debugging.
 * @author Adam Whittaker
 */
public final class Utils{

    
    /**
     * Prevents this static class from being instantiated erroneously.
     */
    private Utils(){}
    
    
    /**
     * R: The randomizer for the project.
     * PERFORMANCE_LOG: The crash and performance logger.
     * SPEED_TESTER: The speed tester.
     * ROBOT: An entity which can perform automatic clicks at high-speed.
     */
    public static final Random R = new Random();
    public static PerformanceLog PERFORMANCE_LOG;
    public final static SpeedTester SPEED_TESTER = new SpeedTester();
    public static AutoClicker ROBOT;
    static{
        //Initializes the performance logs and auto-clicker.
        try{
            PERFORMANCE_LOG = new PerformanceLog();
        }catch(FileNotFoundException e){
            System.err.println("PrintStream failed.");
        }
        try{
            ROBOT = new AutoClicker();
        }catch(AWTException e){
            System.err.println("Robot failed.");
        }
    }
    
    
    /**
     * A marker annotation for unfinished methods/classes.
     */
    public static @interface Unfinished{
        String value() default "";
    }
    
    /**
     * An annotation to clarify which thread is being used for a method.
     */
    public static @interface ThreadUsed{
        String value() default "";
    }
    
    /**
     * An annotation to keep track of when each method has been tested.
     */
    public static @interface Tested{
        String value() default ""; //Store the date of current testing.
    }
    
    
    /**
     * Returns a random element from the given array.
     * @param <T> The type of the array.
     * @param ary The array.
     * @return
     */
    public static <T> T getRandomElement(T[] ary){
        return ary[R.nextInt(ary.length)];
    }
    
    /**
     * Prints an integer array.
     * @param ary
     */
    @Tested("12/11/2019")
    public static void printArray(int[] ary){
        System.out.print("[");
        for(int x : ary){
            System.out.print(x + " ");
        }
        System.out.println("]");
    }
    
    /**
     * Prints a 2D integer array.
     * @param ary
     */
    @Tested("12/11/2019")
    public static void print2DArray(int[][] ary){
        for(int[] row : ary){
            printArray(row);
        }
    }
    
    /**
     * Copies a 2D integer array.
     * @param ary
     * @param upto The maximum y coordinate to copy to.
     * @return
     */
    public static int[][] copy2DArray(int[][] ary, int upto){
        int[][] clone = new int[Math.min(upto, ary.length)][];
        for(int n=0;n<clone.length;n++){
            clone[n] = new int[ary[n].length];
            System.arraycopy(ary[n], 0, clone[n], 0, ary[n].length);
        }
        return clone;
    }
    
    /**
     * Checks if the given string can be parsed into a double.
     * @param str
     * @return
     */
    public static boolean isDouble(String str){
        if(str.startsWith("-")) str = str.substring(1);
        if(str.isEmpty() || str.startsWith(".") || str.endsWith(".")) return false;
        boolean dot = false; //If a decimal point has been found in the string.
        //Iterates through all the string's characters.
        for(char c : str.toCharArray()){
            if(!isDigit(c)){
                if(c=='.'){
                    if(dot) return false;
                    else dot = true;
                }else return false;
            }
        }
        return true;
    }
    
    /**
     * Checks if the given string can be parsed into an int.
     * @param str
     * @return
     */
    public static boolean isInteger(String str){
        if(str.startsWith("-")) str = str.substring(1);
        if(str.isEmpty()) return false;
        for(char c : str.toCharArray()) if(!isDigit(c)) return false;
        return true;
    }
    
    /**
     * Hermit's smoothing cumulative distribution function.
     * @param x A number between 0 and 1
     * @return A smoother version of the number (closer to 0.5).
     */
    public static double fade(double x){
        return x * x * x * (x * (x * 6 - 15) + 10);
    }

    /**
     * Interpolates the value between the two given points based on the given 
     * weight.
     * @param a The first value.
     * @param b The second value.
     * @param x The relative weight of the second value (0: a, 1: b).
     * @return
     */
    public static double interpolate(double a, double b, double x){
        return (1D - x) * a + x * b;
    }
    
    /**
     * interpolates between three points by using a triangular function (two
     * sloping lines).
     * @param input The input to predict.
     * @param x0 The input at the start.
     * @param x1 The input at the middle.
     * @param x2 The input at the end.
     * @param y0 The output at the start.
     * @param y1 The output at the middle.
     * @param y2 The output at the end.
     * @return The predicted output value.
     */
    public static double triangulate(double input, double x0, double x1, double x2,
            double y0, double y1, double y2){
        if(input<x1) return gradient(x0, y0, x1, y1)*(input-x1) + y1;
        else return gradient(x1, y1, x2, y2)*(input-x1) + y1;
    }
    
    /**
     * Finds the gradient of the line between two given points.
     * @param x0
     * @param y0
     * @param x1
     * @param y1
     * @return
     */
    public static double gradient(double x0, double y0, double x1, double y1){
        return (y1-y0)/(x1-x0);
    }
    
    /**
     * Computes the average of the nth entries in the given array of arrays.
     * @param n The index.
     * @param arrays The arrays.
     * @return The average for that entry.
     */
    public static int indexAverage(int n, int[]... arrays){
        int total = 0;
        for(int[] ary : arrays)
            total += ary[n];
        return total/arrays.length;
    }
    
    /**
     * Capitalizes the first letter of the string.
     * @param str The string.
     * @return
     */
    public static String capitalize(String str){
        return toUpperCase(str.charAt(0)) + str.substring(1);
    }
    
    /**
     * Converts parameter names of the form "thisIsAnExample" to user friendly
     * "This Is An Example".
     * @param str The string.
     * @return
     */
    public static String spaceCamelCase(String str){
        for(int n=0;n<str.length();n++){
            if(isUpperCase(str.charAt(n))){
                str = str.substring(0, n) + " " + str.charAt(n) + str.substring(n+1);
                n++;
            }
        }
        return capitalize(str);
    }
    
    /**
     * Converts strings of the form "this_is_an_example" to user friendly
     * "This Is An Example".
     * @param str The string.
     * @return
     */
    public static String convertUnderscoreCase(String str){
        boolean allowCapital = true;
        for(int n=0;n<str.length();n++){
            if(isUpperCase(str.charAt(n))){
                if(!allowCapital){
                    str = substitute(str, n, toLowerCase(str.charAt(n)));
                }else allowCapital = false;
            }else if(str.charAt(n) == '_'){
                str = substitute(str, n, ' ');
                allowCapital = true;
            }
        }
        return str;
    }
    
    /**
     * Substitutes the letter in the given index of the string with the given
     * character.
     * @param str The string.
     * @param n The index.
     * @param sub The substitute character.
     * @return
     */
    public static String substitute(String str, int n, char sub){
        return str.substring(0, n) + sub + str.substring(n+1);
    }
    
    /**
     * Checks whether the given map contains the given RGB pixel array as a key.
     * @param <T> The type of the values of the entries in the map.
     * @param map The map.
     * @param ary The target array.
     * @return True if it contains the array.
     */
    public static <T> boolean mapContainsArray(List<SimpleEntry<int[], T>> map, int[] ary){
        return map.stream().anyMatch((e) -> (rgbPixelEquals(e.getKey(), ary)));
    }
    
    /**
     * Gets a value from a list map with RGB pixels as keys.
     * @param <T> The type of the values of the entries in the map.
     * @param map The map.
     * @param ary The key array.
     * @return
     */
    public static <T> T getValueFromMap(List<SimpleEntry<int[], T>> map, int[] ary){
        for(SimpleEntry<int[], T> e : map){
            if(rgbPixelEquals(e.getKey(), ary)) return e.getValue();
        }
        return null;
    }
    
    
    /**
     * The main method for debugging.
     * @param args The arguments.
     * @throws IOException
     */
    public static void main(String... args) throws Exception{
        System.out.println("Running...");
        
        
    }
    
}
