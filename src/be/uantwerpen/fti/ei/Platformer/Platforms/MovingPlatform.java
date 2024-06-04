package be.uantwerpen.fti.ei.Platformer.Platforms;

import Dependencies.GraphicsContext;

import java.awt.*;

public class MovingPlatform extends  PlatformEntity{

    int stepSizeX  = 2 ;

    int movementSize = 150 ;
    int distanceMoved ;



    public MovingPlatform(int x , int y , int h , int w , GraphicsContext gr ) {        //constructor
        movementComp.setPosX(x);
        movementComp.setPosY(y);
        height = h ;
        width =  w;
        this.gr = gr ;
        id = 7;


        if(movementComp.getPosX() < gr.backgroundImg.getWidth()/2){
            movementComp.setVx(1);
        }
        else movementComp.setVx(-1);

    }


    @Override
    public void Draw(){

        Graphics2D g2d = gr.getG2d();

        g2d.drawImage (gr.movingPlatformSprite, movementComp.getPosX() , - movementComp.getPosY() , null); //drawing y-axis is reverse (top left is 0,0)

    }


    /**
     * Method UpdateMovX
     * updates x position of the platform
     */
    @Override
    public void UpdateMovX(){



        if(movementComp.getVx() == -1){                              //moves the platform
            movementComp.incX(stepSizeX);
            distanceMoved -= stepSizeX ;
            if (distanceMoved < stepSizeX){
                distanceMoved = movementSize;
                movementComp.setVx(1);
            }
        }
        else {
            movementComp.incX(-stepSizeX);
            distanceMoved -= stepSizeX ;
            if (distanceMoved < stepSizeX){
                distanceMoved = movementSize;
                movementComp.setVx(-1);
            }
        }
    }
}
