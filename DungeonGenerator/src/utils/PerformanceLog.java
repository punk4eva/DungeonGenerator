
package utils;

import static gui.core.DungeonViewer.ANIMATOR;
import static gui.core.DungeonViewer.HEIGHT;
import static gui.core.DungeonViewer.WIDTH;
import gui.core.MouseInterpreter;
import gui.core.Window;
import static gui.core.Window.VIEWER;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author Adam Whittaker
 */
public class PerformanceLog extends PrintStream implements MouseListener, KeyListener{
    
    
    private final DateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss:SSS: ");
    
    private final boolean PRINT_ZOOM = true, 
            FILE_CRASH_REPORT = true,
            PRINT_CLICKS = true, 
            PRINT_KEYS = true;

    
    public PerformanceLog() throws FileNotFoundException{
        super(new File("log/performance.txt"));
    }
    
    
    public void println(boolean crash, String str){
        super.println((crash ? "$ " : "") + dateFormat.format(new Date()) + str);
    }
    
    public void log(Exception e){
        println(true, e.getClass().getName() + 
                ": " + e.getMessage());
        e.printStackTrace(this);
        printCrashData();
    }
    
    public void printCrashData(){
        println(true, "     ---- CRASH DATA ----");
        println(true, " -   Current state: " + VIEWER.getState());
        println(true, " -   Dimensions: " + WIDTH + ", " + HEIGHT);
        println(true, " -   Animations: " + ANIMATOR.getAnimationNum());
        println(true, " -   Particles: " + ANIMATOR.getParticleNum());
        println(true, " -   sfxVolume: " + Window.sfxVolume);
        println(true, " -   musicVolume: " + Window.musicVolume);
        println(true, " -   Calibration panel: " + VIEWER.getCalibrationPanelName());
        VIEWER.getArea().info.printInfo();
        VIEWER.getArea().info.architecture.biome.printInfo();
        if(FILE_CRASH_REPORT) copyPerformanceFile(getCrashFileName());
    }
    
    private String getCrashFileName(){
        LinkedList<String> fileNames = new LinkedList<>();
        for(File entry : new File("log").listFiles()) 
            fileNames.add(entry.getName());
        
        int n = 0;
        String name = "crash0.txt";
        while(fileNames.contains(name)) name = "crash" + (n++) + ".txt";
        return "log/" + name;
    }
    
    public void copyPerformanceFile(String fileName){
        try (
            BufferedReader in = new BufferedReader(
                new FileReader(new File("log/performance.txt")));
            PrintStream out = new PrintStream(new File(fileName))){
            in.lines().filter(str -> str.contains("$")).forEach(str -> {
                out.println(str);
            });
        }catch(IOException ex){
            System.err.println("Failed to copy file.");
            ex.printStackTrace(System.err);
        }
    }
    
    public void printZoom(double zoom){
        if(PRINT_ZOOM) println("ZOOM: " + zoom);
    }
    
    private void printClick(MouseEvent e){
        if(PRINT_CLICKS){
            Integer[] c = MouseInterpreter.pixelToTile(e.getX(), e.getY());
            println("Mouse x, y, tile x, tile y: " + e.getX() + ", " + e.getY()
                     + ", " + c[0] + ", " + c[1]);
        }
    }
    
    private void printKey(KeyEvent e){
        if(PRINT_KEYS) println("Keypress: " + e.paramString());
    }

    
    @Override
    public void mouseClicked(MouseEvent e){
        printClick(e);
    }

    @Override
    public void mousePressed(MouseEvent e){
        printClick(e);
    }

    @Override
    public void mouseReleased(MouseEvent e){
        printClick(e);
    }

    @Override
    public void mouseEntered(MouseEvent e){}

    @Override
    public void mouseExited(MouseEvent e){}

    @Override
    public void keyTyped(KeyEvent e){
        printKey(e);
    }

    @Override
    public void keyPressed(KeyEvent e){
        printKey(e);
    }

    @Override
    public void keyReleased(KeyEvent e){
        printKey(e);
    }
    
}
