
package generation.corridors;

import components.Area;
import components.tiles.Tile;
import generation.PostCorridorPlacer;
import generation.Searcher;
import graph.Point;
import java.util.function.Function;

/**
 * This class builds corridors along a given path.
 * @author Adam Whittaker
 */
public abstract class CorridorBuilder extends Searcher implements PostCorridorPlacer{
    
    
    /**
     * Creates an instance.
     * @param a The Area.
     */
    public CorridorBuilder(Area a){
        super(a);
    }
    
    
    /**
     * Creates a 3x3 section of corridor around the given points.
     * @param p The previous point in the path.
     * @param a The current point in the path.
     * @param b The next point in the path.
     */
    protected void extend(Point p, Point a, Point b){
        if(!(area.map[b.y][b.x]!=null && area.map[b.y][b.x].equals(Point.Type.DOOR))) area.map[b.y][b.x] = Tile.genFloor(area);
        if(a.x!=b.x){
            if(area.map[b.y-1][b.x]==null){
                area.map[b.y-1][b.x] = Tile.genWall(area);
                area.graph.map[b.y-1][b.x].isCorridor = true;
            }
            if(area.map[b.y+1][b.x]==null){
                area.map[b.y+1][b.x] = Tile.genWall(area);
                area.graph.map[b.y+1][b.x].isCorridor = true;
            }
            if(area.map[a.y-1][a.x]==null){
                area.map[a.y-1][a.x] = Tile.genWall(area);
                area.graph.map[a.y-1][a.x].isCorridor = true;
            }
            if(area.map[a.y+1][a.x]==null){
                area.map[a.y+1][a.x] = Tile.genWall(area);
                area.graph.map[a.y+1][a.x].isCorridor = true;
            }
        }else{
            if(area.map[b.y][b.x-1]==null){
                area.map[b.y][b.x-1] = Tile.genWall(area);
                area.graph.map[b.y][b.x-1].isCorridor = true;
            }
            if(area.map[b.y][b.x+1]==null){
                area.map[b.y][b.x+1] = Tile.genWall(area);
                area.graph.map[b.y][b.x+1].isCorridor = true;
            }
            if(area.map[a.y][a.x-1]==null){
                area.map[a.y][a.x-1] = Tile.genWall(area);
                area.graph.map[a.y][a.x-1].isCorridor = true;
            }
            if(area.map[a.y][a.x+1]==null){
                area.map[a.y][a.x+1] = Tile.genWall(area);
                area.graph.map[a.y][a.x+1].isCorridor = true;
            }
        }
        if(p==null||a.x!=p.x){
            for(int y=a.y-1;y<=a.y+1;y+=2){
                for(int x=a.x-1;x<=a.x+1;x+=2){
                    area.map[y][x] = Tile.genWall(area);
                    area.graph.map[y][x].isCorridor = true;
                }
            }
        }
    }
    
    /**
     * Builds a corridor from a singular Point after a flood fill.
     * @param p
     */
    protected void buildCorridor(Point p){
        Point np, pp = null;
        while(p.cameFrom!=p&&p.cameFrom!=null){
            np = p.cameFrom;
            //Builds the corridor slightly.
            extend(pp, p, np);
            pp = p;
            p = np;
        }
    }
    
    /**
     * A function for a sinusoidal wave emanating from the centre of the Area.
     * @param shift The translation of the input points.
     * @param amplitude The amplitude of the oscillations.
     * @param deviation 2PI * wavelength of the oscillations. 
     * @param phaseShift The phase shift of the cosine function used.
     * @return A function generating a sinusoidal wave with the given parameters.
     */
    public static Function<Point, Double> circularWave(Point shift, double amplitude, double deviation, double phaseShift){
        return p -> amplitude*(Math.cos(euclideanDist(p, shift)/deviation + phaseShift) + 1D)/2D;
    }
    
    /**
     * A function for a manhattan wave emanating from the centre of the Area.
     * @param shift The translation of the input points.
     * @param amplitude The amplitude of the oscillations.
     * @param deviation 2PI * wavelength of the oscillations. 
     * @param phaseShift The phase shift of the cosine function used.
     * @return A function generating a manhattan wave with the given parameters.
     */
    public static Function<Point, Double> diamondWave(Point shift, double amplitude, double deviation, double phaseShift){
        return p -> amplitude*(Math.cos(manhattanDist(p, shift)/deviation + phaseShift) + 1D);
    }
    
    /**
     * A stereotypical Gaussian kernel.
     * @param shift The translation of the input points.
     * @param amplitude The amplitude of the oscillations.
     * @param variance The variance of the kernel.
     * @return A function generating a Gaussian kernel.
     */
    public static Function<Point, Double> gaussianKernel(Point shift, double amplitude, double variance){
        return p -> amplitude*Math.exp(-(Math.pow(p.x-shift.x, 2) + Math.pow(p.y-shift.y, 2))/variance);
    }
    
}
