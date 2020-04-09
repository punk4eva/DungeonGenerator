
package textureGeneration;

import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.LinkedList;
import textureGeneration.ImageBuilder.SerInstruction;
import textureGeneration.ImageBuilder.SerSupplier;

/**
 * Stores an image in instruction format so that it can be regenerated from a
 * serialized file without storing the image directly.
 * @author Adam Whittaker
 */
public class SerImage implements Serializable{

    
    private static final long serialVersionUID = 80074216L;
    
    /**
     * image: The image.
     * supplier: The initial instructions that supply the buffered image.
     * instructions: The list of instructions to perform on the image.
     * upToDate: Whether the image is as its instructions describe it.
     */
    protected transient BufferedImage image;
    
    private final SerSupplier supplier;
    private final LinkedList<SerInstruction> instructions = new LinkedList<>();
    private transient boolean upToDate;
    
    
    /**
     * Create a new instance.
     * @param s The buffered image supplier.
     */
    public SerImage(SerSupplier s){
        supplier = s;
    }
    
    /**
     * Creates a new instance by extracting an image from the given filepath.
     * @param str The filepath.
     */
    public SerImage(String str){
        this(createSupplier(str));
    }
    
    /**
     * Creates an instance.
     * @param img The image.
     */
    public SerImage(BufferedImage img){
        this(() -> img);
    }
    
    
    /**
     * Creates a supplier that retrieves the image from the given file location.
     * @param filepath The filepath.
     * @return
     */
    public static SerSupplier createSupplier(String filepath){
        return () -> ImageBuilder.getImageFromFile(filepath);
    }
    
    /**
     * A default convenience method that gets a blank image supplier.
     * @return
     */
    public static SerImage getBlankSupplier(){
        return new SerImage(() -> new BufferedImage(16, 16, TYPE_INT_ARGB));
    }
    
    
    /**
     * Builds the filter image using the supplier and then performing the
     * instructions.
     */
    public void buildImage(){
        if(!upToDate){
            image = supplier.get();
            instructions.forEach((i) -> {
                i.accept(image);
            });
            upToDate = true;
        }
    }
    
    /**
     * Adds a filter building instruction to the instruction list.
     * @param inst The instruction.
     */
    public void addInstruction(SerInstruction inst){
        instructions.add(inst);
        upToDate = false;
    }
    
    /**
     * Removes a filter building instruction to the instruction list.
     * @param inst The instruction.
     */
    public void removeInstruction(SerInstruction inst){
        instructions.remove(inst);
        upToDate = false;
    }
    
    /**
     * Sets the image raw and sets the "up to date" flag.
     * @param img The image.
     * @param upToDate Whether it is up to date according to the instructions.
     */
    public void setImage(BufferedImage img, boolean upToDate){
        image = img;
        this.upToDate = upToDate;
    }
    
    /**
     * Retrieves the image.
     * @return The image.
     */
    public BufferedImage getImage(){
        return image;
    }
    
    
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        buildImage();
    }

}
