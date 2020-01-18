
package biomes;

import materials.*;
import components.rooms.*;
import generation.rooms.RoomSelector;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import materials.composite.*;
import materials.improvised.*;
import materials.stone.*;
import materials.wood.*;
import utils.Distribution;
import static utils.Utils.R;
import static utils.Utils.performanceLog;

/**
 *
 * @author Adam Whittaker
 */
public class BiomeProcessor{
    
    
    //Wood has minimum complexity level 20
    private final WoodConstructor[] WOODS = new WoodConstructor[]{
        //                 temp       acco       host      height      tech
        new WoodConstructor(10, 0.10,  00, 0.00,  15, 0.13,  15, 0.50,  00, 0.0,  new Birch()),
        new WoodConstructor(15, 0.10,  00, 0.00,  16, 0.12,  10, 0.40,  00, 0.0,  new Oak()),
        new WoodConstructor(25, 0.27,  00, 0.00,  14, 0.12,  05, 0.43,  00, 0.0,  new Mahogany()),
        new WoodConstructor(14, 0.15,  00, 0.00,  15, 0.13,  10, 0.35,  00, 0.0,  new Ebony()),
    };
    private final MaterialConstructor[] MATERIALS = new MaterialConstructor[]{
        //                     temp       acco       host      height      tech     minTech
        new MaterialConstructor(20, 0.03,  40, 0.02,  00, 0.02,  05, 0.20,  65, 0.01,  22,  b -> new WoodPlanks(b.getRandomWood())),
        new MaterialConstructor(25, 0.02,  50, 0.04,  10, 0.02,  00, 0.01,  75, 0.01,  30,  b -> new Brick()),
        new MaterialConstructor(20, 0.07,  18, 0.10,  00, 0.20,  00, 0.10,  12, 0.15,  05,  b -> new Thatch(b.getRandomWood())),
        new MaterialConstructor(20, 0.00,  00, 0.08,  00, 0.00,  -10, 0.2,  10, 0.02,  00,  b -> new CaveStone()),
        new MaterialConstructor(20, 0.01,  75, 0.06,  10, 0.02,  00, 0.01,  70, 0.07,  50,  b -> new Marble()),
        new MaterialConstructor(20, 0.00,  40, 0.15,  10, 0.07,  30, 0.06,  60, 0.07,  40,  b -> new Slate()),
        new MaterialConstructor(20, 0.00,  55, 0.07,  10, 0.02,  00, 0.00,  60, 0.07,  43,  b -> new StoneBrick()),
        new MaterialConstructor(20, 0.00,  45, 0.05,  10, 0.02,  00, 0.00,  60, 0.07,  42,  b -> new StoneSlab())
    };
    public final RoomConstructor[] ROOM_ALGORITHMS = new RoomConstructor[]{
        //                 temp       acco       host      height     tech
        new RoomConstructor(20, 0.10,  25, 0.10,  40, 0.05,  0, 0.01,  35, 0.03,  PlainRoom::new),
        new RoomConstructor(30, 0.00,  65, 0.30,  60, 0.15,  0, 0.00,  55, 0.20,  CentralTrapRoom::new),
        new RoomConstructor(30, 0.00,  65, 0.30,  55, 0.15,  0, 0.00,  60, 0.20,  StatueTrapRoom::new),
        new RoomConstructor(20, 0.00,  50, 0.03,  00, 0.02,  0, 0.10,  42, 0.03,  ChasmVault::new),
        new RoomConstructor(20, 0.06,  70, 0.35,  50, 0.03,  0, 0.00,  85, 0.25,  Laboratory::new),
        new RoomConstructor(15, 0.35,  80, 0.15,  05, 0.50,  0, 0.01,  78, 0.21,  Library::new)
    };
    
    
    private final Wood woodPalette[] = new Wood[3];
    private final Distribution woodDist;
    public final RoomSelector roomSelector;
    public final Biome biome;
    public final int societyTechnology;
    
    
    public BiomeProcessor(Biome b, int s){
        biome = b;
        societyTechnology = s;
        woodDist = selectWoods(b, s);
        roomSelector = genRoomSelector(b, s);
        enumerateMaterials(b, s);
    }
    
    
    public Wood getRandomWood(){
        return woodPalette[woodDist.next()];
    }
    
