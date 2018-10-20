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

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import com.adcox.projectprometheus.userInterface.ProjectPrometheus;
/**
 *
 * @author Andrew
 * @version Apr 12, 2010
 */
public class Ship {
    //basic info
    protected int type;                           //0 through 2, at least
    protected Point centerLoc;                    //(x,y) location of the center of this object
    protected Point barrelLoc;                     //Point from which the ship shoots
    protected Polygon outline;                     //Polygon of the outline of a ship.
    protected Polygon colorIdent;                   //polygon to fill with the identifying color that coresponds to special weapons
    protected int width, height;                  //width and height
    protected int xVel;                            //velocity in x-plane. positive = right
    protected int shiftDownCount;                  //keeps track of how many times the ship has shifted down
    protected int speedUpInterval;                //the number of shifts down between speeding up
    protected Color color;                         //the color this ship is drawn with
    protected Color identifyingColor;               //the color coresponding with the special weapon given for killing this ship
    protected int specialWeaponIndex;               //index of the weapon that is to be gained by user if enough of this kind of ship are killed.
    protected int row;                              //remember what row ship started in
    protected int collumn;                          //remember what collumn ship started in

    //info for customizing ships/missiles
    protected int fireProbability;                 // probability is 1 in fireProbability
    protected int missileWidth;                    // width of missiles fired
    protected int missileSpeed;                    // speed of missiles fired
    protected boolean missileHittable;             //whether or not the missile can be hit and destroyed

    //info for reseting
    protected Point initCenterLoc;                   //initial location
    protected int initX_Vel;                       //initial xVel - remember for reset
    protected int initFireProb;                    //initial value of FireProbability

    //health info
    protected int hitPoints;                      //number of hits it takes to down this ship
    protected int totalHits;                        //number of hits the ship has sustained
    protected boolean isDestroyed;                //whether or not the ship is toast
    protected int pointValue;                     //number of points this ship is worth

    //used in communications
    protected String message;                      //message that Ship sends to other ships
    protected boolean hasReversed;                 //whether or not the ship has reversed
    protected int reversedTimer;                   //to wait a certain time before checking to reverse again
    protected boolean shouldShiftDown;              //whether or not the ship should continue to shift down


    //constants
    static public final int X_MARGIN = 50;             //margins that ships will change direction at
    static public final int TOP_MARGIN = 50;
    static public final int BOTTOM_MARGIN = 70;

    static protected final int VERT_STEP = 10;            //distance ships go down when they change direction
    static protected final int SPEED_UP_AMOUNT = 2;       //magnitude of velocity to increase by when the ships speed up
    static protected final int MAX_SPEED = 10;            //maximum speed ships are allowed to travel
    static protected final int FIRE_PROB_INCREASE = 200;   //amount to increase the fireProbability by

    /**
     * Constructor of the Ship class.
     * @param type  The type of ship.
     * @param location  The center of the Ship.
     * @param width The width of the Ship.
     * @param xVel  The velocit in the x direction.  Positive velocity is East, and negative is West.
     */
    public Ship(int type, Point location, int width, int xVel, int fireProbability, int row, int collumn){
        //set variables
        this.type = type;
        this.centerLoc = (Point) location.clone();
        this.width = width;
        this.xVel = xVel;
        this.fireProbability = fireProbability;
        this.row = row;
        this.collumn = collumn;

        initX_Vel = xVel;
        initCenterLoc = (Point)location.clone();
        initFireProb = fireProbability;
        isDestroyed = false;
        totalHits = 0;
        message = "";
        hasReversed = false;
        reversedTimer = 0;
        shouldShiftDown = true;
        speedUpInterval = 5;
        identifyingColor = Color.red;

        //initialize other variables - just defaults - these should be changed by individual ships
        barrelLoc = new Point();
        hitPoints = 1;
        pointValue = 5;
        height = 5;
        color = Color.blue;
        missileWidth = 10;
        missileSpeed = 1;
        shiftDownCount = 0;
        missileHittable = false;
        outline = defaultPoly();
        colorIdent = new Polygon();
        specialWeaponIndex = -1;
    }//================================================

