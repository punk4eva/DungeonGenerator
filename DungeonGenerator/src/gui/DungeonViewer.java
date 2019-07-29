
package gui;

import components.Area;
import static gui.MouseInterpreter.focusX;
import static gui.MouseInterpreter.focusY;
import static gui.MouseInterpreter.zoom;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import utils.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 */
public class DungeonViewer extends Canvas implements Runnable{
    
    public enum State{
        CHOOSING, LOADING, VIEWING;
    }

    public static final int WIDTH, HEIGHT;
    public transient static PrintStream exceptionStream, performanceStream;
    static{
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH = (int)screen.getWidth();
        HEIGHT = (int)screen.getHeight();
        try{
            exceptionStream = new PrintStream(new File("log/exceptions.txt"));
            performanceStream = new PrintStream(new File("log/performance.txt"));
        }catch(FileNotFoundException e){
            System.err.println("PrintStream failed.");
        }        
    }

    protected volatile boolean running = false;
    private State state = State.VIEWING;
    protected Thread runThread;
    protected Window window;
    protected MouseInterpreter mouse = new MouseInterpreter();

    //public final static Animator animator = new Animator();
    //protected static final GuiManager gui = new GuiManager();
    protected volatile Area area;
    
    /**
     * Creates an instance.
     */
    public DungeonViewer(){
        window = new Window(WIDTH, HEIGHT, "Dungeon Generator", this);
    }
    
    /**
     * @thread render
     */
    @Override
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
        
        switch(state){
            case CHOOSING: break;
            case LOADING: break;
            case VIEWING: paintArea(bsg, frames); break;
        }
        
        bsg.dispose();
        bs.show();
    }
    
    public void paintArea(Graphics2D bsg, int frames){
        BufferedImage buffer = new BufferedImage((int)(WIDTH/zoom), (int)(HEIGHT/zoom), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) buffer.getGraphics();
        
        area.paint(g, focusX, focusY);
        //animator.animate(g, focusX, focusY, frames);
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
        }
    }
    
}
