package be.uantwerpen.fti.ei.Platformer;

import java.awt.*;


import Dependencies.GraphicsContext;
import Dependencies.Input;


public class Player extends  Entity{

    private Input.Inputs animationDir;
    private int score;


    /**
     * Player constructer :
     * Sets initial postition and size of hitbox
     * Also set GraphicsContext
     *
     * @param x : x-position
     * @param y : y-position
     * @param gr : GraphicsContext
     */
    public Player(int x , int y , GraphicsContext gr) {

        movementComp.setPosX(x);
        movementComp.setPosY(y);
        movementComp.setJumpCounter(0);

        width = gr.getPlayerWidth() ;
        height = gr.getPlayerHeight();

        this.gr = gr ;

        animationDir = Input.Inputs.NONE;

    }

    /**
     * Method Draw :
     * Draws the player sprite on the buffer
     * There are multiple player images in the one playersprite,
     * this is for animations.
     * @param jumping : True if the player is jumping, for animation puposes
     * //could make the juming an int that decreases like in the Game for further, more advanced animations -> improvement.
     */
    public  void Draw(boolean jumping){

        Graphics2D g2d = gr.getG2d();
        int size = gr.getSize();

        if (animationDir == Input.Inputs.UP) {

            g2d.drawImage(gr.playerSprite.getSubimage(gr.getPlayerWidth()*3,0 , gr.getPlayerWidth() , gr.getPlayerHeight()), movementComp.getPosX(), -movementComp.getPosY(), null); //drawing y-axis (0,0) is top left

        }
        else {

            if (!jumping) {
                if (animationDir == Input.Inputs.LEFT)
                    g2d.drawImage(gr.playerSprite.getSubimage(gr.getPlayerWidth(), 0, gr.getPlayerWidth(), gr.getPlayerHeight()), movementComp.getPosX(), -movementComp.getPosY(), null); //drawing y-axis (0,0) is top left
                else
                    g2d.drawImage(gr.playerSprite.getSubimage(0, 0, gr.getPlayerWidth(), gr.getPlayerHeight()), movementComp.getPosX(), -movementComp.getPosY(), null); //drawing y-axis (0,0) is top left
            } else {
                if (animationDir == Input.Inputs.LEFT)
                    g2d.drawImage(gr.playerSprite.getSubimage(gr.getPlayerWidth(), gr.getPlayerHeight(), gr.getPlayerWidth(), gr.getPlayerHeight()), movementComp.getPosX(), -movementComp.getPosY(), null); //drawing y-axis (0,0) is top left
                else
                    g2d.drawImage(gr.playerSprite.getSubimage(0, gr.getPlayerHeight(), gr.getPlayerWidth(), gr.getPlayerHeight()), movementComp.getPosX(), -movementComp.getPosY(), null); //drawing y-axis (0,0) is top lef
            }
        }
    }


    /**
     * Method UpdateDirection :
     * Upddates the direction that the player needs to move,
     * this is based on the input of the keyboard.
     * *UP is for shooting
     * @param dir : direction from Input passed from RunGame()
     */
    public void UpdateDirection(Input.Inputs dir) {
        if (dir != Input.Inputs.UP){
            if (dir == Input.Inputs.LEFT){
                movementComp.setVx(-1);
            }
            else if (dir == Input.Inputs.RIGHT){
            movementComp.setVx(1);
            }
            else
            movementComp.setVx(0);
        }
        animationDir = dir ;
    }



    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }






}