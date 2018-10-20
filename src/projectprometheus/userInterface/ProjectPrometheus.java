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
package projectprometheus.userInterface;

import projectprometheus.objects.*;
import projectprometheus.levels.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author Andrew
 * @version June 19, 2010
 */
public class ProjectPrometheus extends AnimationPanel{
    //*****************Variables********************
    static public final int P_WIDTH = 1024;             //width of page
    static public final int P_HEIGHT = 700;             //hieght of page;
    public final int MAX_POWER_UP = 5;                  //maximum amount of power ups the user can have
    public final int TIME_STOP_LENGTH = 150;
    public final int NUM_STARS = 400;
    static public enum ScreenMode{HOME_SCREEN, GAME_PLAY, SPLASH, OPTIONS};

    private ScreenMode mode = ScreenMode.HOME_SCREEN;                //start out in HOME_SCREEN mode
    
//***************************************************************************
    //****Home Screen
    private projectprometheus.objects.Button playBtn = new projectprometheus.objects.Button(437, 400, 30, 70, "PLAY", true);
    private projectprometheus.objects.Button optionsBtn = new projectprometheus.objects.Button(412, 440, 30, 120, "OPTIONS", true);
    private projectprometheus.objects.Button backBtn = new projectprometheus.objects.Button(437, 600, 30, 70, "BACK", true);

//***************************************************************************
    //****Options Screen
    private boolean antialiasing = true;

//***************************************************************************
    //****Game play
    private int levelNum = 0;                           //level user is on
    private int frameNum = 0;                           //frame currently being drawn

    private final int LEVEL_FRAMES_ADD = 60;            //frames to add to the end of a level before switching to next level
    private int levelEnd = 0;                           //the frame the level will end on(To be used with above addition)

    private int pointsEarned = 0;                       //points earned by the user
    private int livesLeft = 1;                          //tries the user has left - lives
    private int powerUpAmount = 0;                      //amount of power user has towards having a specail weapon
    private int powerUpIndex = -1;                      //the type of ship killed to attain a power up
    private Color powerUpColor;                         //the identifyingColor of the ship killed to attain the power up


    private boolean paused = false;                     //whether or not the game is paused
    private boolean game_over = false;                  //whether or not the game is over
    private boolean user_won = false;                   //whether or not the user has won!
    
    private boolean timeStopped = false;                //a power up for the user - stops the enemy ships from moving, firing, etc
    private int timeStopCounter = 0;

    private int leftCollumn, rightCollumn;              //remember which collumn is farthest right and left
    private int topRow, bottomRow;                      //remember which row is top and which is bottom

    //*****************Objects********************
    Toolbar toolbar;

    ArrayList<Level> levels = new ArrayList<Level>();

    ArrayList<Ship> ships = new ArrayList<Ship>();
    ArrayList<Blockade> blockades = new ArrayList<Blockade>();
    ArrayList<Missile> missiles = new ArrayList<Missile>();
    ArrayList<Explosion> explosions = new ArrayList<Explosion>();
    ArrayList<Star> stars = new ArrayList<Star>();

    ArrayList<String> oldMessages = new ArrayList<String>();
    ArrayList<String> newMessages = new ArrayList<String>();

    Font bigFont = new Font("OCR A Std",Font.PLAIN , 100);


    public ProjectPrometheus(){
        super("Project Prometheus", P_WIDTH, P_HEIGHT);

        initLevels();
    }//=======================================

    public void startGamePlay(){
        initShips();
        initBlockades();
        initStars();
        //add in the user's ship for the first time
        ships.add(new Ship_Tarea(1, new Point(P_WIDTH/2, P_HEIGHT - 100), 63, 0, 1, -1, -1));
        toolbar = new Toolbar(150, 30);
    }//=====================================

