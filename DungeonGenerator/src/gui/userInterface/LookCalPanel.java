
package gui.userInterface;

import static gui.core.DungeonViewer.HEIGHT;
import static gui.core.DungeonViewer.WIDTH;
import static gui.core.Window.VIEWER;
import static gui.pages.DungeonScreen.getSettings;
import gui.tools.Button;
import gui.tools.CalibrationPanel;
import gui.tools.ResettableButton;
import gui.tools.Toggle;
import static gui.tools.UIPanel.PANEL_WIDTH;
import java.awt.Graphics2D;
import java.io.File;
import java.util.LinkedList;

/**
 * The calibration panel for controlling the viewing mode of the Dungeon.
 * @author Adam Whittaker
 */
public class LookCalPanel extends CalibrationPanel{

    
    /**
     * Creates a new panel.
     */
    public LookCalPanel(){
        super(new Button[3]);
        //Adds the buttons.
        int buffer = (HEIGHT-7*PANEL_WIDTH/3)/5;
        int y = PANEL_WIDTH/3 + buffer;
        buffer += PANEL_WIDTH/2;
        buttons[0] = genSaveAreaButton(WIDTH-3*PANEL_WIDTH/4, y, PANEL_WIDTH/2, PANEL_WIDTH/2, "png");
        y += buffer;
        buttons[1] = genSaveAreaButton(WIDTH-3*PANEL_WIDTH/4, y, PANEL_WIDTH/2, PANEL_WIDTH/2, "jpg");
        y += buffer;
        buttons[2] = genDMButton(WIDTH-3*PANEL_WIDTH/4, y, PANEL_WIDTH/2, PANEL_WIDTH/2);
    }
    
    
    /**
     * Creates a new button to save the area.
     * @param fileType The type of the file to save the area to.
     * @param x The x of the top-left of the button.
     * @param y The y of the top-left of the button.
     * @param w The button's width.
     * @param h The button's height.
     * @return A button.
     */
    private Button genSaveAreaButton(int x, int y, int w, int h, String fileType){
        return new ResettableButton(x, y, w, h){
            
            @Override
            public void click(int mx, int my){
                VIEWER.getArea().saveAsImage(getAreaFileName(fileType), fileType);
            }

            @Override
            public void paint(Graphics2D g){
                paintButtonBox(g);
                paintText(g, "Save " + fileType.toUpperCase());
            }
        
        };
    }
    
    /**
     * Creates a new button to toggle DM mode.
     * @param x The x of the top-left of the button.
     * @param y The y of the top-left of the button.
     * @param w The button's width.
     * @param h The button's height.
     * @return A button.
     */
    private Button genDMButton(int x, int y, int w, int h){
        return new Toggle(x, y, w, h){
            
            @Override
            public void click(int mx, int my){
                super.click(mx, my);
                getSettings().DM_MODE = getValue();
            }
        
        };
    }
    
    
    /**
     * Generates a file name for the saved area.
     * @param fileType
     * @return
     */
    private String getAreaFileName(String fileType){
        //Creates a list of current area files in the directory which might
        //cause conflict.
        LinkedList<String> fileNames = new LinkedList<>();
        for(File entry : new File("saves").listFiles()) 
            fileNames.add(entry.getName());
        //Finds the next available name not in the directory.
        int n = 0;
        String name = "area0";
        while(nameFree(name, fileNames)) name = "area" + (n++);
        return "saves/" + name + "." + fileType;
    }
    
    /**
     * Checks the given list to see if the given name would cause a mismatch.
     * @param name The name of the would-be file
     * @param fileNames The list of existing file names.
     * @return true if no conflict is caused.
     */
    private boolean nameFree(String name, LinkedList<String> fileNames){
        return fileNames.stream().noneMatch((fileName) -> (fileName.contains(name)));
    }

}
