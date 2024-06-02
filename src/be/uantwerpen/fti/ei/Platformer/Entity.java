package be.uantwerpen.fti.ei.Platformer;

import Dependencies.GraphicsContext;
import be.uantwerpen.fti.ei.Platformer.Movement.MovementComp;


/**
 * Class Entity :
 * Gets used as parent by all entities in game.
 * Has position and dimentions data so it can be used for collision detection.
 * Also some methods get overritten so itteration is eazy in the RunGame.
 *
 * The coordination system used by the entities is so that the orif-gin in in the top left corner of the screen,
 * so the visible screen is in the 4the quarted.
 * The Graphic libray however has a positve Y, so its like a flipped system.
 * So with drawing i use -Y.
 */
public class Entity {

    //for the data oriented design pattern ig
    protected MovementComp movementComp;

    //hitbox
    protected int width;
    protected int height;

    protected GraphicsContext gr ; //every entity has acces to the graphicsContext

    protected int LowDelDistance = -700;  //distance at wich deletion

    //constructor
    public Entity(){
        movementComp = new MovementComp();
    }

    //every entity needs to draw itself
    public void Draw(){};



    //checks full on collision
    public boolean collisionFull(Entity yea){

            if (
                    this.movementComp.getPosX() + yea.width > yea.movementComp.getPosX()  &&
                            this.movementComp.getPosX() < yea.movementComp.getPosX() + yea.width     &&

                            this.movementComp.getPosY() > yea.movementComp.getPosY()  - yea.height &&
                            this.movementComp.getPosY() - this.height < yea.movementComp.getPosY()
            )
            {
                return true;


            }
            else {
                return false;
            }
    }

    //collision between players feet and other full
    public Boolean collisionPlayerPlatform(Entity Platform){ // this one needs to only collide when player falls on to it
                             // so if jumpcounter = 0 then the player is back to falling s collision can occur

        if (this.movementComp.getPosX() + this.width > Platform.movementComp.getPosX() &&
            this.movementComp.getPosX() < Platform.movementComp.getPosX() + Platform.width &&
                this.movementComp.getPosY()-this.height > Platform.movementComp.getPosY()-Platform.height &&
                this.movementComp.getPosY()-this.height < Platform.movementComp.getPosY()
        )
            return true;
        else
            return false;

    }

    //used for checking collision with player-enemy
    public boolean PlayerOnTop(Entity player){

        if( this.movementComp.getPosX() + this.width > player.movementComp.getPosX() &&
                this.movementComp.getPosX() < player.movementComp.getPosX() + player.width &&
           player.movementComp.getPosY() - player.height < this.movementComp.getPosY() &&
                player.movementComp.getPosY() - player.height > this.movementComp.getPosY() - 20        //20 is distance of top to check

        )return  true;
        else  return false;

}

    public MovementComp getMovementComp() {
        return movementComp;
    }

    public void setMovementComp(MovementComp movementComp) {
        this.movementComp = movementComp;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public GraphicsContext getGr() {
        return gr;
    }

    public void setGr(GraphicsContext gr) {
        this.gr = gr;
    }


}




