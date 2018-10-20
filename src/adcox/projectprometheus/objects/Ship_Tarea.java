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

import adcox.projectprometheus.userInterface.ProjectPrometheus;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author Andrew
 * @version May 17, 2010
 */
public class Ship_Tarea extends Ship{
    
    public Ship_Tarea(int type, Point location, int width, int xVel, int fireProbability, int row, int collumn){
        super(type, location, width, xVel, fireProbability, row, collumn);

        xVel = 0;   //this will be user-controlled

        height = width;

        if(type == 1){
            outline = prometheusPoly();
            hitPoints = 5;
            pointValue = 0;
            identifyingColor = Color.blue;
        }
    }//===========================================================

    /**
     * This method makes decisions based on what key has been pressed.
     * @param code int representing the keyCode of a KeyEvent.
     * @param e KeyEvent from input to recognize static keyCodes.
     */
    public void inputKeyPressed(int code, KeyEvent e, ArrayList<Missile> missiles, int powerUpIndex, int powerUpAmount, Color powerUpColor, ProjectPrometheus pp){
        if(code == e.VK_RIGHT){
            //moved ship right, and we're not at the wall
            if(centerLoc.x < ProjectPrometheus.P_WIDTH - X_MARGIN)
                xVel = 10;
            else
                xVel = 0;
        }
        if(code == e.VK_LEFT){
            //move ship left, and we're not at a wall
            if(centerLoc.x > X_MARGIN)
                xVel = -10;
            else
                xVel = 0;
        }
        if(code == e.VK_UP){
            int userMissiles = 0;
            //count the number of user fired missiles on sreen
            for(int i = 0; i< missiles.size(); i++){
                if(missiles.get(i).goingUp)
                    userMissiles++;
            }
            
            //if there aren't any, then user can fire again
            if(userMissiles == 0)
                fire(missiles);      //fire a normal shot
        }
        if(code == e.VK_DOWN){
            int userMissiles = 0;
            //count the number of user fired missiles on sreen
            for(int i = 0; i< missiles.size(); i++){
                if(missiles.get(i).goingUp)
                    userMissiles++;
            }
            //if there aren't any, then user can fire again
            if(userMissiles == 0){
                if(powerUpAmount == 5){
                    if(powerUpIndex == 0)   //Veritcal missile
                        missiles.add(new Missile_Vertical(powerUpColor, barrelLoc, 10, true, 18, false));
                    if(powerUpIndex == 1)   //horizontal left
                        missiles.add(new Missile_Horizontal(powerUpColor, barrelLoc, 7, true, 12, false, true));
                    if(powerUpIndex == 2)   //horizontal right
                        missiles.add(new Missile_Horizontal(powerUpColor, barrelLoc, 7, true, 12, false, false));
                    if(powerUpIndex == 3)
                        missiles.add(new Missile_Diagonal(powerUpColor, barrelLoc, 7, true, 12, false, false, pp));
                    if(powerUpIndex == 4)
                        missiles.add(new Missile_DisintegratorPulse(powerUpColor, barrelLoc, 60, true, 18, false));
                    if(powerUpIndex == 5)
                        missiles.add(new Missile_DisruptorPulse(powerUpColor, barrelLoc, 60, true, 18, false));
                    if(powerUpIndex == 6)
                        missiles.add(new Missile_Electricity(powerUpColor, barrelLoc, 7, true, 12, false));
                    if(powerUpIndex == 7)
                        missiles.add(new Missile_TimeStop(powerUpColor, barrelLoc, 7, true, 12, false, pp));
                    if(powerUpIndex == 8)
                        missiles.add(new Missile_SineWave(powerUpColor, barrelLoc, 7, true, 12, false));
                    if(powerUpIndex == 9){
                        for(int i = 0; i < 5; i++)
                            missiles.add(new Missile_Guided(powerUpColor, barrelLoc, 7, true, 12, false));
                    }
                    if(powerUpIndex == 10)
                        missiles.add(new Missile_ArcOfTruth(powerUpColor, barrelLoc, 100, true, 0, false));
                    pp.updatePowerUpAmount(-1, identifyingColor);
                }
            }
        }
    }//===============================================

    /**
     * This method makes decisions based on what key has been released.
     * @param code int representin the KeyCode of a KeyEvent
     * @param e KeyEvent from input to recognize static keyCodes
     */
    public void inputKeyReleased(int code, KeyEvent e){
        if(code == e.VK_RIGHT){
            //moved ship right
            xVel = 0;
        }
        if(code == e.VK_LEFT){
            //move ship left
            xVel = 0;
        }
    }//=============================================

    @Override
    public void process(){
        //if ship is to the rigth of the right side of the screen
        if(centerLoc.x > ProjectPrometheus.P_WIDTH - X_MARGIN){
            if(xVel == 10)//if ship is trying to go farther right - off screen
                xVel = 0;
        }
        //if ships is to the left of the left side of the screen
        if(centerLoc.x < X_MARGIN){
            if(xVel == -10)//if ship is trying to go farther left - off screen
                xVel = 0;
        }

        centerLoc.translate(xVel, 0);
        outline.translate(xVel, 0);

        barrelLoc.move(centerLoc.x, centerLoc.y - width);    //for Prometheus, at least
    }//==============================================

    //renamed this so the computer doesn't call it every frame
    public void fire(ArrayList<Missile> missiles){
        missiles.add(new Missile(Color.blue, barrelLoc, 7, true, 12, true));
    }//==================================================
    
    @Override   //I don't want this doing anything
    public void fireMissile(ArrayList<Missile> missiles){}
    
    @Override   //I don't want this doing anything
    public void receiveMessages(ArrayList<String> messages){}

