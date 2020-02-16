
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
 *
 * @author Adam Whittaker
 */
public class UIPanel extends MouseAdapter{
    
    
    public final static Color UI_COLOR = new Color(80, 80, 80);
    public final static Color BUTTON_COLOR = new Color(140, 140, 140);
    public static final Color BUTTON_TEXT_COLOR = new Color(40, 40, 40);
    public static final Font BUTTON_TEXT_FONT = new Font(Font.SERIF, Font.BOLD, 18);
    public static final int MINIMIZE_SPEED = 5;
    
    protected int x, width = WIDTH/8;
    protected final Button[] buttons;
    
    
    public UIPanel(int _x, Button[] butt){
        x = _x;
        buttons = butt;
    }
    
    
    public Button[] getButtons(){
        return buttons;
    }
    
    
    public void render(Graphics2D g){
        g.setColor(UI_COLOR);
        g.fillRect(x, 0, width, HEIGHT);
        for(Button b : buttons) b.render(g);
    }
    
    private void moveX(int plus){
        x += plus;
        for(Button b : buttons) b.setXY(b.x + plus, b.y);
    }
    
    private void snap(int _x){
        int change = _x - x;
        x = _x;
        for(Button b : buttons) b.setXY(b.x + change, b.y);
    }
    
    private int getX(){
        return x;
    }
    
    @Override
    public void mouseClicked(MouseEvent me){
        for(Button b : buttons) b.testClick(me.getX(), me.getY());
    }
    
    
    public class MinimizeButton extends Button{
        
        private boolean minimized = false;
        private final boolean left;
        private boolean moving = false;

        
        public MinimizeButton(int _x, int _y, int w, boolean l){
            super(_x, _y, w, w);
            left = l;
        }

        
        @Override
        public void click(int mx, int my){
            if(!moving){
                moving = true;
                if(minimized){
                    minimized = false;
                    addAnimation(left ? MINIMIZE_SPEED : -MINIMIZE_SPEED, UIPanel.this.width);
                }else{
                    minimized = true;
                    addAnimation(left ? -MINIMIZE_SPEED : MINIMIZE_SPEED, UIPanel.this.width);
                }
            }
        }
        
        public void addAnimation(int plus, int dur){
            ANIMATOR.add(new Animation(){
                    
                int duration = dur;
                int target = UIPanel.this.getX() + (int)(Math.signum(plus))*dur;

                @Override
                public void animate(Graphics2D g, int focusX, int focusY, int frames){
                    if(duration>0){
                        duration-=frames*MINIMIZE_SPEED;
                        if(duration>0) moveX(plus*frames);
                        //else moveX(-(int)(duration*Math.signum(plus)));
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
            if(minimized) paintText(g, "+");
            else paintText(g, "-");
        }
        
    }

}
