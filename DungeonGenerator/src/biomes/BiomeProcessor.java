
package biomes;

import builders.DecorationBuilder;
import components.rooms.*;
import generation.rooms.RoomSelector;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import materials.*;
import materials.composite.*;
import materials.improvised.*;
import materials.stone.*;
import materials.wood.*;
import utils.Distribution;
import static utils.Utils.PERFORMANCE_LOG;
import static utils.Utils.R;
import utils.Utils.Unfinished;

/**
 * This class holds the biome and society information for the current project as
 * well as processing this information into the material/room palette that the
 * Area will use.
 * @author Adam Whittaker
 */
public class BiomeProcessor{
    
    
    /**
     * The lists of all possible woods, materials and room generation algorithms
     * that are possible. Wood has minimum complexity level 20 and is otherwise 
     * independent of accommodation, ruination and technology.
     */
    private final WoodConstructor[] WOODS = new WoodConstructor[]{
        //                 temp       host      height      aggro
        new WoodConstructor(10, 0.10,  15, 0.13,  15, 0.50,  0, 0.04,  new Birch()),
        new WoodConstructor(15, 0.10,  16, 0.12,  10, 0.40,  0, 0.00,  new Oak()),
        new WoodConstructor(25, 0.27,  14, 0.12,  05, 0.43,  0, 0.03,  new Mahogany()),
        new WoodConstructor(14, 0.15,  15, 0.13,  10, 0.35,  0, 0.00,  new Ebony()),
        
        new WoodConstructor(10, 0.12,  16, 0.11,  12, 0.33,  0, 0.14,  new Schmetterhaus()),
        new WoodConstructor(50, 0.20,  60, 0.05,  -90, 0.30, 0, 0.00,  new CryingBrownMagmatia()),
        new WoodConstructor(-15, 0.1,  45, 0.04,  50, 0.28,  0, 0.00,  new WhiteMagmatia()),
        new WoodConstructor(8,  0.12,  15, 0.12,  18, 0.40,  0, 0.00,  new Spruce()),
        new WoodConstructor(6,  0.11,  18, 0.14,  22, 0.42,  0, 0.00,  new Redwood()),
        new WoodConstructor(0,  0.07,  20, 0.07,  -20, 0.15, 0, 0.00,  new FungalWood()),
        new WoodConstructor(18, 0.14,  15, 0.13,  00, 0.47,  0, 0.07,  new Palm()),
    };
    private final MaterialConstructor[] MATERIALS = new MaterialConstructor[]{
        //                     temp       acco       host      height      tech     minTech   ruin      aggro
        new MaterialConstructor(20, 0.03,  40, 0.02,  00, 0.02,  00, 0.10,  65, 0.01,  22,  00, 0.16,  0,  0.15,  b -> new WoodPlanks(b.getRandomWood())),
        new MaterialConstructor(25, 0.02,  50, 0.04,  10, 0.02,  00, 0.01,  75, 0.01,  30,  00, 0.04,  40, 0.04,  b -> new Bricks()),
        new MaterialConstructor(20, 0.07,  18, 0.10,  00, 0.20,  00, 0.10,  12, 0.15,  05,  20, 0.08,  0,  0.17,  b -> new Thatch(b.getRandomWood())),
        new MaterialConstructor(20, 0.00,  00, 0.08,  00, 0.00,  -10, 0.2,  10, 0.02,  00,  80, 0.06,  30, 0.02,  b -> new CaveStone()),
        new MaterialConstructor(20, 0.01,  75, 0.06,  10, 0.02,  00, 0.01,  70, 0.07,  50,  50, 0.03,  30, 0.03,  b -> new Marble()),
        new MaterialConstructor(20, 0.00,  40, 0.15,  10, 0.07,  30, 0.06,  60, 0.07,  40,  0,  0.02,  25, 0.05,  b -> new Slate()),
        new MaterialConstructor(20, 0.00,  55, 0.07,  10, 0.02,  00, 0.00,  60, 0.07,  43,  12, 0.07,  40, 0.04,  b -> new StoneBricks()),
        new MaterialConstructor(20, 0.00,  45, 0.05,  10, 0.02,  00, 0.00,  60, 0.07,  42,  10, 0.05,  50, 0.03,  b -> new StoneSlab()),
        new MaterialConstructor(60, 0.04,  00, 0.00,  80, 0.02,  -90, 0.1,  05, 0.00,  00,  00, 0.00,  80, 0.03,  b -> new HellStone()),
        new MaterialConstructor(40, 0.04,  00, 0.00,  70, 0.02,  -80, 0.1,  05, 0.00,  00,  70, 0.04,  70, 0.02,  b -> new DownStone()),
    };
    public final RoomConstructor[] ROOM_ALGORITHMS = new RoomConstructor[]{
        //                 temp       acco       host      height     tech       ruin       aggro
        new RoomConstructor(20, 0.10,  25, 0.05,  40, 0.05,  0, 0.01,  35, 0.03,  40, 0.10,  0,  0.00,  PlainRoom::new),
        new RoomConstructor(30, 0.00,  65, 0.30,  60, 0.15,  0, 0.00,  55, 0.20,  50, 0.04,  40, 0.12,  CentralTrapRoom::new),
        new RoomConstructor(30, 0.00,  65, 0.30,  55, 0.15,  0, 0.00,  60, 0.20,  50, 0.04,  40, 0.12,  StatueTrapRoom::new),
        new RoomConstructor(20, 0.00,  50, 0.03,  50, 0.02,  0, 0.10,  42, 0.03,  30, 0.06,  30, 0.10,  ChasmVault::new),
        new RoomConstructor(20, 0.06,  70, 0.35,  50, 0.03,  0, 0.00,  85, 0.25,  0,  0.07,  80, 0.03,  Laboratory::new),
        new RoomConstructor(15, 0.35,  80, 0.15,  05, 0.50,  0, 0.01,  78, 0.21,  0,  0.12,  0,  0.07,  Library::new),
        new RoomConstructor(20, 0.00,  00, 0.02,  70, 0.02,  -35, 0.15, 0, 0.03,  80, 0.25,  0,  0.04,  StalagmiteRoom::new),  
        new RoomConstructor(-30, 0.07, 10, 0.05,  20, 0.02,  0, 0.00,  10, 0.01,  60, 0.25,  50, 0.06,  CampfireRoom::new),
        
        new RoomConstructor(23, 0.03,  100, 0.04, 40, 0.05,  0, 0.00,  39, 0.03,  00, 0.02,  00, 0.00,  StorageRoom::new),
        new RoomConstructor(38, 0.03,  45, 0.05, 80, 0.03, -100, 0.02, 33, 0.03,  50, 0.07, 100, 0.04,  BurntRoom::new),
        new RoomConstructor(20, 0.00,  00, 0.02,  70, 0.02,  45, 0.04, 00, 0.03,  80, 0.25,  00, 0.06,  CaveInRoom::new),
        new RoomConstructor(20, 0.00,  75, 0.10,  65, 0.03,  0, 0.04,  65, 0.03,  20, 0.10,  90, 0.03,  Graveyard::new),
        new RoomConstructor(20, 0.00,  100, 0.04, 60, 0.05,  0, 0.00,  65, 0.03,  15, 0.07,  50, 0.02,  AltarRoom::new),
    };
    
    
    /**
     * woodPalette: The set of all woods to be used in the Area.
     * woodDist: The relative distribution of the different woods throughout the
     * area based on their relative compatibility with the biome.
     * roomSelector: The algorithm which decides which rooms to generate.
     * biome: The climate zone of the Area.
     * society: The object capturing the characteristics of the society which
     * built the Area.
     */
    private final Wood woodPalette[] = new Wood[WOODS.length];
    private final Distribution woodDist;
    public final RoomSelector roomSelector;
    public final Biome biome;
    public final Society society;
    public final DecorationBuilder decorationBuilder;
    
    
    /**
     * Creates a new instance.
     * @param b The biome.
     * @param s The society.
     */
    public BiomeProcessor(Biome b, Society s){
        biome = b;
        society = s;
        woodDist = selectWoods(b, s);
        roomSelector = genRoomSelector(b, s);
        decorationBuilder = new DecorationBuilder(b, s);
        enumerateMaterials(b, s);
    }
    
    
    /**
     * Returns a random wood type based on the internal palette and 
     * distribution.
     * @return
     */
    public Wood getRandomWood(){
        return woodPalette[woodDist.next()];
    }
    
