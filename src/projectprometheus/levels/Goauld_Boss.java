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
package projectprometheus.levels;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import projectprometheus.objects.Ship_Boss_Goauld;
import projectprometheus.objects.Ship;
import projectprometheus.userInterface.ProjectPrometheus;

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
