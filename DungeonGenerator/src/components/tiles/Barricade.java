
package components.tiles;

import components.Area;
import graph.Point.Type;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import static text.RegexParser.generateDescription;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 */
public class Barricade extends Passage{

    public Barricade(boolean p){
        super("Wooden logs", generateDescription("An impregnable <pile> of old logs stacked high enough to be unliftable. They smell <smell>."), Type.WALL, null, null, p);
    }

    @Override
    public void buildImage(Area area, int x, int y){
        generateFloorImage(area, x, y);
        Graphics2D g = (Graphics2D) image.getGraphics();
        Color wood = area.info.architecture.biome.getRandomWood().color.darker();
        g.setTransform(getRandomRotation());
        g.setColor(wood.darker());
        for(int n=0;n<20;n++)
            g.fillRect(R.nextInt(12), R.nextInt(15), 5+R.nextInt(5), 2);
        g.setTransform(getRandomRotation());
        g.setColor(wood);
        for(int n=0;n<17;n++)
            g.fillRect(R.nextInt(13), R.nextInt(15), 4+R.nextInt(4), 1);
        g.setTransform(getRandomRotation());
        g.setColor(wood.brighter());
        for(int n=0;n<17;n++)
            g.fillRect(R.nextInt(13), R.nextInt(15), 4+R.nextInt(3), 1);
    }
    
    private static AffineTransform getRandomRotation(){
        return AffineTransform.getRotateInstance(R.nextDouble()*2D*Math.PI/9D - Math.PI/9D, 8, 8);
    }

}
