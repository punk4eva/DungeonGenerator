
package gui.questions;

import gui.pages.SelectionScreen;
import gui.tools.IntegerBox;

/**
 *
 * @author Adam Whittaker
 */
public final class DimensionSpecifier extends Specifier{

    
    private DimensionSpecifier(){
        super("Specify the size of the area");
        
        boxes.put("width", new IntegerBox(BOX_X, y, BOX_WIDTH, 
                MENU_HEIGHT, 40, 500, 80));
        boxes.put("height", new IntegerBox(BOX_X, y + MENU_HEIGHT + PADDING, 
                BOX_WIDTH, MENU_HEIGHT, 40, 500, 80));
    }

    
    @Override
    public void process(SelectionScreen sc){
        sc.getInputCollector().collect(new int[]{
            (int)boxes.get("width").getValue(),
            (int)boxes.get("height").getValue()
        });
    }
    
    
    public static final DimensionSpecifier DIMENSION_SPECIFIER
            = new DimensionSpecifier();

}