    public void initLevels(){
        levels.add(new Goauld_Gliders());
        levels.add(new Goauld_CargoShips());
        levels.add(new Goauld_Alkesh());
        levels.add(new Goauld_Motherships());
        levels.add(new Goauld_Mixed_5());
        levels.add(new Goauld_Mixed_2());
        levels.add(new Goauld_Mixed_3());
        levels.add(new Goauld_Mixed_4());
        levels.add(new Goauld_Mixed());
        levels.add(new Goauld_Boss());
        levels.add(new Replicator_Cruisers());
        levels.add(new Replicator_Warships());
        levels.add(new Replicator_Midrange_Warships());
        levels.add(new Replicator_Mixed_2());
        levels.add(new Replicator_Mixed_3());
        levels.add(new Replicator_Mixed_5());
        levels.add(new Replicator_Mixed_6());
        levels.add(new Replicator_Mixed_4());
        levels.add(new Replicator_Mixed());
        levels.add(new Replicator_Boss());
        levels.add(new Ori_Satellites());
        levels.add(new Ori_Darts());
        levels.add(new Ori_Motherships());
        levels.add(new Ori_Mixed());
        levels.add(new Ori_Mixed_2());
        levels.add(new Ori_Mixed_3());
        levels.add(new Ori_Mixed_4());
        levels.add(new Ori_Mixed_5());
        levels.add(new Ori_Mixed_6());
        levels.add(new Ori_Boss());
    }//=================================

    /**
     * Initialize all ships to be present in the level.
     */
    public void initShips(){
        //get the ships from the level
        ships.addAll(levels.get(levelNum).initShips());
        leftCollumn = 0;
        topRow = 0;
        rightCollumn = levels.get(levelNum).getNumCollumns() - 1;     //subtract 1 because we start at 0...
        bottomRow = levels.get(levelNum).getNumRows() - 1;
    }//========================================

    /**
     * Initialize all blockades to be present in the level.
     */
    public void initBlockades(){
        //get the blockades from the level
        blockades = levels.get(levelNum).initBlockades();
    }//=====================================

    /**
     * Initialize the background star field.
     */
    private void initStars(){
        Color[] colors = new Color[5];
        colors[0] = Color.white;
        colors[1] = Color.lightGray;
        colors[2] = Color.blue;
        colors[3] = Color.yellow;
        colors[4] = Color.orange;
        Random randy = new Random();

        for(int i = 0; i < NUM_STARS; i++){
            Color color = colors[randy.nextInt(colors.length)];
            int x = randy.nextInt(P_WIDTH);
            int y = randy.nextInt(P_HEIGHT);

            stars.add(new Star(x, y, color));
        }
    }//==============================================

    /**
     * Updates the variable powerUpAmount.  powerUpAmount keeps track of the amount
     * of power up the user has accumulated.
     * @param weaponIndex int representing the index of the weapon towards which
     * the user is gaining power.
     * @param shipsColor Color representing the color of the ship the user hit.
     */
    public void updatePowerUpAmount(int weaponIndex, Color shipsColor){
        if(weaponIndex == powerUpIndex){
            if(powerUpAmount < MAX_POWER_UP)
                powerUpAmount++;
        }
        else{
            if(weaponIndex == -1)   //Tarea ship sends this index when it has used it's special weapon
                powerUpAmount = 0;
            else{
                if(powerUpAmount != MAX_POWER_UP){  //once the user has the power up, don't let them lose it - they must fire it
                    powerUpAmount = 1;
                    powerUpIndex = weaponIndex;
                    powerUpColor = shipsColor;
                }
            }
        }
    }//=================================
    
    public void addToMissiles(Missile newMissile){
        missiles.add(0, newMissile);
    }//=====================================
    
    public void setTimeStopped(boolean on){
        timeStopped = on;
    }//===================================


//*****************************************************************************
    //Graphics & Animations


