package be.uantwerpen.fti.ei.Platformer.Enemy;

import Dependencies.GraphicsContext;
import be.uantwerpen.fti.ei.Platformer.Movement.MovementComp;

import java.awt.*;


/**
 * Class Monster1
 * This is a monster that will live on a platform and jump op and down on it
 */
public class Monster1 extends Enemy{


    //amount the monster moves
    protected int timeToMove;
    protected int moved = 0;
    protected int dir ; //0 is down , 1 is up
    protected MovementComp attachedPlatformPos;
    /**
     * Constructor of Monster1
     * @param platformsPos  Position of attached platfrom
     * @param gr            GraphicsContext
     */
    public  Monster1(MovementComp platformsPos, GraphicsContext gr){

        id = 25 ;

        attachedPlatformPos = platformsPos ;

        this.gr = gr ;
        height = gr.getMonster1Height();
        width = gr.getMonster1Width() ;

        movementComp.setPosY(platformsPos.getPosY()+height);
        movementComp.setPosX(platformsPos.getPosX());

        dir = 1 ;

        timeToMove =   50;

    }

    /**
     * Method Update
     * Updated the psostion of the monster on its attached platform
     * @param lowDelDistance    the distance atwich the monster needs to be deleted
     * @return                  true if to low -> deletion nessesary
     */
    @Override
    public boolean Update(int lowDelDistance){


        //System.out.println(attachedPlatform.getPosY() + "  ,  " +  attachedPlatform.getLowDelDistance());
        if (dir ==1 ){
            moved ++ ;
            if(moved == timeToMove){
                dir =0 ;
            }

        }
        else{
            moved -- ;
            if (moved == 0 ){
                dir = 1 ;

            }
        }
        movementComp.setPosY(attachedPlatformPos.getPosY()+moved+height);

        if (attachedPlatformPos.getPosY() <= lowDelDistance) return true;          //if deletion nessesary
        else return false;

    }


    public  void Draw(){
        Graphics2D g2d = gr.getG2d();
        int size = gr.getSize();
        g2d.drawImage (gr.monster1Sprite, movementComp.getPosX() , - movementComp.getPosY(), null); //drawing y-axis (0,0) is top left

    }



}
