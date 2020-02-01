package gui.core;

import components.Area;
import components.LevelFeeling;
import components.rooms.Room;
import generation.corridors.*;
import generation.rooms.*;
import gui.core.DungeonViewer.State;
import java.awt.Dimension;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.JFrame;
import static utils.Utils.R;
import static utils.Utils.PERFORMANCE_LOG;
import static utils.Utils.SPEED_TESTER;

/**
 * The actual Window onto which everything is painted.
 * @author Adam Whittaker
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
        try{
            SPEED_TESTER.start();
            DungeonViewer viewer = new DungeonViewer();
            SPEED_TESTER.test("Viewer instanciated");
            viewer.start();
            SPEED_TESTER.test("Viewer started");
            viewer.area = new Area(80, 80, LevelFeeling.DEFAULT_FEELING);
            viewer.setTileFocus(viewer.area.info.width/2, viewer.area.info.height/2);
            SPEED_TESTER.test("Area instanciated");

            LinkedList<Room> list = new LinkedList<>();
            for(int n=0;n<30;n++) list.add(viewer.area.info.architecture.biome.roomSelector.select(7+2*R.nextInt(4), 7+2*R.nextInt(4)));
            SPEED_TESTER.test("Rooms selected");
            //BurrowCaveGrower grower = new BurrowCaveGrower(viewer.area, 0.3, 2, 9, 4, 5, 20, true);
            new RandomRoomPlacer(viewer.area, list, r -> r.addDoorsSparcely(viewer.area)).generate();
            viewer.area.refreshGraph();
            SPEED_TESTER.test("Rooms placed");
            //new SpiderCorridorBuilder(viewer.area, 3, 4, CorridorBuilder.gaussianKernel(new Point(40, 79), 120, 24)).build();
            //new OneToOneCorridorBuilder(viewer.area, 2, null).build();
            //SPEED_TESTER.test("Corridors built");
            //new DenseFractalRoomPlacer(viewer.area, 0).generate();
            //CaveGrower grower = new RadialCaveGrower(viewer.area, 0.48, 8);
            //CaveGrower grower = new ConwayCaveGrower(viewer.area, 0.42, 8,  2, 9,  5, 8);
            WormholeCaveGrower grower = new WormholeCaveGrower(viewer.area, 7, 3, 1.0);
            grower.generate();

            viewer.area.growGrass();
            viewer.area.spillWater();
            SPEED_TESTER.test("Decorations added");
            viewer.area.initializeImages();
            SPEED_TESTER.test("Images initialized");
            SPEED_TESTER.report();
            viewer.setState(State.VIEWING);
        }catch(Exception e){
            PERFORMANCE_LOG.log(e);
            throw e;
        }
    }
    
}