    /**
     * Initializes the internal woodPalette and returns the woodDist.
     */
    private Distribution selectWoods(Biome b, Society s){
        //Sort the list of woods by decreasing probability.
        Arrays.asList(WOODS).stream().filter(w -> w.wood.biomeCompatible(b, s))
                .forEach(w -> w.evaluateProbability(b, s));
        Arrays.sort(WOODS, (w1, w2) -> -Double.compare(w1.probability, w2.probability));
        
        //Copies the first section of the wood array into the woodPallete array.
        for(int n=0;n<woodPalette.length;n++) woodPalette[n] = WOODS[n].wood;
        
        //Creates a new distribution.
        double[] cha = new double[woodPalette.length];
        for(int n=0;n<woodPalette.length;n++) cha[n] = WOODS[n].probability;
        
        return new Distribution(cha);
    }
    
    /**
     * Initializes the internal relative material selection probabilities.
     */
    private void enumerateMaterials(Biome b, Society s){
        for(MaterialConstructor mat : MATERIALS){
            mat.evaluateProbability(b, s);
        }
    }
    
    /**
     * Initializes the internal Room selection algorithm.
     */
    private RoomSelector genRoomSelector(Biome b, Society s){
        for(RoomConstructor rc : ROOM_ALGORITHMS) rc.evaluateProbability(b, s);
        return new RoomSelector(ROOM_ALGORITHMS);
    }
    