    @Override
    protected Graphics renderFrame(Graphics g){
        Graphics2D draw = (Graphics2D) g;
        //set up antialiasing
        if(antialiasing)
            draw.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if(mode == ScreenMode.HOME_SCREEN){
            //background
            g.setColor(Color.black);
            g.fillRect(0, 0, P_WIDTH, P_HEIGHT);
            drawStars(draw);
            //title
            drawString("Project Prometheus", draw);
                        
            //play button
            g.setFont(new Font("OCR A std", Font.PLAIN, 24));
            playBtn.draw(draw);
            optionsBtn.draw(draw);

        }//END OF HOME_SCREEN**********************************
        
        if(mode == ScreenMode.GAME_PLAY){
            drawBackground(draw);

            processMissiles(draw);
            processShips(draw);
            processBlockades(draw);
            processExplosions(draw);
            processToolbar(draw);

            if(paused){
                //display "PAUSED"
                drawString("PAUSED", draw);
            }

            if(game_over){
                //display "GAME OVER"
                drawString("GAME OVER", draw);
            }
            if(user_won){
                drawString("YOU WON!!!!", draw);
            }

            if(frameNum <= 45){
                //display the name of the level
                String name = levels.get(levelNum).getName();
                drawString(name, draw);
            }

            if(readyToLevelUp()){
                if(levelEnd == 0){
                    levelEnd = frameNum + LEVEL_FRAMES_ADD;
                }
                if(frameNum == levelEnd){
                    levelUp();
                }
            }

            frameNum++;
        }

        if(mode == ScreenMode.SPLASH){
            //background
            g.setColor(Color.black);
            g.fillRect(0, 0, P_WIDTH, P_HEIGHT);
            drawStars(draw);
            //text
            if(levels.get(levelNum).race == Level.RaceName.GOAULD)
                drawSplashScreen(draw, 0);
            if(levels.get(levelNum).race == Level.RaceName.REPLICATOR)
                drawSplashScreen(draw, 1);
            if(levels.get(levelNum).race == Level.RaceName.ORI)
                drawSplashScreen(draw, 2);
        }

        if(mode == ScreenMode.OPTIONS){
            //background
            g.setColor(Color.black);
            g.fillRect(0, 0, P_WIDTH, P_HEIGHT);

            drawOptions(draw);

            //instructions
            drawInstructions(draw);

            backBtn.draw(draw);
        }
        
        return g;
    }//========================================

    /**
     * Draw the background of the screen.
     * @param g Graphics object that does the drawing.
     */
    private void drawBackground(Graphics2D g){
        g.setColor(levels.get(levelNum).getBackground());
        g.fillRect(0, 0, P_WIDTH, P_HEIGHT);
        drawStars(g);
    }//====================================

    private void drawStars(Graphics2D g){
        for(int i = 0; i < stars.size(); i++){
            stars.get(i).draw(g);
        }
    }//=========================================

    /**
     * Draws a given string on screen in size 100 font in blue.
     * @param string String representing what is to be drawn to the screen.
     * @param g Graphics object that will be doing the drawing.
     */
    private void drawString(String string, Graphics2D g){
        //display the name of the level
        g.setColor(Color.blue);
        g.setFont(bigFont);
        g.drawString(string, P_WIDTH/2 - (string.length()/2)*51, P_HEIGHT/2);
    }//===========================================

    private String namePowerUp(int powerUpIndex){
        if(powerUpIndex == 0)
            return "Vertical Burst";
        if(powerUpIndex == 1)
            return "Horizontal Left";
        if(powerUpIndex == 2)
            return "Horizontal Right";
        if(powerUpIndex == 3)
            return "Diagonal Burst";
        if(powerUpIndex == 4)
            return "Disintegrator Pulse";
        if(powerUpIndex == 5)
            return "Disruptor Pulse";
        if(powerUpIndex == 6)
            return "Lightning Pulse";
        if(powerUpIndex == 7)
            return "Time Stop";
        if(powerUpIndex == 8)
            return "Sine Wave Missile";
        if(powerUpIndex == 9)
            return "Guided Missile";
        if(powerUpIndex == 10)
            return "Arc of Truth";

        return "NOT_YET_CODED:(";
    }//==========================================
    
    private void drawInstructions(Graphics2D g){
        g.setColor(Color.gray);
        g.setFont(new Font("OCR A std", Font.PLAIN, 24));
        g.drawString("Instructions:", 50, 500);
        
        g.setFont(new Font("OCR A std", Font.PLAIN, 18));
        g.drawString("LEFT-ARROW: move left", 75, 530);
        g.drawString("RIGHT-ARROW: move right", 75, 550);
        g.drawString("UP-ARROW: shoot regularly", 75, 570);
        g.drawString("DOWN-ARROW: shoot special weapons", 75, 590);
        g.drawString("ESCAPE: pause", 75, 610);
        g.drawString("H: Return to home screen", 75, 630);
    }//=============================================

