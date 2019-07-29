
package generation.noise;

import static utils.Utils.R;

public class MidpointDisplacer{

    private final double initialHeight, initialJitter, jitterDecay, maxHeight;
    private final boolean preserveArtifacts, tileArtifacts;

    public MidpointDisplacer(double iH, double iJ, double jD, double mH, boolean pA, boolean tA){
        initialJitter = iJ;
        jitterDecay = jD;
        initialHeight = iH;
        maxHeight = mH;
        preserveArtifacts = pA;
        tileArtifacts = tA;
    }

    public void apply(double[][] map){
        map[0][0] = R.nextDouble() * initialHeight;
        map[0][map[0].length - 1] = R.nextDouble() * initialHeight;
        map[map.length - 1][0] = R.nextDouble() * initialHeight;
        map[map.length - 1][map[0].length - 1] = R.nextDouble() * initialHeight;
        
        fillRectangle(map, 0, 0, map[0].length - 1, map.length - 1, initialJitter);
    }

    private void fillRectangle(double[][] map, int tlx, int tly, int brx, int bry, double jitter){
        int xA = (tlx + brx) / 2, yA = (tly + bry) / 2;

        if(map[tly][xA] == 0 || preserveArtifacts)
            map[tly][xA] = (map[tly][tlx] + map[tly][brx]) / 2D + R.nextDouble() * jitter - jitter / 2;
        if(map[bry][xA]== 0 || preserveArtifacts)
            map[bry][xA] = (map[bry][tlx] + map[bry][brx]) / 2D + R.nextDouble() * jitter - jitter / 2;       
        if(map[yA][tlx]== 0 || preserveArtifacts)
            map[yA][tlx] = (map[tly][tlx] + map[bry][tlx]) / 2D + R.nextDouble() * jitter - jitter / 2;
        if(map[yA][brx]== 0 || preserveArtifacts)
            map[yA][brx] = (map[tly][brx] + map[bry][brx]) / 2D + R.nextDouble() * jitter - jitter / 2;

        if(map[tly][xA] > maxHeight) map[tly][xA] = maxHeight;
        if(map[bry][xA] > maxHeight) map[bry][xA] = maxHeight;
        if(map[yA][tlx] > maxHeight) map[yA][tlx] = maxHeight;
        if(map[yA][brx] > maxHeight) map[yA][brx] = maxHeight;

        if(map[tly][xA] < 0) map[tly][xA] = 0;
        if(map[bry][xA] < 0) map[bry][xA] = 0;
        if(map[yA][tlx] < 0) map[yA][tlx] = 0;
        if(map[yA][brx] < 0) map[yA][brx] = 0;

        if(xA == tlx || yA == tly) return;

        map[yA][xA] = (map[tly][tlx] + map[tly][brx]
                + map[bry][tlx] + map[bry][brx]) / 4D + R.nextDouble() * jitter;
        if(!tileArtifacts) map[yA][xA] -= jitter / 2;
        
        if(map[yA][xA] > maxHeight) map[yA][xA] = maxHeight;
        else if(map[yA][xA] < 0) map[yA][xA] = 0;
        
        fillRectangle(map, xA, yA, brx, bry, jitter * jitterDecay);
        fillRectangle(map, tlx, yA, xA, bry, jitter * jitterDecay);
        fillRectangle(map, xA, tly, brx, yA, jitter * jitterDecay);
        fillRectangle(map, tlx, tly, xA, yA, jitter * jitterDecay);
    }

}
