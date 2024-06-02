package be.uantwerpen.fti.ei.Platformer.Props;

import be.uantwerpen.fti.ei.Platformer.Entity;
import be.uantwerpen.fti.ei.Platformer.Movement.MovementComp;

public class Props extends Entity {

    protected MovementComp attachedPlatformPos;

    protected int positionOnPlat;   //0 = left , 1 = mid , 2 = right

    protected int power ;


    public void Draw() {}

    public  boolean Update(){return true;}

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
