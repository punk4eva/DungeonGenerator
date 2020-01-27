
package components.tiles;

import components.Area;
import components.traps.Trap;
import filterGeneration.ImageBuilder;
import static filterGeneration.ImageBuilder.colorToPixelArray;
import filterGeneration.ImageRecolorer;
import graph.Point.Type;
import static text.RegexParser.generateDescription;
import static utils.Utils.R;
import utils.Utils.Unfinished;

/**
 * A flammable bookshelf.
 * @author Adam Whittaker
 */
public class Bookshelf extends PassageTile{
    
    
    /**
     * The color replacement algorithm to recolour the books in the bookshelf.
     */
    private static final ImageRecolorer RECOLORER = new ImageRecolorer(13, new int[][]{
        {88, 60, 40, 255}, {120, 88, 48, 255}, {128, 100, 64, 255}, 
        {112, 56, 112, 255}, {48,  56, 136, 255}, {160, 92, 32, 255}, 
        {80,  100, 128, 255}, {152, 148, 120, 255}, {88,  0, 16, 255}, 
        {152, 24, 32, 255}, {120, 148, 24, 255}, {160, 96, 24, 255}, 
        {72,  132, 56, 255}
    });

    
    /**
     * Creates a new instance.
     * @param tr
     * @param p
     */
    public Bookshelf(Trap tr, final boolean p){
        super("bookshelf", generateDescription("An old, dry, wooden bookshelf with a <color> finish and volumes of even older <shapeMod> books,"
                + " some in forgotten languages. One of the books is titled \""+getBookName()+"\"."), Type.WALL, null, tr, p);
    }
    
    
    /**
     * Generates a random book name.
     * @return
     */
    private static String getBookName(){
        String ret = "$<book> of ";
        switch(R.nextInt(6)){
            case 0: ret += "$<rune> $<script>s"; 
                break;
            case 1: ret += "$<viscosity> $<red> Potions for Use in Combat";
                break;
            case 2: ret += "the $<pile>s Constructed by $<shapeMod> $<color> $<critter>s";
                break;
            case 3: ret += "$<rune> Ancient $<colorMod> Religions";
                break;
            case 4: ret += "$<color> Mastercrafted Armor";
                break;
            default: ret += "$<appearance> Weapons";
                break;
        }
        return ret;
    }

    
    @Override
    @Unfinished("Write generateStyle0()")
    public void buildImage(Area area, int x, int y){
        switch(R.nextInt(2)){
            case 0: generateStyle1(area); break;
            default: generateStyle1(area); break;
        }
    }
    
    /**
     * Generates an image in one of the alternate styles.
     * @param area
     */
    private void generateStyle1(Area area){
        image = ImageBuilder.getImageFromFile("tiles/bookshelf0.png");
        RECOLORER.recolor(image, new int[][]{
            colorToPixelArray(area.info.architecture.furnitureMaterial.color.darker(), true),
            colorToPixelArray(area.info.architecture.furnitureMaterial.color, true),
            colorToPixelArray(area.info.architecture.furnitureMaterial.color.brighter(), true),
            colorToPixelArray(ImageBuilder.getRandomColor().darker(), true),
            colorToPixelArray(ImageBuilder.getRandomColor().darker(), true),
            colorToPixelArray(ImageBuilder.getRandomColor().darker(), true),
            colorToPixelArray(ImageBuilder.getRandomColor().darker(), true),
            colorToPixelArray(ImageBuilder.getRandomColor().darker(), true),
            colorToPixelArray(ImageBuilder.getRandomColor().darker(), true),
            colorToPixelArray(ImageBuilder.getRandomColor().darker(), true),
            colorToPixelArray(ImageBuilder.getRandomColor().darker(), true),
            colorToPixelArray(ImageBuilder.getRandomColor().darker(), true),
            colorToPixelArray(ImageBuilder.getRandomColor().darker(), true)
        });
    }

}