    @Override
    public void reset(){
        totalHits = 0;
        centerLoc = (Point)initCenterLoc.clone();
        resetPoly();
        isDestroyed = false;
    }//==========================================

    @Override
    public void draw(Graphics2D g){
        g.setColor(Color.darkGray);
        g.fillPolygon(outline);
    }//=================================================

    public Polygon prometheusPoly(){
        int[] xPoints = new int[48];
        int[] yPoints = new int[48];
        int x = centerLoc.x;
        int y = centerLoc.y;
        int b = width/21;

        xPoints[0] = x - b;     //top of ship; left edge of stuck out "nose"
        xPoints[1] = x - b;
        xPoints[2] = x - 3*b;
        xPoints[3] = x - 3*b;
        xPoints[4] = x - 4*b;
        xPoints[5] = x - 4*b;
        xPoints[6] = x - 3*b;
        xPoints[7] = x - 3*b;
        xPoints[8] = x - 2*b;
        xPoints[9] = x - 2*b;
        xPoints[10] = x - 3*b;
        xPoints[11] = x - 3*b;
        xPoints[12] = x - 5*b;
        xPoints[13] = x - 5*b;
        xPoints[14] = x - 8*b;  //top left corner of left hyper-space engine
        xPoints[15] = x - 8*b;
        xPoints[16] = x - 9*b;
        xPoints[17] = x - 5*b;
        xPoints[18] = x - 4*b;
        xPoints[19] = x - 5*b;
        xPoints[20] = x - 5*b;  //bottom left corner of left sublight engine
        xPoints[21] = x - b;
        xPoints[22] = x - b;
        xPoints[23] = x - 2*b;
        xPoints[24] = x + 2*b;
        xPoints[25] = x + b;
        xPoints[26] = x + b;
        xPoints[27] = x + 5*b;
        xPoints[28] = x + 5*b;
        xPoints[29] = x + 4*b;
        xPoints[30] = x + 5*b;
        xPoints[31] = x + 9*b;
        xPoints[32] = x + 8*b;
        xPoints[33] = x + 8*b;
        xPoints[34] = x + 5*b;
        xPoints[35] = x + 5*b;
        xPoints[36] = x + 3*b;
        xPoints[37] = x + 3*b;
        xPoints[38] = x + 2*b;
        xPoints[39] = x + 2*b;
        xPoints[40] = x + 3*b;
        xPoints[41] = x + 3*b;
        xPoints[42] = x + 4*b;
        xPoints[43] = x + 4*b;
        xPoints[44] = x + 3*b;
        xPoints[45] = x + 3*b;
        xPoints[46] = x + b;
        xPoints[47] = x + b;

        yPoints[0] = y - 21*b;  //top left corner of "nose"
        yPoints[1] = y - 20*b;
        yPoints[2] = y - 20*b;
        yPoints[3] = y - 19*b;
        yPoints[4] = y - 19*b;
        yPoints[5] = y - 15*b;
        yPoints[6] = y - 15*b;
        yPoints[7] = y - 14*b;
        yPoints[8] = y - 14*b;
        yPoints[9] = y - 2*b;
        yPoints[10] = y - 1*b;
        yPoints[11] = y;
        yPoints[12] = y;
        yPoints[13] = y - 4*b;
        yPoints[14] = y - 4*b;
        yPoints[15] = y + 8*b;
        yPoints[16] = y + 10*b;
        yPoints[17] = y + 14*b;
        yPoints[18] = y + 14*b;
        yPoints[19] = y + 16*b;
        yPoints[20] = y + 17*b;     //bottom left corner of left sublight engine
        yPoints[21] = y + 17*b;
        yPoints[22] = y + 16*b;
        yPoints[23] = y + 14*b;
        yPoints[24] = y + 14*b;
        yPoints[25] = y + 16*b;
        yPoints[26] = y + 17*b;
        yPoints[27] = y + 17*b;
        yPoints[28] = y + 16*b;
        yPoints[29] = y + 14*b;
        yPoints[30] = y + 14*b;
        yPoints[31] = y + 10*b;
        yPoints[32] = y + 8*b;
        yPoints[33] = y - 4*b;
        yPoints[34] = y - 4*b;
        yPoints[35] = y;
        yPoints[36] = y;
        yPoints[37] = y - 1*b;
        yPoints[38] = y - 2*b;
        yPoints[39] = y - 14*b;
        yPoints[40] = y - 14*b;
        yPoints[41] = y - 15*b;
        yPoints[42] = y - 15*b;
        yPoints[43] = y - 19*b;
        yPoints[44] = y - 19*b;
        yPoints[45] = y - 20*b;
        yPoints[46] = y - 20*b;
        yPoints[47] = y - 21*b;

        return new Polygon(xPoints, yPoints, xPoints.length);
    }//==================================

    @Override
    public void resetPoly(){
        if(type == 1)
            outline = prometheusPoly();
    }//================================
    
    /**
     * Checks to see if the ship intersects with any ships in the ships ArrayList.
     * @param ships ArrayList passed to the ship for use in determining whether or not it intersects a ship.
     */
    public void checkIntersectShip(ArrayList<Ship> ships, ArrayList<Explosion> explosions){
        //goes through all the ships
        for(Ship s : ships){
            if(!(s instanceof Ship_Tarea)){
                if(this.outline.intersects(s.outline.getBounds2D())){
                    //if s is a replicator ship, check to make sure it isn't regenerating
                    if(s instanceof Ship_Replicator){
                        if(((Ship_Replicator)s).getRegenerating() == false){
                            s.respondToHit(explosions);
                            this.respondToHit(explosions);
                        }
                    }
                    else{
                        s.respondToHit(explosions);
                        this.respondToHit(explosions);
                    }
                }
            }
        }
    }//============================================


}
