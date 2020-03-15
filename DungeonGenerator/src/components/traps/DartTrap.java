
package components.traps;

/**
 * A default wall trap.
 * @author Adam Whittaker
 */
public class DartTrap extends WallTrap{

    
    private static final long serialVersionUID = 75903481L;

    
    public DartTrap(boolean rev){
        super("Poison dart trap", "A small poison dart blower is hidden in a nearby wall", rev);
    }

}
