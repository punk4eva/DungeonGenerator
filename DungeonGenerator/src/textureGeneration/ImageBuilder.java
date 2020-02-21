
package textureGeneration;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import static utils.Utils.R;
import static text.RegexParser.COLOR;
import static utils.Utils.PERFORMANCE_LOG;
import static utils.Utils.getRandomItem;

/**
 * A static convenience class containing various image generation functions.
 * @author Adam Whittaker
 */
public final class ImageBuilder{
    
    
    /**
     * Prevents this static class from being instantiated erroneously.
     */
    private ImageBuilder(){}
    
    
    /**
     * WATER_SHADERS: The spritesheet of the water shading images.
     * WATER_SHADE_LEVEL: The level of visibility of tiles underneath the water.
     * (0 = tiles not visible, 255 = water not visible)
     */
    private final static BufferedImage WATER_SHADERS = getImageFromFile("tiles/waterShaders.png");
    private final static int WATER_SHADE_LEVEL = 120; //out of 255
    
    
    /**
     * An interface to mark image suppliers as Serializable automatically.
     */
    public static interface SerSupplier extends Supplier<BufferedImage>, Serializable{}
    
    /**
     * An interface to mark image instructions as Serializable automatically.
     */
    public static interface SerInstruction extends Serializable, Consumer<BufferedImage>{}
    
    
    /**
     * Applies a random noise bitmap to the alpha channel of the given image.
     * @param img The image.
     * @param midPoint The average noise (0-255).
     * @param jitter The maximum displacement in either direction.
     */
    public static void applyAlphaNoise(BufferedImage img, int midPoint, int jitter){
        WritableRaster raster = img.getAlphaRaster();
        int[] pixel = new int[4];
        for(int y=0;y<img.getHeight();y++){
            for(int x=0;x<img.getWidth();x++){
                pixel = raster.getPixel(x, y, pixel);
                if(pixel[0]==0){
                    pixel[0] = midPoint + R.nextInt(2*jitter) - jitter;
                }else{
                    pixel[0] += R.nextInt(2*jitter) - jitter;
                }
                pixel[0] = pixel[0]>255 ? 255 : pixel[0];
                pixel[0] = pixel[0]<0 ? 0 : pixel[0];
                raster.setPixel(x, y, pixel);
            }
        }
    }
    
    /**
     * Retrieves an image from the specified filepath.
     * @param filepath The filepath of the image ("graphics/" is auto-added).
     * @return The image.
     */
    public static BufferedImage getImageFromFile(String filepath){
        try{
            BufferedImage img = ImageIO.read(new File("graphics/" + filepath));
            return img;
        }catch(IOException ex){
            ex.printStackTrace(System.err);
            PERFORMANCE_LOG.log(ex);
            throw new IllegalStateException("Potentially invalid filepath: " + filepath);
        }
    }
    
    /**
     * Converts the given color to an int array.
     * @param c The color.
     * @param alpha Whether the alpha is to be included.
     * @return
     */
    public static int[] colorToPixelArray(Color c, boolean alpha){
        return alpha ? new int[]{c.getRed(), c.getGreen(), c.getBlue(), 255} 
                : new int[]{c.getRed(), c.getGreen(), c.getBlue()};
    }
    
    /**
     * Converts the given color to an int array, ignoring any alpha.
     * @param c The color.
     * @return
     */
    public static int[] colorToPixelArray(Color c){
        return colorToPixelArray(c, false);
    }
    
    public static ImageIcon getRandomIcon(){
        switch(R.nextInt(1)){
            case 0: return new ImageIcon("graphics/gui/icon64.png");
        }
        throw new IllegalStateException();
    }
    
    /**
     * Converts the given image to RGB-only format by redrawing it.
     * @param img The image.
     * @return
     */
    public static BufferedImage convertToRGB(BufferedImage img){
        BufferedImage ret = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        ret.getGraphics().drawImage(img, 0, 0, null);
        return ret;
    }
    
