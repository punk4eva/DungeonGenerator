
package generation;

import static utils.Utils.R;

import graph.Point;

public class MidpointDisplacer{

    private final double initialHeight, initialJitter, jitterDecay, maxHeight;
    private final boolean preserveArtifacts;

    public MidpointDisplacer(double iH, double iJ, double jD, double mH, boolean pA){
        initialJitter = iJ;
        jitterDecay = jD;
        initialHeight = iH;
        maxHeight = mH;
        preserveArtifacts = pA;
    }

    public void apply(Point[][] map){
        map[0][0].value = R.nextDouble() * initialHeight;
        map[0][map[0].length - 1].value = R.nextDouble() * initialHeight;
        map[map.length - 1][0].value = R.nextDouble() * initialHeight;
        map[map.length - 1][map[0].length - 1].value = R.nextDouble() * initialHeight;
        fillSquare(map, 0, 0, map.length - 1, map[0].length - 1, initialJitter);
    }

    public void fillSquare(Point[][] map, int tlx, int tly, int brx, int bry, double jitter){
        int xA = (tlx + brx) / 2, yA = (tly + bry) / 2;

        if(map[tly][xA].value == 0 || preserveArtifacts){
            map[tly][xA].value = (map[tly][tlx].value + map[tly][brx].value) / 2D + R.nextDouble() * jitter - jitter / 2;
        }
        if(map[bry][xA].value == 0 || preserveArtifacts){
            map[bry][xA].value = (map[bry][tlx].value + map[bry][brx].value) / 2D + R.nextDouble() * jitter - jitter / 2;
        }
        if(map[yA][tlx].value == 0 || preserveArtifacts){
            map[yA][tlx].value = (map[tly][tlx].value + map[bry][tlx].value) / 2D + R.nextDouble() * jitter - jitter / 2;
        }
        if(map[yA][brx].value == 0 || preserveArtifacts){
            map[yA][brx].value = (map[tly][brx].value + map[bry][brx].value) / 2D + R.nextDouble() * jitter - jitter / 2;
        }

        if(map[tly][xA].value > maxHeight){
            map[tly][xA].value = maxHeight;
        }
        if(map[bry][xA].value > maxHeight){
            map[bry][xA].value = maxHeight;
        }
        if(map[yA][tlx].value > maxHeight){
            map[yA][tlx].value = maxHeight;
        }
        if(map[yA][brx].value > maxHeight){
            map[yA][brx].value = maxHeight;
        }

        if(map[tly][xA].value < 0){
            map[tly][xA].value = 0;
        }
        if(map[bry][xA].value < 0){
            map[bry][xA].value = 0;
        }
        if(map[yA][tlx].value < 0){
            map[yA][tlx].value = 0;
        }
        if(map[yA][brx].value < 0){
            map[yA][brx].value = 0;
        }

        if(xA == tlx || yA == tly){
            return;
        }

        map[yA][xA].value = (map[tly][tlx].value + map[tly][brx].value
                + map[bry][tlx].value + map[bry][brx].value) / 4D + R.nextDouble() * jitter - jitter / 2;
        if(map[yA][xA].value > maxHeight){
            map[yA][xA].value = maxHeight;
        }else if(map[yA][xA].value < 0){
            map[yA][xA].value = 0;
        }

        fillSquare(map, xA, yA, brx, bry, jitter * jitterDecay);
        fillSquare(map, tlx, yA, xA, bry, jitter * jitterDecay);
        fillSquare(map, xA, tly, brx, yA, jitter * jitterDecay);
        fillSquare(map, tlx, tly, xA, yA, jitter * jitterDecay);
    }

}
