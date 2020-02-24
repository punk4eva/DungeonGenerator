
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
 *
 * @author Adam Whittaker
 */
public class LookCalPanel extends CalibrationPanel{

    
    public LookCalPanel(){
        super(new Button[2]);
        
        int buffer = (HEIGHT-7*PANEL_WIDTH/3)/5;
        int y = PANEL_WIDTH/3 + buffer;
        buffer += PANEL_WIDTH/2;
        buttons[0] = genAreaButton(WIDTH-3*PANEL_WIDTH/4, y, PANEL_WIDTH/2, PANEL_WIDTH/2);
        y += buffer;
        buttons[1] = genDMButton(WIDTH-3*PANEL_WIDTH/4, y, PANEL_WIDTH/2, PANEL_WIDTH/2);
    }
    
    
    private Button genAreaButton(int x, int y, int w, int h){
        return new ResettableButton(x, y, w, h){
            
            @Override
            public void click(int mx, int my){
                VIEWER.getArea().savePNG(getAreaFileName());
            }

            @Override
            public void paint(Graphics2D g){
                paintButtonBox(g);
                paintText(g, "Save as PNG");
            }
        
        };
    }
    
    private Button genDMButton(int x, int y, int w, int h){
        return new Toggle(x, y, w, h){
            
            @Override
            public void click(int mx, int my){
                super.click(mx, my);
                getSettings().DM_MODE = getValue();
            }
        
        };
    }
    
    
    private String getAreaFileName(){
        LinkedList<String> fileNames = new LinkedList<>();
        for(File entry : new File("saves").listFiles()) 
            fileNames.add(entry.getName());
        
        int n = 0;
        String name = "area0.png";
        while(fileNames.contains(name)) name = "area" + (n++) + ".png";
        return "saves/" + name;
    }

}
