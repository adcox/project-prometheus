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
package adcox.projectprometheus.levels;

import adcox.projectprometheus.userInterface.ProjectPrometheus;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import adcox.projectprometheus.objects.Blockade;

/**
 *
 * @author Andrew
 * @version April 16, 2010
 *
 * This level contains only death gliders
 */
public class Goauld_Gliders extends Level {
    
    public Goauld_Gliders(){
        name = "Goa'uld Gliders";
        race = RaceName.GOAULD;
        shipType = 1;
        initX_Vel = 1;
        firingProbibility = 2000;
        numCollumns = 6;
        numRows = 3;
        background = Color.black;
        hasSplash = true;
    }//==========================
    

    @Override
    public ArrayList<Blockade> initBlockades(){
        Random randy = new Random();
        int xLoc = randy.nextInt(ProjectPrometheus.P_WIDTH/2) + ProjectPrometheus.P_WIDTH/4;

        Color brown = new Color(105, 58, 20);
        blockades.add(new Blockade(new Point(xLoc, 450), 50, brown, 5));

        return blockades;
    }//===================================================
}
