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

    protected int id ;

    //for the data oriented design pattern ig
    protected MovementComp movementComp;

    //hitbox
    protected int width;
    protected int height;

    //every entity has acces to the graphicsContext
    protected GraphicsContext gr ;

    /**
     * Constructor of entity
     * Created a Movement component for each entity.
     */
    public Entity(){

    }

    /**
     * Method Draw
     * each entity can use this to draw itself on the panel.
     */
    public void Draw(){};


    /**
     * method CollisionFull
     * Checks if 2 entities are colliding.
     * Gets called from 1 entity to check on.
     * @param yea   yea is the other entity to check on
     * @return      return true if collision detected , else false
     */
    public boolean CollisionFull(Entity yea){

            if (
                    this.movementComp.getPosX() + this.width >= yea.movementComp.getPosX()  &&
                            this.movementComp.getPosX() <= yea.movementComp.getPosX() + yea.width     &&

                            this.movementComp.getPosY() >= yea.movementComp.getPosY()  - yea.height &&
                            this.movementComp.getPosY() - this.height <= yea.movementComp.getPosY()
            )
            {
                return true;


            }
            else {
                return false;
            }
    }


    /**
     * Method CollisionPlayerPlatform
     * Checks if the underside of the player collides with an other entity
     * Gets called from player
     * @param Platform   Platform is the other entity to check with
     * @return           Return true if collision detected , else false
     */
    public Boolean CollisionPlayerPlatform(Entity Platform){


        if (this.movementComp.getPosX() + this.width > Platform.movementComp.getPosX() &&
            this.movementComp.getPosX() < Platform.movementComp.getPosX() + Platform.width &&
                this.movementComp.getPosY()-this.height > Platform.movementComp.getPosY()-Platform.height &&
                this.movementComp.getPosY()-this.height < Platform.movementComp.getPosY()
        )
            return true;
        else
            return false;

    }

    /**
     * Method PlayerOnTop
     * Checks if the underside of player collides with the upside of other entity.
     * underside of player gets checkt to interval ](upside - spaceAbove) , (upside + spaceAbove)[ of other entity.
     * @param player    Player object gets passed in
     * @return          Return true if collision detected , else false
     */
    public boolean PlayerOnTop(Entity player){

        int spaceAbove = 20; //20 is distance of top to check

        if( this.movementComp.getPosX() + this.width > player.movementComp.getPosX() &&
                this.movementComp.getPosX() < player.movementComp.getPosX() + player.width &&

           player.movementComp.getPosY() - player.height < this.movementComp.getPosY() - spaceAbove &&
                player.movementComp.getPosY() - player.height > this.movementComp.getPosY() + spaceAbove

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}




