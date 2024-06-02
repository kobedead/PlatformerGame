package be.uantwerpen.fti.ei.Platformer.Props;

import Dependencies.GraphicsContext;
import be.uantwerpen.fti.ei.Platformer.Movement.MovementComp;

import java.awt.*;


public class Trampoline extends Props {


    private int positionOnPlat;   //0 = left , 1 = mid , 2 = right


    public Trampoline(MovementComp yea , GraphicsContext gr){

        power = 150; //how high player will jump

        attachedPlatformPos = yea;


        this.gr = gr ;
        height = gr.getTrampolinesize();
        width = gr.getTrampolinesize();


        //waar op platform moet trampoline staan? //kan eig bij props class constructor al
        positionOnPlat = (int) (Math.random() * 3);

        if(positionOnPlat == 0){// zet trampline links op platform
            movementComp.setPosX(attachedPlatformPos.getPosX());
        }
        else if(positionOnPlat == 1 ){//zet trampoline in midden van platform
            movementComp.setPosX(attachedPlatformPos.getPosX()+gr.getPlatformWidth()/2-width/2);
        }
        else{ // zet trampoline rechts van platform
            movementComp.setPosX(attachedPlatformPos.getPosX()+gr.getPlatformWidth()-width);
        }

        movementComp.setPosY(attachedPlatformPos.getPosY() + height); //zet trampoline boven op platform

    }

    @Override
    public boolean Update(){
        if(positionOnPlat == 0){// zet trampline links op platform
            movementComp.setPosX(attachedPlatformPos.getPosX());
        }
        else if(positionOnPlat == 1 ){//zet trampoline in midden van platform
            movementComp.setPosX(attachedPlatformPos.getPosX()+gr.getPlatformWidth()/2-width/2);
        }
        else{ // zet trampoline rechts van platform
            movementComp.setPosX(attachedPlatformPos.getPosX()+gr.getPlatformWidth()-width);
        }

        movementComp.setPosY(attachedPlatformPos.getPosY() + height); //zet trampoline boven op platform

        if (attachedPlatformPos.getPosY() <= LowDelDistance) return true;          //if deletion nessesary
        else return false;


    }




    @Override
    public  void  Draw(){

        Graphics2D g2d = gr.getG2d();

        g2d.drawImage (gr.trampolineSprite, movementComp.getPosX() , -movementComp.getPosY() , null); //drawing y-axis is reverse (top left is 0,0)


    }



}
