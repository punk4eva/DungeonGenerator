
package text;

import java.util.HashMap;
import java.util.function.Supplier;
import static utils.Utils.R;
import utils.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 */
public final class RegexParser{
    
    
    private RegexParser(){}
    
    
    private static final HashMap<String, Supplier<String>> EXPRESSIONS = new HashMap<>();
    
    public static final String[] RUNE_DESC = {"strange", "mysterious", "wierd", 
        "curious", "enigmatic", "perplexing", "magical", "mystical", "arcane"};
    public static final String[] COLOR = {"apple green", "aquamarine", "apricot",
        "lime", "sky blue", "amber", "auburn", "gold", "electrum", "silver",
        "azure", "magnolia", "banana", "orange", "blizzard blue", "blueberry",
        "cerulean", "periwinckle", "turquoise", "rose", "bubblegum", "burgundy",
        "chocolate", "coral", "cyan", "dandelion", "chestnut", "tangerine",
        "lemon", "ruby", "emerald", "forest green", "ginger", "tea", "voilet", 
        "amaranth red", "scorpion brown", "amethyst", "charcoal", "asparagus", 
        "ash", "copper", "tin", "beige", "bistre", "olive", "bronze", "sapphire",
        "purple", "boysenberry", "ochre", "maroon", "lavender", 
        "lilac", "sugar brown", "coffee", "scarlet", "crimson", "salmon", 
        "metallic", "mint", "saffron", "eggplant", "firebrick", "flame", "white wine"};
    public static final String[] RED_COLORS = {"black", "ruby", "amaranth red", "rusty", "maroon", "cherry", "scarlet", "crimson", "firy"};
    public static final String[] COLOR_MOD = {"dark ", "bright ", "clear ", "",
        "crystal clear ", "dull ", "vibrant ", "glowing ", "murderous ", "curious "};
    public static final String[] TEMPERATURE = {"warm", "cold", "hot", "lukewarm",
        "ice cold", "chilly", "icy", "bitterly cold", "torrid", "blazing hot"};
    public static final String[] WOODS = {"red mogle wood", "hurian titan wood", 
        "hurian goddess wood", "pinkheart wood", "spireling wood", 
        "spickle wood", "master mogle wood", "schmetterhaus wood", 
        "pingle wood", "pongle wood", "callop wood", "pesous wood",
        "shraub wood", "hulous wood", "albino mori wood", "thickbranch wood",
        "roachwood", "white magmatic wood", "crying brown magmatic wood"};
    public static final String[] SHAPE_MOD = {"", "thin", "thick", "wide", "narrow"};
    public static final String[] SHAPE = {"star-shaped", "conical", "spherical",
        "cubic", "pyramidal", "heart shaped", "skull shaped", "triangular",
        "circular"};
    public static final String[] CONTAINER = {"flask", "bottle", "vial", "jar", "tube"};
    public static final String[] CONTAINER_STOPPER = {"cork", "rubber", "wooden", "glass"};
    public static final String[] TEXTURE = {"frothy", "bubbly", "gelatinous",
        "thick", "effervescent", "creamy"};
    public static final String[] SMELLS_LIKE = {"perfume", "rotten eggs", "freshly cut grass", "burnt plastic", "ash", "a corpse", "some exotic plant",
        "some eccentric plant", "petrichor"};
    @Unfinished("Redesign")
    public static final String[] FOOD = {"chocolate", "a strawberry", "an orange",
        "a squid", "the summer", "the winter", "valentine's chocolate", "fire",
        "wine", "chicken", "dirt"};
    public static final String[] TASTE = {"spicy", "bitter", 
        "sweet", "divine", "vile", "foul", "impure", "horrible", "ok",
        "wonderful", "sour", "stale", "bland"};
    public static final String[] SMELL = {"sweet", "aromatic", "refreshing", "pungent", "fetid",
        "unpleasant", "malodorous", "funky", "funny", "musty", "rancid", "old",
        "fragrant", "foul", "healthy", "unhealthy", "putrid", "hideous"};
    public static final String[] VISCOSITY = {"viscous", "runny", "thick", "syrupy",
        "slimy", "gooey", "watery", "thin"};
    public static final String[] APPEARANCE = {"hideous", "beautiful", "bleak", "pathetic", "wonderful", "dumb", "cute", "curious", "dreamy",
        "radiant", "dazzling", "mischievous"};
    public static final String[] SCRIPT = {"sacred text", "spell", "mantra", "curse", "text", "charm", "conjuration", "hex"};
    public static final String[] PILE = {"pile", "heap", "mound", "tower", "agglomerate"};
    public static final String[] BOOK = {"book", "encyclopedia", "review", "analysis", "anthology", "collection", "poem", "prose", "composition"};
    public static final String[] CRITTER = {"caterpillar", "parasite", "cockroach", "slug", "worm", "wasp", "moth"};
    
