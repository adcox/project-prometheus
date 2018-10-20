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
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;

/**
 *
 * @author Andrew
 * @version June 20, 2010
 */
public class Ship_Boss_Goauld extends Ship{

    private int frameCount = 0;

    public Ship_Boss_Goauld(int type, Point location, int width, int xVel, int fireProbability, int row, int collumn){
        super(type, location, width, xVel, fireProbability, row, collumn);

        specialWeaponIndex = -1;
        shouldShiftDown = false;
        outline = anubisPoly();
        color = new Color(65, 66, 80);  //blueish gray
        colorIdent = anubisColorIdent();
        identifyingColor = Color.LIGHT_GRAY;
        hitPoints = 30;
    }//==============================================

    @Override
    public void resetPoly(){
        outline = anubisPoly();
        colorIdent = anubisColorIdent();
    }//=======================================

    protected Polygon anubisPoly(){
        int b = width/21;
        int x = centerLoc.x;
        int y = centerLoc.y;

        int[] xVals = new int[18];
        xVals[0] = x;               //top of ship
        xVals[1] = x + 3*b;
        xVals[2] = x;
        xVals[3] = x;
        xVals[4] = x + 2*b;
        xVals[5] = x + 5*b;
        xVals[6] = x + 10*b;
        xVals[7] = x + 5*b;
        xVals[8] = x + 2*b;
        xVals[9] = x - 3*b;
        xVals[10] = x - 6*b;
        xVals[11] = x - 11*b;
        xVals[12] = x - 6*b;
        xVals[13] = x - 3*b;
        xVals[14] = x - b;
        xVals[15] = x - b;
        xVals[16] = x - 4*b;
        xVals[17] = x - b;

        int[] yVals = new int[18];
        yVals[0] = y - 3*b; //top of ship
        yVals[1] = y - 2*b;
        yVals[2] = y - 2*b;
        yVals[3] = y - b;
        yVals[4] = y - b;
        yVals[5] = y;
        yVals[6] = y + b;   //end of right pointy thing
        yVals[7] = y + b;
        yVals[8] = y + 2*b;
        yVals[9] = y + 2*b;
        yVals[10] = y + b;
        yVals[11] = y + b;      //end of left pointy thing
        yVals[12] = y;
        yVals[13] = y - b;
        yVals[14] = y - b;
        yVals[15] = y - 2*b;
        yVals[16] = y - 2*b;
        yVals[17] = y - 3*b;

        barrelLoc.x = x;
        barrelLoc.y = y + 2*b;

        return new Polygon(xVals, yVals, xVals.length);
    }//====================================================


    protected Polygon anubisColorIdent(){
        int b = width/21;
        int x = centerLoc.x;
        int y = centerLoc.y;

        int[] xVals = new int[4];
        xVals[0] = x;
        xVals[1] = x;
        xVals[2] = x - b;
        xVals[3] = x - b;

        int[] yVals = new int[4];
        yVals[0] = y - 3*b;
        yVals[1] = y - 2*b;
        yVals[2] = y - 2*b;
        yVals[3] = y - 3*b;

        return new Polygon(xVals, yVals, xVals.length);
    }//======================================

    protected void drawHealthBar(Graphics2D g){
        
        g.setColor(identifyingColor);
        g.setFont(new Font("OCR A std", Font.PLAIN, 24));
        g.drawString("Boss: ", 20, 50);
        g.fillRect(100, 38, 6*(hitPoints - totalHits), 12);
    }//===============================

    @Override
    public void process(){
        super.process();
        frameCount++;
    }//=============================

    @Override
    public void fireMissile(ArrayList<Missile> missiles){
        if(frameCount%22 == 0 && frameCount != 0){
                missiles.add(new Missile_Aimed(identifyingColor, barrelLoc, 7, false, 12, false));
        }
    }//==================================================

    @Override
    public void draw(Graphics2D g){
        super.draw(g);
        drawHealthBar(g);
    }//===============================
    
}
