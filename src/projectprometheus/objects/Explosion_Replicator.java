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
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Andrew
 * @version May 10, 2010
 */
public class Explosion_Replicator extends Explosion {

    private Color replicatorColor;

    public Explosion_Replicator(int explosiveSpeed, Point centerLoc, int numParticles, int radius, Color color){
        super(explosiveSpeed, centerLoc, numParticles, radius);
        replicatorColor = color;
    }//========================================================

    private boolean isNear(Point p1, Point p2, int radius){
        for(int x = -radius; x < radius; x++){
            for(int y = -radius; y < radius; y++){
                if((p1.x + x) > (p2.x - radius) && (p1.x + x) < (p2.x + radius)){
                    if((p1.y + y) > (p2.y - radius) && (p1.y + y) < (p2.y  + radius)){
                        return true;
                    }
                }
            }
        }
        return false;
    }//==================================

    @Override
    public Color randomColor(){
        return replicatorColor; //I want all particles to be the same color
    }//==============================

    @Override
    public void draw(Graphics g){
        if(frameCounter != 0 && frameCounter%2 == 0 && frameCounter < 8){
            initExplosion(numParticles);
        }
        for(int p = 0; p < particles.size(); p++){
            particles.get(p).process();
            particles.get(p).draw(g);

            if(particles.get(p).distanceFromCenter() >= radius){
                particles.get(p).reverseDirection();
            }
            if(frameCounter > 5){       //make sure particles don't disapear right away
                if(isNear(particles.get(p).getLocation(), particles.get(p).getStartLocation(), 5)){
                    particles.remove(p);
                    p--;
                }
            }
        }
        frameCounter++;
    }//====================================
}
