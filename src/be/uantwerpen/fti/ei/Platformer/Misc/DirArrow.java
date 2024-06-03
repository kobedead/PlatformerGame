package be.uantwerpen.fti.ei.Platformer;

import Dependencies.GraphicsContext;
import be.uantwerpen.fti.ei.Platformer.Movement.MovementComp;

import java.awt.*;
import java.awt.geom.Line2D;

import static java.awt.BasicStroke.CAP_ROUND;
import static java.awt.BasicStroke.JOIN_BEVEL;
import static java.lang.Math.cos;
import static java.lang.Math.sin;


/**
 * Class DirArrow
 * This is the arrow that gets used in gamemode2 to
 * point to the disired direction
 */
public class DirArrow {

    private GraphicsContext gr;

    //movementcomponent of the player where the arrow is attached to
    private MovementComp playerPos ;

    //lengt of arrowleg
    private int lenght = 50;

    //angle of arrow (0 is right)
    private  int angle = 0;

    //to help with going to 180 degrees and back
    private boolean dirLeft = true ;

    /**
     * Constructor of DirArrow
     * @param playerPos Position of player
     * @param angle     start angle
     * @param gr        Graphicscontext
     */
    public DirArrow(MovementComp playerPos, int angle , GraphicsContext gr) {
        this.playerPos = playerPos;
        this.gr = gr ;
    }


    /**
     * Method Update
     * Updates the angle of the arrrow [0,180] degrees
     */
    public void Update(){
        if(dirLeft){
            angle += 2 ;
            if (angle == 180)
                dirLeft = false ;

        }else{
            angle -= 2 ;
            if (angle ==0 )
                dirLeft = true ;

        }
    }

    /**
     * Method Draw
     * Draw the line (Line2D object)
     */
    public void Draw(){
        //get starting x and x of a line out of movementcomp of player
        //get coordinates of end with calculation of the angle

        int x1 = playerPos.getPosX()+gr.getPlayerWidth()/2;
        int y1 = playerPos.getPosY()+20;

        double x2 =  cos(angle*3.1415/180)*lenght ;
        double y2 =  sin(angle*3.1415/180)*lenght ;

        Graphics2D g2 = gr.getG2d();

        g2.setStroke(new BasicStroke(5 , CAP_ROUND , JOIN_BEVEL));
        g2.draw(new Line2D.Double(x1, -y1, x1+x2, -y1-y2));

    }


    public int getAngle() {
        return angle;
    }
}
