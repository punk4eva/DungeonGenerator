
package gui.pages;

import gui.core.DungeonViewer;
import gui.core.DungeonViewer.State;
import static gui.core.DungeonViewer.WIDTH;
import static gui.core.Window.VIEWER;
import gui.tools.Button;
import gui.questions.InputCollector;
import gui.tools.NavigationButton;
import gui.questions.QuestionBox;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

/**
 *
 * @author Adam Whittaker
 */
public class SelectionScreen extends MouseAdapter implements Screen{
    
    
    private QuestionBox input;
    private final InputCollector collector = new InputCollector();
    private final LinkedList<QuestionBox> boxList = new LinkedList<>();
    private final Button nextButton = new NextButton(), 
            prevButton = new BackButton();
    
    
    public SelectionScreen(DungeonViewer v){
        v.addMouseListener(this);
    }

    
    @Override
    public void paint(Graphics2D g, int frames){
        g.setColor(DungeonViewer.BACKGROUND_COLOR);
        input.paint(g);
        nextButton.paint(g);
        if(boxList.size() != 1) prevButton.paint(g);
    }
    
    public final void setQuestionBox(QuestionBox box){
        if(input != null) input.deregisterKeys(VIEWER);
        boxList.add(box);
        input = box;
        input.registerKeys(VIEWER);
    }
    
    public final InputCollector getInputCollector(){
        return collector;
    }
    
    public void previousQuestion(){
        input.deregisterKeys(VIEWER);
        boxList.removeLast();
        input = boxList.getLast();
        input.registerKeys(VIEWER);
    }
    
    
    @Override
    public void mouseClicked(MouseEvent me){
        if(VIEWER.getState().equals(State.CHOOSING)){
            input.click(me.getX(), me.getY());
            nextButton.testClick(me.getX(), me.getY());
            prevButton.testClick(me.getX(), me.getY());
        }
    }
    
    
    private class NextButton extends NavigationButton{

        
        public NextButton(){
            super(5*WIDTH/8, "Next");
        }

        
        @Override
        public void click(int mx, int my){
            QuestionBox qBox = input.processAndNext(SelectionScreen.this);
            if(qBox == null) ;
            else setQuestionBox(qBox);
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
