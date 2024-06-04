package be.uantwerpen.fti.ei.Platformer.Movement;

/**
 * Class movementcomp
 * discribes the movement of entities.
 */
public class MovementComp {

    private int PosX ;
    private int PosY ;


    //these are both for the velocity
    private int JumpCounter ; // could be better i think

    private double Vx ; //-1 -> left , 1 -> right


    public int getPosX() {
        return PosX;
    }

    public void setPosX(int posX) {
        PosX = posX;
    }

    public int getPosY() {
        return PosY;
    }

    public void setPosY(int posY) {
        PosY = posY;
    }


    public int getJumpCounter() {
        return JumpCounter;
    }

    public void setJumpCounter(int jumpCounter) {
        JumpCounter = jumpCounter;
    }

    public double getVx() {
        return Vx;
    }

    public void setVx(double vx) {
        Vx = vx;
    }

    public void incX(int getal){
        PosX = PosX + getal;
    }
    public void incY(int getal){
        PosY = PosY + getal;
    }
    public void incJmpCtr(int getal){
        JumpCounter = JumpCounter+getal;
    }





}
