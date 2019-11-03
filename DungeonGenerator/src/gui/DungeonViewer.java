
package gui;

import animation.Animator;
import components.Area;
import static gui.MouseInterpreter.focusX;
import static gui.MouseInterpreter.focusY;
import static gui.MouseInterpreter.zoom;
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
import static utils.Utils.exceptionStream;

/**
 *
 * @author Adam Whittaker
 */
public class DungeonViewer extends Canvas implements Runnable{
    
    public enum State{
        CHOOSING, LOADING, VIEWING;
    }

    public static final int WIDTH, HEIGHT;
    public static final Color BACKGROUND_COLOR = new Color(20,20,20);
    static{
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH = (int)screen.getWidth();
        HEIGHT = (int)screen.getHeight();
    }

    protected volatile boolean running = false;
    private State state = State.VIEWING;
    protected Thread runThread;
    protected Window window;
    protected MouseInterpreter mouse = new MouseInterpreter();

    public final static Animator ANIMATOR = new Animator();
    //protected static final GuiManager gui = new GuiManager();
    protected volatile Area area;
    
    /**
     * Creates an instance.
     */
    public DungeonViewer(){
        window = new Window(WIDTH, HEIGHT, "Dungeon Generator", this);
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
            case CHOOSING: break;
            case LOADING: break;
            case VIEWING: paintArea(bsg, frames); break;
        }
        
        bsg.dispose();
        bs.show();
    }
    
    public void paintArea(Graphics2D bsg, int frames){
        BufferedImage buffer = new BufferedImage((int)(WIDTH/zoom), (int)(HEIGHT/zoom), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) buffer.getGraphics();
        
        area.paint(g, focusX, focusY);
        ANIMATOR.animate(g, focusX, focusY, frames);
        AffineTransform at = AffineTransform.getScaleInstance(zoom, zoom);
        bsg.drawRenderedImage(buffer, at);
        //gui.paint(bsg);
        g.dispose();
    }
    
    @Unfinished("Need to complete")
    public void click(int x, int y){}
    
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
            e.printStackTrace(exceptionStream);
            System.err.println("Fail in stop() method of DungeonViewer");
        }
    }
    
    
    public static Settings getSettings(){
        return Window.VIEWER.area.info.settings;
    }
    
}
