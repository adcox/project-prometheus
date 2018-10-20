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

/**
 *
 * @author Andrew
 * @version June 20, 2010
 */
public class Ship_Replicator extends Ship {
    /**
     * Specific to Replicators:
     * >Ship designs
     * >They regenerate after a hit - you must kill them multiple times
     */

    protected int regenCounter = 0;               //keep track of frames for regenteration
    protected boolean regenerating = false;       //whether or not replicators are regenerating

    protected static final int REGEN_TIME = 60;   //time it takes to regenerate

    public Ship_Replicator(int type, Point location, int width, int xVel, int fireProbability, int row, int collumn){
        super(type, location, width, xVel, fireProbability, row, collumn);

        if(type == 1){
            outline = cruiserPoly();
            colorIdent = cruiserColorIdent();
            hitPoints = 2;
            missileSpeed = 4;
            pointValue = 150;
            speedUpInterval = 10;
            specialWeaponIndex = 4;
            identifyingColor = Color.lightGray;
        }
        if(type == 2){
            outline = spiderPoly();
            colorIdent = spiderColorIdent();
            hitPoints = 2;
            missileSpeed = 5;
            pointValue = 200;
            speedUpInterval = 8;
            specialWeaponIndex = 5;
            identifyingColor = new Color(0, 128, 128);  //dark green blue
        }
        if(type == 3){
            outline = midrangePoly();
            colorIdent = midrangeColorIdent();
            hitPoints = 3;
            missileSpeed = 6;
            pointValue = 300;
            speedUpInterval = 6;
            specialWeaponIndex = 6;
            identifyingColor = new Color(128, 128, 64); //dark brown gold
        }
        
        //these are the same for all replicator ships
        color = Color.darkGray;
        missileWidth = 6;   //all missiles are replicator blocks, so they are the same
        missileHittable = false;
    }//=======================================

    protected Polygon cruiserPoly(){
        int b = width/22;   //base length for ship
        int x = centerLoc.x;
        int y = centerLoc.y;

        int[] xVals = new int[32];
        int[] yVals = new int[32];

        xVals[0] = x - 10*b;      //start at far left appendage, bottom corner
        xVals[1] = x - 10*b;
        xVals[2] = x - 6*b;
        xVals[3] = x - 6*b;
        xVals[4] = x;
        xVals[5] = x;
        xVals[6] = x - 3*b;
        xVals[7] = x - 3*b;
        xVals[8] = x + 5*b;
        xVals[9] = x + 5*b;
        xVals[10] = x - 3*b;
        xVals[11] = x - 3*b;
        xVals[12] = x + 10*b;
        xVals[13] = x + 10*b;
        xVals[14] = x + 6*b;
        xVals[15] = x + 6*b;
        xVals[16] = x + 12*b;
        xVals[17] = x + 12*b;
        xVals[18] = x - 3*b;
        xVals[19] = x - 3*b;
        xVals[20] = x + 8*b;
        xVals[21] = x + 8*b;
        xVals[22] = x - 3*b;
        xVals[23] = x - 3*b;
        xVals[24] = x + b;
        xVals[25] = x + b;
        xVals[26] = x - 6*b;
        xVals[27] = x - 6*b;
        xVals[28] = x - 8*b;
        xVals[29] = x - 8*b;
        xVals[30] = x - 6*b;
        xVals[31] = x - 6*b;


        yVals[0] = y;
        yVals[1] = y - b;
        yVals[2] = y - b;
        yVals[3] = y - 5*b;
        yVals[4] = y - 5*b;
        yVals[5] = y - 4*b;
        yVals[6] = y - 4*b;
        yVals[7] = y - 3*b;
        yVals[8] = y - 3*b;
        yVals[9] = y - 2*b;
        yVals[10] = y - 2*b;
        yVals[11] = y - b;
        yVals[12] = y - b;
        yVals[13] = y;
        yVals[14] = y;
        yVals[15] = y + b;
        yVals[16] = y + b;
        yVals[17] = y + 2*b;
        yVals[18] = y + 2*b;
        yVals[19] = y + 3*b;
        yVals[20] = y + 3*b;
        yVals[21] = y + 4*b;
        yVals[22] = y + 4*b;
        yVals[23] = y + 5*b;
        yVals[24] = y + 5*b;
        yVals[25] = y + 6*b;
        yVals[26] = y + 6*b;
        yVals[27] = y + 3*b;
        yVals[28] = y + 3*b;
        yVals[29] = y + 2*b;
        yVals[30] = y + 2*b;
        yVals[31] = y;

        barrelLoc.x = x - 10*b;
        barrelLoc.y = y;

        return new Polygon(xVals, yVals, xVals.length);
    }//===============================================
    