    /**
     * Gets a random material based on the relative distributions.
     * @param filter The predicate that the material has to pass to be 
     * considered.
     * @return
     */
    @Unfinished("Inefficient")
    public Material getMaterial(Predicate<Material> filter){
        //Filters the material constructors based on whether they are compatible
        //with the given biome and society.
        
        List<MaterialConstructor> constructors = Arrays.asList(MATERIALS).stream()
            .filter(mat -> filter.and(m -> m.biomeCompatible(biome, society))
                    .test(mat.material.apply(this))).collect(Collectors.toList());
        /*HashMap<MaterialConstructor, Material> materialMap = new HashMap<>();
        Material material;
        for(MaterialConstructor mat : MATERIALS){
            material = mat.material.apply(this);
            if(mat.usesWood){
                for(Wood wood : woodPalette)
            }
            if(material.biomeCompatible(biome, society) && filter.test(material)) materialMap.put(mat, material);
        }*/
        
        //Sums the total probability weights
        double chance = R.nextDouble() * constructors.stream().mapToDouble(mat -> mat.probability).sum();
        double count = 0;
        //Return a material based on its desirability with respect to the given
        //biome and society.
        for(MaterialConstructor mat : constructors){
            count += mat.probability;
            if(chance<=count) return mat.material.apply(this);
        }
        throw new IllegalStateException("Count: " + count + ", Chance: " + chance
                + ", Predicate might be too strict.");
    }
    
    
    /**
     * A probability density function.
     * @param x The input.
     * @param variance The tolerance of the output to change (0 to 1).
     * @param bound The extremal bound. The function gives a minimal output of 1 here.
     * @param displacement The median value.
     * @return
     */
    public static double probFunction(double x, double variance, double bound, double displacement){
        return (variance * 51D + 50D)/(1D + Math.pow(10D * (x - displacement) * variance / bound, 2));
    }
    
    
    /**
     * Prints out relevant information to the PerformanceLog for debugging.
     */
    public void printInfo(){
        PERFORMANCE_LOG.println("      ---- Biome information ----");
        PERFORMANCE_LOG.println(" -    Biome: " + biome.name());
    }
    
    
    /**
     * This class represents a function to construct a random Room.
     */
    public static class RoomConstructor extends BiomeDependent{
        
        
        /**
         * Constructs a random Room with the given width and height.
         */
        public final BiFunction<Integer, Integer, Room> constructor;
        
        
        public RoomConstructor(int tem, double tV, int a, double aV, int ho, double hV,
                int he, double heV, int tec, double tecV, int ruin, double ruV, 
                int aggro, double aggV,
                BiFunction<Integer, Integer, Room> con){
            super(tem, tV, a, aV, ho, hV, he, heV, tec, tecV, ruin, ruV, aggro, 
                    aggV);
            constructor = con;
        }
        
    }
    
    /**
     * This class represents a function to construct a Material.
     */
    private static class MaterialConstructor extends BiomeDependent{
        
        
        public final Function<BiomeProcessor, Material> material;
        public final int minTech;
        
        
        public MaterialConstructor(int tem, double tV, int a, double aV, int ho, double hV,
                int he, double heV, int tec, double tecV, int minTe, int ruin, 
                double ruV, int aggro, double aggV,
                Function<BiomeProcessor, Material> mat){
            super(tem, tV, a, aV, ho, hV, he, heV, tec, tecV, ruin, ruV, aggro, 
                    aggV);
            minTech = minTe;
            material = mat;
        }
        
    }
    
    /**
     * This class represents a function to construct a Wood.
     */
    private static class WoodConstructor extends BiomeDependent{
        
        
        public final Wood wood;
        
        
        public WoodConstructor(int tem, double tV, int ho, double hV,
                int he, double heV, int aggro, double aggV, Wood w){
            super(tem, tV, 0, 0.0, ho, hV, he, heV, 0, 0.0, 0, 0.00, aggro, 
                    aggV);
            wood = w;
        }
        
    }

}
