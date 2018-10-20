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
import projectprometheus.userInterface.ProjectPrometheus;

/**
 *
 * @author Andrew
 * @version May 17, 2010
 */
public class Ship_Ori extends Ship {
    /**
     * This class will know all the specifics about the 3 Ori ships
     */

    public Ship_Ori(int type, Point location, int width, int xVel, int fireProbability, int row, int collumn){
        super(type, location, width, xVel, fireProbability, row, collumn);
        
        if(type == 1){
            outline = dartPoly();
            color = Color.white;
            colorIdent = dartColorIdent();
            identifyingColor = new Color(0, 255, 255);      //aqua blue
            pointValue = 150;
            missileSpeed = 6;
            specialWeaponIndex = 8;
        }
        if(type == 2){
            outline = satellitePoly();
            color = Color.gray;
            colorIdent = satelliteColorIdent();
            identifyingColor = Color.yellow;
            pointValue = 100;
            missileSpeed = 8;
            specialWeaponIndex = 7;
        }
        if(type == 3){
            outline = motherPoly();
            color = new Color(224, 224, 224);   //very light gray
            colorIdent = motherColorIdent();
            identifyingColor = new Color(255, 44, 6);      //scarlet
            hitPoints = 2;
            pointValue = 300;
            missileSpeed = 10;
            specialWeaponIndex = 9;
        }
        if(type == 4){
            outline = motherPoly();
            color = new Color(224, 224, 224);
            colorIdent = motherColorIdent();
            identifyingColor = new Color(100,200,100);
            hitPoints = 3;
            pointValue = 400;
            missileSpeed = 11;
            specialWeaponIndex = 10;
        }
    }//=======================================
    
    protected Polygon satellitePoly(){
        int b = width/17;
        int x = centerLoc.x;
        int y = centerLoc.y;
        
        int[] xVals = new int[20];
        xVals[0] = x;   //top of satellite
        xVals[1] = x + b;   //top of satellite
        xVals[2] = x + 2*b;
        xVals[3] = x + 2*b;
        xVals[4] = x + b;
        xVals[5] = x + 2*b;
        xVals[6] = x + 6*b;     //top right corner of appendage
        xVals[7] = x + 6*b;
        xVals[8] = x + 2*b;
        xVals[9] = x + 2*b;
        xVals[10] = x + b;  //bottom of satellite
        xVals[11] = x;      //bottom of satellite
        xVals[12] = x - b;
        xVals[13] = x - b;
        xVals[14] = x - 5*b;    
        xVals[15] = x - 5*b;    //top left of left appendage
        xVals[16] = x - b;
        xVals[17] = x;
        xVals[18] = x - b;
        xVals[19] = x - b;
        
        int[] yVals = new int[20];
        yVals[0] = y - 5*b;     //top of satellite
        yVals[1] = y - 5*b;
        yVals[2] = y - 4*b;
        yVals[3] = y - 3*b;
        yVals[4] = y - 2*b;
        yVals[5] = y;
        yVals[6] = y;       //top of right appendage
        yVals[7] = y + b;
        yVals[8] = y + b;
        yVals[9] = y + 3*b;
        yVals[10] = y + 5*b;    //bottom of satellite
        yVals[11] = y + 5*b;
        yVals[12] = y + 3*b;
        yVals[13] = y + b;
        yVals[14] = y + b;
        yVals[15] = y;  //top of left appendage
        yVals[16] = y;
        yVals[17] = y - 2*b;
        yVals[18] = y - 3*b;
        yVals[19] = y - 4*b;
        
        barrelLoc.x = x;
        barrelLoc.y = y + 5*b;
        
        return new Polygon(xVals, yVals, xVals.length);
    }//=============================================

    protected Polygon satelliteColorIdent(){
        int b = width/17;
        int x = centerLoc.x;
        int y = centerLoc.y;

        int[] xVals = new int[4];
        xVals[0] = x;       //top left corner
        xVals[1] = x + b;   //top right corner
        xVals[2] = x + b;   //bottom right corner
        xVals[3] = x;       //bottom left corner

        int[] yVals = new int[4];
        yVals[0] = y - 4*b;     //top left corner
        yVals[1] = y - 4*b;     //top right corner
        yVals[2] = y - 3*b;      //bottom right corner
        yVals[3] = y - 3*b;      //bottom left corner

        return new Polygon(xVals, yVals, xVals.length);
    }//===================================