    private void drawSplashScreen(Graphics2D g, int race){
        g.setFont(new Font("OCR A std", Font.PLAIN, 100));
        if(race == 0){      //GOAULD
            g.setColor(Color.orange);
            g.drawString("The Goa'uld", 200, 150);
            g.setFont(new Font("OCR A std", Font.PLAIN, 24));
            g.drawString("The Goa'uld are a parasitic race of aliens that take over ", 200, 350);
            g.drawString("humans as host bodies.  They are pure evil, and will stop", 200, 380);
            g.drawString("at nothing to take over the galaxy.  You must destroy them", 200, 410);
            g.drawString("before they can destroy us.  I don't think you will find", 200, 440);
            g.drawString("them too difficult to defeat.", 200, 470);
        }
        if(race == 1){      //REPLICATOR
            g.setColor(Color.lightGray);
            g.drawString("The Replicators", 200, 150);
            g.setFont(new Font("OCR A std", Font.PLAIN, 24));
            g.drawString("The Replicators are a completely robotic race. They are made", 200, 350);
            g.drawString("of small 'blocks'.  When a Replicator is shot, it shatters and", 200, 380);
            g.drawString("then reforms, or 'replicates'.  These aliens are completely", 200, 410);
            g.drawString("rutheless.  They will canibalize any technology they can get.", 200, 440);
            g.drawString("Be careful.", 200, 470);
        }
        if(race == 2){      //ORI
            g.setColor(Color.white);
            g.drawString("The Ori", 200, 150);
            g.setFont(new Font("OCR A std", Font.PLAIN, 24));
            g.drawString("The Ori are a race of god-like aliens that are made of energy. ", 200, 350);
            g.drawString("They are the self proclaimed gods of their religion, Origin. They", 200, 380);
            g.drawString("have convinced their followers to conquer the galaxy and destroy", 200, 410);
            g.drawString("all non-believers.  We have not accepted their religion, and as such", 200, 440);
            g.drawString("they wish to destroy us.  You are our last line of defense.  Good luck.", 200, 470);
        }

        g.setFont(new Font("OCR A std", Font.PLAIN, 24));
        g.setColor(Color.blue);
        g.drawString("Press ENTER to start playing!", 200, 600);
    }//==============================================

    private void drawOptions(Graphics2D g){
        g.setColor(Color.blue);
        g.setFont(new Font("OCR A std", Font.PLAIN, 24));

        g.drawString("Toggle antialiasing: Press A", 300, 200);
        g.drawString("Antialiasing: " + antialiasing, 400, 230);
    }

    /**
     * Process the missiles that are present on screen.
     * @param g Graphics object responsible for drawing.
     */
    private void processMissiles(Graphics2D g){
        for(int i = 0; i<missiles.size(); i++){
            if(!paused && !game_over){
                missiles.get(i).process();
                missiles.get(i).checkHitsShip(ships, explosions);
                missiles.get(i).checkIntersectMissiles(missiles);
                missiles.get(i).checkHitsBlockade(blockades, explosions);
            }
            
            missiles.get(i).draw(g);
            
            if(missiles.get(i).getDestroyed()){
                missiles.remove(i);
                i--;
            }
            //Take care of the Time Stop power up
            if(timeStopped)
                timeStopCounter++;
            else
                timeStopCounter = 0;
            if(timeStopCounter >= TIME_STOP_LENGTH){
                timeStopCounter = 0;
                timeStopped = false;
            }
        }
    }//========================================

