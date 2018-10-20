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
import java.util.ArrayList;
import adcox.projectprometheus.userInterface.ProjectPrometheus;

/**
 *
 * @author Andrew
 */
public class Missile_Aimed extends Missile_Vertical{
    private Point destination = new Point();
    private int xVel, yVel;
    private boolean hasTarget = false;

    public Missile_Aimed(Color color, Point location, int width, boolean goingUp, int speed, boolean hittable){
        super(color, location, width, goingUp, speed, hittable);

        xVel = 0;
        yVel = speed;
    }//============================================

    private void getVelocities(){
        double deltaX = destination.x - this.centerLoc.x;
        double deltaY = destination.y - this.centerLoc.y;
        double theta = Math.atan(deltaY/deltaX);
        //set x and y velocites
        xVel = (int)(speed*Math.cos(theta));
        if(deltaX < 0 && xVel > 0)
            xVel = -xVel;

        yVel = (int)(speed*Math.sin(theta));
        if(deltaY < 0 && yVel > 0)
            yVel = -yVel;
        if(deltaY > 0 && yVel < 0)
            yVel = -yVel;
    }//===================================

    @Override
    public void process(){
        centerLoc.translate(xVel, yVel);

        if(centerLoc.y < 0 || centerLoc.y >= ProjectPrometheus.P_HEIGHT){
            //missile is off the screen
            isDestroyed = true;
        }
    }//=================================

    @Override
    public void checkHitsShip(ArrayList<Ship> ships, ArrayList<Explosion> explosions){
        if(!hasTarget){
            for(int i = 0; i < ships.size(); i++)
                if(ships.get(i) instanceof Ship_Tarea){
                    destination = ships.get(i).getLocation();
                    hasTarget = true;
                    getVelocities();
                }
        }
        super.checkHitsShip(ships, explosions);
    }//====================================

}
