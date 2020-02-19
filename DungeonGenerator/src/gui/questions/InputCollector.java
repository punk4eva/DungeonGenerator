
package gui.questions;

import generation.*;

/**
 *
 * @author Adam Whittaker
 */
public class InputCollector{
    
    
    private PostCorridorPlacer postCorridorBuilder;
    private MultiPlacer multiPlacer;
    private RoomPlacer roomPlacer;
    
    
    public void setAlgorithm(Object alg){
        if(alg instanceof MultiPlacer) multiPlacer = (MultiPlacer) alg;
        else if(alg instanceof PostCorridorPlacer) postCorridorBuilder = 
                (PostCorridorPlacer) alg;
        else if(alg instanceof RoomPlacer) roomPlacer = (RoomPlacer) alg;
    }

}
