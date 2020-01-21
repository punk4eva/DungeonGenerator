package gui.core;

import components.Area;
import components.LevelFeeling;
import components.rooms.Room;
import generation.corridors.*;
import generation.rooms.*;
import java.awt.Dimension;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.JFrame;
import static utils.Utils.R;
import static utils.Utils.PERFORMANCE_LOG;

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
        try{
            DungeonViewer viewer = new DungeonViewer();
            viewer.area = new Area(80, 80, LevelFeeling.DEFAULT_FEELING);

            LinkedList<Room> list = new LinkedList<>();
            for(int n=0;n<20;n++) list.add(viewer.area.info.architecture.biome.roomSelector.select(7+2*R.nextInt(4), 7+2*R.nextInt(4)));
            //BurrowCaveGrower grower = new BurrowCaveGrower(viewer.area, 0.3, 2, 9, 4, 5, 20, true);
            new RandomRoomPlacer(viewer.area, list, r -> r.addDoorsSparcely(viewer.area)).generate();
            viewer.area.refreshGraph();
            //new SpiderCorridorBuilder(viewer.area, 3, 4, CorridorBuilder.gaussianKernel(new Point(40, 79), 120, 24)).build();
            new OneToOneCorridorBuilder(viewer.area, 2, null).build();
            //new DenseFractalRoomPlacer(viewer.area, 0).generate();
            //CaveGrower grower = new CaveGrower(viewer.area, 0.48, 7);
            //grower.build();
            //viewer.area.refreshGraph();
            //grower.buildCorridors();

            viewer.area.growGrass();
            viewer.area.spillWater();
            viewer.area.initializeImages();
            //DungeonViewer.ANIMATOR.addGenerator(viewer.area.info.settings.getTorchAnimation(80, 80));

            viewer.start();
        }catch(Exception e){
            PERFORMANCE_LOG.log(e);
            throw e;
        }
    }
    
}
