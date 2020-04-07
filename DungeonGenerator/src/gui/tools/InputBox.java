
package gui.tools;

import static gui.core.DungeonViewer.HEIGHT;
import static gui.tools.UIPanel.BUTTON_TEXT_COLOR;
import static gui.tools.UIPanel.BUTTON_TEXT_FONT;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

/**
 * A means of receiving and storing user input.
 * @author Adam Whittaker
 */
public abstract class InputBox{
    
    
    protected static final int MENU_HEIGHT = HEIGHT*64/1080;
    protected static final int PADDING = 6*HEIGHT/1080;
    protected static final Color TITLE_COLOR = Color.YELLOW,
            HIGHLIGHT_COLOR = new Color(98, 96, 147),
            TEXT_COLOR = Color.YELLOW.darker();
    protected static final Font TITLE_FONT = 
            new Font(Font.MONOSPACED, Font.BOLD, 36*HEIGHT/1080);
    
    
    protected int x, y, width, height;
    
    
    /**
     * Creates a new instance.
     * @param _x The x coordinate of the box.
     * @param _y The y coordinate of the box.
     * @param w The width of the box.
     * @param h The height of the box.
     */
    public InputBox(int _x, int _y, int w, int h){
        x = _x;
        y = _y;
        width = w;
        height = h;
    }
    
    
    /**
     * The box responds to a click on the given coordinates.
     * @param mx The mouse x.
     * @param my The mouse y.
     */
    public abstract void click(int mx, int my);
    
    /**
     * Paints the input box.
     * @param g The graphics.
     */
    public abstract void paint(Graphics2D g);
    
    
    /**
     * Retrieves the box's x value.
     * @return
     */
    public int getX(){
        return x;
    }
    
    /**
     * Retrieves the box's y value.
     * @return
     */
    public int getY(){
        return y;
    }
    
    /**
     * Retrieves the box's width.
     * @return
     */
    public int getWidth(){
        return width;
    }
    
    /**
     * Retrieves the box's height.
     * @return
     */
    public int getHeight(){
        return height;
    }
    
    /**
     * Sets the box's position.
     * @param _x The x
     * @param _y The y
     */
    protected void setXY(int _x, int _y){
        x = _x;
        y = _y;
    }
    
    /**
     * Changes the box's dimensions.
     * @param w The width.
     * @param h The height.
     */
    protected void resize(int w, int h){
        width = w;
        height = h;
    }
    
    
    /**
     * Default box painting method for implementations.
     * @param g The graphics
     * @param x The x coordinate
     * @param y The y coordinate
     * @param width The width
     * @param height The height
     * @param padding The padding between the edge of the box and the inside.
     */
    protected void paintBox(Graphics2D g, int x, int y, int width, int height, 
            int padding){
        g.setColor(UIPanel.BUTTON_COLOR);
        g.fill3DRect(x-padding/2, y-padding/2, width+padding, height+padding, true);
        g.setColor(UIPanel.UI_COLOR);
        g.fill3DRect(x+padding/2, y+padding/2, width-padding, height-padding, false);
    }
    
    /**
     * Default box painting method for implementations.
     * @param g The graphics
     * @param x The x coordinate
     * @param y The y coordinate
     * @param padding The padding between the edge of the box and the inside.
     */
    protected void paintBox(Graphics2D g, int x, int y, int padding){
        paintBox(g, x, y, width, height, padding);
    }
    
    /**
     * Default text painting method for implementations. Paints the given string
     * in the centre of the box.
     * @param g The graphics.
     * @param str The string.
     */
    protected void paintText(Graphics2D g, String str){
        paintText(g, str, x, y, width, height, 
                BUTTON_TEXT_FONT, BUTTON_TEXT_COLOR);
    }
    
    /**
     * Default text painting method for implementations.
     * @param g The graphics.
     * @param str The string.
     * @param x The x coordinate
     * @param y The y coordinate
     * @param width The width
     * @param height The height
     * @param col The color of the string.
     * @param font The font to use.
     */
    protected void paintText(Graphics2D g, String str, int x, int y, 
            int width, int height, Font font, Color col){
        g.setFont(font);
        g.setColor(col);
        FontMetrics f = g.getFontMetrics();
        g.drawString(str, x+(width - f.stringWidth(str))/2, y + height/2 + f.getDescent());
    }
    
    
    /**
     * Checks whether the given coordinate is inside the given rectangle.
     * @param x The x coordinate of the top-left of the rectangle.
     * @param y The y coordinate of the top-left of the rectangle.
     * @param width The width.
     * @param height The height.
     * @param mx The x coordinate.
     * @param my The y coordinate.
     * @return True if it is.
     */
    protected boolean withinBounds(int x, int y, int width, int height, int mx, 
            int my){
        return x<mx && mx<x+width && y<my && my<y+height;
    }
    
    /**
     * Checks whether the given coordinate is inside this box.
     * @param mx The x coordinate.
     * @param my The y coordinate.
     * @return True if it is.
     */
    public boolean withinBounds(int mx, int my){
        return withinBounds(x, y, width, height, mx, my);
    }
    
}
