package be.uantwerpen.fti.ei.Platformer.Platforms;

import Dependencies.GraphicsContext;

import java.awt.*;

public class CrackingPlatform extends PlatformEntity{

    public CrackingPlatform(int x , int y , int h , int w , GraphicsContext gr ) {        //constructor
        movementComp.setPosX(x);
        movementComp.setPosY(y);
        height = h ;
        width =  w;
        this.gr = gr ;
        id = 6;
    }


    public void Draw(){

        Graphics2D g2d = gr.getG2d();

        g2d.drawImage (gr.crackingPlatformSprite, movementComp.getPosX() , -movementComp.getPosY() , null); //drawing y-axis is reverse (top left is 0,0)

    }




}
