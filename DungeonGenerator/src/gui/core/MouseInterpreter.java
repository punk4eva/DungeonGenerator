
package gui.core;

import static gui.core.DungeonViewer.HEIGHT;
import static gui.core.DungeonViewer.WIDTH;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import static utils.Utils.PERFORMANCE_LOG;
import utils.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 * 
 * Handles Mouse input.
 */
public class MouseInterpreter extends MouseAdapter{
    
    public volatile static int focusX, focusY;
    private int xOfDrag=-1, yOfDrag=-1;
    public volatile static double zoom = 1.0;
    public static final double MAX_ZOOM = 8.0, MIN_ZOOM = 0.512;
    
    @Override
    @Unfinished("Complete (might remove entire class)")
    public void mouseClicked(MouseEvent me){
        if(me.isConsumed()) return;
        //Integer[] p = pixelToTile(me.getX(), me.getY());
        //Window.VIEWER.click(p[0], p[1]);
    }
    
    @Override
    public void mouseReleased(MouseEvent me){
        if(xOfDrag!=-1){
            xOfDrag = -1;
        }
    }
    
    @Override
    public void mouseDragged(MouseEvent me){
        if(xOfDrag == -1){
            xOfDrag = (int)(me.getX()/zoom) - focusX;
            yOfDrag = (int)(me.getY()/zoom) - focusY;        
        }
        focusX = (int)(me.getX()/zoom) - xOfDrag;
        focusY = (int)(me.getY()/zoom) - yOfDrag;
    }
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent me){
        double x = focusX-me.getX()/zoom, y = focusY-me.getY()/zoom;
        switch(me.getWheelRotation()){
            case -1: if(zoom<MAX_ZOOM){
                synchronized(this){
                    zoom *= 1.25;
                    setFocusDirectly((int)(x+me.getX()/zoom), (int)(y+me.getY()/zoom));
                }
                PERFORMANCE_LOG.printZoom(zoom);
            }
                break;
            default: if(zoom>MIN_ZOOM){
                synchronized(this){
                    zoom /= 1.25;
                    setFocusDirectly((int)(x+me.getX()/zoom), (int)(y+me.getY()/zoom));
                }
                PERFORMANCE_LOG.printZoom(zoom);
            }
        }
        //setFocusBounds(Window.VIEWER.area);
    }
    
    /**
     * Translates on-screen pixel click coordinates to tile coordinates.
     * @param mx The x coordinate of the click.
     * @param my The y coordinate of the click.
     * @return An int array representing the tile coordinates.
     */
    public static Integer[] pixelToTile(double mx, double my){
        return new Integer[]{Math.floorDiv((int)(mx/zoom)-focusX, 16), Math.floorDiv((int)(my/zoom)-focusY, 16)};
    }
    
    /**
     * Translates tile coordinates into on-screen pixel coordinates.
     * @param tx The x coordinate of the click.
     * @param ty The y coordinate of the click.
     * @return An int array representing the on-screen coordinates.
     */
    public static Integer[] tileToPixel(int tx, int ty){
        return new Integer[]{16*tx+8+focusX, 16*ty+8+focusY};
    }
    
    /**
     * Sets the focus based on the tile coordinates.
     * @param tilex
     * @param tiley
     */
    public void setTileFocus(int tilex, int tiley){
        focusX = (int)(WIDTH/zoom)/2 - tilex * 16;
        focusY = (int)(HEIGHT/zoom)/2 - tiley * 16;
    }
    
    /**
     * Sets the focus based on the pixel coordinates.
     * @param x
     * @param y
     */
    public void setPixelFocus(int x, int y){
        focusX = (int)(WIDTH/zoom/2d) - x;
        focusY = (int)(HEIGHT/zoom/2d) - y;
    }
    
    /**
     * Sets the focus directly (top-left rather than centre).
     * @param x The x pixel
     * @param y The y pixel
     */
    public void setFocusDirectly(int x, int y){
        focusX = x;
        focusY = y;
    }
    
    /**
     * Sets the focus bounds for a given Area.
     * @param area The Area.
     */
    /*public void setFocusBounds(Area area){
        int temp = WIDTH - 32 - (int)((area.info.width*16)*zoom);
        if(temp > 16){
            maxFX = temp;
            minFX = 16;
        }else{
            maxFX = 16;
            minFX = temp;
        }
        temp = HEIGHT - 32 - (int)((area.info.height*16)*zoom);
        if(temp > 16){
            maxFY = temp;
            minFY = 16;
        }else{
            maxFY = 16;
            minFY = temp;
        }
    }*/
    
    /**
     * Returns the centre coordinates of the screen.
     * @return
     */
    public static int[] getCenter(){
        return new int[]{(int)(WIDTH/zoom/2d), (int)(HEIGHT/zoom/2d)};
    }
    
}
