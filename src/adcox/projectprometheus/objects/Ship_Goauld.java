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

import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author Andrew
 * @version Apr 7, 2010
 */
public class Ship_Goauld extends Ship {
    /**this class will know specific things about the 3 different types
     * of Goa'uld ships
     */

    public Ship_Goauld(int type, Point location, int width, int xVel, int fireProbability, int row, int collumn){
        super(type, location, width, xVel, fireProbability, row, collumn);
        /**
         * Type 1: death glider
         * Type 2: Cargo ship - Tel'tak
         * Type 3: Alkesh
         * Type 4: Mother Ship
         */

        if(type == 1){
            pointValue = 50;
            outline = gliderPolygon();
            color = Color.gray;
            colorIdent = gliderColorIdent();
            identifyingColor = new Color(83, 180, 251); //light blue
            missileWidth = 4;
            missileSpeed = 3;
            missileHittable = true;
            specialWeaponIndex = 0;
        }
        if(type == 2){
            pointValue = 100;
            outline = cargoPolygon();
            color = Color.gray;
            colorIdent = cargoColorIdent();
            identifyingColor = new Color(249, 211, 0);  //gold
            missileWidth = 6;
            missileSpeed = 4;
            missileHittable = true;
            specialWeaponIndex = 1;
        }
        if(type == 3){
            outline = alkeshPoly();
            color = Color.lightGray;
            colorIdent = alkeshColorIdent();
            identifyingColor = Color.green;
            pointValue = 150;
            missileWidth = 8;
            missileSpeed = 4;
            missileHittable = false;
            specialWeaponIndex = 2;
        }
        if(type == 4){
            outline = motherPoly();
            color = new Color(65, 66, 80);  //blueish gray
            colorIdent = motherColorIdent();
            identifyingColor = Color.MAGENTA;
            pointValue = 200;
            missileWidth = 10;
            missileSpeed = 4;
            hitPoints = 2;
            missileHittable = false;
            specialWeaponIndex = 3;
        }
        height = width;
    }//=======================================

    protected Polygon gliderPolygon(){
        int b = width/14;
        int x = centerLoc.x;
        int y = centerLoc.y;

        int[] xVals = new int[7];
        xVals[0] = x-7*b;   //left wingtip
        xVals[1] = x-b;
        xVals[2] = x;
        xVals[3] = x+b;
        xVals[4] = x+7*b;   //right wingtip
        xVals[5] = x+b;
        xVals[6] = x-b;

        int[] yVals = new int[7];
        yVals[0] = y + 2*b;
        yVals[1] = y - b;
        yVals[2] = y-2*b;
        yVals[3] = y-b;
        yVals[4] = y + 2*b;
        yVals[5] = y;
        yVals[6] = y;

        barrelLoc = (Point)centerLoc.clone();

        return new Polygon(xVals, yVals, xVals.length);
    }//================================================
    
    protected Polygon gliderColorIdent(){
        int b = width/14;
        int x = centerLoc.x;
        int y = centerLoc.y;
        
        int[] xVals = new int[4];
        xVals[0] = x;   //top corner of diamond
        xVals[1] = x + b;
        xVals[2] = x;   //bottom corner of diamond
        xVals[3] = x - b;
        
        int[] yVals = new int[4];
        yVals[0] = y - 2*b; //top corner of diamond
        yVals[1] = y - b;
        yVals[2] = y;       //bottom corner of diamond
        yVals[3] = y - b;
        
        return new Polygon(xVals, yVals, xVals.length);
    }//=================================================
    
    protected Polygon cargoPolygon(){
        int b = width/10;
        int x = centerLoc.x;
        int y = centerLoc.y;
        
        int[] xVals = new int[6];
        int[] yVals = new int[6];
        
        xVals[0] = x;           //start at top corner
        xVals[1] = x + 2*b;
        xVals[2] = x + 3*b;     //far right corner
        xVals[3] = x;           //bottom corner
        xVals[4] = x - 3*b;
        xVals[5] = x - 2*b;     //far left corner
            
        yVals[0] = y - 3*b;     //top corner
        yVals[1] = y;
        yVals[2] = y + b;       //far left corner
        yVals[3] = y + 2*b;     //bottom
        yVals[4] = y + b;
        yVals[5] = y;

        barrelLoc.x = x;
        barrelLoc.y = y + 2*b;

        return new Polygon(xVals, yVals, xVals.length);
    }//===============================================
    
