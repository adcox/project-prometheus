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

import projectprometheus.userInterface.ProjectPrometheus;
import projectprometheus.objects.*;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Andrew
 * @version April 16, 2010
 */
public class Level {
    //general Level stuff
    protected String name;                            //name of the level
    protected Color background;                        //color of the background
    protected boolean hasSplash;                        //whether or not there is a splash screen for this level
    //Ship stuff
    protected int numCollumns, numRows;                //number of collumns and rows of ships
    protected ArrayList<Ship> ships;                  //all the ships in this level
    protected int shipType;                           //the sub-type of ship within a race
    protected int firingProbibility;                   //probability every frame that a ship will fire
    protected int initX_Vel;                          //initial x_Velocity of a ship
    //Blockade stuff
    protected int numBlockades;                       //the number of blockades
    protected ArrayList<Blockade> blockades;          //all the blockades in this level
    //keep track of races

    static public enum RaceName{GOAULD, REPLICATOR, ORI, GOA_BOSS, REP_BOSS, ORI_BOSS};
    public RaceName race = RaceName.GOAULD;
    
    
    public Level(){
        //defaults - initializing all variables
        name = "DEFAULT";
        numCollumns = 6;
        numRows = 3;
        race = RaceName.GOAULD;
        shipType = 1;
        background = Color.black;
        firingProbibility = 1200;
        initX_Vel = 1;
        hasSplash = false;
        
        ships = new ArrayList<Ship>();
        blockades = new ArrayList<Blockade>();
    }//========================================
    
    public ArrayList<Ship> initShips(){
        int stepX = (ProjectPrometheus.P_WIDTH - 2*Ship.X_MARGIN)/numCollumns;
        int stepY = (ProjectPrometheus.P_HEIGHT/3)/numRows;
        int startX = ProjectPrometheus.P_WIDTH/(numCollumns + 2);

        for(int row = 0; row < (numRows); row++){
            for(int collum = 0; collum < (numCollumns); collum++){
                Point location = new Point();
                location.x = startX + stepX*collum;
                location.y = Ship.TOP_MARGIN + stepY*row;

                //Note - size is 4/5 of stepX
                if(race.equals(RaceName.GOAULD))
                    ships.add(new Ship_Goauld(shipType, location, (stepX*4)/5, initX_Vel, firingProbibility, row, collum));
                if(race.equals(RaceName.REPLICATOR))
                    ships.add(new Ship_Replicator(shipType, location, (stepX*4)/5, initX_Vel, firingProbibility, row, collum));
                if(race.equals(RaceName.ORI))
                    ships.add(new Ship_Ori(shipType, location, (stepX*4)/5, initX_Vel, firingProbibility, row, collum));
                if(race.equals(RaceName.GOA_BOSS))
                    ships.add(new Ship_Boss_Goauld(shipType, new Point(ProjectPrometheus.P_WIDTH/2, 250), (stepX*4)/6, initX_Vel, firingProbibility, row, collum));
                if(race.equals(RaceName.REP_BOSS))
                    ships.add(new Ship_Boss_Replicator(shipType, new Point(location.x, 200), (stepX*4)/5, initX_Vel, firingProbibility, row, collum));
                if(race.equals(RaceName.ORI_BOSS))
                    ships.add(new Ship_Ori(shipType, location, (stepX*4)/5, initX_Vel, firingProbibility, row, collum));
            }
        }

        return ships;
    }//==================================
    
    public ArrayList<Blockade> initBlockades(){
        return blockades;
    }//=====================================

    public String getName(){return name;}

    public int getNumShips(){return ships.size();}

    public Color getBackground(){return this.background;}
    
    public int getNumCollumns(){return numCollumns;}
    
    public int getNumRows(){return numRows;}

    public boolean hasSplashScreen(){return hasSplash;}


}//END OF LEVEL CLASS
