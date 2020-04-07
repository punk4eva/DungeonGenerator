
package gui.questions;

import generation.RoomPlacer;
import static generation.rooms.GreedyGoblinPlacer.GREEDY_GOBLIN_SPECIFIER;
import static generation.rooms.RandomRoomPlacer.RANDOM_ROOM_SPECIFIER;
import static gui.core.DungeonViewer.WIDTH;
import gui.pages.SelectionScreen;
import gui.tools.DropDownMenu;

/**
 * The user chooses a room placement algorithm.
 * @author Adam Whittaker
 */
public class RoomPlacerMenu extends 
        DropDownMenu<ClassSpecifier<? extends RoomPlacer>>{

    
    /**
     * Creates an instance.
     */
    public RoomPlacerMenu(){
        super("Select the room placement algorithm", WIDTH/3);
        
        items.add(RANDOM_ROOM_SPECIFIER);
        items.add(GREEDY_GOBLIN_SPECIFIER);
    }
    
    
    @Override
    public void process(SelectionScreen sc){
        sc.addQuestionBox(get());
    }

}
