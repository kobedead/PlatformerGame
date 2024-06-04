package be.uantwerpen.fti.ei.Platformer.Platforms;
import be.uantwerpen.fti.ei.Platformer.Entity;
import be.uantwerpen.fti.ei.Platformer.Movement.MovementComp;

/**
 * Class PlatformEntity, child of Entity
 * This class will be the parent class of all platform used in the game
 */
public class PlatformEntity extends Entity {



    //gets score to add to players score (need to find something better ig)
    public int tempScore;

    //true if something is mounted to platform
    protected boolean onTop;


    /**
     * Constructor PlatformEntity
     */
    public PlatformEntity(){
        movementComp = new MovementComp();
        onTop  = false;
    }

    //to update the movingplatforms
    public void UpdateMovX(){
    }

    /**
     * Method CollisionY
     * Checks if 2 platfroms are on the same level
     * Gets called from first platform to check for.
     * @param yea   Second platform to check for
     * @return      return true if collision detected , else false
     */
    public boolean CollisionY(Entity yea ){         //check so all platfroms are other niveau
        if (this.movementComp.getPosY() > yea.getMovementComp().getPosY()-yea.getHeight()-10 &&
                this.movementComp.getPosY()-this.height< yea.getMovementComp().getPosY() +10){



            return true;
        }

        else return false;
    }





    //if this method is used for level gevenration instead of the one above, the levels look real diffrent
    //could be a cool extra feature

    /**
     * Method CollisionBig
     * Checks collsion between 2 platforms, but add extra hitbox to the one
     * where the function gets called from.
     * This method also handles props that are attached better
     * @param yea   second platform to check on
     * @return      return true if collision detected , else false
     */
    public boolean CollisionBig(Entity yea){    //gets called from platform that already exsist
        int extraHitboxX = 80 ;
        int extraHitboxYBelow = 80 ;
        int extraHitboxYAbove ;

        if (this.isOnTop()){
             extraHitboxYAbove = 170 ;
        }
        else extraHitboxYAbove = 80 ;


        if( this.movementComp.getPosX() + yea.getWidth() + extraHitboxX > yea.getMovementComp().getPosX()  &&
                this.movementComp.getPosX() - extraHitboxX < yea.getMovementComp().getPosX() + yea.getWidth()     &&

                this.movementComp.getPosY() + extraHitboxYAbove > yea.getMovementComp().getPosY()  - yea.getHeight() &&
                this.movementComp.getPosY() - this.height  - extraHitboxYBelow < yea.getMovementComp().getPosY() )
        {
            return true;
        }else return false;




    }






    public boolean isOnTop() {
        return onTop;
    }

    public void setOnTop(boolean onTop) {
        this.onTop = onTop;
    }


    public int GetId(){
        return id;
    }


}
