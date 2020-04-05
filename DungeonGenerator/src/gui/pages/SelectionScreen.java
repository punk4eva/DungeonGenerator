
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
import gui.questions.InputCollector;
import gui.questions.QuestionBox;
import gui.questions.Specifier;
import gui.tools.Button;
import gui.tools.NavigationButton;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

/**
 * A screen that lets the user specify the dungeon parameters.
 * @author Adam Whittaker
 */
public class SelectionScreen extends MouseAdapter implements Screen{
    
    
    /**
     * input: The current question posed to the user.
     * collector: The entity that accumulates all input form the user so far.
     * boxList: Stores the list of questions for the user.
     * currentIndex: The index of the current question in the boxList.
     * next/prevButton: Buttons to navigate the question menu.
     */
    private QuestionBox input;
    private final InputCollector collector = new InputCollector();
    private final LinkedList<QuestionBox> boxList = new LinkedList<>();
    private int currentIndex = 0;
    private final Button nextButton = new NextButton(), 
            prevButton = new BackButton();
    
    
    /**
     * Creates a new instance.
     * @param v The viewer.
     */
    public SelectionScreen(DungeonViewer v){
        v.addMouseListener(this);
        boxList.add(BIOME_MENU);
        boxList.add(SOCIETY_SPECIFIER);
        boxList.add(DIMENSION_SPECIFIER);
        boxList.add(ROOM_PLACER_MENU);
        setQuestionBox(boxList.getFirst());
    }

    
    @Override
    public void paint(Graphics2D g, int frames){
        g.setColor(DungeonViewer.BACKGROUND_COLOR);
        input.paint(g);
        nextButton.paint(g);
        if(currentIndex>0) prevButton.paint(g);
        ANIMATOR.animate(g, 0, 0, frames);
    }
    
    /**
     * Changes the current question box.
     * @param box The new box.
     */
    public final void setQuestionBox(QuestionBox box){
        if(box != null){
            if(input != null) input.deregisterKeys(VIEWER);
            input = box;
            input.registerKeys(VIEWER);
        }
    }
    
    /**
     * Gets the input collector.
     * @return
     */
    public final InputCollector getInputCollector(){
        return collector;
    }
    
    /**
     * Navigates to the previous question.
     */
    public void previousQuestion(){
        currentIndex--;
        setQuestionBox(boxList.get(currentIndex));
    }
    
    /**
     * Navigates to the next question.
     */
    public void nextQuestion(){
        //Checks if there are more questions remaining.
        if(currentIndex<boxList.size()-1){
            //Increments the question index until there is a valid question.
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
            //Otherwise creates the dungeon.
        }else finish();
    }
    
    /**
     * Adds another question to the boxList.
     * @param box
     */
    public void addQuestionBox(QuestionBox box){
        boxList.add(box);
    }
    
    /**
     * Creates the dungeon using the user's parameters.
     */
    public void finish(){
        VIEWER.setState(State.LOADING);
        
        SCREEN.setArea(collector.createArea());
        SCREEN.setTileFocus(SCREEN.getArea().info.width/2, SCREEN.getArea().info.height/2);
        
        VIEWER.setState(State.VIEWING);
    }
    
    /**
     * Checks whether the question is blank (no user options).
     * @param box The question.
     * @return true if it is.
     */
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
    
    
    /**
     * The button that goes to the next question.
     */
    private class NextButton extends NavigationButton{

        
        /**
         * Creates an instance.
         */
        public NextButton(){
            super(5*WIDTH/8, "Next");
        }

        
        @Override
        public void click(int mx, int my){
            input.process(SelectionScreen.this);
            nextQuestion();
        }
        
    }
    
    
    /**
     * The button that goes to the previous question.
     */
    private class BackButton extends NavigationButton{

        
        /**
         * Creates an instance.
         */
        public BackButton(){
            super(WIDTH/4, "Back");
        }

        
        @Override
        public void click(int mx, int my){
            previousQuestion();
        }
        
    }

}
