package generation;

import java.util.Random;

import graph.Point;
import utils.Vector;

public class PerlinNoiseGenerator{

    private static final Random R = new Random();
    static{
        //R.setSeed(4623779473289L);
    }

    private final Point[][] map;
    private final Vector[][] vec;
    private final int octaveNum;
    private final double lacunarity, persistence;
    private double amplitude;

    public PerlinNoiseGenerator(Point[][] m, double amp, int oc, double l, double p){
        map = m;
        vec = new Vector[m.length + 1][m[0].length + 1];
        octaveNum = oc;
        lacunarity = l;
        persistence = p;
        amplitude = amp;
        initializeRandomVectors();
    }

    private void initializeRandomVectors(){
        for(int y = 0; y < vec.length; y++){
            for(int x = 0; x < vec[0].length; x++){
                vec[y][x] = new Vector(R);
                //Utils.printArray(vec[y][x].v);
            }
        }
    }

    public void apply(){
        int xCoarse = 60, yCoarse = 60;
        for(int n = 0; n < octaveNum; n++){
            overlayOctave(amplitude, xCoarse, yCoarse);
            amplitude *= persistence;
            xCoarse *= lacunarity;
            yCoarse *= lacunarity;
        }
    }

    private double fade(double x){
        return x * x * x * (x * (x * 6 - 15) + 10);
    }

    private double interpolate(double a, double b, double x){
        return (1D - x) * a + x * b;
    }

    private void overlayOctave(double p, int xCoarse, int yCoarse){
        double min = 1000, max = -1000;
        for(int y = 0; y < map.length; y++){
            for(int x = 0; x < map[0].length; x++){
                map[y][x].value += p * getPerlin(x, y, xCoarse, yCoarse);
                if(min > map[y][x].value){
                    min = map[y][x].value;
                }
                if(max < map[y][x].value){
                    max = map[y][x].value;
                }
            }
        }
        System.out.println("Min: " + min * Math.sqrt(2D) + "; Max: " + max * Math.sqrt(2D));
    }

    private double getPerlin(int x, int y, int xC, int yC){
        double xN = fade((double) (x % xC) / xC), yN = fade((double) (y % yC) / yC),
                iU = interpolate(dotTL(x, y, xC, yC), dotTR(x, y, xC, yC), xN),
                iD = interpolate(dotBL(x, y, xC, yC), dotBR(x, y, xC, yC), xN);
        //System.out.println(" x: "+x+" y: "+y+" iU: "+iU+" iD: "+iD);
        return interpolate(iU, iD, yN);
    }

    private double dot(int x, int y, int xV, int yV, int xC, int yC){
        return vec[yV][xV].v[0] * (x - xV) / xC + vec[yV][xV].v[1] * (y - yV) / yC;
    }

    private double dotTL(int x, int y, int xC, int yC){
        int xV = x - (x % xC);
        int yV = y - (y % yC);
        return dot(x, y, xV, yV, xC, yC);
    }

    private double dotTR(int x, int y, int xC, int yC){
        try{
            int xV = x - (x % xC) + xC;
            int yV = y - (y % yC);
            return dot(x, y, xV, yV, xC, yC);
        }catch(ArrayIndexOutOfBoundsException e){
            return 0;
        }
    }

    private double dotBL(int x, int y, int xC, int yC){
        try{
            int xV = x - (x % xC);
            int yV = y - (y % yC) + yC;
            return dot(x, y, xV, yV, xC, yC);
        }catch(ArrayIndexOutOfBoundsException e){
            return 0;
        }
    }

    private double dotBR(int x, int y, int xC, int yC){
        try{
            int xV = x - (x % xC) + xC;
            int yV = y - (y % yC) + yC;
            return dot(x, y, xV, yV, xC, yC);
        }catch(ArrayIndexOutOfBoundsException e){
            return 0;
        }
    }

}