    protected Polygon cruiserColorIdent(){
        int b = width/22;   //base length for ship
        int x = centerLoc.x;
        int y = centerLoc.y;
        
        int[] xVals = new int[6];
        xVals[0] = x - 4*b;     //top of hexagon
        xVals[1] = x - 3*b;     //right side of hexagon
        xVals[2] = x - 3*b;
        xVals[3] = x - 4*b;     //bottom of hexagon
        xVals[4] = x - 5*b;     //left side of hexagon
        xVals[5] = x - 5*b;
        
        int[] yVals = new int[6];
        yVals[0] = y - b;   //top of hexagon
        yVals[1] = y;
        yVals[2] = y + b;
        yVals[3] = y + 2*b; //bottom of hexagon
        yVals[4] = y + b;
        yVals[5] = y;
        
        return new Polygon(xVals, yVals, xVals.length);
    }//==============================================

    protected Polygon spiderPoly(){
        int b = width/21;
        int x = centerLoc.x;
        int y = centerLoc.y;

        int[] xVals = new int[30];
        xVals[0] = x;   //start at top of spider
        xVals[1] = x + 6*b;
        xVals[2] = x + 9*b;
        xVals[3] = x + 10*b;
        xVals[4] = x + 10*b;    //bottom of right most foot
        xVals[5] = x + 9*b;
        xVals[6] = x + 9*b;
        xVals[7] = x + 8*b;
        xVals[8] = x + 6*b;
        xVals[9] = x + 5*b;
        xVals[10] = x + 4*b;
        xVals[11] = x + 3*b;
        xVals[12] = x + 2*b;
        xVals[13] = x + b;
        xVals[14] = x;
        xVals[15] = x - b;  //bottom left corner of center leg
        xVals[16] = x - 2*b;
        xVals[17] = x - 3*b;
        xVals[18] = x - 4*b;
        xVals[19] = x - 5*b;
        xVals[20] = x - 6*b;
        xVals[21] = x - 7*b;
        xVals[22] = x - 9*b;
        xVals[23] = x - 10*b;
        xVals[24] = x - 10*b;
        xVals[25] = x - 11*b;
        xVals[26] = x - 11*b;   //bottom left corner of left most foot
        xVals[27] = x - 10*b;
        xVals[28] = x - 7*b;
        xVals[29] = x - b;      //back to the top

        int[] yVals = new int[30];
        yVals[0] = y - 5*b;     //top of spider
        yVals[1] = y - 4*b;
        yVals[2] = y - b;
        yVals[3] = y + 3*b;
        yVals[4] = y + 4*b;     //bottom right corner of right most foot
        yVals[5] = y + 4*b;
        yVals[6] = y + 3*b;
        yVals[7] = y;
        yVals[8] = y - 2*b;
        yVals[9] = y - 2*b;
        yVals[10] = y + 5*b;
        yVals[11] = y + 5*b;
        yVals[12] = y - 1*b;
        yVals[13] = y - 1*b;
        yVals[14] = y + 6*b;
        yVals[15] = y + 6*b;        //bottom left corner of center foot
        yVals[16] = y - 1*b;
        yVals[17] = y - 1*b;
        yVals[18] = y + 5*b;
        yVals[19] = y + 5*b;
        yVals[20] = y - 2*b;
        yVals[21] = y - 2*b;
        yVals[22] = y;
        yVals[23] = y + 3*b;
        yVals[24] = y + 4*b;
        yVals[25] = y + 4*b;        //Bottom left corner of left most leg
        yVals[26] = y + 3*b;
        yVals[27] = y - b;
        yVals[28] = y - 4*b;
        yVals[29] = y - 5*b;        //back to top

        barrelLoc.x = x;
        barrelLoc.y = y + 6*b;

        return new Polygon(xVals, yVals, xVals.length);
    }//===============================
    
