/**
 *  Project Prometheus: A Stargate SG-1 version of Space Invaders
 * 
 *  Copyright (C) 2010 Andrew Cox
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  To contact me, send an email to andrewandstuff@gmail.com or visit 
 *  <http://andrew.swcox.com/Of_Andrew_and_Stuff/Programming.html>
 */
package projectprometheus.objects;

import java.awt.Color;
import java.awt.Point;

/**
 *
 * @author Andrew
 * @version May 21, 2010
 *
 * A Disruptor Pulse flys and looks just like a Disintegrator Pulse, but
 * a Disruptor Pulse depletes the enemy's lives by 2 instead of 1.
 */
public class Missile_DisruptorPulse extends Missile_DisintegratorPulse {
    
    public Missile_DisruptorPulse(Color color, Point location, int width, boolean goingUp, int speed, boolean hittable){
        super(color, location,width, goingUp, speed, hittable);
        
        damageInflicted = 2;
    }//=====================================================
}

