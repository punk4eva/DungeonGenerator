package generation.noise;

import utils.Vector;

public class PerlinNoiseGenerator{

    
    private final Vector[][] vec;
    private final int octaveNum;
    private final double lacunarity, persistence;
    private double amplitude;

    
    public PerlinNoiseGenerator(int width, int height, double amp, int oc, double l, double p){
        vec = new Vector[height][width];
        octaveNum = oc;
        lacunarity = l;
        persistence = p;
        amplitude = amp;
        initializeRandomVectors();
    }

    
    private void initializeRandomVectors(){
        /*for(int y = 1; y < vec.length-1; y++){
            for(int x = 1; x < vec[0].length-1; x++){
                vec[y][x] = new Vector();
            }
        }
        vec[0][0] = vec[vec.length-2][vec[0].length-2];
        vec[vec.length-1][vec[0].length-1] = vec[1][1];
        vec[vec.length-1][0] = vec[1][vec[0].length-2];
        vec[0][vec[0].length-1] = vec[vec.length-2][1];
        for(int x=1;x<vec[0].length-1;x++){
            vec[0][x] = vec[vec.length-2][x];
            vec[vec.length-1][x] = vec[1][x];
        }
        for(int y=1;y<vec.length-1;y++){
            vec[y][0] = vec[y][vec.length-2];
            vec[y][vec.length-1] = vec[y][1];
        }*/
        //int w = vec[0].length/3, h = vec.length/3;
        for(int y = 0; y < vec.length; y++){
            for(int x = 0; x < vec[0].length; x++){
                //if(x<=w && y<=h) vec[y][x] = new Vector();
                //else vec[y][x] = vec[y%h][x%w];
                
                vec[y][x] = new Vector();
            }
        }
    }

    public void apply(double[][] map){
        int xCoarse = map[0].length/4, yCoarse = map.length/4;
        for(int n = 0; n < octaveNum; n++){
            overlayOctave(map, amplitude, xCoarse, yCoarse);
            amplitude *= persistence;
            xCoarse *= lacunarity;
            yCoarse *= lacunarity;
            if(xCoarse<1) xCoarse = 1;
            if(yCoarse<1) yCoarse = 1;
        }
        shiftMean(map, 125);
    }
    
    private void shiftMean(double[][] map, double mean){
        for(int y=0;y<map.length;y++){
            for(int x=0;x<map[0].length;x++){
                map[y][x] += mean;
            }
        }
    }

    private double fade(double x){
        return x * x * x * (x * (x * 6 - 15) + 10);
    }

    private double interpolate(double a, double b, double x){
        return (1D - x) * a + x * b;
    }

    private void overlayOctave(double[][] map, double p, int xCoarse, int yCoarse){
        double min = Integer.MAX_VALUE, max = -Integer.MIN_VALUE;
        for(int y = 0; y < map.length; y++){
            for(int x = 0; x < map[0].length; x++){
                map[y][x] += p * getPerlin(x, y, xCoarse, yCoarse);
                if(min > map[y][x]){
                    min = map[y][x];
                }
                if(max < map[y][x]){
                    max = map[y][x];
                }
            }
        }
        System.out.println("Min: " + min * Math.sqrt(2D) + "; Max: " + max * Math.sqrt(2D));
    }

    private double getPerlin(int x, int y, int xC, int yC){
        double xN = fade((double) (x % xC) / xC), 
                yN = fade((double) (y % yC) / yC),
                iU = interpolate(dotTL(x, y, xC, yC), dotTR(x, y, xC, yC), xN),
                iD = interpolate(dotBL(x, y, xC, yC), dotBR(x, y, xC, yC), xN);
        return interpolate(iU, iD, yN);
    }

    private double dot(double x, double y, int xV, int yV, double xC, double yC){
        int j = yV % vec.length;
        int i = xV % vec[0].length;
        //try{
            return vec[j][i].v[0] * (x - (double)xV) / xC + vec[j][i].v[1] * (y - (double)yV) / yC;
        /*}catch(Exception e){
            System.out.println("vector: " + vec[yV][xV] + ", " + xV + ", " + yV);
            throw new IllegalStateException();
        }*/
    }

    private double dotTL(int x, int y, int xC, int yC){
        int xV = x - (x % xC);
        int yV = y - (y % yC);
        return dot(x, y, xV, yV, xC, yC);
    }

    private double dotTR(int x, int y, int xC, int yC){
        //try{
            int xV = x - (x % xC) + xC;
            int yV = y - (y % yC);
            return dot(x, y, xV, yV, xC, yC);
        /*}catch(ArrayIndexOutOfBoundsException e){
            return 0;
        }*/
    }

    private double dotBL(int x, int y, int xC, int yC){
        //try{
            int xV = x - (x % xC);
            int yV = y - (y % yC) + yC;
            return dot(x, y, xV, yV, xC, yC);
        /*}catch(ArrayIndexOutOfBoundsException e){
            return 0;
        }*/
    }

    private double dotBR(int x, int y, int xC, int yC){
        //try{
            int xV = x - (x % xC) + xC;
            int yV = y - (y % yC) + yC;
            return dot(x, y, xV, yV, xC, yC);
        /*}catch(ArrayIndexOutOfBoundsException e){
            return 0;
        }*/
    }

}
