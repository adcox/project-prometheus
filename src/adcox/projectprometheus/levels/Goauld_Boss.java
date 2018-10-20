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
 */
package adcox.projectprometheus.levels;

import java.awt.Color;

/**
 *
 * @author Andrew
 */
public class Goauld_Boss extends Level{

    public Goauld_Boss(){
        name = "Anubis Attacks";
        race = RaceName.GOA_BOSS;
        shipType = 1;
        initX_Vel = 1;
        firingProbibility = 75;
        numCollumns = 1;
        numRows = 1;
        background = Color.black;
    }//============================
}
