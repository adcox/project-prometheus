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

import adcox.projectprometheus.objects.*;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import adcox.projectprometheus.userInterface.ProjectPrometheus;

/**
 *
 * @author Andrew
 * @version April 16, 2010
 *
 * This level is all types of the replicator ships
 */
public class Replicator_Mixed extends Level {

    public Replicator_Mixed(){
        name = "Carnal Clique";
        race = RaceName.REPLICATOR;
        numCollumns = 5;
        numRows = 3;
        background = Color.black;
        initX_Vel = 1;
        firingProbibility = 1200;
    }//==========================

    @Override
    public ArrayList<Ship> initShips(){
        int stepX = (ProjectPrometheus.P_WIDTH - 2*Ship.X_MARGIN)/numCollumns;
        int stepY = (ProjectPrometheus.P_HEIGHT/3)/numRows;
        int startX = ProjectPrometheus.P_WIDTH/(numCollumns + 2);

        for(int row = 0; row < (numRows); row++){
            for(int collum = 0; collum < (numCollumns); collum++){
                Point location = new Point();
                location.x = startX + stepX*collum;
                location.y = Ship.TOP_MARGIN + stepY*row;

                //Note - size is 3/5 of stepX
                Random randy = new Random();
                shipType = randy.nextInt(3) + 1;
                ships.add(new Ship_Replicator(shipType, location, (stepX*3)/5, initX_Vel, firingProbibility, row, collum));
            }
        }

        return ships;
    }//======================================
}

