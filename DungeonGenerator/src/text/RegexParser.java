
package text;

import java.util.HashMap;
import java.util.function.Supplier;
import static utils.Utils.R;
import static utils.Utils.getRandomItem;
import utils.Utils.Unfinished;

/**
 * Parses a given well-formed regular expression String into a procedurally
 * generated description for materials/biomes/etc.
 * @author Adam Whittaker
 */
public final class RegexParser{
    
    
    /**
     * Prevents this static class from being instantiated erroneously.
     */
    private RegexParser(){}
    
    
    /**
     * Maps from regular expressions to replacement String generators.
     */
    private static final HashMap<String, Supplier<String>> EXPRESSIONS = new HashMap<>();
    
    /**
     * Dictionary of possible replacement Strings.
     * RUNE_DESC: Adjectives for mysterious things.
     * COLOR: Colors
     * RED_COLORS: Red colors
     * COLOR_MOD: meta-adjectives for colors.
     * TEMPERATURE: temperature adjectives.
     * WOODS: fantasy wood types.
     * SHAPE_MOD: meta-adjectives for shape.
     * SHAPE: Adjectives for shape.
     * CONTAINER: Types of container.
     * CONTAINER_STOPPER: Materials for the stopper for a container.
     * TEXTURE: texture adjectives.
     * SMELLS_LIKE: similes for smell.
     * FOOD: food items.
     * TASTE: taste adjectives.
     * SMELL: smell adjectives.
     * VISCOSITY: viscosity adjectives.
     * APPEARANCE: General appearance adjectives.
     * SCRIPT: A type of sacred text.
     * PILE: synonyms for the word "pile".
     * BOOK: Types of book.
     * CRITTER: Types of insect / other vermin.
     */
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
    public static final String[] RED_COLORS = {"black", "ruby", "amaranth red", 
        "rusty", "maroon", "cherry", "scarlet", "crimson", "firy"};
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
    public static final String[] SMELLS_LIKE = {"perfume", "rotten eggs", 
        "freshly cut grass", "burnt plastic", "ash", "a corpse", "some exotic plant",
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
    public static final String[] APPEARANCE = {"hideous", "beautiful", "bleak", 
        "pathetic", "wonderful", "dumb", "cute", "curious", "dreamy",
        "radiant", "dazzling", "mischievous"};
    public static final String[] SCRIPT = {"sacred text", "spell", "mantra", 
        "curse", "text", "charm", "conjuration", "hex"};
    public static final String[] PILE = {"pile", "heap", "mound", "tower", "agglomerate"};
    public static final String[] BOOK = {"book", "encyclopedia", "review", 
        "analysis", "anthology", "collection", "poem", "prose", "composition"};
    public static final String[] CRITTER = {"caterpillar", "parasite", 
        "cockroach", "slug", "worm", "wasp", "moth"};
    
    static{
        EXPRESSIONS.put("<rune>", () -> getRandomItem(RUNE_DESC));
        EXPRESSIONS.put("<color>", () -> getRandomItem(COLOR));
        EXPRESSIONS.put("<red>", () -> getRandomItem(RED_COLORS));
        EXPRESSIONS.put("<colorMod>", () -> getRandomItem(COLOR_MOD));
        EXPRESSIONS.put("<temperature>", () -> getRandomItem(TEMPERATURE));
        EXPRESSIONS.put("<wood>", () -> getRandomItem(WOODS));
        EXPRESSIONS.put("<shapeMod>", () -> getRandomItem(SHAPE_MOD));
        EXPRESSIONS.put("<shape>", () -> getRandomItem(SHAPE));
        EXPRESSIONS.put("<container>", () -> getRandomItem(CONTAINER));
        EXPRESSIONS.put("<contStopper>", () -> getRandomItem(CONTAINER_STOPPER));
        EXPRESSIONS.put("<smellsLike>", () -> getRandomItem(SMELLS_LIKE));
        EXPRESSIONS.put("<food>", () -> getRandomItem(FOOD));
        EXPRESSIONS.put("<viscosity>", () -> getRandomItem(VISCOSITY));
        EXPRESSIONS.put("<appearance>", () -> getRandomItem(APPEARANCE));
        EXPRESSIONS.put("<script>", () -> getRandomItem(SCRIPT));
        EXPRESSIONS.put("<pile>", () -> getRandomItem(PILE));
        EXPRESSIONS.put("<book>", () -> getRandomItem(BOOK));
        EXPRESSIONS.put("<critter>", () -> getRandomItem(CRITTER));        
        
        EXPRESSIONS.put("<texture>", () -> {
            switch(R.nextInt(5)){
                case 0: case 1: case 2: return getRandomItem(TEXTURE);
                case 3: return "full of " + getRandomItem(COLOR_MOD) + getRandomItem(COLOR) + " coloured " + getRandomItem(SHAPE_MOD) + " "
                    + getRandomItem(SHAPE) + " fragments";
                default: return "speckled with " + getRandomItem(APPEARANCE) + " " + getRandomItem(COLOR) + " "
                    + getRandomItem(SHAPE) + " " + getRandomItem(new String[]{"particulates", "flakes", "particles", "residue"});
            }
        });
        EXPRESSIONS.put("<taste>", () -> R.nextDouble()<0.5 ? getRandomItem(TASTE) : "like " + getRandomItem(FOOD));
        EXPRESSIONS.put("<smell>", () -> {
            switch(R.nextInt(6)){
                case 0: case 1: case 2: return getRandomItem(SMELL);
                case 3: return "like " + getRandomItem(FOOD);
                case 4: return "like " + getRandomItem(WOODS);
                default: return "like " + getRandomItem(SMELLS_LIKE);
            }
        });
        
    }
    
    
    /**
     * Converts the given regular expression String into a description.
     * @param blueprint The regex String.
     * @return A randomized description.
     */
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
