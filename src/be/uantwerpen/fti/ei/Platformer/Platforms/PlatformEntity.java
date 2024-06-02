package be.uantwerpen.fti.ei.Platformer.Platforms;

import be.uantwerpen.fti.ei.Platformer.Entity;

public class PlatformEntity extends Entity {


    protected int id; //to tell platfroms apart

    public int tempScore;

    protected boolean onTop;    //true if something mounted to platform

public PlatformEntity(){
        onTop  =false;

}

    public void Draw(){
    }
    public void UpdateMovX(){
    }




    public boolean ToLow(){         //check if platform out of screen below

        if( -this.movementComp.getPosY()+this.height > gr.backgroundImg.getHeight() ) return true;
        else return false;

    }

    public boolean CollisionY(Entity yea ){         //check so all platfroms are other niveau
        if (this.movementComp.getPosY() > yea.getMovementComp().getPosY()-yea.getHeight()-10 &&
                this.movementComp.getPosY()-this.height< yea.getMovementComp().getPosY() +10){



            return true;
        }

        else return false;
    }

    public boolean isOnTop() {
        return onTop;
    }

    public void setOnTop(boolean onTop) {
        this.onTop = onTop;
    }


    public int getLowDelDistance() {
        return LowDelDistance;
    }

    public void setLowDelDistance(int lowDelDistance) {
        LowDelDistance = lowDelDistance;
    }

    public int GetId(){
        return id;
    }


}
