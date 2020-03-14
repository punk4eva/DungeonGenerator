
package components.traps;

import static components.tiles.Tile.paintHiddenFilter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import static utils.Utils.R;

/**
 * A trap located on the floor.
 * @author Adam Whittaker
 */
public class FloorTrap extends Trap{

    
    private static final long serialVersionUID = 6578930952456L;
    
    
    /**
     * randomly initialized booleans to determine whether to paint each aspect
     * of the trap.
     */
    private boolean horizontalOuter, verticalOuter, diagonal, innerLeft, 
            innerRight;

    
    /**
     * Creates an instance by forwarding the arguments.
     * @param n
     * @param desc
     * @param rev
     */
    public FloorTrap(String n, String desc, boolean rev){
        super(n, desc, rev);
        horizontalOuter = R.nextDouble()<0.5;
        verticalOuter = R.nextDouble()<0.5;
        diagonal = R.nextDouble()<0.5;
        innerLeft = R.nextDouble()<0.5;
        innerRight = R.nextDouble()<0.5;
    }
    
    
    private void paint(Graphics2D g, int x, int y){       
        g.setColor(triggered ? Color.BLACK : color);
        //Outer border
        g.drawLine(x+6, y+3, x+9, y+3);
        g.drawLine(x+6, y+12, x+9, y+12);
        g.drawLine(x+3, y+6, x+3, y+9);
        g.drawLine(x+12, y+6, x+12, y+9);
        //horizontal outer dots
        if(horizontalOuter){
            g.fillRect(x+4, y+5, 1, 1);
            g.fillRect(x+4, y+10, 1, 1);
            g.fillRect(x+11, y+5, 1, 1);
            g.fillRect(x+11, y+10, 1, 1);
        }
        //vertical outer dots
        if(verticalOuter){
            g.fillRect(x+5, y+4, 1, 1);
            g.fillRect(x+5, y+11, 1, 1);
            g.fillRect(x+10, y+4, 1, 1);
            g.fillRect(x+10, y+11, 1, 1);
        }
        //diagonal inner dots
        if(diagonal){
            g.fillRect(x+6, y+6, 1, 1);
            g.fillRect(x+6, y+9, 1, 1);
            g.fillRect(x+9, y+6, 1, 1);
            g.fillRect(x+9, y+9, 1, 1);
        }
        //inner left dots
        if(innerLeft){
            g.fillRect(x+7, y+6, 1, 1);
            g.fillRect(x+9, y+7, 1, 1);
            g.fillRect(x+8, y+9, 1, 1);
            g.fillRect(x+6, y+8, 1, 1);
        }
        //inner right dots
        if(innerRight){
            g.fillRect(x+8, y+6, 1, 1);
            g.fillRect(x+9, y+8, 1, 1);
            g.fillRect(x+7, y+9, 1, 1);
            g.fillRect(x+6, y+7, 1, 1);
        }
    }
    
    /**
     * Creates a copy of this floor trap.
     * @return
     */
    public final FloorTrap copy(){
        FloorTrap trap = new FloorTrap(name, description, revealed);
        trap.color = color;
        trap.horizontalOuter = horizontalOuter;
        trap.verticalOuter = verticalOuter;
        trap.diagonal = diagonal;
        trap.innerLeft = innerLeft;
        trap.innerRight = innerRight;
        return trap;
    }

    @Override
    public void accept(BufferedImage t){
        if(revealed) paint((Graphics2D)t.getGraphics(), 0, 0);
    }

    @Override
    public void drawHiddenAspects(Graphics2D g, int x, int y){
        if(!revealed){
            paint(g, x, y);
            paintHiddenFilter(g, x, y);
        }
    }

}
