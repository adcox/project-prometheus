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
package adcox.projectprometheus.userInterface;


/**
 * Class ArcadeRunner
 * Runs and animates subclasses of MotionPanel
 *
 * @author Spock adapted from Travis' Arcade Runner adapted from AppletAE
 * @version 3-02-2008
 */

import java.awt.event.*;
import javax.swing.JFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;   //For AudioClip

public class GameAppletRunner extends JApplet{

    int FPS = 22;
    JFrame myFrame;

    public GameAppletRunner() {
        myFrame = new JFrame();
        myFrame.addWindowListener(new Closer());
        addFrameComponents();
        startAnimation();
        myFrame.setVisible(true);
    }//=============================================

    public void addFrameComponents() {
        AnimationPanel world = new ProjectPrometheus();
        myFrame.setTitle(world.getMyName());
        myFrame.add(world);
    }//=============================================



    public void startAnimation() {
        javax.swing.Timer t = new javax.swing.Timer(1000/FPS, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                myFrame.getComponent(0).repaint();
                myFrame.setSize(myFrame.getComponent(0).getPreferredSize());
            }
        });
        t.start();
    }//=============================================


    public static void main(String[] args) {
        GameAppletRunner runner = new GameAppletRunner();
    } //=============================================



    private static class Closer extends java.awt.event.WindowAdapter {
        public void windowClosing (java.awt.event.WindowEvent e) {
            System.exit (0);
        }   //======================
    }//=============================================


}//END of GameAppletRunner()
