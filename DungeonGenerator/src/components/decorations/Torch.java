
package components.decorations;

import components.mementoes.AreaInfo;
import filterGeneration.ImageBuilder;
import static filterGeneration.ImageBuilder.colorToPixelArray;
import filterGeneration.ImageRecolorer;
import static gui.DungeonViewer.ANIMATOR;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Adam Whittaker
 */
public class Torch extends Decoration implements WallDecoration{

    
    private final int[][] palette;
    //private final ParticleGenerator particles;
    
    private final static ImageRecolorer RECOLORER = new ImageRecolorer(10, new int[][]{
            {243, 115, 240},
            {215, 119, 223},
            {199, 110, 210}
    });
    
    
    public Torch(AreaInfo info, int x, int y){
        super("torch", "A simple torch hanging from the wall.", false);
        palette = new int[][]{colorToPixelArray(info.architecture.furnitureMaterial.color.brighter(), true),
            colorToPixelArray(info.architecture.furnitureMaterial.color, true),
            colorToPixelArray(info.architecture.furnitureMaterial.color.darker(), true)};
        ANIMATOR.addGenerator(info.settings.getTorchAnimation(x*16, y*16));
    }

    
    @Override
    public void drawImage(Graphics2D g, int _x, int _y){
        BufferedImage torch = ImageBuilder.getImageFromFile("torch.png");
        RECOLORER.recolor(torch, palette);
        g.drawImage(torch, _x, _y, null);
    }

}
