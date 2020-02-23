
package gui.pages;

import static biomes.Biome.BIOME_MENU;
import static biomes.Society.SOCIETY_SPECIFIER;
import static generation.RoomPlacer.ROOM_PLACER_MENU;
import gui.core.DungeonViewer;
import gui.core.DungeonViewer.State;
import static gui.core.DungeonViewer.WIDTH;
import static gui.core.Window.SCREEN;
import static gui.core.Window.VIEWER;
import static gui.pages.DungeonScreen.ANIMATOR;
import static gui.questions.DimensionSpecifier.DIMENSION_SPECIFIER;
import gui.tools.Button;
import gui.questions.InputCollector;
import gui.tools.NavigationButton;
import gui.questions.QuestionBox;
import gui.questions.Specifier;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import static utils.Utils.SPEED_TESTER;

/**
 *
 * @author Adam Whittaker
 */
public class SelectionScreen extends MouseAdapter implements Screen{
    
    
    private QuestionBox input;
    private final InputCollector collector = new InputCollector();
    private final LinkedList<QuestionBox> boxList = new LinkedList<>();
    private int currentIndex = 0;
    private final Button nextButton = new NextButton(), 
            prevButton = new BackButton();
    
    
    public SelectionScreen(DungeonViewer v){
        v.addMouseListener(this);
        boxList.add(BIOME_MENU);
        boxList.add(SOCIETY_SPECIFIER);
        boxList.add(DIMENSION_SPECIFIER);
        boxList.add(ROOM_PLACER_MENU);
    }

    
    @Override
    public void paint(Graphics2D g, int frames){
        g.setColor(DungeonViewer.BACKGROUND_COLOR);
        input.paint(g);
        nextButton.paint(g);
        if(currentIndex>0) prevButton.paint(g);
        ANIMATOR.animate(g, 0, 0, frames);
    }
    
    public final void setQuestionBox(QuestionBox box){
        if(input != null) input.deregisterKeys(VIEWER);
        input = box;
        input.registerKeys(VIEWER);
    }
    
    public final InputCollector getInputCollector(){
        return collector;
    }
    
    public void previousQuestion(){
        currentIndex--;
        setQuestionBox(boxList.get(currentIndex));
    }
    
    public void nextQuestion(){
        if(currentIndex<boxList.size()-1){
            currentIndex++;
            while(isVoidQuestion(boxList.get(currentIndex))){
                boxList.get(currentIndex).process(this);
                currentIndex++;
                if(currentIndex>=boxList.size()){
                    finish();
                    return;
                }
            }
            setQuestionBox(boxList.get(currentIndex));
        }else finish();
    }
    
    public void addQuestionBox(QuestionBox box){
        boxList.add(box);
    }
    
    public void finish(){
        VIEWER.setState(State.LOADING);
        
        SCREEN.setArea(collector.createArea());
        SCREEN.setTileFocus(SCREEN.getArea().info.width/2, SCREEN.getArea().info.height/2);
        
        VIEWER.setState(State.VIEWING);
    }
    
    private boolean isVoidQuestion(QuestionBox box){
        return (box instanceof Specifier) && ((Specifier) box).isEmpty();
    }
    
    
    @Override
    public void mouseClicked(MouseEvent me){
        if(VIEWER.getState().equals(State.CHOOSING)){
            input.click(me.getX(), me.getY());
            nextButton.testClick(me.getX(), me.getY());
            if(currentIndex>0) prevButton.testClick(me.getX(), me.getY());
        }
    }
    
    
    private class NextButton extends NavigationButton{

        
        public NextButton(){
            super(5*WIDTH/8, "Next");
        }

        
        @Override
        public void click(int mx, int my){
            input.process(SelectionScreen.this);
            nextQuestion();
        }
        
    }
    
    
    private class BackButton extends NavigationButton{

        
        public BackButton(){
            super(WIDTH/4, "Back");
        }

        
        @Override
        public void click(int mx, int my){
            previousQuestion();
        }
        
    }

}