    /**
     * Process the ships that are present on screen.
     * @param g Graphics object responsible for drawing
     */
    private void processShips(Graphics2D g){
        ArrayList<Integer> collumns = new ArrayList<Integer>(); //remember all of the collumns
        ArrayList<Integer> rows = new ArrayList<Integer>();     //remember all of the rows

        for(int i = 0; i<ships.size(); i++){
            if(!paused && !game_over){
                //if time isn't stopped, then move ships and let them fire
                if(!timeStopped){
                   ships.get(i).process();
                   ships.get(i).fireMissile(missiles);
                }else{  //if time IS stopped, only let user move
                    if(ships.get(i) instanceof Ship_Tarea)
                        ships.get(i).process();
                }
                
                
                //get message from the ship
                if(!ships.get(i).transmitMessage().equals("Nothin'"))
                    newMessages.add((String)ships.get(i).transmitMessage());
                //send last turn's messages to the ship
                ships.get(i).receiveMessages(oldMessages);
                
                //have the user's ship check to see if it is hitting another ship
                if(ships.get(i) instanceof Ship_Tarea)
                    ((Ship_Tarea)ships.get(i)).checkIntersectShip(ships, explosions);
                else{    //if it isn't the user's ship, remember it's collumn for comparison later...
                    collumns.add(ships.get(i).getCollumn());
                    rows.add(ships.get(i).getRow());
                    
                    
                    //DEBUGGING*********************
                    //******************************
//                    g.setColor(Color.red);
//                    g.setFont(new Font("OCR A std", Font.PLAIN, 16));
//                    g.drawString("Fire-Prob: " + ships.get(i).getFiringProb(), 75, 600);


                    //check to see if a ship on the bottom row is at the bottom of the screen
                    if(ships.get(i).getRow() == bottomRow){
                        if(ships.get(i).getLocation().y >= P_HEIGHT - Ship.BOTTOM_MARGIN){
                            newMessages.add("DONT_SHIFT_DOWN");     //if the ship is at bottom of screen, tell all ships not to shift down
                        }
                    }
                }
                
            }

            ships.get(i).draw(g);
            
            //if the user's ship is destroyed, tell the enemy ships - they will reset
            if(ships.get(i).getDestroyed()){
                if(ships.get(i) instanceof Ship_Tarea){
                    if(livesLeft > 0){  //if there are more than 0 lives left
                        ships.get(i).reset();   //reset the user's ship
                        livesLeft --;           //subtract from his lives
                        powerUpAmount = 0;      //set powerUps to 0
                        powerUpIndex = -1;
                        missiles.clear();
                    }
                    else{                       //if the user is out of lives
                        ships.remove(i);        //remove them from the screen
                        game_over = true;
                    }
                    newMessages.add("USER_DEAD");
                }
                else{
                    updatePowerUpAmount(ships.get(i).getSpecialWeaponIndex(), ships.get(i).getIndentifyingColor());
                    pointsEarned += ships.get(i).getPointValue();
                    ships.remove(i);
                    i--;
                    newMessages.add("ONE_OF_US_DIED");
                }
            }          
            
        }//end of loop through all ships

        //**********************************************
        //Check to see if user has made the lateral spread of the fleet smaller
        
        int rightIndex = 0;      //index of the collumn in collumns that is farthest to the left
        int leftIndex = 0;     //index of the collumn in collumns that is farthest to the right
     
        for(int i = 1; i <collumns.size(); i++){
            //find the biggest collumn number: the right
            if(collumns.get(i) > collumns.get(rightIndex))
                rightIndex = i;
            //find the smallest collumn number: the left
            if(collumns.get(i) < collumns.get(leftIndex))
                leftIndex = i;
        }
        if(collumns.size() > 0){
            //check to see if our smallest collumn number is bigger than what we thought the left collumn was
            if(leftCollumn < collumns.get(leftIndex)){
                newMessages.add("SPEED_UP");
                leftCollumn = collumns.get(leftIndex);
            }
            //check to see if the biggest collumn number found is smaller than what we though the far right collumn was
            if(rightCollumn > collumns.get(rightIndex)){
                newMessages.add("SPEED_UP");
                rightCollumn = collumns.get(rightIndex);
            }
        }
        
        //******************************************************
        //Check to see if vertical spread of fleet has changed
        int topIndex = 0;       //index of value that is the top-most row
        int bottomIndex = 0;    //index of value that is the bottom-most row
        
        for(int i = 1; i<rows.size(); i++){
            //check to see if there is a row above rows(topIndex): lesser values
            if(rows.get(i) < rows.get(topIndex))
                topIndex = i;
            //check to see if there is a row below rows(bottomIndex): greater values
            if(rows.get(i) > rows.get(bottomIndex))
                bottomIndex = i;
        }
        if(rows.size() > 0){
            //remember what the top and bottom rows are
            topRow = rows.get(topIndex);
            bottomRow = rows.get(bottomIndex);
        }
    
        oldMessages.clear();        //clear last frames' messages
        oldMessages = (ArrayList<String>)newMessages.clone();  //put this frames' messages into oldMessages
        newMessages.clear();        //clear this frames' messages
    }//======================================

