
package gui.questions;

import gui.core.DungeonViewer;
import static gui.core.DungeonViewer.HEIGHT;
import static gui.core.DungeonViewer.WIDTH;
import static gui.core.Window.VIEWER;
import gui.tools.UIPanel;
import gui.tools.ValueInputBox;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.util.LinkedHashMap;

/**
 * An abstract blueprint class for questions that let the user specify 
 * attributes of other classes.
 * @author Adam Whittaker
 */
public abstract class Specifier extends QuestionBox{
    
    
    /**
     * INPUT_NAME_X: The x coordinate of the name of the question.
     * BOX_X: The x coordinate of the question.
     * BOX_WIDTH: The width of the question box.
     * title: The title of the array of questions to display.
     * boxes: The list of questions.
     */
    protected final static int INPUT_NAME_X = WIDTH/4, BOX_X = 5*WIDTH/8,
            BOX_WIDTH = WIDTH/8;
    
    private final String title;
    protected final LinkedHashMap<String, ValueInputBox> boxes = new LinkedHashMap<>();
    
    
    /**
     * Creates an instance.
     * @param v The viewer
     * @param ti The title
     * @param width The width.
     * @param height The height.
     */
    public Specifier(DungeonViewer v, String ti, int width, int height){
        super((WIDTH-width)/2, (HEIGHT-height)/2, width, height);
        title = ti;
    }
    
    /**
     * Creates an instance with the default dimensions.
     * @param ti The title.
     */
    public Specifier(String ti){
        this(VIEWER, ti, WIDTH/3, HEIGHT/3);
    }
    
    
    @Override
    public void click(int mx, int my){
        //Loops through the boxes to find which one was clicked.
        boxes.entrySet().forEach((entry) -> {
            entry.getValue().click(mx, my);
        });
    }
    
    @Override
    public void paint(Graphics2D g){
        //Paints all the questions in the specifier and the title.
        paintTitle(g);
        boxes.entrySet().stream().forEach((entry) -> {
            entry.getValue().paint(g);
            paintText(g, entry.getKey(), INPUT_NAME_X, entry.getValue().getY(),
                    WIDTH/8, MENU_HEIGHT, UIPanel.BUTTON_TEXT_FONT, TEXT_COLOR);
        });
    }
    
    /**
     * Paints the title onto the given graphics.
     * @param g The graphics.
     */
    private void paintTitle(Graphics2D g){
        g.setFont(TITLE_FONT);
        g.setColor(TITLE_COLOR);
        FontMetrics f = g.getFontMetrics();
        g.drawString(title, x+(width - f.stringWidth(title))/2, 
                y-f.getHeight()-PADDING + f.getDescent());
    }
    
    /**
     * Checks whether the specifier has no questions.
     * @return true if it is empty of questions.
     */
    public boolean isEmpty(){
        return boxes.isEmpty();
    }
    
    @Override
    public final void registerKeys(DungeonViewer v){
        //Loops through the questions and registers them if they are key 
        //listeners.
        boxes.entrySet().stream().map(entry -> entry.getValue())
                .filter(inp -> inp instanceof KeyListener).forEach(key -> {
                    v.addKeyListener((KeyListener)key);
                });
    }
    
    @Override
    public final void deregisterKeys(DungeonViewer v){
        //Loops through the questions and de-registers them if they are key 
        //listeners.
        boxes.entrySet().stream().map(entry -> entry.getValue())
                .filter(inp -> inp instanceof KeyListener).forEach(key -> {
                    v.removeKeyListener((KeyListener)key);
                });
    }

}
