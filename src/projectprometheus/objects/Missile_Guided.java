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
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import com.adcox.projectprometheus.userInterface.ProjectPrometheus;

/**
 *
 * @author Andrew
 * @version May 24, 2010
 * 
 * This missile picks a target and moves towards it
 */

public class Missile_Guided extends Missile{
    
    private boolean lockedOn = false;
    private Ship target;
    private int xVel, yVel;
    
    public Missile_Guided(Color color, Point location, int width, boolean goingUp, int speed, boolean hittable){
        super(color, location, width, goingUp, speed, hittable);
        yVel = -speed;
        xVel = 0;
    }//==========================================
    
    private void pickTarget(ArrayList<Ship> ships){
        Random randy = new Random();
        //pick a ship that isn't the user
        do{
            target = ships.get(randy.nextInt(ships.size()));
        }while(target instanceof Ship_Tarea);
    }//================================================

    @Override
    public void checkHitsShip(ArrayList<Ship> ships, ArrayList<Explosion> explosions){
        if(!lockedOn){
            pickTarget(ships);
            lockedOn = true;
        }
        super.checkHitsShip(ships, explosions);
    }//========================================
    
    @Override
    public void process(){
        //find path to target's location
        if(target != null){
            double deltaX = target.getLocation().x - this.centerLoc.x;
            double deltaY = target.getLocation().y - this.centerLoc.y;
            double theta = Math.atan(deltaY/deltaX);
            //set x and y velocites
            xVel = (int)(speed*Math.cos(theta));
            if(deltaX < 0 && xVel > 0)
                xVel = -xVel;
            yVel = (int)(speed*Math.sin(theta));
            if(deltaY < 0 && yVel > 0)
                yVel = -yVel;
            
        }

        centerLoc.translate(xVel, yVel);

        if(centerLoc.y < 0 || centerLoc.y >= ProjectPrometheus.P_HEIGHT){
            //missile is off the screen
            isDestroyed = true;
        }
    }//====================================

    @Override
    public void draw(Graphics2D g){
        Point origin = new Point();
        origin = new Point(centerLoc.x - xVel, centerLoc.y - yVel);

        // Create and setup the gradient paint and special stroke.
        g.setPaint(new GradientPaint(origin, Color.BLACK, centerLoc, color));
        g.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        // Draw the Missile path.
        g.drawLine(origin.x, origin.y, centerLoc.x, centerLoc.y);
    }//=====================================
    
}
