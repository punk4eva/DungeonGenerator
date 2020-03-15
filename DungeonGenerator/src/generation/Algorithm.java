
package generation;

/**
 * A corridor generation/room placement algorithm that acts on an Area.
 * @author Adam Whittaker
 */
public interface Algorithm{
    
    /**
     * Generates and builds all corridors in the Area.
     */
    public abstract void generate();
    
}
