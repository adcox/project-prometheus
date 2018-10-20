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
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Andrew
 * @version August 1, 2010
 *
 */
public class Supergate extends Blockade {

    private boolean shipsInTransit = false;      //whether or not a ship is coming through
    private int frameCount = 0;                 //what frame we're on - used for timing ships
    private int arrivalInterval = 300;         //how often a ship comes through - measured in frames


    public Supergate(Point location, int width, int hitPoints){
        super(location, width, Color.gray, hitPoints);
    }//======================================

    /*
     * Determines whether or not the supergate is sending a ship through
     */
    public boolean sendingShips(){return shipsInTransit;}

    /*
     * draws the Supergate
     */
    @Override
    public void draw(Graphics2D g){
        g.setColor(new Color(110,110,110));     //dark gray
        g.fillOval(location.x, location.y, width, width);
        if(shipsInTransit)
            g.setColor(Color.blue);
        else
            g.setColor(Color.black);
        g.fillOval(location.x + 5, location.y + 5, width - 10, width - 10);
    }

    @Override
    public void process(){
        frameCount++;
        if(frameCount % arrivalInterval == 0 && frameCount != 0){
            shipsInTransit = true;
        }
    }//===================================

    @Override
    public void respondToHit(ArrayList<Explosion> explosions, boolean goingUp){}

    @Override
    public void checkIntersectShip(ArrayList<Ship> ships, ArrayList<Explosion> explosions){}
}