    public void process(){
        //change direction and magnitude x-velocity if ship gets to margins
        if(centerLoc.x - width/2 < X_MARGIN ||
                centerLoc.x + width/2 > ProjectPrometheus.P_WIDTH - X_MARGIN){
            message = "REACHED_EDGE";
        }
        else{
            message = "Nothin'";
        }
        //change position
        centerLoc.translate(xVel, 0);
        //update the polygon
        outline.translate(xVel, 0);
        //update indetifying color polygon
        colorIdent.translate(xVel, 0);
        //update barrelLocation
        barrelLoc.translate(xVel, 0);
        //if the ship has reversed direction, wait 10 frames before accepting another reverse message
        if(hasReversed){
            reversedTimer++;                //add one to the timer
            if(reversedTimer >= 20){        //if we reach ten frames later
                hasReversed = false;        //reset the variable
                reversedTimer = 0;          //reset the timer
            }
        }
    }//========================================

    /**
     * Initialize the polygon object representing the ship.  This polygon
     * will be used to draw, and for reference in determining if a missile
     * has hit the ship.
     */
    protected Polygon defaultPoly(){
        //as default, polygon will be a box
        int[] yPoints = new int[4];
        int[] xPoints = new int[4];

        yPoints[0] = centerLoc.y - width/2;     //start top left corner of box
        yPoints[1] = centerLoc.y - width/2;
        yPoints[2] = centerLoc.y + width/2;
        yPoints[3] = centerLoc.y + width/2;

        xPoints[0] = centerLoc.x - width/2;     //aslo start at top left corner
        xPoints[1] = centerLoc.x + width/2;
        xPoints[2] = centerLoc.x + width/2;
        xPoints[3] = centerLoc.x - width/2;

        return new Polygon(xPoints, yPoints, 4);
    }//========================================

    /**
     * Resets the polygon to where it was originaly.  Each individual ships should
     * override this method and reset their individual polygons.
     */
    protected void resetPoly(){
        if(type == 1)
            outline = defaultPoly();
    }//==========================================

    public void respondToHit(ArrayList<Explosion> explosions){
        totalHits++;      //add to hits this ship has taken
        if(totalHits >= hitPoints){
            isDestroyed = true;
            explosions.add(new Explosion(12, (Point)centerLoc.clone(), 40, 3*width/2));
        }
    }//========================================

    /**
     * Resets the ship to its original settings.
     */
    public void reset(){
        xVel = initX_Vel;
        shiftDownCount = 0;
        fireProbability = initFireProb;
        message = "";
        hasReversed = false;
        reversedTimer = 0;
        shouldShiftDown = true;
        
        centerLoc = (Point)initCenterLoc.clone();
        resetPoly();
    }//==========================================

    /**
     * Fires a missile from the ship if probability think it appropriate.  The
     * method gets a random number between 1 and the fireProbability.  If that
     * number is equal to 1, the ship will fire a missile.
     * @param missiles ArrayList<Missile> containing all missiles being displayed on the screen.
     */
    public void fireMissile(ArrayList<Missile> missiles){
        Random randy = new Random();
        int temp = randy.nextInt(fireProbability);
        if(temp == 1)
            missiles.add(new Missile(identifyingColor, barrelLoc, missileWidth, false, missileSpeed, missileHittable));
    }//========================================

