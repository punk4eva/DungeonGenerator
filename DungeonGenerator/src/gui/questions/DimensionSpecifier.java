
package gui.questions;

import gui.pages.SelectionScreen;
import gui.tools.IntegerBox;

/**
 *
 * @author Adam Whittaker
 */
public final class DimensionSpecifier extends Specifier{
    
    
    public static final int[] DEFAULT_DIMENSIONS = new int[]{80, 80, 27};

    
    private DimensionSpecifier(){
        super("Specify the size of the area");
        
        boxes.put("width", new IntegerBox(BOX_X, y, BOX_WIDTH, 
                MENU_HEIGHT, 40, 500, DEFAULT_DIMENSIONS[0]));
        boxes.put("height", new IntegerBox(BOX_X, y + MENU_HEIGHT + PADDING, 
                BOX_WIDTH, MENU_HEIGHT, 40, 500, DEFAULT_DIMENSIONS[1]));
        boxes.put("rooms", new IntegerBox(BOX_X, y + 2*(MENU_HEIGHT + PADDING), 
                BOX_WIDTH, MENU_HEIGHT, 1, 800, DEFAULT_DIMENSIONS[2]));
    }

    
    @Override
    public void process(SelectionScreen sc){
        sc.getInputCollector().collect(new int[]{
            (int)boxes.get("width").getValue(),
            (int)boxes.get("height").getValue(),
            (int)boxes.get("rooms").getValue()
        });
    }
    
    
    public static final DimensionSpecifier DIMENSION_SPECIFIER
            = new DimensionSpecifier();

}
