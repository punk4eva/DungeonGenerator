
package gui.core;

import animation.Animator;
import components.Area;
import static gui.core.MouseInterpreter.focusX;
import static gui.core.MouseInterpreter.focusY;
import static gui.core.MouseInterpreter.zoom;
import gui.pages.LoadingScreen;
import gui.pages.Screen;
import gui.userInterface.GUI;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import utils.Utils.ThreadUsed;
import utils.Utils.Unfinished;
import static utils.Utils.PERFORMANCE_LOG;

/**
 *
 * @author Adam Whittaker
 */
public class DungeonViewer extends Canvas implements Runnable, Screen{

    
    public enum State{
        CHOOSING, LOADING, VIEWING;
    }
    
    
    private static final long serialVersionUID = 1653987L;

    public static final int WIDTH, HEIGHT;
    public static final Color BACKGROUND_COLOR = new Color(20,20,20);
    static{
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH = (int)screen.getWidth();
        HEIGHT = (int)screen.getHeight();
    }

    protected volatile boolean running = false;
    private State state = State.LOADING;
    protected Thread runThread;
    protected Window window;
    protected MouseInterpreter mouse = new MouseInterpreter();

    public final static Animator ANIMATOR = new Animator();
    private static Settings SETTINGS = new Settings();
    protected final GUI gui;
    protected volatile Area area;
    private final static LoadingScreen LOADING_SCREEN = new LoadingScreen();
    
    
    /**
     * Creates an instance.
     */
    public DungeonViewer(){
        window = new Window(WIDTH, HEIGHT, "Dungeon Generator", this);
        addMouseListener(PERFORMANCE_LOG);
        addKeyListener(PERFORMANCE_LOG);
        gui = new GUI(this);
    }
    
    
    /**
     * Starts the pacemaker and initializes mouse input.
     */
    @Override
    @ThreadUsed("Render")
    public void run(){
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        addMouseWheelListener(mouse);
        this.requestFocus();
        window.pacemaker.start();
    }

    /**
     * Renders the game.
     * @param frames
     * @thread render
     */
    public void render(int frames){
        BufferStrategy bs = this.getBufferStrategy();
        Graphics2D bsg = (Graphics2D) bs.getDrawGraphics();
        
        bsg.setColor(BACKGROUND_COLOR);
        bsg.fillRect(0, 0, WIDTH, HEIGHT);
        
        switch(state){
            case CHOOSING: 
                break;
            case LOADING: LOADING_SCREEN.paint(bsg, frames); 
                break;
            case VIEWING: paint(bsg, frames);
                break;
        }
        
        bsg.dispose();
        bs.show();
    }
    
    @Override
    public void paint(Graphics2D bsg, int frames){
        BufferedImage buffer = new BufferedImage((int)(WIDTH/zoom), (int)(HEIGHT/zoom), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) buffer.getGraphics();
        
        area.paint(g, focusX, focusY);
        ANIMATOR.animate(g, focusX, focusY, frames);
        AffineTransform at = AffineTransform.getScaleInstance(zoom, zoom);
        bsg.drawRenderedImage(buffer, at);
        gui.render(bsg);
        g.dispose();
    }

    
    /**
     * Starts the game.
     */
    public final synchronized void start(){
        runThread = new Thread(this, "Run Thread");
        runThread.setDaemon(true);
        runThread.start();
        running = true;
    }

    /**
     * Stops the game.
     */
    public final synchronized void stop(){
        try{
            runThread.join();
            running = false;
        }catch(InterruptedException e){
            PERFORMANCE_LOG.log(e);
            System.err.println("Fail in stop() method of DungeonViewer");
        }
    }
    
    
    public static Settings getSettings(){
        return SETTINGS;
    }
    
    public static void setSettings(Settings s){
        SETTINGS = s;
    }
    
    public State getState(){
        return state;
    }
    
    public void setState(State s){
        state = s;
    }
    
    public String getCalibrationPanelName(){
        return gui.getCalibrationPanelName();
    }
    
    public Area getArea(){
        return area;
    }
    
}
