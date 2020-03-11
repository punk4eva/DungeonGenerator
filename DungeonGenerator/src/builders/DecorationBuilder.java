
package builders;

import biomes.Biome;
import biomes.BiomeDependent;
import static biomes.BiomeDependent.selectFromBiome;
import biomes.Society;
import components.Area;
import components.decorations.*;
import components.decorations.assets.*;
import java.util.function.Function;
import utils.Distribution;
import utils.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 * A decoration for the floor.
 */
public final class DecorationBuilder{
    
    
    private static final DecConstructor[] FLOOR_DECORATIONS = new DecConstructor[]{
        new DecConstructor(27, 0.12,  100, 0.08,  0, 0.10,  0, 0.05,  20, 0.12,  50, 0.08,  60, 0.03,  (a) -> new Excrement(0.5)),
        new DecConstructor(28, 0.05,  55, 0.07,  0, 0.04,  0, 0.00,  47, 0.08,  10, 0.06,  30, 0.02,  (a) -> new Pot(a.info)),
        new DecConstructor(00, 0.00,  00, 0.02,  65, 0.06,  0, 0.00,  0, 0.00,  63, 0.06,  70, 0.06,  (a) -> new SkeletalRemains(0.5))
    };
    
    private static final DecConstructor[] WALL_DECORATIONS = new DecConstructor[]{
        new DecConstructor(-10, 0.02,  30, 0.02,  50, 0.01,  0, 0.00,  40, 0.25,  5, 0.05,  40, 0.03,  (a) -> new Torch(a.info))
    };
    
    
    private final Distribution floorDistrib,
            wallDistrib;
    
    
    public DecorationBuilder(Biome b, Society s){
        floorDistrib = selectFromBiome(FLOOR_DECORATIONS, b, s);
        wallDistrib = selectFromBiome(WALL_DECORATIONS, b, s);
    }

    
    /**
     * Gets a random floor decoration.
     * @param area
     * @return 
     */
    @Unfinished("Make more dependent on Biome")
    public Decoration getFloorDecoration(Area area){
        return FLOOR_DECORATIONS[floorDistrib.next()]
                .decorationGenerator.apply(area);
    }
    
    /**
     * Returns a random Wall decoration for the given Area.
     * @param area
     * @return
     */
    @Unfinished("Make more dependent on biome")
    public Decoration getWallDecoration(Area area){
        return WALL_DECORATIONS[wallDistrib.next()]
                .decorationGenerator.apply(area);
    }
    
    
    private static class DecConstructor extends BiomeDependent{
    
        
        private final Function<Area, Decoration> decorationGenerator;
        
        
        public DecConstructor(int tem, double tV, int a, double aV, 
                int ho, double hV, int he, double heV, int tec, double tecV, 
                int ruin, double ruV, int aggro, double aggV, 
                Function<Area, Decoration> dec){
            super(tem, tV, a, aV, ho, hV, he, heV, tec, tecV, ruin, ruV, aggro, 
                    aggV);
            decorationGenerator = dec;
        }
    
    }

}
