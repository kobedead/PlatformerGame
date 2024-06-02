package be.uantwerpen.fti.ei.Platformer.Enemy;

import be.uantwerpen.fti.ei.Platformer.Entity;
import be.uantwerpen.fti.ei.Platformer.Movement.MovementComp;

public class Enemy extends Entity {
    protected int timeToMove;
    protected int moved = 0;
    protected int dir ; //0 is down , 1 is up
    protected MovementComp attachedPlatformPos;






public boolean Update(){return true;}

}
