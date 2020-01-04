
package gui.tools;

import animation.Animation;
import static gui.core.DungeonViewer.ANIMATOR;
import static gui.core.DungeonViewer.HEIGHT;
import static gui.core.DungeonViewer.WIDTH;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
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
    
    protected int x, width = WIDTH/6;
    protected final Button[] buttons;
    
    
    public UIPanel(int _x, Button[] butt){
        x = _x;
        buttons = butt;
    }
    
    
    public void render(Graphics g){
        g.setColor(UI_COLOR);
        g.fillRect(x, 0, width, HEIGHT);
        for(Button b : buttons) b.render(g);
    }
    
    private void moveX(int plus){
        x += plus;
        for(Button b : buttons) b.setXY(b.x + plus, b.y);
    }
    
    @Override
    public void mouseClicked(MouseEvent me){
        for(Button b : buttons) b.testClick(me.getX(), me.getY());
    }
    
    
    public class MinimizeButton extends Button{
        
        private boolean minimized = false;
        private final boolean left;

        
        public MinimizeButton(int _x, int _y, int w, boolean l){
            super(_x, _y, w, w);
            left = l;
        }

        
        @Override
        public void click(){
            if(minimized){
                minimized = false;
                addAnimation(left ? MINIMIZE_SPEED : -MINIMIZE_SPEED, UIPanel.this.width);
            }else{
                minimized = true;
                addAnimation(left ? -MINIMIZE_SPEED : MINIMIZE_SPEED, UIPanel.this.width);
            }
        }
        
        public void addAnimation(int plus, int dur){
            ANIMATOR.add(new Animation(-1, -1){
                    
                int duration = dur;

                @Override
                public void animate(Graphics2D g, int focusX, int focusY, int frames){
                    if(duration>0){
                        duration-=frames*MINIMIZE_SPEED;
                        if(duration>0) moveX(plus*frames);
                        else moveX(plus*(frames+duration));
                    }else done = true;
                }

            });
        }

        @Override
        public void paint(Graphics g){
            g.setFont(BUTTON_TEXT_FONT);
            g.setColor(BUTTON_TEXT_COLOR);
            FontMetrics f = g.getFontMetrics();
            if(minimized) g.drawString("+", x+(width - f.charWidth('+'))/2, y + width/2 + f.getDescent());
            else g.drawString("-", x+(width - f.charWidth('-'))/2, y + width/2 + f.getDescent());
        }
        
    }

}