    /**
     * Receives messages and acts on them.
     * @param messages ArrayList<String> that consists of all other ships' messages.
     */
    public void receiveMessages(ArrayList<String> messages){

        for(int i = 0; i <messages.size(); i++){
            if(messages.get(i).equals("REACHED_EDGE")){
                if(!hasReversed){
                    hasReversed = true;         //remember that I've switched
                    xVel = -xVel;   //reverse direction
                    
                    if(centerLoc.y < ProjectPrometheus.P_HEIGHT - BOTTOM_MARGIN && shouldShiftDown){
                        centerLoc.translate(0, VERT_STEP);  //shift down a spot
                        barrelLoc.translate(0, VERT_STEP);
                        colorIdent.translate(0, VERT_STEP);
                        outline.translate(0, VERT_STEP);    //shift the polygon
                        shiftDownCount++;
                        if(shiftDownCount%speedUpInterval == 0){
                            //check to make sure the ship doesn't exeed max speed
                            if(Math.abs(xVel) + 1 <= MAX_SPEED){
                                if(xVel > 0)
                                    xVel += 1;      //increase the velocity relative to direction
                                else
                                    xVel -= 1;
                            }
                        }
                    }
                }
            }
            if(messages.get(i).equals("USER_DEAD")){
                reset();
            }
            if(messages.get(i).equals("SPEED_UP")){
                //make sure ship doesn't exceed max speed
                if(Math.abs(xVel) + SPEED_UP_AMOUNT <= MAX_SPEED){
                    if(xVel > 0)
                        xVel += SPEED_UP_AMOUNT;      //increase the velocity relative to direction
                    else
                        xVel -= SPEED_UP_AMOUNT;
                }else{//if adding speed goes over the max, set thrusters to maximum:)
                    if(xVel > 0)
                        xVel = MAX_SPEED;
                    else
                        xVel = -MAX_SPEED;
                }
            }
            if(messages.get(i).equals("DONT_SHIFT_DOWN")){
                shouldShiftDown = false;
            }
            if(messages.get(i).equals("ONE_OF_US_DIED")){
                //increase the fireing probability
                if(fireProbability > FIRE_PROB_INCREASE){
                    fireProbability -= FIRE_PROB_INCREASE;
                }
                else{
                    fireProbability = 100;  //set to 100 if subtracting FIRE_PROB_INCREASe results in negative number
                }
            }
        }
    }//============================================

 
    public String transmitMessage(){return message;}

    protected void darkenColor(){
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        if(r - 40 > 0)
            r -=40;
        if(g - 40 > 0)
            g -= 40;
        if(b - 40 > 0)
            b -= 40;

        color = new Color(r,g,b);
    }//============================

//******************************************************************************
    //GETTERS AND SETTERS :-)
    
    //normal ship stuff
    public int getType(){return type;}
    
    public Point getLocation(){return centerLoc;}
    
    public void setLocation(Point p){ centerLoc = (Point)p.clone();}
    
    public Point getInitCenterLoc(){return initCenterLoc;}
    
    public Point getBarrelLoc(){ return barrelLoc;}
    
    public void moveBarrelLoc(int dx, int dy){ barrelLoc.move(dx, dy);}

    public int getX_Velocity(){return xVel;}
    
    public void setX_Vel(int v){xVel = v;}

    public void setDestroyed(boolean destroyed){isDestroyed = destroyed;}

    public Polygon getPolygon(){return outline;}

    public void setPolygon(Polygon poly){outline = poly;}
    
    public void translatePoly(int dx, int dy){ outline.translate(dx, dy);}

    public boolean getDestroyed(){return isDestroyed;}
    
    public void setColor(Color c){color = c;}
    
    public Color getColor(){return color;}
    
    public Color getIndentifyingColor(){return identifyingColor;}
    
    public int getSpecialWeaponIndex(){return specialWeaponIndex;}
    
    public int getRow(){return row;}
    
    public int getCollumn(){return collumn;}
    
    //points and health

    public int getPointValue(){return pointValue;}
    
    public void setPointValue(int points){pointValue = points;}

    public int getTotalHits(){return totalHits;}
    
    public void setTotalHits(int h){totalHits = h;}

    public int getHitPoints(){return hitPoints;}
    
    //missile stuff
    public int getFiringProb(){return fireProbability;}

    public void setMissileWidth(int w){ missileWidth = w;}
    
    public void setMissileSpeed(int s){ missileSpeed = s;}
    
    public void setMissileHittable(boolean b){missileHittable = b;}
    
    public void setHitPoints(int p){hitPoints = p;}
    
    public void setHeight(int h){height = h;}
    
    public int getWidth(){return width;}

    /**
     * Draws the ship.
     * @param g Graphics2D object that is responsible for drawing the ship.
     */
    public void draw(Graphics2D g){
        g.setColor(color);
        g.fillPolygon(outline);
        g.setColor(identifyingColor);
        g.fillPolygon(colorIdent);
    }//===========================
}
