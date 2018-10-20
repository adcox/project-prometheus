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
package adcox.projectprometheus.objects;

import adcox.projectprometheus.userInterface.ProjectPrometheus;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Andrew
 * @version May 18, 2010
 *
 * Vertical Missile:
 * > shoots upwards until through all ships in it's way
 */
public class Missile_ArcOfTruth extends Missile{
    protected Point origin = new Point();

    public Missile_ArcOfTruth(Color color, Point location, int width, boolean goingUp, int speed, boolean hittable){
        super(color, location, width, goingUp, speed, hittable);
        origin = (Point) centerLoc.clone();
    }//============================================

    @Override   //goes through everything
    public void respondToHit(){}

    @Override
    public void process(){}

    @Override
    public void draw(Graphics2D g){
        // Create and setup the gradient paint and special stroke.
        g.setColor(color);
        g.setPaint(new GradientPaint(origin, Color.BLACK, new Point(origin.x, 0), color));
        g.fillRect(origin.x - width/2, origin.y, width, ProjectPrometheus.P_HEIGHT);
    }//=========================================

}
