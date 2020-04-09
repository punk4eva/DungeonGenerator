
package utils.test;

import static gui.core.DungeonViewer.HEIGHT;
import static gui.core.DungeonViewer.WIDTH;
import gui.core.MouseInterpreter;
import static gui.core.Window.SCREEN;
import static gui.core.Window.VIEWER;
import static gui.pages.DungeonScreen.ANIMATOR;
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
import java.util.stream.Collectors;

/**
 * Records messages and logs exceptions in a separate file for debugging 
 * purposes.
 * @author Adam Whittaker
 */
public class PerformanceLog extends PrintStream implements MouseListener, KeyListener{
    
    
    /**
     * dateFormat: The format of the date.
     * PRINT_ZOOM: Whether to print changes to the zoom.
     * FILE_CRASH_REPORT: Whether to store the crash report in a separate file.
     * PRINT_CLICKS: Whether to print click events.
     * PRINT_KEYS: Whether to print key events.
     */
    private final DateFormat dateFormat = new 
        SimpleDateFormat("MM-dd HH:mm:ss:SSS: ");
    
    private final boolean PRINT_ZOOM = true, 
            FILE_CRASH_REPORT = true,
            PRINT_CLICKS = true, 
            PRINT_KEYS = true;

    
    /**
     * Creates a new instance and logs the starting memory.
     * @throws FileNotFoundException
     */
    public PerformanceLog() throws FileNotFoundException{
        super(new File("log/performance.txt"));
        dualPrint("Free memory: " + (Runtime.getRuntime().freeMemory()/1048576) + "MB");
    }
    
    
    @Override
    public void println(String str){
        super.println(dateFormat.format(new Date()) + str);
    }
    
    /**
     * Records the given exception.
     * @param e
     */
    public void log(Exception e){
        println("$ " + e.getClass().getName() + 
                ": " + e.getMessage());
        e.printStackTrace(this);
        printCrashData();
    }
    
    /**
     * Prints a memory dump to the log.
     */
    public void printCrashData(){
        println("     ---- CRASH DATA ----");
        println(" -   Current state: " + VIEWER.getState());
        println(" -   Dimensions: " + WIDTH + ", " + HEIGHT);
        println(" -   Animations: " + ANIMATOR.getAnimationNum());
        println(" -   Particles: " + ANIMATOR.getParticleNum());
        println(" -   Calibration panel: " + SCREEN.getGUI()
                .getCalibrationPanelName());
        if(VIEWER.getArea()!=null){
            VIEWER.getArea().info.printInfo();
            VIEWER.getArea().info.architecture.biomeProcessor.printInfo();
        }
        println("$    ---- END OF CRASH DATA ----");
        if(FILE_CRASH_REPORT) copyCrashFromPerformanceFile(getCrashFileName());
    }
    
    /**
     * Prints the given message to the console and the internal log.
     * @param message
     */
    public final void dualPrint(String message){
        System.out.println(message);
        println(message);
    }
    
    /**
     * Prints the free memory.
     */
    public void printFreeMemory(){
        println("Free memory: " + (Runtime.getRuntime().freeMemory()/1048576) + "MB");
    }
    
    /**
     * Creates a new crash file name that will not cause conflicts with existing 
     * files.
     * @return The new file name.
     */
    private String getCrashFileName(){
        //The list of existing file names.
        LinkedList<String> fileNames = new LinkedList<>();
        for(File entry : new File("log").listFiles()) 
            fileNames.add(entry.getName());
        
        int n = 0;
        String name = "crash0.txt";
        //While the name exists in the file.
        while(fileNames.contains(name)) name = "crash" + (n++) + ".txt";
        return "log/" + name;
    }
    
    public void copyCrashFromPerformanceFile(String fileName){
        try(
            //Creates the input and output streams.
            BufferedReader in = new BufferedReader(
                new FileReader(new File("log/performance.txt")));
            PrintStream outStream = new PrintStream(new File(fileName))){
            //Loops through the lines and prints all lines between the crash
            //markers denoted by '$'.
            boolean printing = false;
            for(String line : in.lines().collect(Collectors.toList())){
                if(printing){
                    outStream.println(line);
                    if(line.contains("$")) return;
                }else if(line.contains("$")){
                    printing = true;
                    outStream.println(line);
                }
            }
            //Error handling
        }catch(IOException ex){
            System.err.println("Failed to copy file.");
            ex.printStackTrace(System.err);
        }
    }
    
    /**
     * Prints the zoom information.
     * @param zoom The new zoom level.
     */
    public void printZoom(double zoom){
        if(PRINT_ZOOM) println("ZOOM: " + zoom);
    }
    
    /**
     * Prints the mouse click information.
     * @param e The click event.
     */
    private void printClick(MouseEvent e){
        if(PRINT_CLICKS){
            Integer[] c = MouseInterpreter.pixelToTile(e.getX(), e.getY());
            println("Mouse x, y, tile x, tile y: " + e.getX() + ", " + e.getY()
                     + ", " + c[0] + ", " + c[1]);
        }
    }
    
    /**
     * Prints the key press information.
     * @param e The key event.
     */
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
