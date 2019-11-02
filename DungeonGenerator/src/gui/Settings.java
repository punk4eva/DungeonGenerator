
package gui;

/**
 *
 * @author Adam Whittaker
 */
public class Settings{

    public enum GraphicsQuality{
        STATIC(0, "Static", "No animations"), LOW(1, "Low", "Optimized for fast generation."),
        MEDIUM(2, "Medium", "Compromises graphics quality on less apparent features it decrease load time."),
        HIGH(3, "High", "Compromises load time for highest quality graphics, even on less apparent features.");
        
        public final int value;
        public final String name, description;
        
        
        GraphicsQuality(int val, String n, String desc){
            value = val;
            name = n;
            description = desc;
        }
        
    }
    
    
    public GraphicsQuality GRAPHICS = GraphicsQuality.HIGH;
    
}
