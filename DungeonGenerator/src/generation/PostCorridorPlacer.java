
package generation;

/**
 * Marks an algorithm that generates corridors after a different algorithm has
 * placed Rooms into the Area.
 * @author Adam Whittaker
 */
public interface PostCorridorPlacer{
    /**
     * Generates and builds all corridors in the Area.
     */
    public abstract void generate();
}
