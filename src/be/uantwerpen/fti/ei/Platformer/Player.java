package be.uantwerpen.fti.ei.Platformer;

import java.awt.*;


import Dependencies.GraphicsContext;
import Dependencies.Input;
import be.uantwerpen.fti.ei.Platformer.Movement.MovementComp;


public class Player extends  Entity{

    private int animationDir;
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
        movementComp = new MovementComp();

        movementComp.setPosX(x);
        movementComp.setPosY(y);
        movementComp.setJumpCounter(0);

        width = gr.getPlayerWidth() ;
        height = gr.getPlayerHeight();

        this.gr = gr ;

        animationDir = 0;

    }

    /**
     * Method Draw :
     * Draws the player sprite on the buffer
     * There are multiple player images in the one playersprite,
     * this is for animations.
     * @param jumping : True if the player is jumping, for animation puposes
     * //could make the juming an int that decreases like in the Game for further, more advanced animations -> improvement.
     */
    public  void Draw(int animation ){

        Graphics2D g2d = gr.getG2d();
        int size = gr.getSize();

        if (animationDir == 500) {

            g2d.drawImage(gr.playerSprite.getSubimage(gr.getPlayerWidth()*3,0 , gr.getPlayerWidth() , gr.getPlayerHeight()), movementComp.getPosX(), -movementComp.getPosY(), null); //drawing y-axis (0,0) is top left

        }
        else {


            //regular  (left-right)
             if (animation ==  0) {
                if (animationDir == -1)
                    g2d.drawImage(gr.playerSprite.getSubimage(gr.getPlayerWidth(), 0, gr.getPlayerWidth(), gr.getPlayerHeight()), movementComp.getPosX(), -movementComp.getPosY(), null); //drawing y-axis (0,0) is top left
                else
                    g2d.drawImage(gr.playerSprite.getSubimage(0, 0, gr.getPlayerWidth(), gr.getPlayerHeight()), movementComp.getPosX(), -movementComp.getPosY(), null); //drawing y-axis (0,0) is top left
            }
            //jumping
             else if (animation == 1 ) {
                if (animationDir == -1)
                    g2d.drawImage(gr.playerSprite.getSubimage(gr.getPlayerWidth(), gr.getPlayerHeight(), gr.getPlayerWidth(), gr.getPlayerHeight()), movementComp.getPosX(), -movementComp.getPosY(), null); //drawing y-axis (0,0) is top left
                else
                    g2d.drawImage(gr.playerSprite.getSubimage(0, gr.getPlayerHeight(), gr.getPlayerWidth(), gr.getPlayerHeight()), movementComp.getPosX(), -movementComp.getPosY(), null); //drawing y-axis (0,0) is top lef
            }

            //Jetter animation
            else if (animation == 5){
                 if (animationDir == -1)

                     g2d.drawImage(gr.playerSprite.getSubimage(gr.getPlayerWidth()*2, gr.getPlayerHeight(), gr.getPlayerWidth(), gr.getPlayerHeight()), movementComp.getPosX(), -movementComp.getPosY(), null); //drawing y-axis (0,0) is top left
                 else


                     g2d.drawImage(gr.playerSprite.getSubimage(gr.getPlayerWidth()*3, gr.getPlayerHeight(), gr.getPlayerWidth(), gr.getPlayerHeight()), movementComp.getPosX(), -movementComp.getPosY(), null); //drawing y-axis (0,0) is top left



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
    public void UpdateDirection(double dir) {
        if (dir != 500){

            movementComp.setVx(dir);

        }
        animationDir = (int) dir ;
    }



    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }






}
