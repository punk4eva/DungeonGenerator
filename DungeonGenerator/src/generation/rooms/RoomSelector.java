
package generation.rooms;

import biomes.BiomeProcessor.RoomConstructor;
import components.Area;
import components.rooms.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import static utils.Utils.R;

/**
 * Stores a probability distribution selecting possible Room generation 
 * algorithms.
 * @author Adam Whittaker
 */
public class RoomSelector{
    
    
    /**
     * rooms: A HashMap pairing a Room-generation algorithm with a relative 
     * probability of being selected.
     */
    private final HashMap<BiFunction<Integer, Integer, Room>, Double> rooms;
    
    
    /**
     * Creates an instance.
     * @param cons The array of room construction algorithms and their relative
     * probabilities.
     */
    public RoomSelector(RoomConstructor[] cons){
        rooms = new HashMap<>();
        for(RoomConstructor con : cons){
            rooms.put(con.constructor, con.probability);
        }
    }
    
    
    /**
     * Selects and generates a random Room based on their respective 
     * probabilities.
     * @param w The width of the room.
     * @param h The height of the room.
     * @return
     */
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
    
    
    public static LinkedList<Room> getDefaultRoomList(int numRooms, Area area){
        LinkedList<Room> list = new LinkedList<>();
        for(int n=0;n<numRooms;n++) list.add(area.info.architecture.
                biomeProcessor.roomSelector.select(
                        7+2*R.nextInt(4), 7+2*R.nextInt(4)));
        return list;
    }

}