    protected Polygon spiderColorIdent(){
        int b = width/21;
        int x = centerLoc.x;
        int y = centerLoc.y;
        
        int[] xVals = new int[4];
        xVals[0] = x;       //top right corner
        xVals[1] = x;       //bottom right corner
        xVals[2] = x - b;   //bottom left corner
        xVals[3] = x - b;   //top left corner
        
        int[] yVals = new int[4];
        yVals[0] = y - 4*b;     //top right corner
        yVals[1] = y - 3*b;     //bottom right corner
        yVals[2] = y - 3*b;     //bottom left corner
        yVals[3] = y - 4*b;     //top left corner
        
        return new Polygon(xVals, yVals, xVals.length);
    }//======================================

    protected Polygon midrangePoly(){
        int b = width/15;
        int x = centerLoc.x;
        int y = centerLoc.y;
        
        int[] xVals = new int[14];
        xVals[0] = x + b;
        xVals[1] = x + 4*b;
        xVals[2] = x + 6*b;
        xVals[3] = x + 7*b;
        xVals[4] = x + 6*b; //top right corner of right side-pod thing
        xVals[5] = x + 4*b;
        xVals[6] = x + b;
        xVals[7] = x - 2*b;
        xVals[8] = x - 5*b;
        xVals[9] = x - 7*b; //top left corner of left side-pod thing
        xVals[10] = x - 8*b;
        xVals[11] = x - 7*b;
        xVals[12] = x - 5*b;
        xVals[13] = x - 2*b;

        int[] yVals = new int[14];
        yVals[0] = y - 2*b;
        yVals[1] = y + b;
        yVals[2] = y + b;
        yVals[3] = y;       //far right corner
        yVals[4] = y - b;
        yVals[5] = y - b;
        yVals[6] = y + b;
        yVals[7] = y + b;
        yVals[8] = y - b;
        yVals[9] = y - b;
        yVals[10] = y;      //far left corner
        yVals[11] = y + b;
        yVals[12] = y + b;
        yVals[13] = y - 2*b;

        barrelLoc.x = x;
        barrelLoc.y = y + b;

        return new Polygon(xVals, yVals, xVals.length);
    }//==================================

    protected Polygon midrangeColorIdent(){
        int b = width/15;
        int x = centerLoc.x;
        int y = centerLoc.y;
        
        int[] xVals = new int[4];
        xVals[0] = x;           //top right corner of trapazoid
        xVals[1] = x + b;       //bottom right corner of trapazoid
        xVals[2] = x - 2*b;     //bottom left corner
        xVals[3] = x - b;       //top left corner
        
        int[] yVals = new int[4];
        yVals[0] = y - 2*b;     //top right corner
        yVals[1] = y;           //bottom right corner
        yVals[2] = y;           //bottom left corner
        yVals[3] = y - 2*b;     //top left corner
        
        return new Polygon(xVals, yVals, xVals.length);
    }//========================================

    @Override
    public void process(){
        super.process();
        if(regenerating)
            regenCounter++;
        if(regenCounter > REGEN_TIME){
            regenerating = false;
            regenCounter = 0;
        }
    }//=============================

    @Override
    public void respondToHit(ArrayList<Explosion> explosions){
        totalHits++;
        if(totalHits < hitPoints){
            explosions.add(new Explosion_Replicator((3*width)/REGEN_TIME, (Point)centerLoc.clone(), 50, 3*width/2, color));
            regenerating = true;
        }
        else
        {
            isDestroyed = true;
            explosions.add(new Explosion(12, (Point)centerLoc.clone(), 50, 3*width/2));
        }
    }//===================================

    @Override
    protected void resetPoly(){
        if(type == 1){
            outline = cruiserPoly();
            colorIdent = cruiserColorIdent();
        }
        if(type == 2){
            outline = spiderPoly();
            colorIdent = spiderColorIdent();
        }
        if(type == 3){
            outline = midrangePoly();
            colorIdent = midrangeColorIdent();
        }
    }//===============================================

    @Override
    public void draw(Graphics2D g){
        if(!regenerating)   //only draw the polygon if it isn't regenerating
            super.draw(g);
    }//==================================

    public boolean getRegenerating(){return regenerating;}
}
