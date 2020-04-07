
package gui.tools;

import animation.Animation;
import static gui.core.DungeonViewer.HEIGHT;
import static gui.core.DungeonViewer.WIDTH;
import static gui.pages.DungeonScreen.ANIMATOR;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The base blueprint class for all panels.
 * @author Adam Whittaker
 */
public class UIPanel extends MouseAdapter{
    
    
    /**
     * UI_COLOR: The color of the background of the panel.
     * BUTTON_COLOR: The color of buttons.
     * BUTTON_TEXT_COLOR: The color of the text of buttons.
     * BUTTON_TEXT_FONT: The font of the text in buttons.
     * MINIMIZE_SPEED: The pixels per tick that the UI moves when minimizing.
     * PANEL_WIDTH: The width of the panels.
     * x: The current x coordinate of the panel.
     * buttons: All the buttons in the panel.
     */
    public final static Color UI_COLOR = new Color(80, 80, 80);
    public final static Color BUTTON_COLOR = new Color(140, 140, 140);
    public static final Color BUTTON_TEXT_COLOR = new Color(40, 40, 40);
    public static final Font BUTTON_TEXT_FONT = new Font(Font.SERIF, Font.BOLD, 24*HEIGHT/1080);
    public static final int MINIMIZE_SPEED = 5;
    public static final int PANEL_WIDTH = WIDTH/8;
    
    protected int x;
    protected final Button[] buttons;
    
    
    /**
     * Creates an instance.
     * @param _x The x coordinate of the panel.
     * @param butt The buttons in the panel.
     */
    public UIPanel(int _x, Button[] butt){
        x = _x;
        buttons = butt;
    }
    
    
    /**
     * Gets all the buttons in the panel for debugging purposes.
     * @return an array.
     */
    public Button[] getButtons(){
        return buttons;
    }
    
    
    /**
     * Renders this panel.
     * @param g The graphics.
     */
    public void render(Graphics2D g){
        g.setColor(UI_COLOR);
        g.fillRect(x, 0, PANEL_WIDTH, HEIGHT);
        for(Button b : buttons) b.paint(g);
    }
    
    /**
     * Increments the x coordinate of this panel by the given amount.
     * @param plus The amount to move right in pixels.
     */
    private void moveX(int plus){
        //Move the background.
        x += plus;
        //Move the buttons.
        for(Button b : buttons) b.setXY(b.x + plus, b.y);
    }
    
    /**
     * Sets the x coordinate of the panel.
     * @param _x The new x coordinate.
     */
    private void snap(int _x){
        moveX(_x-x);
    }
    
    /**
     * Gets the top-left x coordinate of the panel.
     * @return
     */
    private int getX(){
        return x;
    }
    
    @Override
    public void mouseClicked(MouseEvent me){
        for(Button b : buttons) b.testClick(me.getX(), me.getY());
    }
    
    
    /**
     * The button that minimizes the panel.
     */
    public class MinimizeButton extends Button{
        
        
        /**
         * minimized: Whether the panel is minimized.
         * left: Whether the panel is on the left of the screen.
         * moving: Whether the panel is currently minimizing/un-minimizing.
         */
        private boolean minimized = false;
        private final boolean left;
        private boolean moving = false;

        
        /**
         * Creates a new instance.
         * @param _x The x coordinate.
         * @param _y The y coordinate.
         * @param w The length of a side of the button.
         * @param l Whether the panel is on the left.
         */
        public MinimizeButton(int _x, int _y, int w, boolean l){
            super(_x, _y, w, w);
            left = l;
        }

        
        @Override
        public void click(int mx, int my){
            if(!moving){
                moving = true;
                //Decides whether to move the panel left or right.
                if(minimized){
                    minimized = false;
                    addAnimation(left ? MINIMIZE_SPEED : -MINIMIZE_SPEED, UIPanel.this.PANEL_WIDTH);
                }else{
                    minimized = true;
                    addAnimation(left ? -MINIMIZE_SPEED : MINIMIZE_SPEED, UIPanel.this.PANEL_WIDTH);
                }
            }
        }
        
        /**
         * Adds the minimize animation to the animator.
         * @param plus The pixels to move to the right each tick.
         * @param dur The duration of the animation in ticks.
         */
        public void addAnimation(int plus, int dur){
            ANIMATOR.add(new Animation(){
                    
                int duration = dur;
                int target = UIPanel.this.getX() + (int)(Math.signum(plus))*dur;

                @Override
                public void animate(Graphics2D g, int focusX, int focusY, int frames){
                    if(duration>0){
                        duration-=frames*MINIMIZE_SPEED;
                        if(duration>0) moveX(plus*frames);
                    }else{
                        done = true;
                        moving = false;
                        snap(target);
                    }
                }

            });
        }

        @Override
        public void paint(Graphics2D g){
            paintButtonBox(g);
            if(minimized) paintText(g, "+");
            else paintText(g, "-");
        }
        
    }

}
