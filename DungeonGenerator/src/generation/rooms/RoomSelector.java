
package generation.rooms;

import biomes.BiomeProcessor.RoomConstructor;
import components.rooms.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import static utils.Utils.R;
import utils.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 */
public class RoomSelector{
    
    /**
     * 0: Plain room
     * 1: Central trap room
     * 2: Statue trap room
     * 3: Library
     */
    private final HashMap<BiFunction<Integer, Integer, Room>, Double> rooms;
    
    
    @Unfinished("Delete once unused")
    public RoomSelector(double[] prob){
        rooms = new HashMap<>();
        rooms.put((w, h) -> new PlainRoom(w, h), prob[0]);
        rooms.put((w, h) -> new CentralTrapRoom(w, h), prob[1]);
        rooms.put((w, h) -> new StatueTrapRoom(w, h), prob[2]);
        rooms.put((w, h) -> new Library(w, h), prob[3]);
        rooms.put((w, h) -> new ChasmVault(w, h), prob[4]);
        rooms.put((w, h) -> new Laboratory(w, h), prob[5]);
    }
    
    public RoomSelector(RoomConstructor[] cons){
        rooms = new HashMap<>();
        for(RoomConstructor con : cons){
            rooms.put(con.constructor, con.probability);
        }
    }
    
    
    public Room select(int w, int h){
        HashMap<BiFunction<Integer, Integer, Room>, Double> map = 
                (HashMap<BiFunction<Integer, Integer, Room>, Double>) rooms.clone();
        double chance, count;
        Entry<BiFunction<Integer, Integer, Room>, Double> entry;
        
        while(!map.isEmpty()){
            chance = R.nextDouble()*(map.entrySet().stream().mapToDouble(e -> e.getValue()).sum());
            count = 0;

            for(Iterator<Entry<BiFunction<Integer, Integer, Room>, Double>> iter = map.entrySet().iterator();iter.hasNext();){
                entry = iter.next();
                count += entry.getValue();
                if(chance<count){
                    try{
                        return entry.getKey().apply(w, h);
                    }catch(IllegalArgumentException e){
                        iter.remove();
                        break;
                    }
                }
            }
        }
        throw new IllegalStateException("No large enough room for dimensions: " + w + ", " + h);
    }

}
