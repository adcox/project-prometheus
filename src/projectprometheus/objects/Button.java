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

/**
 *
 * @author Andrew
 * @version December 24, 2009
 */
public class Button{
        private int myX, myY;       //location top left corner
        private int myHeight, myWidth;
        private String myText;        //text to display
        private boolean clickable;  //whether or not user can click on this

        Color background = Color.BLACK;

        public Button(int x, int y, int height, int width,
                String text, boolean canClick){
                myX = x;
                myY = y;
                myHeight = height;
                myWidth = width;
                myText = text;
                clickable = canClick;
        }//=====================================

        public void changeLocation(int x, int y){
            myX = x;
            myY = y;
        }//========================================

        public int getX(){
            return myX;
        }//========================================

        public int getY(){
            return myY;
        }//======================================

        public int getRightSide(){
            return myX + myWidth;
        }//======================================

        public int getBottom(){
            return myY + myHeight;
        }//======================================

        public void changeSize(int height, int width){
            myHeight = height;
            myWidth = width;
        }//=========================================

        public void changeClickable(boolean canClick){
            clickable = canClick;
        }//=========================================

        public void changeBackground(Color newColor){
            background = newColor;
        }//=========================================

        public void draw(Graphics2D g){
            g.setColor(background);
            g.fillRect(myX, myY, myWidth, myHeight);
            g.setColor(Color.blue);
            g.draw3DRect(myX, myY, myWidth, myHeight, true);
            if(myText != null)
                g.drawString(myText, myX + 5, myY + myHeight - 5);
        }//========================================
    }