    /**
     * Process all blockades present on screen.
     * @param g Graphics object responsible for drawing.
     */
    private void processBlockades(Graphics2D g){
        for(int i = 0; i<blockades.size(); i++){
            if(!paused && !game_over){
                blockades.get(i).process();
                blockades.get(i).checkIntersectShip(ships, explosions);
            }
            blockades.get(i).draw(g);

            if(blockades.get(i).getDestroyed()){
                blockades.remove(i);
                i--;
            }
        }
    }//========================================

    private void processExplosions(Graphics2D g){
        for(int i = 0; i<explosions.size(); i++){
            if(!paused && !game_over){
                explosions.get(i).draw(g);
            }

            if(explosions.get(i).getDestroyed()){
                explosions.remove(i);
                i--;
            }
        }
    }//========================================

    private void processToolbar(Graphics2D g){
        //find the user's ship and get it's health to initialize the toolbar
        for(int i = 0; i<ships.size(); i++){
            if(ships.get(i) instanceof Ship_Tarea){
                double hits = ships.get(i).getTotalHits();
                double hitPoints = ships.get(i).getHitPoints();
                double health = ((hitPoints - hits)/(hitPoints))*100;
                toolbar.updateInformation(pointsEarned, livesLeft, (int)health, powerUpAmount, powerUpColor, namePowerUp(powerUpIndex));
            }
        }
        toolbar.draw(g);
    }//=============================================

//*****************************************************************************
    //User-Inputs

    @Override
    @SuppressWarnings("static-access")
    public void keyPressed (KeyEvent e) {
        int code = e.getKeyCode();
        Character c = e.getKeyChar();

        if(mode == ScreenMode.GAME_PLAY){
            //send the key information to any user-controlled ships
            for(Ship s : ships){
                if(s instanceof Ship_Tarea){
                    ((Ship_Tarea)s).inputKeyPressed(code, e, missiles, powerUpIndex, powerUpAmount, powerUpColor, this);
                }
            }

            if(code == e.VK_ESCAPE){
                //pause or unpause the game
                paused = !paused;
            }
            if(code == e.VK_H){
                //new game
                reset();
                mode = ScreenMode.HOME_SCREEN;
                initLevels();   //so that I can cheat
            }
        }
        if(mode == ScreenMode.SPLASH){
           if(code == e.VK_H){
                //new game
                reset();
                mode = ScreenMode.HOME_SCREEN;
                initLevels();   //so that I can cheat
                initStars();
            }
           if(code == e.VK_ENTER){
               mode = ScreenMode.GAME_PLAY;
               if(levelNum == 0)
                    startGamePlay();
           }
        }

        if(mode == ScreenMode.HOME_SCREEN){
            if(code == e.VK_9){
                String input = JOptionPane.showInputDialog("Input level number.");
                try{
                    levelNum = Integer.parseInt(input);
                    if(levelNum >= levels.size()){
                        JOptionPane.showMessageDialog(this, "There is not a level with this number");
                        levelNum = 0;
                    }
                }
                catch(Exception x){
                    JOptionPane.showMessageDialog(this, "You must enter a number only! ::  " + x.toString());
                }
            }
        }

        if(mode == ScreenMode.OPTIONS){
            if(code == e.VK_A){
                antialiasing = !antialiasing;
            }
        }
    }//=============================================

