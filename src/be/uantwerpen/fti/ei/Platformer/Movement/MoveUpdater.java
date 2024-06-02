package be.uantwerpen.fti.ei.Platformer.Movement;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;


public class MoveUpdater {

    protected LuaValue update_func; //luaFunction


    /**
     * MoveUpdater constructor:
     * Here the lua file : "update.lua" gets opened and the
     * function update gets pulled out and stored in the class
     * as update_func.
     */
    public MoveUpdater() {
        LuaValue _G = JsePlatform.standardGlobals();
        _G.get("dofile").call(LuaValue.valueOf("src/update.lua"));

        update_func = _G.get("update");    //gets function out of file
    }


    /**
     * Method MovementComp :
     * Here the movement of the player gets updated.
     * This gets done by passing the movementComp of the player trough
     * the luafunction : update.
     * there are a couple more parameters that get passes to the function.
     * @param movementComp : All the movement info of the player
     * @param screenwidth  : The total width of the screen
     * @param collision    : True when the player has jumped on something
     * @param X_only       : True when only the x coordinate of the player needs to be updated
     * @return             : Returns the movementComp so the player can use this
     */
    public MovementComp UpdatePlayer(MovementComp movementComp, int screenwidth, boolean collision, boolean X_only) {

        //ill first make a lua table with all the relevant playerinfo to get updated  //could automate with reflexion ig
        LuaTable table = new LuaTable();

        table.hashset(LuaValue.valueOf("PosX"), LuaValue.valueOf(movementComp.getPosX()));
        table.hashset(LuaValue.valueOf("PosY"), LuaValue.valueOf(movementComp.getPosY()));
        table.hashset(LuaValue.valueOf("JmpCount"), LuaValue.valueOf(movementComp.getJumpCounter()));
        table.hashset(LuaValue.valueOf("coll"), LuaValue.valueOf(collision));
        table.hashset(LuaValue.valueOf("MaxX"), LuaValue.valueOf(screenwidth));
        table.hashset(LuaValue.valueOf("dir"), LuaValue.valueOf(movementComp.getVx()));

        //pass the table to the function update.lua
        LuaValue retvals = update_func.call(table); //gets table back out of luafunction

        //get updated position out of functiontable
        if (!X_only) { //for if platforms get scrolled instead of player then X_only == true
            movementComp.setPosY(retvals.get("PosY").toint());
            movementComp.setJumpCounter(retvals.get("JmpCount").toint());
        }

        movementComp.setPosX(retvals.get("PosX").toint());
        return movementComp;


    }

    /**
     * Method MovementComp :
     * Here the movementComp of the platforms get passed trough the same lua funtion
     * as the player, only in reverse.
     * This will make the platform scroll down when the player gets to high.
     * @param movementComp : The movementComp of the platform
     * @param scoreEnable  : When enabled the score will be set on the jumpcount
     * @return  : returns the movmentComp so the platform can use this
     */
    public MovementComp UpdatePlatform(MovementComp movementComp , boolean scoreEnable) {

        //ill first make a lua table with all the relevant playerinfo to get updated  //could automate with reflexion ig
        LuaTable table = new LuaTable();

        table.hashset(LuaValue.valueOf("PosX"), LuaValue.valueOf(movementComp.getPosX()));
        table.hashset(LuaValue.valueOf("PosY"), LuaValue.valueOf(-movementComp.getPosY()));
        table.hashset(LuaValue.valueOf("coll"), LuaValue.valueOf(false));
        table.hashset(LuaValue.valueOf("JmpCount"), LuaValue.valueOf(movementComp.getJumpCounter()));

        //pass this table to the function update.lua

        LuaValue retvals = update_func.call(table); //gets table back out of luafunction

        //get updated position out of functiontable

        if (!scoreEnable){
            movementComp.setJumpCounter(retvals.get("JmpCount").toint());
            movementComp.setPosX(retvals.get("PosX").toint());
            movementComp.setPosY(-retvals.get("PosY").toint()); //negative cause reverse motion as player
            }
        else
            movementComp.setJumpCounter(retvals.get("Score").toint());



        return  movementComp ;

    }








}
