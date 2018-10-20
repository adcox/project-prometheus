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

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Andrew
 * @version Jun 20, 2010
 */
public class Ship_Boss_Replicator extends Ship_Replicator {

    private int frameCount = 0;

    public Ship_Boss_Replicator(int iteration, Point location, int width, int xVel, int fireProbability, int row, int collumn){
        super(iteration, location, width, xVel, fireProbability, row, collumn);
    }//============================================

    @Override
    public void respondToHit(ArrayList<Explosion> explosions){
        totalHits++;
        if(totalHits < hitPoints){  //react to a hit
            explosions.add(new Explosion_Replicator((3*width)/REGEN_TIME, (Point)centerLoc.clone(), 50, 3*width/2, color));
            regenerating = true;
        }
        else
        {
            if(type < 3){   //if the ship is less than tpe 3
                type++;     //CHANGE TYPE!! This is the cool boss part
                totalHits = 0;  //reset the health back to 100%
                resetSettings();
                explosions.add(new Explosion_Replicator((3*width)/REGEN_TIME, (Point)centerLoc.clone(), 50, 3*width/2, color));
                regenerating = true;
            }
            else{
                isDestroyed = true;
                explosions.add(new Explosion(12, (Point)centerLoc.clone(), 50, 3*width/2));
            }
        }
    }//================================================

    @Override
    public void process(){
        super.process();
        frameCount++;
    }//=============================

    @Override
    public void fireMissile(ArrayList<Missile> missiles){
        if(frameCount%25 == 0 && frameCount != 0 && !regenerating){
                missiles.add(new Missile_Aimed(identifyingColor, barrelLoc, 7, false, 12, false));
        }
    }//==================================================

    private void resetSettings(){
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
    }//==============================================
}
