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
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Andrew
 * @version Apr 7, 2010
 */
public class Blockade {
    /**
     * Blockades will be circular.  Should be planets, moons, stars, asteroids, etc.
     */
    
    protected Point location;                        //center of the Blockade - used for intersect()
    protected Polygon outline;                        //outline of the blockade
    protected int width;                              //width of the blockade
    protected Color color;                            //color of the blockade
    protected int r, g, b;                            //r,g,b aspects of color

    protected int hitPoints;                          //number of hits it takes to kill this blockade
    protected int currentHealth;                      //number of hits blockade has sustained

    protected boolean isDestroyed;                    //whether or not blockade is dead
    
    public static final int DARKEN_AMOUNT = 50;
    public static final int Y_MOVE_DISTANCE = 30;


    public Blockade(Point center, int width, Color color, int hitPoints){
        location = (Point)center.clone();
        this.width = width;
        this.color = color;
        r = color.getRed();
        g = color.getGreen();
        b = color.getBlue();
        this.hitPoints = hitPoints;
        isDestroyed = false;
        currentHealth = 100;
        
        
        outline = asteroidPoly();
    }//===============================================

    /**
     * Initialize the polygon object representing the blockade.  This polygon
     * will be used to draw, and for reference in determining if a missile
     * has hit the blockade.
     */
    public Polygon initPolygon(){
        //as default, polygon will be a box
        int[] yPoints = new int[4];
        int[] xPoints = new int[4];

        yPoints[0] = location.y - width/2;     //start top left corner of box
        yPoints[1] = location.y - width/2;
        yPoints[2] = location.y + width/2;
        yPoints[3] = location.y + width/2;

        xPoints[0] = location.x - width/2;     //aslo start at top left corner
        xPoints[1] = location.x + width/2;
        xPoints[2] = location.x + width/2;
        xPoints[3] = location.x - width/2;

        return new Polygon(xPoints, yPoints, 4);
    }//========================================
    
    public Polygon asteroidPoly(){
        int b = width/3;
        int[] yPoints = new int[8];
        int[] xPoints = new int[8];

        xPoints[0] = location.x;
        xPoints[1] = location.x - 2*b;
        xPoints[2] = location.x - 3*b;
        xPoints[3] = location.x - b;
        xPoints[4] = location.x;
        xPoints[5] = location.x + 2*b;
        xPoints[6] = location.x + 3*b;
        xPoints[7] = location.x + b;

        yPoints[0] = location.y - 2*b;
        yPoints[1] = location.y - b;
        yPoints[2] = location.y +b;
        yPoints[3] = location.y + 2*b;
        yPoints[4] = location.y + b;
        yPoints[5] = location.y + 3*b;
        yPoints[6] = location.y;
        yPoints[7] = location.y - 3*b;

        return new Polygon(xPoints, yPoints, xPoints.length);
    }
    
    public void process(){}
    
    public void respondToHit(ArrayList<Explosion> explosions, boolean goingUp){
        int hitAmount = 100/hitPoints;      //find amount to subtract based on the number of hitpoints
        currentHealth -= hitAmount;         //subtract from health
        darken();
        //if the object that hit this is going up - it will be a missile
        if(goingUp){
            location.y -= Y_MOVE_DISTANCE;     //bump the blockade up
            outline.translate(0, -Y_MOVE_DISTANCE);
        }
        if(currentHealth <= 0){
            isDestroyed = true;
            explosions.add(new Explosion(12, (Point)location.clone(), 50, 3*width/2));
        }
    }//==========================================
    
    public void darken(){
        if(r - DARKEN_AMOUNT > 0)
            r -= DARKEN_AMOUNT;
        if(g - DARKEN_AMOUNT > 0)
            g -= DARKEN_AMOUNT;
        if(b - DARKEN_AMOUNT > 0)
            b -= DARKEN_AMOUNT;
        
        color = new Color(r, g, b);
    }//===============================
    
    public Point getLocation(){return location;}
    
    public boolean getDestroyed(){return isDestroyed;}
    
    public int getPointValue(){return hitPoints;}

    public Polygon getPolygon(){return outline;}

    public void draw(Graphics2D g){
        g.setColor(color);
        g.fillPolygon(outline);
    }//===================================================
    
    /**
     * Checks to see if the ship intersects with any ships in the ships ArrayList.
     * @param ships ArrayList passed to the ship for use in determining whether or not it intersects a ship.
     */
    public void checkIntersectShip(ArrayList<Ship> ships, ArrayList<Explosion> explosions){
        //goes through all the ships
        for(Ship s : ships){
            if(!(s instanceof Ship_Tarea)){
                if(this.outline.intersects(s.getPolygon().getBounds2D())){
                    s.respondToHit(explosions);
                    this.respondToHit(explosions, false);
                }
            }
        }
    }//============================================

}
