
package gui.pages;

import animation.Animator;
import components.Area;
import gui.core.DungeonViewer;
import static gui.core.DungeonViewer.HEIGHT;
import static gui.core.DungeonViewer.WIDTH;
import gui.core.MouseInterpreter;
import static gui.core.MouseInterpreter.focusX;
import static gui.core.MouseInterpreter.focusY;
import static gui.core.MouseInterpreter.zoom;
import gui.core.Settings;
import gui.core.Window;
import gui.userInterface.GUI;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import utils.Utils.ThreadUsed;
import utils.Utils.Unfinished;

/**
 * Stores the GUI components and paints the Area.
 * @author Adam Whittaker
 */
public class DungeonScreen implements Screen{
    
    
    /**
     * mouse: Handles mouse input.
     * ANIMATOR: Handles all animations.
     * SETTINGS: Stores all user settings for the current Area.
     * gui: The gui.
     * area: The Area that is being viewed currently.
     */
    protected final MouseInterpreter mouse = new MouseInterpreter();
    
    public final static Animator ANIMATOR = new Animator();
    private static Settings SETTINGS = new Settings();
    private final GUI gui;
    private volatile Area area;
    
    
    /**
     * Creates a new instance.
     * @param v The DungeonViewer.
     */
    public DungeonScreen(DungeonViewer v){
        gui = new GUI(v);
        v.addMouseListener(mouse);
        v.addMouseMotionListener(mouse);
        v.addMouseWheelListener(mouse);
    }

    
    @Unfinished("Synchronized block might affect framerate.")
    @ThreadUsed("Render")
    @Override
    public void paint(Graphics2D bsg, int frames){
        BufferedImage buffer = new BufferedImage((int)(WIDTH/zoom), (int)(HEIGHT/zoom), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) buffer.getGraphics();
        
        synchronized(Window.VIEWER){
            area.paint(g, focusX, focusY);
            ANIMATOR.animate(g, focusX, focusY, frames);
            AffineTransform at = AffineTransform.getScaleInstance(zoom, zoom);
            bsg.drawRenderedImage(buffer, at);
        }
        gui.render(bsg);
        g.dispose();
    }
    
    
    /**
     * Gets the Settings.
     * @return
     */
    public static Settings getSettings(){
        return SETTINGS;
    }
    
    /**
     * Sets the Settings object.
     * @param s The new settings.
     */
    public static void setSettings(Settings s){
        SETTINGS = s;
    }
    
    /**
     * Retrieves the name of the calibration panel for debugging purposes.
     * @return
     */
    public String getCalibrationPanelName(){
        return gui.getCalibrationPanelName();
    }
    
    /**
     * Gets the current area.
     * @return
     */
    public Area getArea(){
        return area;
    }
    
    /**
     * Sets the current area.
     * @param a
     */
    public void setArea(Area a){
        area = a;
    }
    
    /**
     * Sets the focus to a tile at the given tile coordinates.
     * @param x
     * @param y
     */
    public void setTileFocus(int x, int y){
        mouse.setTileFocus(x, y);
    }

}
