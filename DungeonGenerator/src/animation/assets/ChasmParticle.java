
package animation.assets;

import animation.Particle;
import java.awt.Graphics2D;
import static utils.Utils.R;

/**
 * An aerosol emanating from a Chasm.
 * @author Adam Whittaker
 */
public class ChasmParticle extends Particle{

    
    public ChasmParticle(){
        super(R.nextInt(2)+1, R.nextInt(2)+1, 30, 30, 30);
        vely = -(R.nextDouble()*0.07+0.03);
        velx = 0;
        alpha = 160;
    }

    
    @Override
    public void update(int frames){
        motor(frames);

        //Randomly perturb x coordinate.
        double c = R.nextDouble();
        if(c<0.05) x--;
        else if(c<0.1) x++;

        //Decrease visibility or expire particle.
        if(alpha>5) alpha-=2;
        else expired = true;
    }

    @Override
    public void draw(Graphics2D g, int focusX, int focusY, int frames){
        defaultDraw(g, focusX, focusY, frames);
    }

}
