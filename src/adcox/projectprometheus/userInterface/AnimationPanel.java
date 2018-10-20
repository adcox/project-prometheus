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
 * Class AnimationPanel
 * Parent class for all AnimationPanels
 * Do not make changes to this class, only extend
 * 
 * 
 * @author Travis Rother
 * adapted by Spock 3-2-08 - change to abstract 
 * @version 2-25-2008
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.applet.AudioClip;

public abstract class AnimationPanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener {
    
    // Global variables
    // -- do not need to be set in subclasses
    /**
     * The number of fram that is currently being rendered.
     */
    public int frameNumber;
    /**
     * The x-location of the mouse.
     */
    public int mouseX;
    /**
     * The y-location of the mouse.
     */
    public int mouseY;

    
    /**
     * Constructor for an AnimationPanel().
     * @param n String representing the name of the panel.
     * @param width int representing the width of the frame.
     * @param height int representing the height of the frame.
     */
    public AnimationPanel(String n, int width, int height){
        frameNumber = 0;
        mouseX = 0;
        mouseY = 0;
        myName = n;
        
        this.setPreferredSize(new Dimension(width,height));
        this.setLocation(80,80);    //move to the right
        this.setVisible (true);         // make it visible to the user
        this.setFocusable(true);
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }//=========================================
    
///////////////////////////////////////////////////    
    /** Method renderFrame()
     * This is what is repeatedly animated,
     * it paints your graphics to the frame.
     * Don't forget to extend this!
     * @param g Graphics object responsible for drawing.
     */
    protected Graphics renderFrame(Graphics g) {
        return g;
    }//=============================================
///////////////////////////////////////////////////       

    
    
    String myName;
    /**
     * Returns the name of the panel.
     * @return String representing the name of the panel.
     */
    public String getMyName() {
        return myName;
    }//=============================================

    
     
////// Event Listener Methods //////
    // -- Inherited in all subclasses of MotionPanel
    // -- Extend these however you like!
    
    // KeyListener
    /**
     * Responds to a key being pressed.
     * @param e KeyEvent telling you information about the key that was pressed.
     */
    public void keyPressed (KeyEvent e) {}
    /**
     * Responds to a key being typed.
     * @param e KeyEvent telling you information about the key that was type.
     */
    public void keyTyped (KeyEvent e) {}
    /**
     * Responds to a key being released.
     * @param e KeyEvent telling you information about the key that was released.
     */
    public void keyReleased (KeyEvent e) {}  
    
    // MouseListener
    /**
     * Responds to the mouse being clicked.
     * @param e MouseEvent telling you information about the mouse.
     */
    public void mouseClicked(MouseEvent e) {}
    /**
     * Responds to the mouse entering a region.
     * @param e MouseEvent telling you information about the mouse.
     */
    public void mouseEntered(MouseEvent e) {}
    /**
     * Responds to the mouse exiting a region.
     * @param e MouseEvent telling you information about the mouse.
     */
    public void mouseExited(MouseEvent e) {}
    /**
     * Responds to the mouse being pressed.
     * @param e MouseEvent telling you information about the mouse.
     */
    public void mousePressed(MouseEvent e) {}
    /**
     * Responds to the mouse being released.
     * @param e MouseEvent telling you information about the mouse.
     */
    public void mouseReleased(MouseEvent e) {}     
    /**/    
    
    // MouseMotionListener
    // -- mouse position tracked.
    // -- you shouldn't have to extend these methods, but if you do,
    // -- remember to call super() to keep mouse tracking functionality

    /**
     * Responds to the mouse being moved.
     * @param e MouseEvent telling you information about the mouse.
     */
    public void mouseMoved(MouseEvent e) { mouseX = e.getX(); mouseY = e.getY(); }
    /**
     * Responds to the mouse being dragged.
     * @param e MouseEvent telling you information about the mouse.
     */
    public void mouseDragged(MouseEvent e) { mouseX = e.getX(); mouseY = e.getY(); }   
    /**/
    
////// -- end Event Listeners () -- //////
    
    
    // method paintComponent
    // inherited from Class JPanel
    // does the repaint() of your panel
    // --- DO NOT MODifY ---
    /**
     * Paints the screen.
     * @param g Graphics object responsible for drawing/redering.
     */
    public void paintComponent(Graphics g) {
        frameNumber++;
        this.requestFocusInWindow();
        g = renderFrame(g);
    }//=============================================
    
} //-- End AnimationPanel() Class
