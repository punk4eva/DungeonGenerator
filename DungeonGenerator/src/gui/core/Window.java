package gui.core;

import components.Area;
import components.LevelFeeling;
import components.rooms.Room;
import generation.corridors.*;
import generation.rooms.*;
import gui.core.DungeonViewer.State;
import gui.pages.DungeonScreen;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import static textureGeneration.ImageBuilder.getRandomIcon;
import static utils.Utils.R;
import static utils.Utils.PERFORMANCE_LOG;
import static utils.Utils.ROBOT;
import static utils.Utils.SPEED_TESTER;

/**
 * The actual Window onto which everything is painted.
 * @author Adam Whittaker
 */
public class Window{
    
    
    public static DungeonViewer VIEWER;
    public static DungeonScreen SCREEN;
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
        frame.setIconImage(getRandomIcon().getImage());
        frame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
            new ImageIcon("graphics/gui/cursor.png").getImage(),
            new Point(0,0), "Blue Cursor"));
        //@Unfinished uncomment
        //frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
        frame.add(dV);
        frame.setVisible(true);
        VIEWER = dV;
        SCREEN = dV.DUNGEON_SCREEN;
        pacemaker = new Pacemaker(VIEWER);
    }
    
    
    public static void main(String... args) throws IOException{
        try{
            SPEED_TESTER.start();
            DungeonViewer viewer = new DungeonViewer();
            viewer.start();
            SPEED_TESTER.test("Viewer instanciated");
            
            SCREEN.setArea(new Area(80, 80, LevelFeeling.DEFAULT_FEELING));
            SCREEN.setTileFocus(SCREEN.getArea().info.width/2, SCREEN.getArea().info.height/2);

            LinkedList<Room> list = new LinkedList<>();
            for(int n=0;n<30;n++) list.add(SCREEN.getArea().info.architecture.biome.roomSelector.select(7+2*R.nextInt(4), 7+2*R.nextInt(4)));
            //BurrowCaveGrower grower = new BurrowCaveGrower(viewer.area, 0.3, 2, 9, 4, 5, 20, true);
            //new RandomRoomPlacer(SCREEN.getArea(), list, r -> r.addDoorsSparcely(SCREEN.getArea())).generate();
            new GreedyGoblinPlacer(SCREEN.getArea(), 5, list, wall -> R.nextDouble(), AbstractRoomPlacer::roomSizeComparator).generate();
            SCREEN.getArea().refreshGraph();
            SPEED_TESTER.test("Rooms placed");
            //new SpiderCorridorBuilder(viewer.area, 3, 4, CorridorBuilder.gaussianKernel(new Point(40, 79), 120, 24)).build();
            //new OneToOneCorridorBuilder(viewer.area, 2, null).build();
            //SPEED_TESTER.test("Corridors built");
            //new DenseFractalRoomPlacer(viewer.area, 0).generate();
            //CaveGrower grower = new RadialCaveGrower(SCREEN.getArea(), 0.48, 8);
            //CaveGrower grower = new ConwayCaveGrower(SCREEN.getArea(), 0.42, 8,  2, 9,  5, 8);
            //WormholeCaveGrower grower = new WormholeCaveGrower(SCREEN.getArea(), 7, 3, 1.0);
            //grower.generate();

            SCREEN.getArea().growGrass();
            SCREEN.getArea().spillWater();
            SPEED_TESTER.test("Decorations added");
            SCREEN.getArea().initializeImages();
            SPEED_TESTER.test("Images initialized");
            
            /*SCREEN.getArea().savePNG("saves/area.png");
            SPEED_TESTER.test("Area saved");*/
            
            SPEED_TESTER.report();
            viewer.setState(State.VIEWING);
            
            //ROBOT.spamButton(SCREEN.getGUI().getControlPanelButtons()[0], 150, 5);
        }catch(Exception e){
            PERFORMANCE_LOG.log(e);
            throw e;
        }
    }
    
}
