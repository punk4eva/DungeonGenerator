
package generation;

/**
 * A marker interface for algorithms which place corridors and Rooms at the same
 * time.
 * @author Adam Whittaker
 */
public interface MultiPlacer extends PostCorridorPlacer, RoomPlacer{}
