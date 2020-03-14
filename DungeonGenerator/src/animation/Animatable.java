/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animation;

/**
 * An entity that has an associated Animation.
 * @author Adam Whittaker
 */
public interface Animatable{
    
    /**
     * Creates the Animation of this entity.
     * @param x The tile x.
     * @param y The tile y.
     * @return
     */
    public Animation createAnimation(int x, int y);
    
}
