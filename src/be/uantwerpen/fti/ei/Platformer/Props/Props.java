package be.uantwerpen.fti.ei.Platformer.Props;

import be.uantwerpen.fti.ei.Platformer.Entity;
import be.uantwerpen.fti.ei.Platformer.Movement.MovementComp;

/**
 * Class Props
 * This class is for things that power up the player in game
 */
public class Props extends Entity {



    //where on the platform does the prop live
    protected int positionOnPlat;   //0 = left , 1 = mid , 2 = right

    //how high does player jump if landed on specific prop
    protected int power ;


    //used to update the position of the props each frame to the postion of the platfrom attached,
    //but the trampoline would visibly lag a bit.
    //so instead of updating each frame i just made a new checkcollision so it wouldt matter if position
    //of platform got shared with the prop
    /**
     * Method CollisionPropPlayer
     * Used beacuse props use movementcomponent of attached platform
     * checks collision of players feet with prop
     *
     * @param player
     * @return      Return true if collision detected , else false
     */
    public boolean CollisionPropPlayer(Entity player ){

        if(positionOnPlat == 0){//  trampline links op platform

            if (player.getMovementComp().getPosX() + player.getWidth() > this.movementComp.getPosX() &&
                    player.getMovementComp().getPosX() < this.movementComp.getPosX() + this.width &&
                    player.getMovementComp().getPosY()-player.getHeight() > this.movementComp.getPosY() &&
                    player.getMovementComp().getPosY()-player.getHeight() < this.movementComp.getPosY()+this.height
            )
                return true;
            else
                return false;


        }
        else if(positionOnPlat == 1 ){// trampoline in midden van platform

            if (player.getMovementComp().getPosX() + player.getWidth() > this.movementComp.getPosX() - gr.getPlatformWidth()/2-width/2 &&
                    player.getMovementComp().getPosX() < this.movementComp.getPosX() + gr.getPlatformWidth()/2-width/2 &&
                    player.getMovementComp().getPosY()-player.getHeight() > this.movementComp.getPosY()&&
                    player.getMovementComp().getPosY()-player.getHeight() < this.movementComp.getPosY()+this.height
            )
                return true;
            else
                return false;

        }
        else{ // trampoline rechts van platform

            if (player.getMovementComp().getPosX() + player.getWidth() > this.movementComp.getPosX() - gr.getPlatformWidth()-width  &&
                    player.getMovementComp().getPosX() < this.movementComp.getPosX() + gr.getPlatformWidth() &&
                    player.getMovementComp().getPosY()-player.getHeight() > this.movementComp.getPosY() &&
                    player.getMovementComp().getPosY()-player.getHeight() < this.movementComp.getPosY()+this.height
            )
                return true;
            else
                return false;
        }




    }


    public int getId() {
        return id;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
