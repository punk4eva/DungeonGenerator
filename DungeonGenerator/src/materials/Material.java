
package materials;

/**
 *
 * @author Adam Whittaker
 */
public class Material{

    public enum Type{
        SURFACE, UNDERGROUND, NATURAL;
    }
    
    public String name;
    public String description;
    public double resilience;
    public double complexity;
    
    
}
