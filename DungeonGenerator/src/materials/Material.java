
package materials;

import biomes.Biome;
import biomes.Society;
import textureGeneration.DichromeTexture;
import textureGeneration.Texture;
import textureGeneration.ImageBuilder;
import java.awt.Color;
import java.util.Objects;

/**
 * This class represents a real world material with a unique texture.
 * @author Adam Whittaker
 */
public abstract class Material{
    
    
    /**
     * description: The description of the material.
     * color: The general color of the material.
     * resilience: The maximum hostility that can be withstood by the material.
     * complexity: The minimum technology level required to make this material.
     * maxTemp: The maximum average daily temperature that the material can 
     * tolerate.
     * minHeight, maxHeight: The altitude boundaries where the material spawns.
     * furniture, door, floor, wall: Whether the material can be used to make
     * the respective tile types.
     * filter: The texture of the material.
     */
    public final String description;
    public final Color color;
    
    public final double resilience;
    public final double complexity;
    public final double maxTemp;
    public final double minHeight, maxHeight;
    
    public final boolean furniture, door, floor, wall;
    
    public Texture texture;
    
    
    /**
     * Creates a new instance by initializing the fields in the order of their
     * declaration.
     * @param desc
     * @param col
     * @param res
     * @param comp
     * @param mTemp
     * @param minH
     * @param maxH
     * @param furn
     * @param d
     * @param fl
     * @param wa
     */
    public Material(String desc, Color col, double res, double comp, 
            double mTemp, double minH, double maxH, boolean furn, boolean d, 
            boolean fl, boolean wa){
        description = desc;
        color = col;
        furniture = furn;
        door = d;
        floor = fl;
        wall = wa;
        resilience = res;
        complexity = comp;
        maxTemp = mTemp;
        minHeight = minH;
        maxHeight = maxH;
    }
    
    
    /**
     * Checks whether the given biome and society satisfy the material's 
     * internal requirements.
     * @param b The Biome.
     * @param s The Society.
     * @return True if they satisfy the requirements.
     */
    public boolean biomeCompatible(Biome b, Society s){
        return b.temperature<=maxTemp && b.hostility<=resilience && 
                minHeight <= b.height && b.height <= maxHeight && 
                s.technology>complexity;
    }
    
    /**
     * Sets a default texture for the material.
     * @param filePath The filepath to the texture image.
     * @param num The number of alternate textures.
     */
    protected final void setDefaultTexture(String filePath, int num){
        texture = new DichromeTexture(() -> ImageBuilder.getImageFromFile("tiles/" +filePath + "/" + filePath + num + ".png"), color);
        texture.addInstruction(img -> ImageBuilder.applyAlphaNoise(img, 10, 4));
        texture.buildFilterImage();
    }
    
    @Override
    public boolean equals(Object mat){
        return mat.getClass().isInstance(this);
    }

    @Override
    public int hashCode(){
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.color);
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.resilience) ^ (Double.doubleToLongBits(this.resilience) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.complexity) ^ (Double.doubleToLongBits(this.complexity) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.maxTemp) ^ (Double.doubleToLongBits(this.maxTemp) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.minHeight) ^ (Double.doubleToLongBits(this.minHeight) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.maxHeight) ^ (Double.doubleToLongBits(this.maxHeight) >>> 32));
        return hash;
    }
    
}
