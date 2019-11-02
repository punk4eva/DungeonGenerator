
package components.traps;

import java.awt.Color;
import java.awt.Graphics2D;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 */
public class FloorTrap extends Trap{
    
    private boolean horizontalOuter, verticalOuter, diagonal, innerLeft, 
            innerRight;

    public FloorTrap(String n, String desc, boolean rev){
        super(n, desc, rev);
        horizontalOuter = R.nextDouble()<0.5;
        verticalOuter = R.nextDouble()<0.5;
        diagonal = R.nextDouble()<0.5;
        innerLeft = R.nextDouble()<0.5;
        innerRight = R.nextDouble()<0.5;
    }

    @Override
    public void drawImage(Graphics2D g, int x, int y){
        if(!revealed) return;
        
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

}