    public static Color makeBluer(Color col, double factor, boolean preserveBrightness){
        Color c = new Color(col.getRed(), col.getGreen(), 
                col.getBlue() + (int)((255D - col.getBlue())*factor));
        if(preserveBrightness) return setBrightness(c, getBrightness(col));
        else return c;
    }
    
    public static Color makeGreener(Color col, double factor, boolean preserveBrightness){
        Color c = new Color(col.getRed(), 
                col.getGreen() + (int)((255D - col.getGreen())*factor), 
                col.getBlue());
        if(preserveBrightness) return setBrightness(c, getBrightness(col));
        else return c;
    }
    
    public static Color makeRedder(Color col, double factor, boolean preserveBrightness){
        Color c = new Color(col.getRed() + (int)((255D - col.getRed())*factor),
                col.getGreen(), col.getBlue());
        if(preserveBrightness) return setBrightness(c, getBrightness(col));
        else return c;
    }
    
    public static int getBrightness(Color col){
        return (col.getBlue() + col.getGreen() + col.getRed())/3;
    }
    
    public static Color setBrightness(Color col, double brightness){
       double factor = brightness / getBrightness(col);
       return new Color((int)(col.getRed()*factor), 
                (int)(col.getGreen()*factor),
                (int)(col.getBlue()*factor));
    }

    
    /**
     * Gets the color with the given name.
     * @param name The name.
     * @return
     */
    public static Color getColor(String name){
        switch(name){
            //General colors
            case "apple green": return Color.decode("#00ff1d");
            case "aquamarine": return Color.decode("#7FFFD4");
            case     "apricot": return Color.decode("#FBCEB1");
            case "lime": return Color.decode("#BFFF00");
            case     "sky blue": return Color.decode("#87CEEB");
            case     "amber": return Color.decode("#FFBF00");
            case     "auburn": return Color.decode("#A52A2A");
            case     "gold": return Color.decode("#FFD700");
            case     "electrum": return Color.decode("#fef3d4");
            case     "silver": return Color.decode("#C0C0C0");
            case "azure": return Color.decode("#007FFF");
            case     "magnolia": return Color.decode("#F4E9D8");
            case     "banana": return Color.decode("#e7f26c");
            case     "orange": return Color.decode("#FF8000");
            case     "blizzard blue": return Color.decode("#50BFE6");
            case     "blueberry": return Color.decode("#4570E6");
            case "cerulean": return Color.decode("#1DACD6");
            case     "periwinckle": return Color.decode("#C3CDE6");
            case     "turquoise": return Color.decode("#6CDAE7");
            case     "rose": return Color.decode("#ED0A3F");
            case     "bubblegum": return Color.decode("#FC80A5");
            case     "burgundy": return Color.decode("#900020");
            case "chocolate": return Color.decode("#AF593E");
            case     "coral": return Color.decode("#FF7F50");
            case     "cyan": return Color.decode("#00FFFF");
            case     "dandelion": return Color.decode("#FED85D");
            case     "chestnut": return Color.decode("#954535");
            case     "tangerine": return Color.decode("#FF9966");
            case "lemon": return Color.decode("#FFFF9F");
            case     "ruby": return Color.decode("#AA4069");
            case     "emerald": return Color.decode("#14A989");
            case     "forest green": return Color.decode("#5FA777");
            case     "ginger": return Color.decode("#f78614");
            case     "tea": return Color.decode("#995006");
            case     "voilet": return Color.decode("#8359A3");
            case "amaranth red": return Color.decode("#E52B50");
            case     "scorpion brown": return Color.decode("#513315");
            case     "amethyst": return Color.decode("#9966CC");
            case     "charcoal": return Color.decode("#36454F");
            case     "asparagus": return Color.decode("#87A96B");
            case "ash": return Color.decode("#919191");
            case     "copper": return Color.decode("#B87333");
            case     "tin": return Color.decode("#b5a14a");
            case     "beige": return Color.decode("#F5F5DC");
            case     "bistre" : return Color.decode("#3D2B1F");
            case     "olive" : return Color.decode("#808000");
            case     "bronze": return Color.decode("#CD7F32");
            case     "sapphire": return Color.decode("#0F52BA");
            case "purple" : return Color.decode("#800080");
            case     "boysenberry": return Color.decode("#910a0a");
            case     "ochre" : return Color.decode("#CC7722");
            case     "maroon" : return Color.decode("#800000");
            case     "lavender" : return Color.decode("#E6E6FA");
            case "lilac" : return Color.decode("#C8A2C8");
            case     "sugar brown": return Color.decode("#aa5500");
            case     "coffee": return Color.decode("#6F4E37");
            case     "scarlet" : return Color.decode("#FF2400");
            case     "crimson" : return Color.decode("#FF003F");
            case     "salmon" : return Color.decode("#FA8072");
            case "metallic": return Color.decode("#bbbbbb");
            case     "mint" : return Color.decode("#3EB489");
            case     "saffron" : return Color.decode("#F4C430");
            case     "eggplant": return Color.decode("#614051");
            case     "firebrick": return Color.decode("#ff5400");
            case     "flame": return Color.decode("#f84400");
            case     "white wine": return Color.decode("#dae8a9");
            case     "red" : return Color.decode("#ff0000");
            //fantasy materials
            case "red mogle wood": return Color.decode("#872f10");
            case "hurian titan wood": return Color.decode("#eddbd5");
            case "hurian goddess wood": return Color.decode("#edd5e3");
            case "pinkheart wood": return Color.decode("#edb2af");
            case "spireling wood": return Color.decode("#602d00");
            case "spickle wood": return Color.decode("#603a19");
            case "master mogle wood": return Color.decode("#9e734d");
            case "schmetterhaus wood": return Color.decode("#639b41");
            case "pingle wood": return Color.decode("#9b7741");
            case "pongle wood": return Color.decode("#6d593a");
            case "callop wood": return Color.decode("#a2adaa");
            case "pesous wood": return Color.decode("#490b40");
            case "shraub wood": return Color.decode("#110c00");
            case "hulous wood": return Color.decode("#7a4d09");
            case "albino mori wood": return Color.decode("#ede4aa");
            case "thickbranch wood": return Color.decode("#6b4a00");
            case "roachwood": return Color.decode("#556b00");
            case "crying brown magmatic wood": return Color.decode("#5b0606");
            case "white magmatic wood": return Color.decode("#ffffff");
            //real materials
            case "clay": return Color.decode("#966432");
            case "stone": return Color.decode("#7b7a73");
            case "marble": return Color.decode("#8b8563");
            case "birch": return Color.decode("#e1c785").darker();
            case "dark oak": return Color.decode("#261609");
            case "oak": return Color.decode("#c89959").darker();
            case "mahogany": return Color.decode("#5a2e11");
            case "ebony": return new Color(40, 20, 20);
            case "brick": return Color.decode("#68250a").darker();
            default: throw new IllegalStateException("Illegal color: " + name);
        }
    }
    
    /**
     * Gets a random color from the library of colors in text.RegexParser.
     * @return
     */
    public static Color getRandomColor(){
        return getColor(getRandomItem(COLOR));
    }
    
    
    /**
     * Applies the water shader for a given tile.
     * @param tile The tile.
     * @param shaderCode The integer representation of which of the NESW 
     * adjacent tiles are water.
     * @return
     */
    public static BufferedImage applyWaterShader(BufferedImage tile, int shaderCode){
        BufferedImage img = new BufferedImage(tile.getWidth(), tile.getHeight(), BufferedImage.TYPE_INT_ARGB);
        WritableRaster readRaster = tile.getRaster(), shadeRaster = WATER_SHADERS.getRaster(),
                writeRaster = img.getRaster();
        int[] pixel = new int[4];
        int alpha;
        shaderCode *= 16;
        for(int y=0;y<tile.getHeight();y++){
            for(int x=0;x<tile.getWidth();x++){
                alpha = shadeRaster.getPixel(x+shaderCode, y, pixel)[3];
                pixel = readRaster.getPixel(x, y, pixel);
                pixel[3] = alpha==0 ? WATER_SHADE_LEVEL : alpha;
                writeRaster.setPixel(x, y, pixel);
            }
        }
        return img;
    }

}