    static{
        EXPRESSIONS.put("<rune>", () -> grabWord(RUNE_DESC));
        EXPRESSIONS.put("<color>", () -> grabWord(COLOR));
        EXPRESSIONS.put("<red>", () -> grabWord(RED_COLORS));
        EXPRESSIONS.put("<colorMod>", () -> grabWord(COLOR_MOD));
        EXPRESSIONS.put("<temperature>", () -> grabWord(TEMPERATURE));
        EXPRESSIONS.put("<wood>", () -> grabWord(WOODS));
        EXPRESSIONS.put("<shapeMod>", () -> grabWord(SHAPE_MOD));
        EXPRESSIONS.put("<shape>", () -> grabWord(SHAPE));
        EXPRESSIONS.put("<container>", () -> grabWord(CONTAINER));
        EXPRESSIONS.put("<contStopper>", () -> grabWord(CONTAINER_STOPPER));
        EXPRESSIONS.put("<smellsLike>", () -> grabWord(SMELLS_LIKE));
        EXPRESSIONS.put("<food>", () -> grabWord(FOOD));
        EXPRESSIONS.put("<viscosity>", () -> grabWord(VISCOSITY));
        EXPRESSIONS.put("<appearance>", () -> grabWord(APPEARANCE));
        EXPRESSIONS.put("<script>", () -> grabWord(SCRIPT));
        EXPRESSIONS.put("<pile>", () -> grabWord(PILE));
        EXPRESSIONS.put("<book>", () -> grabWord(BOOK));
        EXPRESSIONS.put("<critter>", () -> grabWord(CRITTER));        
        
        EXPRESSIONS.put("<texture>", () -> {
            switch(R.nextInt(5)){
                case 0: case 1: case 2: return grabWord(TEXTURE);
                case 3: return "full of " + grabWord(COLOR_MOD) + grabWord(COLOR) + " coloured " + grabWord(SHAPE_MOD) + " "
                    + grabWord(SHAPE) + " fragments";
                default: return "speckled with " + grabWord(APPEARANCE) + " " + grabWord(COLOR) + " "
                    + grabWord(SHAPE) + " " + grabWord(new String[]{"particulates", "flakes", "particles", "residue"});
            }
        });
        EXPRESSIONS.put("<taste>", () -> R.nextDouble()<0.5 ? grabWord(TASTE) : "like " + grabWord(FOOD));
        EXPRESSIONS.put("<smell>", () -> {
            switch(R.nextInt(6)){
                case 0: case 1: case 2: return grabWord(SMELL);
                case 3: return "like " + grabWord(FOOD);
                case 4: return "like " + grabWord(WOODS);
                default: return "like " + grabWord(SMELLS_LIKE);
            }
        });
        
    }
    
    
    public static String grabWord(String[] ary){
        return ary[R.nextInt(ary.length)];
    }
    
    public static String generateDescription(String blueprint){
        String text = "", exp = "";
        boolean inExpression = false, capital = false;
        for(char c : blueprint.toCharArray()){
            switch(c){
                case '<':
                    inExpression = true;
                    exp = "<";
                    break;
                case '>':
                    inExpression = false;
                    if(capital){ 
                        String temp = EXPRESSIONS.get(exp + ">").get();
                        if(!temp.isEmpty()) text += temp.substring(0,1).toUpperCase() + temp.substring(1);
                        capital = false;
                    }else text += EXPRESSIONS.get(exp + ">").get();
                    break;
                case '$': 
                    capital = true;
                    break;
                default:
                    if(inExpression) exp += c;
                    else if(c != ' ' || text.isEmpty() || text.charAt(text.length()-1) != ' '){
                        text += c;
                    }
                    break;
            }
        }
        return text;
    }
    
}
