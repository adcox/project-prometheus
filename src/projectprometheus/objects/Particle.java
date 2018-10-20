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

import java.awt.*;

/**
*
* @author Andrew
* @version Apr 16, 2010
*/

/**
 * This is a sub-class inside the Explosion class.  A Particle is a piece
 * of an explosion.
 */
public class Particle{
    Point centerLoc;
    Point startLoc;
    int radius;
    Color color;
    double xVel, yVel;
    double exactX, exactY;
    int frameCounter;

    /**
     * Constructor of the Particle class.
     * @param centerLoc Point representing the center of a Particle.
     * @param radius int representing the radius of a Particle.
     * @param color Color representing the color of a Particle.
     * @param xVel int representing the x component of the velocity of a Particle.
     * @param yVel int representing the y component of the velocity of a Particle.
     */
    public Particle(Point centerLoc, int radius, Color color, double xVel, double yVel){
        this.centerLoc = (Point)centerLoc.clone();
        startLoc = (Point)centerLoc.clone();
        exactX = centerLoc.x;
        exactY = centerLoc.y;
        this.radius = radius;
        this.color = color;
        this.xVel = xVel;
        this.yVel = yVel;
        frameCounter = 0;
    }//===================================

    /**
     * Increases the y-velocity to mimic gravity and moves the center
     * location in accordance to the x and y velocities.
     */
    public void process(){
        exactX += xVel;                     //remember exact locations
        exactY += yVel;

        centerLoc.move((int)exactX, (int)exactY);    //move point
        frameCounter++;                             //add to frame counter
    }//===============================

    public double distanceFromCenter(){
        double deltaX = Math.abs(exactX - startLoc.x);
        double deltaY = Math.abs(exactY - startLoc.y);

        return Math.sqrt(deltaX*deltaX + deltaY*deltaY);
    }//=================================

    /**
     * Reversed the direction of a particle by changing the sign of both x and
     * y velocities.
     */
    public void reverseDirection(){
        xVel = -xVel;
        yVel = -yVel;
    }//====================================

    /**
     * This method recalculates the x and y velocities of the particle to send
     * it towards Point p.
     * @param p Point that the particle is going to move towards
     */
    public void sendTo(Point p){
        double deltaX = exactX - p.x;
        double deltaY = exactY - p.y;

        xVel = deltaX/15;
        yVel = deltaY/15;
    }//===============================

    public double getX_Vel(){return xVel;}
    public double getY_Vel(){return yVel;}
    public Point getLocation(){return centerLoc;}
    public Point getStartLocation(){return startLoc;}

    /**
     * Draws the particle.
     * @param g Graphics object that performs the drawing.
     */
    public void draw(Graphics g){
        g.setColor(color);
        g.fillOval(centerLoc.x, centerLoc.y, radius, radius);
    }//=================================
}//END OF PARTICLE CLASS
