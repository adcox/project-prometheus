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

import java.awt.Color;

/**
 *
 * @author Andrew
 * @version Jun 20, 2010
 */
public class Replicator_Boss extends Level {

    public Replicator_Boss(){
        name = "Fifth Fight";
        race = RaceName.REP_BOSS;
        shipType = 1;
        initX_Vel = 2;
        firingProbibility = 75;
        numCollumns = 2;
        numRows = 1;
        background = Color.black;
    }
}
