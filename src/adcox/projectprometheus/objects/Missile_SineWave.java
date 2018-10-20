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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import adcox.projectprometheus.userInterface.ProjectPrometheus;

/**
 *
 * @author Andrew
 * @version May 25, 2010
 */
public class Missile_SineWave extends Missile {
    
    private int yVel, xVel;
    private double theta = 0;
    
    private final double THETA_STEP = Math.PI/180;
    
    public Missile_SineWave(Color color, Point location, int width, boolean goingUp, int speed, boolean hittable){
        super(color, location, width, goingUp, speed, hittable);
        yVel = 0;
        xVel = speed;
    }//==========================================
    
    @Override
    public void process(){
        xVel = (int)(2*speed*Math.cos(theta));
        yVel = (int)(speed*Math.sin(theta));
        if(yVel > 0)
            yVel = -yVel;
        theta += 6*THETA_STEP;
        
        centerLoc.translate(xVel, yVel);
        
        if(centerLoc.y < 0 || centerLoc.y >= ProjectPrometheus.P_HEIGHT){
            //missile is off the screen
            isDestroyed = true;
        }
    }//=================================
    
    @Override
    public void respondToHit(){}
    
    @Override
    public void draw(Graphics2D g){
        Point origin = new Point();
        origin = new Point(centerLoc.x - xVel, centerLoc.y - yVel);

        // Create and setup the gradient paint and special stroke.
        g.setPaint(new GradientPaint(origin, Color.BLACK, centerLoc, color));
        g.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        // Draw the Missile path.
        g.drawLine(origin.x, origin.y, centerLoc.x, centerLoc.y);
    }
}