    @Override
    public void keyReleased(KeyEvent e){
        int code = e.getKeyCode();
        if(mode == ScreenMode.GAME_PLAY){
            for(Ship s : ships){
                if(s instanceof Ship_Tarea){
                    ((Ship_Tarea)s).inputKeyReleased(code, e);
                }
            }
        }
    }//==================================================

    @Override
    public void mouseClicked(MouseEvent e){
        if(mode == ScreenMode.HOME_SCREEN){
            if(mouseX > playBtn.getX() && mouseX < playBtn.getRightSide()
                && mouseY > playBtn.getY() && mouseY < playBtn.getBottom()){

                if(levels.get(levelNum).hasSplashScreen()){
                    mode = ScreenMode.SPLASH;
                }else{
                    mode = ScreenMode.GAME_PLAY;
                    startGamePlay();
                }
            }
            if(mouseX > optionsBtn.getX() && mouseX < optionsBtn.getRightSide()
                && mouseY > optionsBtn.getY() && mouseY < optionsBtn.getBottom()){

                mode = ScreenMode.OPTIONS;
            }
        }
        if(mode == ScreenMode.OPTIONS){
            //back button
            if(mouseX > backBtn.getX() && mouseX < backBtn.getRightSide()
                && mouseY > backBtn.getY() && mouseY < backBtn.getBottom()){
                    mode = ScreenMode.HOME_SCREEN;
            }
        }
    }//==================================

    @Override
    public void mouseMoved(MouseEvent e){
        super.mouseMoved(e);
        if(mode == ScreenMode.HOME_SCREEN){
            //play button
            if(mouseX > playBtn.getX() && mouseX < playBtn.getRightSide()
                && mouseY > playBtn.getY() && mouseY < playBtn.getBottom()){
                playBtn.changeBackground(Color.white);
            }
            else
                playBtn.changeBackground(Color.black);
            //options button
            if(mouseX > optionsBtn.getX() && mouseX < optionsBtn.getRightSide()
                && mouseY > optionsBtn.getY() && mouseY < optionsBtn.getBottom()){
                optionsBtn.changeBackground(Color.white);
            }
            else
                optionsBtn.changeBackground(Color.black);
        }
        if(mode == ScreenMode.OPTIONS){
            //back button
            if(mouseX > backBtn.getX() && mouseX < backBtn.getRightSide()
                && mouseY > backBtn.getY() && mouseY < backBtn.getBottom()){
                backBtn.changeBackground(Color.white);
            }
            else
                backBtn.changeBackground(Color.black);
        }

    }//=========================================


//******************************************************************************
    //Calculations & Thinking

    private boolean readyToLevelUp(){
        if(ships.size() - 1 == 0){
            if(ships.get(0) instanceof Ship_Tarea){
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }//============================

    private void levelUp(){
        if(levelNum >= levels.size() - 1){
            //User has won!!
            user_won = true;
        }else{
            levelNum++;
            if(levels.get(levelNum).hasSplashScreen()){
                mode = ScreenMode.SPLASH;
            }

            frameNum = 0;
            levelEnd = 0;
            
            oldMessages.clear();
            newMessages.clear();
            //clear all ArrayLists
            Ship temp = null;
            for(int s = 0; s < ships.size(); s++){
                //copy the user's ship
                if(ships.get(s) instanceof Ship_Tarea){
                    temp = ships.get(s);
                }
            }
            ships.clear();
            blockades.clear();
            missiles.clear();
            explosions.clear();
            stars.clear();
            //re-initialize everything for the new level
            ships.add(temp);
            initShips();
            initBlockades();
            initStars();
        }
    }//================================
    
    private void reset(){
        levelNum = 0;
        frameNum = 0;
        pointsEarned = 0;
        livesLeft = 1;
        powerUpAmount = 0;
        powerUpIndex = -1;
        paused = false;
        game_over = false;
        
        oldMessages.clear();
        newMessages.clear();
        ships.clear();
        missiles.clear();
        explosions.clear();
        blockades.clear();
        levels.clear();
        stars.clear();
    }//===================================

}//END OF PROJECT PROMETHEUS