    protected Polygon cargoColorIdent(){
        int b = width/10;
        int x = centerLoc.x;
        int y = centerLoc.y;
        
        int[] xVals = new int[3];
        xVals[0] = x;       //bottom corner of triangle
        xVals[1] = x - b;   //left corner
        xVals[2] = x + b;   //right corner
        
        int[] yVals = new int[3];
        yVals[0] = y + 2*b; //bottom corner
        yVals[1] = y + b;
        yVals[2] = y + b;
        
        return new Polygon(xVals, yVals, xVals.length);
    }//=============================================
    
    protected Polygon alkeshPoly(){
        int b = width/8;
        int x = centerLoc.x;
        int y = centerLoc.y;
        
        int[] xVals = new int[7];
        int[] yVals = new int[7];
        
        xVals[0] = x;   //top point
        xVals[1] = x - 2*b;
        xVals[2] = x - 4*b;
        xVals[3] = x - b;
        xVals[4] = x + b;
        xVals[5] = x + 4*b;
        xVals[6] = x + 2*b;
        
        yVals[0] = y - 3*b;
        yVals[1] = y - b;
        yVals[2] = y;
        yVals[3] = y + b;
        yVals[4] = y + b;
        yVals[5] = y;
        yVals[6] = y - b;

        barrelLoc.x = x;
        barrelLoc.y = y + b;
        
        return new Polygon(xVals, yVals, xVals.length);
    }//================================================
    
    protected Polygon alkeshColorIdent(){
        int b = width/8;
        int x = centerLoc.x;
        int y = centerLoc.y;
        
        int[] xVals = new int[3];
        xVals[0] = x;   //bottom of triangle
        xVals[1] = x - 2*b;     //left side of triangle
        xVals[2] = x + 2*b;
        
        int[] yVals = new int[3];
        yVals[0] = y;
        yVals[1] = y - b;
        yVals[2] = y - b;
        
        return new Polygon(xVals, yVals, xVals.length);
    }//=====================================

    protected Polygon motherPoly(){
        int b = width/10;
        int x = centerLoc.x;
        int y = centerLoc.y;

        int[] xVals = new int[8];
        int[] yVals = new int[8];

        xVals[0] = x;   //top point
        xVals[1] = x - b;
        xVals[2] = x - 5*b;
        xVals[3] = x - b;
        xVals[4] = x;       //bottom indent
        xVals[5] = x + b;
        xVals[6] = x + 5*b;
        xVals[7] = x + b;

        yVals[0] = y - 3*b;
        yVals[1] = y - b;
        yVals[2] = y;
        yVals[3] = y + b;
        yVals[4] = y;       //bottom indent
        yVals[5] = y + b;
        yVals[6] = y;
        yVals[7] = y - b;

        barrelLoc = (Point)centerLoc.clone();

        return new Polygon(xVals, yVals, xVals.length);
    }//==========================================

    protected Polygon motherColorIdent(){
        int b = width/10;
        int x = centerLoc.x;
        int y = centerLoc.y;

        int[] xVals = new int[3];
        xVals[0] = x;       //top of triangle
        xVals[1] = x + b;   //right corner
        xVals[2] = x - b;   //left corner

        int[] yVals = new int[3];
        yVals[0] = y - 2*b;     //top of triangle
        yVals[1] = y - b;       //right corner
        yVals[2] = y - b;       //left corner

        return new Polygon(xVals, yVals, xVals.length);
    }//==========================================

    @Override
    public void resetPoly(){
        if(type == 1){
            outline = gliderPolygon();
            colorIdent = gliderColorIdent();
        }
        if(type == 2){
            outline = cargoPolygon();
            colorIdent = cargoColorIdent();
        }
        if(type == 3){
            outline = alkeshPoly();
            colorIdent = alkeshColorIdent();
        }
        if(type == 4){
            outline = motherPoly();
            colorIdent = motherColorIdent();
        }
    }//================================

    @Override
    public void respondToHit(ArrayList<Explosion> explosions){
        super.respondToHit(explosions);
        if(type == 4){
            //darkerOrange
            color = new Color(200, 64, 0);
        }
    }//===================================

}
