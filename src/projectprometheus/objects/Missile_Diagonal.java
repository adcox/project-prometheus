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

import java.awt.BasicStroke;
import com.adcox.projectprometheus.userInterface.ProjectPrometheus;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Andrew
 * @Version May 19, 2010
 * 
 * DiagonalMissile flys upward until it hits a missile, then it moves diagonaly,
 * splitting into two missiles
 */
public class Missile_Diagonal extends Missile{
    
    private int hitCount = 0;
    private boolean hasTurned = false;
    private boolean goNE = false;
    private int xVel, yVel;
    private ProjectPrometheus pp;
    
    public Missile_Diagonal(Color color, Point location, int width, boolean goingUp, int speed, boolean hittable, boolean goNE, ProjectPrometheus pp){
        super(color, location, width, goingUp, speed, hittable);
        this.pp = pp;
        this.goNE = goNE;
        xVel = 0;
        yVel = speed;
    }//====================================================
      
    
    @Override
    public void respondToHit(){
        hitCount++;
        if(hitCount == 1){
            hasTurned = true;
            xVel = yVel;
            //"split" the orginal missile
            if(!goNE){  //the first missile goes NW, so it will split, but the second, which goes NE wont
                Missile_Diagonal temp = new Missile_Diagonal(color, centerLoc, width, goingUp, speed, hittable, true, pp);
                temp.respondToHit();
                pp.addToMissiles(temp);
            }
        }
    }//=================================
    
    @Override
    public void process(){
        //if the missile has turned, it moves at a 45 degree angle to the north-east
        if(hasTurned){
            if(goNE)
                centerLoc.translate(xVel, -yVel);
            else
                centerLoc.translate(-xVel, -yVel);
            
            //check to see if it's off screen
            if(centerLoc.x < 0 || centerLoc.x > ProjectPrometheus.P_WIDTH || 
                    centerLoc.y < 0 || centerLoc.y > ProjectPrometheus.P_HEIGHT){
                isDestroyed = true;
            }
        }
        else{
            super.process();
        }
    }//==============================
    
    @Override
    public void draw(Graphics2D g){
        Point origin = new Point();
        if(goingUp)
            origin = new Point(centerLoc.x, centerLoc.y + speed);
        else
            origin = new Point(centerLoc.x, centerLoc.y - speed);
        if(hasTurned){
            if(goNE)
                origin = new Point(centerLoc.x - speed, centerLoc.y + speed);
            else
                origin = new Point(centerLoc.x + speed, centerLoc.y + speed);
        }

        // Create and setup the gradient paint and special stroke.
        g.setPaint(new GradientPaint(origin, Color.BLACK, centerLoc, color));
        g.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        // Draw the Missile path.
        g.drawLine(origin.x, origin.y, centerLoc.x, centerLoc.y);
    }
}
