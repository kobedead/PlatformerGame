package be.uantwerpen.fti.ei.Platformer.Props;

import Dependencies.GraphicsContext;
import be.uantwerpen.fti.ei.Platformer.Entity;
import be.uantwerpen.fti.ei.Platformer.Movement.MovementComp;

import java.awt.*;


/**
 * Class Trampoline
 * This is the class for the trampoline that get attached to a platform
 * and boost the jump of a player.
 */
public class Trampoline extends Props {

    /**
     * Constructor Trampoline
     * Sets basic parameters specific to trampoline.
     * Also determins where on the platform it will live.
     * @param yea   MovementComponent of the pltaform it is attached to
     * @param gr    GraphicsContext used to draw
     */
    public Trampoline(MovementComp yea , GraphicsContext gr) {

        id = 20 ;

        power = 150; //how high player will jump

        //uses movement comp of attached platform
        movementComp = yea;

        //get graphicscontext and dimentions
        this.gr = gr;
        height = gr.getTrampolinesize();
        width = gr.getTrampolinesize();

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

            g2d.drawImage(gr.trampolineSprite, movementComp.getPosX(), -movementComp.getPosY() - height, null); //drawing y-axis is reverse (top left is 0,0)
        } else if (positionOnPlat == 1) {//zet trampoline in midden van platform

            g2d.drawImage(gr.trampolineSprite, movementComp.getPosX() + gr.getPlatformWidth() / 2 - width / 2, -movementComp.getPosY() - height, null); //drawing y-axis is reverse (top left is 0,0)

        } else { // zet trampoline rechts van platform

            g2d.drawImage(gr.trampolineSprite, movementComp.getPosX() + gr.getPlatformWidth() - width, -movementComp.getPosY() - height, null); //drawing y-axis is reverse (top left is 0,0)

        }

    }





}
