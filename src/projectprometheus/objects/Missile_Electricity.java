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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Andrew
 * @version May 21, 2010
 *
 * The Electricity missile flies up and hits a ship.  It then arcs electricity
 * to 4 other ships and kills them
 */
public class Missile_Electricity extends Missile{

    private boolean hasHitFirstShip = false;
    private ArrayList<Point> shipsLocations = new ArrayList<Point>();
    private int frameCount = 0;
    private boolean killShips = false;
    private Color[] colors = new Color[4];

    public Missile_Electricity(Color color, Point location, int width, boolean goingUp, int speed, boolean hittable){
        super(color, location, width, goingUp, speed, hittable);

        damageInflicted = 3;
        colors[0] = Color.yellow;
        colors[1] = Color.white;
        colors[2] = Color.cyan;
        colors[3] = Color.black;
    }//===============================================

    @Override
    public void process(){
        if(!hasHitFirstShip)
            super.process();
        else{
            frameCount++;
            if(frameCount > 29)
                killShips = true;
            //at certain time intervals, "electrocute" the ships - one by one
        }
    }//======================================

    @Override
    public void respondToHit(){
        hasHitFirstShip = true;
    }//=======================

    private void getShips(Ship firstHitShip, ArrayList<Ship> ships, ArrayList<Explosion> explosions){
        ArrayList<Integer> shipsIndexes = new ArrayList<Integer>();
        shipsLocations.add(firstHitShip.getLocation());

        int shipsFound = 1;

        for(int i = 0; i < ships.size(); i++){
            if(ships.get(i) != firstHitShip && !(ships.get(i) instanceof Ship_Tarea) && shipsFound <= 3){
                //if the ship isn't the first hit ship and it isn't the user
                shipsLocations.add(ships.get(i).getLocation());
                shipsIndexes.add(i);
                shipsFound++;
            }
        }
        if(killShips){
            for(int i = 0; i < shipsIndexes.size(); i++){
                int indexOfShip = shipsIndexes.get(i);
                ships.get(indexOfShip).setTotalHits(10);
                ships.get(indexOfShip).respondToHit(explosions);
            }
            this.isDestroyed = true;
            firstHitShip.setTotalHits(10);
            firstHitShip.respondToHit(explosions);
        }
    }//===============================

    @Override
    public void draw(Graphics2D g){
        if(hasHitFirstShip){
            Random randy = new Random();
            for(int i = 1; i < shipsLocations.size(); i++){
                //draw lines between points in the ships polygons
                Point p1 = shipsLocations.get(i - 1);
                Point p2 = shipsLocations.get(i);

                int colorPick = randy.nextInt(4);   //random number between 0 and 4
                g.setColor(colors[colorPick]);

                g.drawLine(p1.x, p1.y, p2.x, p2.y);

                g.setPaintMode();
            }
        }
        else{
            super.draw(g);
        }
    }//==============================

    /**
     * Checks to see if the missile intersects with any ships in the ships ArrayList.
     * @param ships ArrayList passed to the missile for use in determining whether or not it intersects a ship.
     */
    @Override
    public void checkHitsShip(ArrayList<Ship> ships, ArrayList<Explosion> explosions){
        for(Ship s : ships){
            if((s instanceof Ship_Tarea && !this.goingUp) || (!(s instanceof Ship_Tarea) && this.goingUp)){
                if(checkIntersects(s.getPolygon())){
                    if(!this.isDestroyed){
                        if(s instanceof Ship_Replicator){
                            //if it's a replicator ship, check to make sure it isn't regenerating
                            if(((Ship_Replicator)s).getRegenerating() == false){
                                getShips(s, ships, explosions);
                                this.respondToHit();
                                break;
                            }
                        }
                        else{
                            getShips(s, ships, explosions);
                            this.respondToHit();
                            break;
                        }
                    }
                }
            }
        }
    }//================================================

}
