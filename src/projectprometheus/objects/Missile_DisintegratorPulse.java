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
 */
package com.adcox.projectprometheus.objects;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Andrew
 * @version May 21, 2010
 *
 * A Disintegrator Pulse flys vertically upward and kills all ships, but only
 * kills one life
 */
public class Missile_DisintegratorPulse extends Missile {

    public Missile_DisintegratorPulse(Color color, Point location, int width, boolean goingUp, int speed, boolean hittable){
        super(color, location, width, goingUp, speed, hittable);
        
    }//====================================================
    
    @Override       //don't respond to a hit
    public void respondToHit(){}
    
    @Override
    public void draw(Graphics2D g){
        Point origin = new Point();
        if(goingUp)
            origin = new Point(centerLoc.x, centerLoc.y + speed);
        else
            origin = new Point(centerLoc.x, centerLoc.y - speed);
        // Create and setup the gradient paint and special stroke.
        g.setPaint(new GradientPaint(origin, Color.BLACK, centerLoc, color));

        g.fillArc(centerLoc.x - width/2, centerLoc.y, width, width/2, 0, 180);
        
    }//=======================================
}
