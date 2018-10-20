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

import com.adcox.projectprometheus.userInterface.ProjectPrometheus;
import java.awt.Color;
import java.awt.Point;

/**
 *
 * @author Andrew
 * @version May 24, 2010
 */
public class Missile_TimeStop extends Missile {
    
    ProjectPrometheus pp;
    
    public Missile_TimeStop(Color color, Point location, int width, boolean goingUp, int speed, boolean hittable, ProjectPrometheus pp){
        super(color, location, width, goingUp, speed, hittable);
        this.pp = pp;
    }//==========================================
    
    @Override
    public void respondToHit(){
        isDestroyed = true;
        pp.setTimeStopped(true);
    }//===============================
}
