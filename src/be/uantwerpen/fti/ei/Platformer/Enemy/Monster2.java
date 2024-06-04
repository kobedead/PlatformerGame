package be.uantwerpen.fti.ei.Platformer.Enemy;

import Dependencies.GraphicsContext;
import be.uantwerpen.fti.ei.Platformer.Movement.MovementComp;

import java.awt.*;


/**
 * Class Monster2
 * This monster will not be attached to a platform, and
 * will chase the player in a predetermined area
 */
public class Monster2 extends Enemy{


    MovementComp playerPos ;

    int chaseRadius = 100 ;


    public  Monster2(int x  , int y ,  MovementComp playerPos, GraphicsContext gr){

        id = 26 ;

        this.gr = gr ;

        //monster2 set!!
        height = gr.getMonster1Height();
        width = gr.getMonster1Width() ;

        //gets x and y from GenOne function
        movementComp.setPosX(x);
        movementComp.setPosY(y);

        System.out.println("new");

        //to chase
        this.playerPos = playerPos ;


    }


    //need to pass this monster with all the platforms to get updated if the platforms move down



    /**
     * Method Update
     * chases player if player comes in the chaseradius
     * @param lowDelDistance
     * @return
     */
    public boolean Update(int lowDelDistance){



    //if player collides with chaseradius
    if (
            this.movementComp.getPosX() + this.width + chaseRadius >= playerPos.getPosX()  &&
            this.movementComp.getPosX() - chaseRadius <= playerPos.getPosX() + gr.getPlayerWidth()    &&

            this.movementComp.getPosY() +chaseRadius >= playerPos.getPosY()  - gr.getPlayerHeight() &&
            this.movementComp.getPosY() - this.height - chaseRadius<= playerPos.getPosY()
            ) {


        //here the monster needs to chasee the player

        //x-axis chase
        if(playerPos.getPosX() < movementComp.getPosX()){
            movementComp.setPosX(movementComp.getPosX()-1);
        }else if (playerPos.getPosX() > movementComp.getPosX()){
            movementComp.setPosX(movementComp.getPosX()+1);
        }

        //y-axis chase
        if(playerPos.getPosY() < movementComp.getPosY()){
            movementComp.setPosY(movementComp.getPosY()-1);
        }else if (playerPos.getPosY() > movementComp.getPosY()){
            movementComp.setPosY(movementComp.getPosY()+1);
        }



    }
    else
    {
    //monser needs to stay ig
            }



    //if monster goes out of bounds then delete it
    if(movementComp.getPosY() < lowDelDistance || movementComp.getPosX() < 0 || movementComp.getPosX() > gr.getScreenWidth()) {

        return true ;
    }else
        return  false;





}




    public  void Draw(){
        Graphics2D g2d = gr.getG2d();
        //monster2 better
        g2d.drawImage (gr.monster2Sprite, movementComp.getPosX() , - movementComp.getPosY(), null); //drawing y-axis (0,0) is top left

    }










}










