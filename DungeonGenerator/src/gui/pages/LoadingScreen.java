
package gui.pages;

import static gui.core.DungeonViewer.HEIGHT;
import static gui.core.DungeonViewer.WIDTH;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

/**
 *
 * @author Adam Whittaker
 */
public class LoadingScreen implements Screen{
    
    
    private final static int CIRCLE_DIAMETER = 112, CIRCLE_WIDTH = 8;
    private final static Color BACKGROUND_COLOR = Color.BLACK, 
            CIRCLE_COLOR = Color.WHITE;
    
    private final static double ANGULAR_VELOCITY = 0.04;
    private final static int RECTANGLE_HEIGHT = CIRCLE_WIDTH*3;
    private final static Rectangle RECTANGLE = new Rectangle(
            (WIDTH-CIRCLE_DIAMETER)/2, (HEIGHT-RECTANGLE_HEIGHT)/2,
            CIRCLE_DIAMETER, RECTANGLE_HEIGHT);
    
    private final AffineTransform rotation = new AffineTransform();

    
    @Override
    public void paint(Graphics2D g, int frames){
        updateAngle(frames);
        
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(CIRCLE_COLOR);
        g.fillOval((WIDTH-CIRCLE_DIAMETER)/2, (HEIGHT-CIRCLE_DIAMETER)/2, 
                CIRCLE_DIAMETER, CIRCLE_DIAMETER);
        g.setColor(BACKGROUND_COLOR);
        g.fillOval((WIDTH-CIRCLE_DIAMETER)/2 + CIRCLE_WIDTH, 
                (HEIGHT-CIRCLE_DIAMETER)/2 + CIRCLE_WIDTH, 
                CIRCLE_DIAMETER - 2*CIRCLE_WIDTH, 
                CIRCLE_DIAMETER - 2*CIRCLE_WIDTH);
        g.fill(rotation.createTransformedShape(RECTANGLE));
    }
    
    private void updateAngle(int frames){
        rotation.rotate(ANGULAR_VELOCITY * frames, WIDTH/2, HEIGHT/2);
    }

}
