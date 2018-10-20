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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Andrew
 * @version Apr 16, 2010
 */
public class Explosion {
    protected int explosiveSpeed;
    protected int radius;
    protected Point centerLoc;
    protected int frameCounter;
    protected int numParticles;

    ArrayList<Particle> particles = new ArrayList<Particle>();

    /**
     * Constructor for the explosion class.
     * @param explosiveSpeed int representing the vector velocity of any particle
     * initialy after the explosion.
     * @param centerLoc Point representing the center of the explosion.
     * @param numParticles int representing the number of particles that rain down
     * from this explosion.
     * @param radius int representing how far the particles can get from the center
     * before they disapear.
     */
    public Explosion(int explosiveSpeed, Point centerLoc, int numParticles, int radius){
        this.explosiveSpeed = explosiveSpeed;
        this.centerLoc = (Point) centerLoc.clone();
        this.radius = radius;
        this.numParticles = numParticles;
        initExplosion(numParticles);
    }//==================================

    /**
     * Creates all the particles and adds them to an ArrayList of Particles.
     * @param numParticles int representing the number of particles to be created
     * from this explosion.
     */
    public void initExplosion(int numParticles){
        double theta = (Math.PI * 2)/numParticles;
        for(int i = 0; i < numParticles; i++){
            double xVel = explosiveSpeed * Math.cos(i*theta);
            double yVel = explosiveSpeed * Math.sin(i*theta);
            particles.add(new Particle(centerLoc, 2, randomColor(), xVel, yVel));
        }
    }//==========================================

    /**
     * Randomly picks an amount of green to add to 255 red and 0 blue.  This makes
     * any shade of red, orange or yellow.
     * @return Color - any shade of red, orange or yellow.
     */
    public Color randomColor(){
        int g;
        Random randy = new Random();
        g = randy.nextInt(255);
        return new Color(255, g, 0);
    }//==================================

    /**
     * Returns the location of the center of the explosion.
     * @return Point representing the center of the explosion.
     */
    public Point getLocation(){return centerLoc;}

    /**
     * Determines whether or not the explosion is destroyed.
     * @return boolean representing status of being destroyed.
     */
    public boolean getDestroyed(){
        if(particles.size() > 0)
            return false;
        else
            return true;
    }//=================================

    /**
     * Performs all drawing and proccessing needs of the explosion.
     * @param g Graphics object that performs the drawing.
     */
    public void draw(Graphics g){
        if(frameCounter != 0 && frameCounter%2 == 0 && frameCounter < 8){
            initExplosion(numParticles);
        }
        for(int p = 0; p < particles.size(); p++){
            particles.get(p).process();
            particles.get(p).draw(g);

            if(particles.get(p).distanceFromCenter() >= radius){
                particles.remove(p);
                p--;
            }
        }
        frameCounter++;
    }//======================================
}
