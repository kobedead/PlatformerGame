package be.uantwerpen.fti.ei.Platformer.Enemy;

import be.uantwerpen.fti.ei.Platformer.Entity;
import be.uantwerpen.fti.ei.Platformer.Movement.MovementComp;

public class Enemy extends Entity {



public Enemy(){
    movementComp = new MovementComp();
}



public boolean Update(int lowDelDistance){return true;}

}
