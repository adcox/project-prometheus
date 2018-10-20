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

import com.adcox.projectprometheus.userInterface.ProjectPrometheus;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 *
 * @author Andrew
 * @version May 17, 2010
 */
public class Missile {
    protected Point centerLoc;                    //(x,y) location of the center of this object
    protected int width;                          //width
    protected Color color;                        //color of the missile

    protected boolean goingUp;                      //true = up, false = down
    protected int speed;                          //speed (pix/frame) that missile is moving
    protected int damageInflicted;                  //the amount of damage this missile does to objects in the way

    protected boolean isDestroyed;                //whether or not this missile is destroyed
    protected boolean hittable;                   //whether or not this missile can be hit by the user's missile

    /**
     * Constructor of the Missile Class.
     * @param type The type of missile to be created.
     * @param location  The center of the missile.
     * @param width The width of the missile.
     * @param direction The direction the missile is traveling. True is up, false is down.
     * @param speed The speed at which the missile is moving (pixels/frame)
     */
    public Missile(Color color, Point location, int width, boolean goingUp, int speed, boolean hittable){
        this.centerLoc = (Point)location.clone();
        this.width = width;
        this.goingUp = goingUp;
        this.speed = speed;
        this.color = color;
        this.hittable = hittable;
        isDestroyed = false;
        damageInflicted = 1;
    }//=============================================

    /**
    * Determines whether the missile is destroyed.
    * @return boolean representing the life-status of the missile.
    */
    public boolean getDestroyed(){return isDestroyed;}

    /**
     * Returns whether the missile can be hit or not.
     * @return boolean representing hittability of the missile.
     */
    public boolean getHittable(){return hittable;}

    /**
     * Returns the center of the missile.
     * @return Point representing the center of the missile.
     */
    public Point getLocation(){return centerLoc;}

    /**
     * Returns the speed of the missile
     * @return int representing the speed of the missile
     */
    public int getSpeed(){return speed;}

    /**
     * Performs any actions nessesary when a missile is hit.
     */
    public void respondToHit(){
        isDestroyed = true;
    }//=====================================

    /**
     * Checks to see if the missile intersects with any ships in the ships ArrayList.
     * @param ships ArrayList passed to the missile for use in determining whether or not it intersects a ship.
     */
    public void checkHitsShip(ArrayList<Ship> ships, ArrayList<Explosion> explosions){
        for(Ship s : ships){
            if((s instanceof Ship_Tarea && !this.goingUp) || (!(s instanceof Ship_Tarea) && this.goingUp)){
                if(checkIntersects(s.getPolygon())){
                    if(!this.isDestroyed){
                        if(s instanceof Ship_Replicator){
                            //if it's a replicator ship, check to make sure it isn't regenerating
                            if(((Ship_Replicator)s).getRegenerating() == false){
                                for(int d = 0; d < damageInflicted; d++){
                                    s.respondToHit(explosions);
                                    if(s.getDestroyed())
                                        break;
                                }
                                this.respondToHit();
                                break;
                            }
                        }
                        else{
                            for(int d = 0; d < damageInflicted; d++){
                                    s.respondToHit(explosions);
                                    if(s.getDestroyed())
                                        break;
                                }
                            this.respondToHit();
                            break;
                        }
                    }
                }
            }
        }
    }//================================================
    
    /**
     * Checks to see if the missile intersects with any blockades in the blockade ArrayList.
     * @param ships ArrayList passed to the missile for use in determining whether or not it intersects a blockade.
     */
    public void checkHitsBlockade(ArrayList<Blockade> blockades, ArrayList<Explosion> explosions){
        for(Blockade b : blockades){
            if(checkIntersects(b.getPolygon())){
                if(!this.isDestroyed){
                    for(int d = 0; d < damageInflicted; d++){
                        b.respondToHit(explosions, this.goingUp);
                        if(b.getDestroyed())
                            break;
                    }
                    this.respondToHit();
                    break;
                }
            }
        }
    }//================================================

    /**
     * Checks to see if the missile has hit any other missiles.  Missiles fired
     * by the user will be killed if they intersect a missile fired by an enemy,
     * but not vice versa.
     * @param missiles ArrayList of Missile objects to check with.
     */
    public void checkIntersectMissiles(ArrayList<Missile> missiles){
        //check to see if this missile is fired by the user
        if(this.goingUp){
            //go through all the missiles
            for(int m = 0; m <missiles.size(); m++){
                if(!missiles.get(m).goingUp){   //make sure a missile doesn't hit itself
                    int mRad = missiles.get(m).width/2;
                    //if this missile's x coordinate is within width of missile in array
                    if(this.centerLoc.x >= missiles.get(m).centerLoc.x - mRad &&
                            this.centerLoc.x <= missiles.get(m).centerLoc.x + mRad){
                        //if this missile's y coordinate is within radius of the missile - vertically
                        if(this.centerLoc.y <= missiles.get(m).centerLoc.y &&
                                this.centerLoc.y >= missiles.get(m).centerLoc.y - mRad){
                            if(missiles.get(m).getHittable()){
                                missiles.get(m).respondToHit();
                            }
                            if(this.getHittable()){
                                this.respondToHit();
                            }
                            break;
                        }
                    }
            }
            }
        }
    }//=============================================

    protected boolean checkIntersects(Polygon p){
        int x = centerLoc.x - width/2;
        int y = centerLoc.y;

        return p.intersects(x, y, width, speed);
    }//==========================================

    /**
     * Performs any actions nessesary to run a missile.
     */
    public void process(){
        if(goingUp)
            centerLoc.translate(0, -speed);       //update centerLoc
        else
            centerLoc.translate(0, speed);

        if(centerLoc.y < 0 || centerLoc.y >= ProjectPrometheus.P_HEIGHT){
            //missile is off the screen
            isDestroyed = true;
        }
    }//===========================================

    /**
     * Contains the code to draw a missile.
     * @param g Graphics object that performs the drawing.
     */
    public void draw(Graphics2D g){
        Point origin = new Point();
        if(goingUp)
            origin = new Point(centerLoc.x, centerLoc.y + speed);
        else
            origin = new Point(centerLoc.x, centerLoc.y - speed);

        // Create and setup the gradient paint and special stroke.
        g.setPaint(new GradientPaint(origin, Color.BLACK, centerLoc, color));
        g.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        // Draw the Missile path.
        g.drawLine(origin.x, origin.y, centerLoc.x, centerLoc.y);

//        g.setColor(Color.red);
//        g.drawString("centerLoc: " + centerLoc.x + " , " + centerLoc.y, centerLoc.x + width, centerLoc.y);
    }//==========================================
}
