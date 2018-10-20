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
package adcox.projectprometheus.objects;

import adcox.projectprometheus.userInterface.ProjectPrometheus;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Andrew
 * @version May 18, 2010
 * 
 * Horizontal Missile:
 * > shoots upwards until it hits a ship, then turns left or right and continues
 * through all ships in it's way 
 */
public class Missile_Horizontal extends Missile{
    
    private int hitCount = 0;
    private boolean turnLeft;
    private boolean goingHorizontal = false;
    
    public Missile_Horizontal(Color color, Point location, int width, boolean goingUp, int speed, boolean hittable, boolean turnLeft){
        super(color, location, width, goingUp, speed, hittable);
        
        this.turnLeft = turnLeft;
    }//============================================
    
    @Override   //this method should do nothing - it can't intersect a missile
    public void checkIntersectMissiles(ArrayList<Missile> missiles){}
    
    @Override
    public void respondToHit(){
        hitCount++;
        if(hitCount >= 1){
            goingHorizontal = true;
        }
    }//=================================
    
    @Override
    public void process(){
        if(goingHorizontal){        //check to see if the missile is going horizontally
            if(turnLeft)
                centerLoc.translate(-speed, 0);
            else
                centerLoc.translate(speed, 0);
            if(centerLoc.x < 0 || centerLoc.x > ProjectPrometheus.P_WIDTH)
                isDestroyed = true;     //off screen
        }
        else{
            super.process();        //otherwise, behave like a normal missile
        }
    }//===========================================
    
    @Override
    public void draw(Graphics2D g){
        if(!goingHorizontal){
            super.draw(g);
        }else{
            //only change is to setting (Point) origin - changes x instead of y
            Point origin = new Point();
            if(turnLeft)
                origin = new Point(centerLoc.x + 2*speed, centerLoc.y);
            else
                origin = new Point(centerLoc.x - 2*speed, centerLoc.y);

            // Create and setup the gradient paint and special stroke.
            g.setPaint(new GradientPaint(origin, Color.BLACK, centerLoc, color));
            g.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            // Draw the Missile path.
            g.drawLine(origin.x, origin.y, centerLoc.x, centerLoc.y);
        }
    }//===============================================
    
}
