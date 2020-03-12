
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
 *
 * @author Adam Whittaker
 */
public abstract class Specifier extends QuestionBox{
    
    
    protected final static int INPUT_NAME_X = WIDTH/4, BOX_X = 5*WIDTH/8,
            BOX_WIDTH = WIDTH/8;
    
    private final String title;
    protected final LinkedHashMap<String, ValueInputBox> boxes = new LinkedHashMap<>();
    
    
    public Specifier(DungeonViewer v, String ti, int width, int height){
        super((WIDTH-width)/2, (HEIGHT-height)/2, width, height);
        title = ti;
    }
    
    public Specifier(String ti){
        this(VIEWER, ti, WIDTH/3, HEIGHT/3);
    }
    
    
    @Override
    public void click(int mx, int my){
        boxes.entrySet().forEach((entry) -> {
            entry.getValue().click(mx, my);
        });
    }
    
    @Override
    public void paint(Graphics2D g){
        paintTitle(g);
        boxes.entrySet().stream().forEach((entry) -> {
            entry.getValue().paint(g);
            paintText(g, entry.getKey(), INPUT_NAME_X, entry.getValue().getY(),
                    WIDTH/8, MENU_HEIGHT, UIPanel.BUTTON_TEXT_FONT, TEXT_COLOR);
        });
    }
    
    private void paintTitle(Graphics2D g){
        g.setFont(TITLE_FONT);
        g.setColor(TITLE_COLOR);
        FontMetrics f = g.getFontMetrics();
        g.drawString(title, x+(width - f.stringWidth(title))/2, 
                y-f.getHeight()-PADDING + f.getDescent());
    }
    
    public boolean isEmpty(){
        return boxes.isEmpty();
    }
    
    @Override
    public final void registerKeys(DungeonViewer v){
        boxes.entrySet().stream().map(entry -> entry.getValue())
                .filter(inp -> inp instanceof KeyListener).forEach(key -> {
                    v.addKeyListener((KeyListener)key);
                });
    }
    
    @Override
    public final void deregisterKeys(DungeonViewer v){
        boxes.entrySet().stream().map(entry -> entry.getValue())
                .filter(inp -> inp instanceof KeyListener).forEach(key -> {
                    v.removeKeyListener((KeyListener)key);
                });
    }

}
