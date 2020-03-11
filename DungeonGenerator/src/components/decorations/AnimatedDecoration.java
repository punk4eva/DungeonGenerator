/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components.decorations;

import animation.Animation;

/**
 *
 * @author Adam
 */
public interface AnimatedDecoration extends Decoration{
    
    
    public Animation createAnimation(int x, int y);
    
}
