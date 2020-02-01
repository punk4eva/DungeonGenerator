
package generation;

/**
 * Marks an algorithm which generates a corridor before a different algorithm 
 * would generate Rooms.
 * @author Adam Whittaker
 */
public interface PreCorridorPlacer{
    public void generate();
}
