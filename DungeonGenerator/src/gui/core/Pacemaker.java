
package gui.core;

/**
 *
 * @author Adam Whittaker
 */
public class Pacemaker implements Runnable{

    
    private final DungeonViewer viewer;
    private final Thread renderThread;
    private long now;
    
    private final static int BUFFER_NUM = 4;
    private final static long DELAY = 1000L/60L;
    
    
    /**
     * Creates a new instance.
     * @param v The dungeon viewer.
     */
    public Pacemaker(DungeonViewer v){
        viewer = v;
        renderThread = new Thread(this, "Render Thread");
    }
    
    
    /**
     * Starts rendering.
     */
    public void start(){
        renderThread.start();
    }
    
    @Override
    public void run(){
        long n;
        int frames;
        viewer.createBufferStrategy(BUFFER_NUM);
        now = System.currentTimeMillis();
        while(viewer.running){
            n = System.currentTimeMillis();
            if(n-now>DELAY){
                frames = (int)((n-now)/DELAY);
                now = n;
                viewer.render(frames);
            }
        }
    }
    
}
