package be.uantwerpen.fti.ei.Platformer;

import Dependencies.GraphicsContext;

import java.awt.*;

public class Bullet  extends Entity{

    private int Dir ;    // -1 , 0 , 1 (l/m/u)

    private  int speed  = 10 ;

    public Bullet(Player originPlayer , int h , int w , GraphicsContext gr  ){


        movementComp.setPosX(originPlayer.movementComp.getPosX() + (originPlayer.getWidth()/2) - (w/2));
        movementComp.setPosY(originPlayer.movementComp.getPosY());


        width = w ;
        height = h ;
        this.gr = gr ;

        Dir = originPlayer.getMovementComp().getVx() ;

    }

    public void UpdateBull(){

        switch (Dir){
            case -1 :
                movementComp.incX(-2);
                movementComp.incY(speed);
                break;

            case 1 :
                movementComp.incX(1);
                movementComp.incY(speed);
                break;

            default:
                movementComp.incY(speed+1);
                break;
        }


    }

    public  void Draw(){

        Graphics2D g2d = gr.getG2d();
        g2d.drawImage (gr.bulletSprite, movementComp.getPosX() , - movementComp.getPosY(), null); //drawing y-axis (0,0) is top left


    }

}
