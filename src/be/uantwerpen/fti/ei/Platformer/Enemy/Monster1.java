package be.uantwerpen.fti.ei.Platformer.Enemy;

import Dependencies.GraphicsContext;
import be.uantwerpen.fti.ei.Platformer.Movement.MovementComp;

import java.awt.*;

public class Monster1 extends Enemy{



    public  Monster1(MovementComp platformsPos, int h , int  w , GraphicsContext gr){

        attachedPlatformPos = platformsPos ;

        this.gr = gr ;
        height = h;
        width = w ;

        movementComp.setPosY(platformsPos.getPosY()+h);
        movementComp.setPosX(platformsPos.getPosX());

        dir = 1 ;

        timeToMove =   50;

    }


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
