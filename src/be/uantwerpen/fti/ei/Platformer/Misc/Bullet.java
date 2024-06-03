package be.uantwerpen.fti.ei.Platformer.Misc;

import Dependencies.GraphicsContext;
import be.uantwerpen.fti.ei.Platformer.Entity;
import be.uantwerpen.fti.ei.Platformer.Movement.MovementComp;
import be.uantwerpen.fti.ei.Platformer.Player;

import java.awt.*;


/**
 * Class Bullet
 */
public class Bullet  extends Entity {

    /**
     * Constructor Bullet
     * @param originPlayer position of the player
     * @param gr           graphicscontext
     */
    public Bullet(MovementComp originPlayer , GraphicsContext gr  ){

        int speed  = 10 ;
        movementComp.setJumpCounter(10);

        movementComp.setPosX(originPlayer.getPosX() + (gr.getPlayerWidth()/2) - (gr.getBulletsize()/2));
        movementComp.setPosY(originPlayer.getPosY());


        width = gr.getBulletsize() ;
        height = gr.getBulletsize() ;
        this.gr = gr ;

        movementComp.setVx(originPlayer.getVx());

    }

    /**
     * Method UpdateBull
     * Updates postion of bullet, based of off player postion when generated
     */
    public void UpdateBull(){

        movementComp.incX((int)(2*movementComp.getVx()));
        movementComp.incY(movementComp.getJumpCounter());

    }

    /**
     * Method Draw
     * Draws the bullet on jpanel
     */
    public  void Draw(){

        Graphics2D g2d = gr.getG2d();
        g2d.drawImage (gr.bulletSprite, movementComp.getPosX() , - movementComp.getPosY(), null); //drawing y-axis (0,0) is top left


    }

}
