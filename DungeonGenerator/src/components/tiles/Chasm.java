
package components.tiles;

import animation.Animatable;
import animation.Animation;
import components.Area;
import graph.Point.Type;
import static gui.pages.DungeonScreen.getSettings;
import java.awt.image.BufferedImage;
import textureGeneration.SerImage;

/**
 * A hole.
 * @author Adam Whittaker
 */
public class Chasm extends Tile implements Animatable{

    
    private static final long serialVersionUID = 56728905L;

    
    /**
     * Creates an instance.
     */
    public Chasm(){
        super("chasm", "This is a <appearance>-looking void in the ground. It would probably be extremely painful or even lethal to fall into it.", Type.NULL, null, null);
    }
    
    
    @Override
    public void buildImage(Area area, int x, int y){
        image = new SerImage(() -> 
                new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB));
    }

    @Override
    public Animation createAnimation(int x, int y){
        return getSettings().getChasmAnimation(x*16, y*16);
    }

}
