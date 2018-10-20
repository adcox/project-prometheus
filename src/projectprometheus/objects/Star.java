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
package projectprometheus.objects;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Andrew
 * @version May 26, 2010
 */
public class Star {
    private int x, y;
    private Color color;

    public Star(int x, int y, Color color){
        this.x = x;
        this.y = y;
        this.color = color;
    }//====================================

    public void draw(Graphics2D g){
        g.setColor(color);
        g.fillRect(x, y, 1, 1);
    }//==================================
}
