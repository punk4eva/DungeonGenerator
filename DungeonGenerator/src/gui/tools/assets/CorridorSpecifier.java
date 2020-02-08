
package gui.tools.assets;

import gui.tools.DropDownMenu;

/**
 *
 * @author Adam Whittaker
 */
public abstract class CorridorSpecifier extends DropDownMenu<String>{

    
    private final String name;
    
    
    public CorridorSpecifier(String na, String ti, int width){
        super(ti, str -> true, width);
        name = na;
    }
    
    
    @Override
    public String toString(){
        return name;
    }
    

}
