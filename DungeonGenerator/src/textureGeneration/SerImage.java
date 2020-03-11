
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
 *
 * @author Adam Whittaker
 */
public class SerImage implements Serializable{

    
    private static final long serialVersionUID = 80074216L;
    
    protected transient BufferedImage image;
    
    private final SerSupplier supplier;
    private final LinkedList<SerInstruction> instructions = new LinkedList<>();
    private transient boolean upToDate;
    
    
    public SerImage(SerSupplier s){
        supplier = s;
    }
    
    public SerImage(String str){
        this(createSupplier(str));
    }
    
    public SerImage(BufferedImage img){
        this(() -> img);
    }
    
    
    public static SerSupplier createSupplier(String str){
        return () -> ImageBuilder.getImageFromFile(str);
    }
    
    public static SerImage getBlank(){
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
    
    public void setImage(BufferedImage img, boolean upToDate){
        image = img;
        this.upToDate = upToDate;
    }
    
    public BufferedImage getImage(){
        return image;
    }
    
    
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        buildImage();
    }

}