    protected Polygon dartPoly(){
        int b = width/14;
        int x = centerLoc.x;
        int y = centerLoc.y;
        
        int[] xVals = new int[6];
        xVals[0] = x + 4*b;     //very top most point
        xVals[1] = x + 5*b;
        xVals[2] = x + 4*b;
        xVals[3] = x + 2*b;     //bottom flat line, left corner
        xVals[4] = x - 4*b;
        xVals[5] = x + 2*b;
        
        int[] yVals = new int[6];
        yVals[0] = y - 2*b;
        yVals[1] = y;
        yVals[2] = y + b;
        yVals[3] = y + b;
        yVals[4] = y;
        yVals[5] = y - b;
        
        barrelLoc.x = x + 3*b;
        barrelLoc.y = y + b;
        
        return new Polygon(xVals, yVals, xVals.length);
    }//==================================================

    protected Polygon dartColorIdent(){
        int b = width/14;
        int x = centerLoc.x;
        int y = centerLoc.y;

        int[] xVals = new int[3];
        xVals[0] = x + 2*b;     //top of triangle
        xVals[1] = x + 2*b;     //bottom corner
        xVals[2] = x - 2*b;           //far left corner

        int[] yVals = new int[3];
        yVals[0] = y - b;     //top of triangle
        yVals[1] = y + b;           //bottom
        yVals[2] = y;           //far left

        return new Polygon(xVals, yVals, xVals.length);
    }//=================================================
    
    protected Polygon motherPoly(){
        int b = (width/12);
        int x = (centerLoc.x);
        int y = centerLoc.y;
        
        int[] xVals = new int[12];
        xVals[0] = x + 6*b;     //top front corner
        xVals[1] = x + 6*b;     //bottom front corner
        xVals[2] = x + 4*b;
        xVals[3] = x + 3*b;
        xVals[4] = x - b;
        xVals[5] = x - b;
        xVals[6] = x - 2*b;
        xVals[7] = x - 6*b;     //rear bottom corner
        xVals[8] = x - 6*b;
        xVals[9] = x - 5*b;
        xVals[10] = x - b;
        xVals[11] = x + 2*b;
        
        int[] yVals = new int[12];
        yVals[0] = y - b;
        yVals[1] = y + 3*b;
        yVals[2] = y + 3*b;
        yVals[3] = y + b;
        yVals[4] = y + b;
        yVals[5] = y + 2*b;
        yVals[6] = y + b;
        yVals[7] = y + b;
        yVals[8] = y - b;
        yVals[9] = y;
        yVals[10] = y;
        yVals[11] = y - b;
        
        barrelLoc.x = x + 6*b;
        barrelLoc.y = y + 2*b;
        
        return new Polygon(xVals, yVals, xVals.length);
    }//==================================

    protected Polygon motherColorIdent(){
        int b = (width/12);
        int x = (centerLoc.x);
        int y = centerLoc.y;

        int[] xVals = new int[4];
        xVals[0] = x + 6*b;     //top right corner
        xVals[1] = x + 6*b;     //bottom right corner
        xVals[2] = x + 5*b;     //bottom left
        xVals[3] = x + 5*b;     //top left

        int[] yVals = new int[4];
        yVals[0] = y - b;       //top right
        yVals[1] = y + 3*b;     //bottom right
        yVals[2] = y + 2*b;     //bottom left
        yVals[3] = y;           //top left

        return new Polygon(xVals, yVals, xVals.length);
    }//========================================
    
    @Override
    protected void resetPoly(){
        if(type == 1){
            outline = dartPoly();
            colorIdent = dartColorIdent();
        }
        if(type == 2){
            outline = satellitePoly();
            colorIdent = satelliteColorIdent();
        }
        if(type == 3){
            outline = motherPoly();
            colorIdent = motherColorIdent();
        }
        if(type == 4){
            outline = motherPoly();
            colorIdent = motherColorIdent();
        }
    }//=============================

    @Override
    public void respondToHit(ArrayList<Explosion> explosions){
        super.respondToHit(explosions);
        if(type == 3 || type == 4)   //motherships take two hits; get darker
            darkenColor();
    }//===============================

    @Override
    public void fireMissile(ArrayList<Missile> missiles){
        Random randy = new Random();
        int temp = randy.nextInt(fireProbability);
        if(temp == 1)
            if(type != 4)
                missiles.add(new Missile_Vertical(identifyingColor, barrelLoc, missileWidth, false, missileSpeed, missileHittable));
            else
                missiles.add(new Missile_Aimed(identifyingColor, barrelLoc, missileWidth, false, missileSpeed, missileHittable));
    }//========================================

}
