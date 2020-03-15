
package components.traps;

/**
 * A default floor trap.
 * @author Adam Whittaker
 */
public class BearTrap extends FloorTrap{

    
    private static final long serialVersionUID = 5749233L;

    
    public BearTrap(boolean rev){
        super("Bear trap", "The simplest of traps: a hidden pressure plate and "
                + "a set of closing metal jaws to wound and trap prey.", rev);
    }

}
