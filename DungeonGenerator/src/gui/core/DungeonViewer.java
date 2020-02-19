
package gui.core;

import components.Area;
import generation.corridors.BurrowCaveGrower;
import gui.pages.*;
import gui.questions.AlgorithmSpecifier;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.lang.reflect.Constructor;
import utils.Utils.ThreadUsed;
import static utils.Utils.PERFORMANCE_LOG;

/**
 * The main hub class of the project. It manages the different states and 
 * threads of the program.
 * @author Adam Whittaker
 */
public class DungeonViewer extends Canvas implements Runnable{

    
    /**
     * The possible states of the program.
     */
    public enum State{
        CHOOSING, LOADING, VIEWING;
    }
    
    
    private static final long serialVersionUID = 1653987L;

    /**
     * WIDTH, HEIGHT: The dimensions of the screen in pixels.
     * BACKGROUND_COLOR: The color of the negative space of the dungeon.
     */
    public static final int WIDTH, HEIGHT;
    public static final Color BACKGROUND_COLOR = new Color(20,20,20);
    static{
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH = (int)screen.getWidth();
        HEIGHT = (int)screen.getHeight();
    }

    protected volatile boolean running = false;
    private State state;
    protected Thread runThread;
    protected Window window;

    protected final DungeonScreen DUNGEON_SCREEN;
    private final LoadingScreen LOADING_SCREEN = new LoadingScreen();
    private final SelectionScreen SELECTION_SCREEN;
    
    
    /**
     * Creates an instance.
     */
    public DungeonViewer(){
        DUNGEON_SCREEN = new DungeonScreen(this);
        window = new Window(WIDTH, HEIGHT, "Dungeon Generator", this);
        addMouseListener(PERFORMANCE_LOG);
        addKeyListener(PERFORMANCE_LOG);
        
        SELECTION_SCREEN = new SelectionScreen(this);
        SELECTION_SCREEN.setQuestionBox(new AlgorithmSpecifier<BurrowCaveGrower>(this, ((Constructor<BurrowCaveGrower>)BurrowCaveGrower.class.getConstructors()[0]), 
                "Burrow Cave Grower", "Design the burrows", WIDTH/3, HEIGHT/3));
    }
    
    
    /**
     * Starts the pacemaker and initializes mouse input.
     */
    @Override
    @ThreadUsed("Run")
    public void run(){
        this.requestFocus();
        window.pacemaker.start();
    }

    /**
     * Renders the program.
     * @param frames The number of frames that have passed.
     * @thread render
     */
    @ThreadUsed("Render")
    public void render(int frames){
        BufferStrategy bs = this.getBufferStrategy();
        Graphics2D bsg = (Graphics2D) bs.getDrawGraphics();
        
        bsg.setColor(BACKGROUND_COLOR);
        bsg.fillRect(0, 0, WIDTH, HEIGHT);
        
        switch(state){
            case CHOOSING: SELECTION_SCREEN.paint(bsg, frames);
                break;
            case LOADING: LOADING_SCREEN.paint(bsg, frames); 
                break;
            case VIEWING: DUNGEON_SCREEN.paint(bsg, frames);
                break;
        }
        
        bsg.dispose();
        bs.show();
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
    
    
    /**
     * Gets the current state of the program.
     * @return
     */
    public State getState(){
        return state;
    }
    
    /**
     * Sets the current state of the program.
     * @param s The state.
     */
    public void setState(State s){
        state = s;
    }
    
    /**
     * Gets the current Area that is being viewed.
     * @return
     */
    public Area getArea(){
        return DUNGEON_SCREEN.getArea();
    }
    
    /**
     * Sets the loading screen message.
     * @param str
     */
    public void setLoadingMessage(String str){
        LOADING_SCREEN.setMessage(str);
    }
    
    public int getTitleBarSize(){
        return window.frame.getInsets().top;
    }
    
}
