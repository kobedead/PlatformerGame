package be.uantwerpen.fti.ei.Platformer.Props;

import Dependencies.GraphicsContext;
import be.uantwerpen.fti.ei.Platformer.Movement.MovementComp;

import java.awt.*;

public class Jetter extends Props{

    /**
     * Constructor Jetter
     * Sets basic parameters specific to Jetter.
     * Also determins where on the platform it will live.
     * @param yea   MovementComponent of the platform it is attached to
     * @param gr    GraphicsContext used to draw
     */
    public Jetter(MovementComp yea , GraphicsContext gr) {

        id = 21 ;

        power = 300; //how high player will jump

        //uses movement comp of attached platform
        movementComp = yea;

        //get graphicscontext and dimentions
        this.gr = gr;
        height = gr.getJetterSize();
        width = gr.getJetterSize();

        //waar op platform moet trampoline staan? //kan eig bij props class constructor al
        positionOnPlat = (int) (Math.random() * 3);

    }


    /**
     * Method Draw
     * Draws the trampoline sprite on screen.
     * Checks what position it needs to have on the platform also.
     */
    @Override
    public void Draw() {

        Graphics2D g2d = gr.getG2d();


        if (positionOnPlat == 0) {// zet trampline links op platform

            g2d.drawImage(gr.jetterSprite, movementComp.getPosX(), -movementComp.getPosY() - height, null); //drawing y-axis is reverse (top left is 0,0)
        } else if (positionOnPlat == 1) {//zet trampoline in midden van platform

            g2d.drawImage(gr.jetterSprite, movementComp.getPosX() + gr.getPlatformWidth() / 2 - width / 2, -movementComp.getPosY() - height, null); //drawing y-axis is reverse (top left is 0,0)

        } else { // zet trampoline rechts van platform

            g2d.drawImage(gr.jetterSprite, movementComp.getPosX() + gr.getPlatformWidth() - width, -movementComp.getPosY() - height, null); //drawing y-axis is reverse (top left is 0,0)

        }

    }









}