    private Distribution selectWoods(Biome b, int s){
        Arrays.asList(WOODS).stream().filter(w -> w.wood.biomeCompatible(b, s)).forEach(w -> w.evaluateProbability(b, s));
        Arrays.sort(WOODS, (w1, w2) -> -Double.compare(w1.probability, w2.probability));
        
        for(int n=0;n<woodPalette.length;n++) woodPalette[n] = WOODS[n].wood;
        
        double[] cha = new double[woodPalette.length];
        for(int n=0;n<woodPalette.length;n++) cha[n] = WOODS[n].probability;
        
        return new Distribution(cha);
    }
    
    private void enumerateMaterials(Biome b, int s){
        for(MaterialConstructor mat : MATERIALS){
            mat.evaluateProbability(b, s);
        }
    }
    
    private RoomSelector genRoomSelector(Biome b, int s){
        for(RoomConstructor rc : ROOM_ALGORITHMS) rc.evaluateProbability(b, s);
        return new RoomSelector(ROOM_ALGORITHMS);
    }
    
    public Material getMaterial(Predicate<Material> filter){
        List<MaterialConstructor> constructors = Arrays.asList(MATERIALS).stream().filter(mat -> filter.and(m -> m.biomeCompatible(biome, societyTechnology)).test(mat.material.apply(this))).collect(Collectors.toList());
        double chance = R.nextDouble() * constructors.stream().mapToDouble(mat -> mat.probability).sum();
        double count = 0;
        for(MaterialConstructor mat : constructors){
            count += mat.probability;
            if(chance<=count) return mat.material.apply(this);
        }
        throw new IllegalStateException("Count: " + count + ", Chance: " + chance);
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
    
    
    public void printInfo(){
        performanceLog.println(true, "      ---- Biome information ----");
        performanceLog.println(true, " -    Biome: " + biome.name());
    }
    
    
    private static class ObjectConstructor{
    
        
        public final int temperature, accommodation, hostility, height, technology;
        public final double tempV, accV, hostV, heightV, techV;
        
        public double probability = 0;
        
        
        public ObjectConstructor(int tem, double tV, int a, double aV, int ho, double hV,
                int he, double heV, int tec, double tecV){
            temperature = tem;
            accommodation = a;
            hostility = ho;
            height = he;
            technology = tec;
            tempV = tV;
            accV = aV;
            hostV = hV;
            heightV = heV;
            techV = tecV;
        }
        
        
        double evaluateProbability(Biome b, int s){
            probability = probFunction(b.temperature, tempV, 80, temperature);
            probability += probFunction(b.accommodation, accV, 50, accommodation);
            probability += probFunction(b.hostility, hostV, 50, hostility);
            probability += probFunction(b.height, heightV, 100, height);
            probability += probFunction(s, techV, 50, technology);
            return probability;
        }
        
    }
    
    public static class RoomConstructor extends ObjectConstructor{
        
        
        public final BiFunction<Integer, Integer, Room> constructor;
        
        
        public RoomConstructor(int tem, double tV, int a, double aV, int ho, double hV,
                int he, double heV, int tec, double tecV, 
                BiFunction<Integer, Integer, Room> con){
            super(tem, tV, a, aV, ho, hV, he, heV, tec, tecV);
            constructor = con;
        }
        
    }
    
    private static class MaterialConstructor extends ObjectConstructor{
    
        
        public final Function<BiomeProcessor, Material> material;
        public final int minTech;
        
        
        public MaterialConstructor(int tem, double tV, int a, double aV, int ho, double hV,
                int he, double heV, int tec, double tecV, int minTe,
                Function<BiomeProcessor, Material> mat){
            super(tem, tV, a, aV, ho, hV, he, heV, tec, tecV);
            minTech = minTe;
            material = mat;
        }
        
    }
    
    private static class WoodConstructor extends ObjectConstructor{
        
        
        public final Wood wood;
        
        
        public WoodConstructor(int tem, double tV, int a, double aV, int ho, double hV,
                int he, double heV, int tec, double tecV, 
                Wood w){
            super(tem, tV, a, aV, ho, hV, he, heV, tec, tecV);
            wood = w;
        }
        
    }

}
