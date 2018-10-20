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

import projectprometheus.userInterface.ProjectPrometheus;
import java.awt.*;

/**
 *
 * @author Andrew
 * @version Apr 17, 2010
 */
public class Toolbar {
    private int totalPoints;                 //the number of points the user has
    private int livesLeft;                   //the number of lives the user has left
    private int health;                      //the amount of health the user has left
    private int powerUpAmount;               //the amount of power up the user has gained
    private Color powerUpColor;             //the color of the power up
    private String powerUpName;             //name of the power up

    private Polygon leftPoly, rightPoly;                 //Polygon of the outline
    private int width, height;

    Font pointFont = new Font("OCR A Std",Font.PLAIN , 28);
    Font powerUpFont = new Font("OCR A Std", Font.PLAIN, 20);


    public Toolbar(int width, int height){
        this.width = width;
        this.height = height;
        leftPoly = getLeftSide();
        rightPoly = getRightSide();
        health = 100;
        totalPoints = 0;
        livesLeft = 0;
        powerUpAmount = 0;
        powerUpColor = Color.blue;
    }//==============================================

    public void updateInformation(int points, int lives, int health, int pwrUpAmnt, Color pwrUpColor, String pwrUpName){
        this.totalPoints = points;
        this.livesLeft = lives;
        this.health = health;
        powerUpAmount = pwrUpAmnt;
        powerUpColor = pwrUpColor;
        powerUpName = pwrUpName;
    }//==============================================

    public void draw(Graphics g){
        //draw toolbar outline
        g.setColor(Color.darkGray);
        g.fillPolygon(leftPoly);
        g.fillPolygon(rightPoly);

        //display points
        g.setColor(Color.blue);
        g.setFont(pointFont);
        g.drawString("" + totalPoints, 10, 4*height/5);

        //display power ups
        g.setColor(powerUpColor);
        if(powerUpAmount < 5){
            int xValue = 120;
            for(int i = 0; i < powerUpAmount; i++){
                g.fillRect(xValue, height/5, 10, 3*height/5);
                xValue += 12;       //each box is 10 pixels wide, with a 2 pixel margin between them
            }
        }else{
            g.setFont(powerUpFont);
            g.drawString(powerUpName, 120, 4*height/5);
        }
        
        //display health
        g.setColor(Color.blue);
        int p_width = ProjectPrometheus.P_WIDTH;
        int w = width/8;
        int h = height/8;
        double healthPercent = (double)health/100;
        int healthLength = (int)((5*w)*healthPercent);
        g.fillRect(p_width - 5*w, h, healthLength, 6*h);

        //display lives
        g.setFont(pointFont);
        g.drawString("" + livesLeft, p_width - 6*w, 4*height/5);
    }//==============================================

    public Polygon getLeftSide(){
        int[] yPoints = new int[4];
        int[] xPoints = new int[4];
        int w = width/8;

        xPoints[0] = 0;     //top left corner
        xPoints[1] = 19*w;   //top right corner
        xPoints[2] = 17*w;
        xPoints[3] = 0;

        yPoints[0] = 0;     //top left corner
        yPoints[1] = 0;   //top right corner
        yPoints[2] = height;
        yPoints[3] = height;

        return new Polygon(xPoints, yPoints, xPoints.length);
    }//==============================

    public Polygon getRightSide(){
        int[] yPoints = new int[4];
        int[] xPoints = new int[4];
        int w = width/10;
        int p_width = ProjectPrometheus.P_WIDTH;

        xPoints[0] = p_width - 8*w;     //top left corner
        xPoints[1] = p_width - 0;   //top right corner
        xPoints[2] = p_width - 0;
        xPoints[3] = p_width - 10*w;

        yPoints[0] = 0;     //top left corner
        yPoints[1] = 0;   //top right corner
        yPoints[2] = height;
        yPoints[3] = height;

        return new Polygon(xPoints, yPoints, xPoints.length);
    }//==============================
}
