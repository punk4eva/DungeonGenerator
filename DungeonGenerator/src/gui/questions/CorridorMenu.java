
package gui.questions;

import generation.PostCorridorPlacer;
import static generation.corridors.BurrowCaveGrower.BURROW_CAVE_SPECIFIER;
import static generation.corridors.OneToOneCorridorBuilder.ONE_TO_ONE_SPECIFIER;
import static generation.corridors.RadialCaveGrower.RADIAL_CAVE_SPECIFIER;
import static generation.corridors.SpiderCorridorBuilder.SPIDER_SPECIFIER;
import static generation.corridors.WormholeCaveGrower.WORMHOLE_CAVE_SPECIFIER;
import static gui.core.DungeonViewer.WIDTH;
import gui.pages.SelectionScreen;
import gui.tools.DropDownMenu;

/**
 * User chooses the corridor generation algorithm.
 * @author Adam Whittaker
 */
public class CorridorMenu extends 
        DropDownMenu<ClassSpecifier<? extends PostCorridorPlacer>>{

    
    /**
     * Creates a new instance.
     */
    public CorridorMenu(){
        super("Select the corridor generation algorithm", WIDTH/3);
        
        items.add(BURROW_CAVE_SPECIFIER);
        items.add(RADIAL_CAVE_SPECIFIER);
        items.add(WORMHOLE_CAVE_SPECIFIER);
        items.add(ONE_TO_ONE_SPECIFIER);
        items.add(SPIDER_SPECIFIER);
    }
    
    
    @Override
    public void process(SelectionScreen sc){
        sc.addQuestionBox(get());
    }
    
}
