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
package com.adcox.projectprometheus.levels;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import com.adcox.projectprometheus.objects.Blockade;
import com.adcox.projectprometheus.objects.Supergate;

/**
 *
 * @author Andrew
 */
public class Ori_Boss extends Level {

    public Ori_Boss(){
        name = "Aggressive Adria";
        race = RaceName.ORI_BOSS;
        shipType = 4;
        initX_Vel = 1;
        firingProbibility = 1000;
        numCollumns = 5;
        numRows = 2;
        background = Color.black;
    }//====================================

    @Override
    public ArrayList<Blockade> initBlockades(){
        blockades.add(new Supergate(new Point(50,50), 100, 1));

        return blockades;
    }//===================================================
}
