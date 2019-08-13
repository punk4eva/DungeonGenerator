package gui;

import components.Area;
import components.LevelFeeling;
import components.rooms.Room;
import generation.corridors.CaveGrower;
import generation.rooms.RoomPlacer;
import java.awt.Dimension;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.JFrame;
import utils.DungeonColorer;
import static utils.Utils.getRandomRoom;

/**
 *
 * @author Adam Whittaker
 * 
 * The actual Window onto which everything is painted.
 */
public class Window{
    
    public static DungeonViewer VIEWER;
    protected JFrame frame;
    //public static final SoundSystem soundSystem = new SoundSystem();
    public Pacemaker pacemaker;
    public static float sfxVolume = 0, musicVolume = 0;
    
    /**
     * Creates a new instance.
     * @param width The width of the frame.
     * @param height The height of the frame.
     * @param title The title of the frame.
     * @param dV The DungeonViewer peer.
     */
    public Window(int width, int height, String title, DungeonViewer dV){
        frame = new JFrame(title);

        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        //@Unfinished uncomment
        //frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
        frame.add(dV);
        frame.setVisible(true);
        VIEWER = dV;
        pacemaker = new Pacemaker(VIEWER);
    }
    
    
    public static void main(String... args) throws IOException{
        DungeonViewer viewer = new DungeonViewer();
        viewer.area = new Area(80, 80, LevelFeeling.DEFAULT_FEELING);
        
        LinkedList<Room> list = new LinkedList<>();
        for(int n=0;n<20;n++) list.add(getRandomRoom());
        //new RoomPlacer(viewer.area, list).generate();
        //viewer.area.refreshGraph();
        //new SpiderCorridorBuilder(viewer.area, 10, false).build();
        CaveGrower grower = new CaveGrower(viewer.area, 0.3, 2, 9, 4, 5, 20, true, true);
        grower.build();
        new RoomPlacer(viewer.area, list).generate();
        viewer.area.refreshGraph();
        grower.ensureConnectedness();
        viewer.area.initializeImages();
        
        viewer.area.graph.makePNG("saves/map.png", new DungeonColorer());
        
        viewer.start();
    }
    
}
