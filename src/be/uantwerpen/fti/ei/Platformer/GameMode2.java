package be.uantwerpen.fti.ei.Platformer;

import Dependencies.GraphicsContext;
import Dependencies.Input;
import be.uantwerpen.fti.ei.Platformer.DirArrow;
import be.uantwerpen.fti.ei.Platformer.Enemy.Enemy;
import be.uantwerpen.fti.ei.Platformer.Enemy.Monster1;
import be.uantwerpen.fti.ei.Platformer.Misc.Bullet;
import be.uantwerpen.fti.ei.Platformer.Movement.MoveUpdater;
import be.uantwerpen.fti.ei.Platformer.Platforms.CrackingPlatform;
import be.uantwerpen.fti.ei.Platformer.Platforms.MovingPlatform;
import be.uantwerpen.fti.ei.Platformer.Platforms.PlatformEntity;
import be.uantwerpen.fti.ei.Platformer.Platforms.StaticPlatform;
import be.uantwerpen.fti.ei.Platformer.Props.Props;
import be.uantwerpen.fti.ei.Platformer.Props.Trampoline;

import java.io.*;
import java.util.ArrayList;

import static java.lang.Math.cos;

/**
 * Class that runs the game, and manages it.
 * Here the actual running game is defined.
 */
public class GameMode2 {

    private GraphicsContext grCtx;
    private Input input;


    private be.uantwerpen.fti.ei.Platformer.Player player;

    private DirArrow dirArrow ;
    private  int angle = 0;

    private ArrayList<PlatformEntity> platforms;
    private ArrayList<Enemy> enemies;
    private ArrayList<Bullet> bullets;
    private ArrayList<Props> props ;

    Input.Inputs keyInput;

    private boolean playerLocked  = false ;


    private Entity jumpedOnEntity ; //to check if jum is nessessary


    private int jumped = 0 ; //for jumping animation

    private boolean propHit;    //for higher jump



    private int lowDelDistance ;

    private int triggerHeight = 50;         //from what distance above screen new platform needs to be generated

    private int maxJumpHeight = 100;        //for variation in platform compactness

    private int wasMonster = 0;             //to help with collision of m0nster-platform

    private boolean wasTrampo = false ;

    private PlatformEntity currentPlatform; //what platform the player last jumped on

    private int hightToScroll = -600 ;      //if the player gets to this hight the platforms will move istead of player

    private PlatformEntity highestPlatform; //highest platform in the game

    private int amountOfPlatformEntities;   //amount of platforms in the game

    private boolean isRunning;              //while(isrunning) for game

    private boolean isPaused;               //while(ispaused) dor pause feature (thanks)



    private boolean begin = true;           //for begin of game (setup)
    private boolean gameOver = false;       //to get out of game


    private MoveUpdater updater ;

    private int treadSleep = 15 ; //how long the fixel delay is (tread.sleep)


//----------------------------------------------------------------------------------------------------------------------



    /**
     * constuctor of the game
     * sets the graphicsContecs, gameDimentions and Input
     * @param grCtx  the graphicscontent for the sprites and drawing on screen.
     */
    public GameMode2(GraphicsContext grCtx , Input input) {
        this.grCtx = grCtx;
        this.input = input;
        updater = new MoveUpdater(grCtx.getScreenHeight() , grCtx.getScreenWidth());
        lowDelDistance = -grCtx.getScreenHeight()+150 ;
        hightToScroll = -grCtx.getScreenHeight()+250;

    }


    /**Menthod RunGame :
     * Gets called from menu when play is selected.
     * Here the actual game gets run, you can pause or resume by pressing space.
     * A lot of methods get called from here, to check for collisions and generate platforms, ... .
     * When the player dies this method will return to GameMenu, where gameover will be handeled.
     * @throws IOException
     */

    public int RunGame() throws IOException {

        //draw backgrounf
        grCtx.getG2d().drawImage(grCtx.backgroundImg, 0, 0, null);

        //create player
        player = new Player((grCtx.getScreenWidth() - grCtx.getPlayerWidth()) / 2, -(grCtx.getScreenHeight() - grCtx.getPlayerHeight() - 50), grCtx);
        //create arrow
        dirArrow = new DirArrow(player.getMovementComp(), angle , grCtx);

        //init entities arrays
        platforms = new ArrayList();
        enemies = new ArrayList();
        bullets = new ArrayList();
        props = new ArrayList();

        //generates platforms in first frame
        GenBegin();
        //draws platform in first frame
        DrawPlatforms();
        //draw player
        player.Draw(false);

        //draw string for info
        grCtx.getG2d().drawString("Press escape to start", grCtx.getScreenWidth() / 2 - 100, 100);

        //render frame
        grCtx.render();

        //set running and pauzed
        isRunning = true;
        isPaused = true;


        while (isRunning) {
            //get user input
            if (input.inputAvailable()) {
                 keyInput = input.getInput();
                if (keyInput == Input.Inputs.ESC)
                    isPaused = !isPaused;
                else if (keyInput == Input.Inputs.UP ) {
                    player.UpdateDirection(cos(dirArrow.getAngle() *3.1415/180));
                    bullets.add(new Bullet(player.getMovementComp() , grCtx));
                }

            }
            //run the logic of the game
            try {
                //if not pauzed
                if (!isPaused) {
                    //if player is in locked mode(attached to platform)
                    if(playerLocked){


                        dirArrow.Update();



                        //if space gets pressed the players will be launched in the direction of the arrow
                        if (keyInput == Input.Inputs.SPACE){
                            keyInput = null ;

                            player.UpdateDirection(cos(dirArrow.getAngle() *3.1415/180));

                            updater.UpdatePlayer(player.movementComp , true , false);
                            playerLocked = false ;
                            jumped = 10 ;

                        }




                    }
                    else {


                        //if player gets to high -> let platforms move down
                        if ((currentPlatform.movementComp.getPosY() > hightToScroll && player.movementComp.getJumpCounter() > 0) || (propHit && player.movementComp.getJumpCounter() > 0)) {

                            //update platforms and bring jumpcountr back to player
                            player.movementComp.setJumpCounter(UpdatePlatformsY(player.movementComp.getJumpCounter()));
                            updater.UpdatePlayer(player.getMovementComp() , false , true);


                        } else {
                            propHit = false;


                            updater.UpdatePlayer(player.getMovementComp() , false , false);


                            //check if player collide
                            jumpedOnEntity = CheckCollision();

                            if (jumpedOnEntity != null) {//player landed on something and needs to stay on it

                                //lock player y position to the y position of the object it landed on
                                player.getMovementComp().setPosY(jumpedOnEntity.getMovementComp().getPosY() + grCtx.getPlayerHeight());

                                playerLocked = true;

                            }

                        }

                        //generate new platform when highest platform to is low
                        if (highestPlatform.movementComp.getPosY() < triggerHeight) {
                            Gen();
                        }

                        //if player is to low he dies.
                        if (-player.movementComp.getPosY() > grCtx.getScreenHeight()) {
                            gameOver = true;
                        }

                        //this gets done here because gameover can be called from other functions aswell.
                        if (gameOver == true) return player.getScore();









                    }


                    UpdateMovePlatform();  //for the moving platform
                    UpdateProps();
                    UpdateEnemy();
                    UpdateBullets();
                    //could be done in 1 function ig
                    DrawPlatforms();
                    DrawProps();
                    DrawEnemies();
                    DrawBullets();

                    //for the jumping animation of the player
                    if (jumped > 0) {
                        player.Draw(true);
                        jumped--;
                    } else {
                        player.Draw(false);
                    }

                    if(playerLocked)dirArrow.Draw(); //so arrow gets drawn over the rest

                    //draws score
                    grCtx.getG2d().drawString(String.valueOf(player.getScore()), grCtx.getScreenWidth() / 2 - 10, 100);
                    //render everything that is drew
                    grCtx.render();



                }
                Thread.sleep(15);   //wait intervall

            } catch (InterruptedException e) {
                System.out.println(e.getStackTrace());

            }
        }
        return player.getScore();
    }




    /**
     * Method GenBegin :
     * Gets called from RunGame.
     * Here platforms, props and enemies get generated.
     * The whole visible screen + some above it will get platfroms generated on it.
     * Only static platforms will be generated
     *
     */
    public void GenBegin() {

        int extraHeight = 200; //hight above screen to also draw onto

        //fills visable screen with platforms
        if (begin) {

            int MaxPlatforms = 15;

            //platform for player to stand on in beginning
            platforms.add(new StaticPlatform((grCtx.getScreenWidth() / 2) - 30, -grCtx.getScreenHeight() + 50, grCtx.getPlatformHeight(), grCtx.getPlatformWidth(), grCtx));
            amountOfPlatformEntities++;            //increase the total amount of platforms
            currentPlatform = platforms.getLast(); //set beginplatform as the current one

            //generate highest platforms that can be drawn
            int x = (int) (Math.random() * (grCtx.getScreenWidth() - grCtx.getPlatformWidth()));
            // int x = random.nextInt((grCtx.getScreenWidth() - grCtx.getPlatformWidth()) - 0) + 0;
            platforms.add(new StaticPlatform(x, 200, grCtx.getPlatformHeight(), grCtx.getPlatformWidth(), grCtx));
            highestPlatform = platforms.getLast();   //make this platform the highestflatform (eazy for later)
            amountOfPlatformEntities++;            //increase the total amount of platforms


            int platAlAanwezig = platforms.size();

            //get a random number for amount of platforms drawn (wiggle room of 3 )
            int aantal = (int) (Math.random() * (MaxPlatforms - (MaxPlatforms - 3))) + (MaxPlatforms - 3);


            int y;
            for (int i = 0; i <= aantal; i++) {

                //genrate random x&y in screen dimentions
                y = -((int) (Math.random() * (grCtx.getScreenHeight() + extraHeight)) - extraHeight);
                x = (int) (Math.random() * (grCtx.getScreenWidth() - grCtx.getPlatformWidth()));

                platforms.add(new StaticPlatform(x, y, grCtx.getPlatformHeight(), grCtx.getPlatformWidth(), grCtx));

                //chack for collision with already exiscting platforms //doesnt work that wel????????
                for (int j = 0; j < platforms.size(); j++) {

                    if (platforms.getLast().CollisionBig(platforms.get(j)) && (platforms.get(j) != platforms.getLast())) {
                        platforms.removeLast();                                 //remove last one if collides with one on screen
                        amountOfPlatformEntities--;
                        --i;
                    }

                }
            }

        }


    }


    /**
     * Method GenBegin :
     * Gets called from RunGame.
     * Here platforms, props and enemies get generated..
     * only one platform, prop and/or enemy will get generated,
     * this happens by a calling to the GenOne method.
     * The variation of the platforms, props and/or enemies will be based on the players score.
     *
     */
    public void Gen(){
        //fills space above screen with platforms
        if (player.getScore() < 3000) {                    //level 1
            GenOne(1, 80);
        } else if (player.getScore() < 6000) {                   //level 2
            GenOne(2, 100);
        } else if (player.getScore() < 10000) {                   //level 3
            GenOne(3, 130);
        } else if (player.getScore() < 15000) {                   //level 4
            GenOne(4, 160);
        } else {
            GenOne(5, 190);
        }
    }


    //can be fused together quite easly haha

    /**
     *
     * Method GenOne :
     * Gets called from gen, to generate a platform, prop and/or enemy.
     * Here the level of the game that is based of the score of the player gets checked.
     * this level will the determine the variation in the spawning of entities.
     * The spawning gets done above the screen, above the triggerHeight member.
     * @param level the level (1-5) at wich the player is locate.
     * @param maxPlatformSpacing this will determine how far apart the platforms may spawn apart.
     *
     *
     */
    public void GenOne(int level, int maxPlatformSpacing ) { //freq
        int color = 1; //to choose wich platform

        if (level == 2) {
            color = (int) (Math.random() * 99) + 1;
        } else if (level == 3) {
            color = (int) (Math.random() * 70) + 30;
        } else if (level == 4) {
            color = (int) (Math.random() * 63) + 45;
        } else if (level == 5) {
            color = (int) (Math.random() * 63) + 45;
        }

        //generates x coordinate but if enemie had spawn the platforms will try to not collide with enemie
        int xp;
        if ((wasMonster > 0 && enemies.size() != 0)  ) {
            if ((enemies.getLast().movementComp.getPosX() < grCtx.getScreenWidth()/2)) {
                xp = (int) (Math.random() * ((grCtx.getScreenWidth() / 2) - grCtx.getPlatformWidth())) + grCtx.getScreenWidth() / 2;
                color = 10 ; //moving platform after monster can be shit
                wasMonster--;
            } else {
                xp = (int) (Math.random() * ((grCtx.getScreenWidth() / 2) - grCtx.getPlatformWidth()));
                color = 10 ; //moving platform after monster can be shit
                wasMonster--;
            }
        }
        else if (wasTrampo){    //so trampo gets space above it
            if (platforms.getLast().movementComp.getPosX() < grCtx.getScreenWidth()/2){
                xp = (int) (Math.random() * ((grCtx.getScreenWidth() / 2) - grCtx.getPlatformWidth())) + grCtx.getScreenWidth() / 2;
                wasTrampo = false ;
            }
            else{
                xp = (int) (Math.random() * ((grCtx.getScreenWidth() / 2) - grCtx.getPlatformWidth()));
                wasTrampo = false ;
            }
        }
        else {
            xp = (int) (Math.random() * (grCtx.getScreenWidth() - grCtx.getPlatformWidth()));
        }

        //random y coordinate //the +5 is so that the platforms not touch
        int yp = (int) (Math.random() * (maxJumpHeight)) + triggerHeight + grCtx.getPlatformHeight() + 5;


        //staticplatform generate
        if (color <= 50) {
            platforms.add(new StaticPlatform(xp, yp, grCtx.getPlatformHeight(), grCtx.getPlatformWidth(), grCtx));
            amountOfPlatformEntities++;
        }

        //crackling
        else if (color <= 75) {
            // makes sure there are not 2 cracklings in a row
            if (platforms.getLast().GetId() == 6) {
                platforms.add(new StaticPlatform(xp, yp, grCtx.getPlatformHeight(), grCtx.getPlatformWidth(), grCtx));
                amountOfPlatformEntities++;

            } else {
                platforms.add(new CrackingPlatform(xp, yp,grCtx.getPlatformHeight(), grCtx.getPlatformWidth(), grCtx));
                amountOfPlatformEntities++;

            }
        }
        //horizontal moving
        else if (color <= 90) {
            platforms.add(new MovingPlatform(xp, yp, grCtx.getPlatformHeight(), grCtx.getPlatformWidth(), grCtx));
            amountOfPlatformEntities++;

        }
        //trampoline (with static platform)

        else if (color <= 98) {
            platforms.add(new StaticPlatform(xp, yp, grCtx.getPlatformHeight(), grCtx.getPlatformWidth(), grCtx));
            props.add(new Trampoline(platforms.getLast().movementComp, grCtx));
            amountOfPlatformEntities++;
            wasTrampo = true ;
        }

        //monster
        else if (color <= 105 && amountOfPlatformEntities > 30 ) {
            //with more monsters i can chose here by random also

            //for now MOnster1 on top of staticplatform
            platforms.add(new StaticPlatform(xp, yp, grCtx.getPlatformHeight(), grCtx.getPlatformWidth(), grCtx));
            enemies.add(new Monster1(platforms.getLast().getMovementComp(), grCtx.getMonster1Height(), grCtx.getMonster1Width(), grCtx));

            wasMonster = (int) (Math.random() * (2)) + 1;   //to help with colission of monster-platform

            amountOfPlatformEntities = 0 ;


        }
        //rocket or something more rare -> improvement
        else if (color <= 108 && level > 3) {


            //safe
        } else
            platforms.add(new StaticPlatform(xp, yp, grCtx.getPlatformHeight(), grCtx.getPlatformWidth(), grCtx));


        //hoogste platform aanpassen
        if (platforms.getLast().GetId() >= 10) {
            highestPlatform = platforms.get(platforms.size() - 2); //kan -1?
        } else {
            highestPlatform = platforms.getLast(); //new added is new highest
        }
    }


    /**
     * Method CheckCollison :
     * Gets called from RunGame.
     * Here the collision between the player and the platforms, props and/or enemies get checked.
     * It could alse induce a GameOver if the players moves into an enemy from the wrong angle, this will cause a
     * double return to the menu.
     *
     * @return when the collision gets detected and the player needs to jump after the collision, the method will return true.
     * other wise it returns false.
     */
    public Entity CheckCollision() { //check all collision with player

        //colision between player and platform
        for (PlatformEntity yea : platforms) {

            if (player.CollisionPlayerPlatform(yea) && player.movementComp.getJumpCounter() < 0) {          ///falling onto platform
                if (yea.GetId() == 6) {                  //cracklingplatform
                    platforms.remove(yea);
                    return null;
                } else {                                //all other platforms
                    currentPlatform = yea;
                    return yea ;
                }
            }
        }

        //collirsion between player and props
        for (Props prop : props) {
            if (player.CollisionPlayerPlatform(prop) && player.movementComp.getJumpCounter() < 0) {  ///falling onto prop
                player.movementComp.setJumpCounter(prop.getPower());
                propHit = true;
                return prop;

            }
        }

        //collision between player and enemies
        for (int i = 0; i < enemies.size(); i++) {
            if (player.movementComp.getJumpCounter() > 0 && player.CollisionFull(enemies.get(i))) {
                gameOver = true;
                return null ;
            } else if (player.movementComp.getJumpCounter() < 0 && enemies.get(i).PlayerOnTop(player) && player.CollisionFull(enemies.get(i))) { //idk of laatste && moet
                enemies.remove(i);
                wasMonster = 0 ;
                return null;
                //mss true nog zetten voor jump na kill
            } else if (player.movementComp.getJumpCounter() < 0 && player.CollisionFull(enemies.get(i))) {
                gameOver = true;
                return null;
            }

        }
        return null;
    }

    /**
     * Method UpdateBullets :
     * Gets called from RunGame.
     * Here will be checked if a bullet moves off the screen and if the bullets hit an enemy.
     * When a bullet moves off screen the bullet will get deleted.
     * When a bullet collides with an enemie, the ememy will get deleted.
     */
    public void UpdateBullets() {
        ArrayList<Enemy> ToRemove = new ArrayList();                                                //voor het wegvliegen  + collision monster
        ArrayList<Bullet> ToRemoveB = new ArrayList();

        //checks for out of bound bullets and hits with enemies
        if (bullets != null) {

            for (Bullet bullet : bullets) {

                bullet.UpdateBull();    //this with updater would be silly

                if (bullet.movementComp.getPosY() > 0) ToRemoveB.add(bullet);


                for (Enemy enemy : enemies) {
                    if (enemy.CollisionFull(bullet)) {
                        ToRemove.add(enemy);
                    }
                }
            }
            enemies.removeAll(ToRemove);
            bullets.removeAll(ToRemoveB);
        }
    }


    /**
     * Method UpdateMovePlatform :
     * Gets called from RunGame.
     * Here all the Horizotal moving platforms get individually updated.
     * This by calling their one Update method one by one.
     *
     */
    public void UpdateMovePlatform() {  //for the movingplatform

        for (PlatformEntity yea : platforms) {

            if (yea.GetId() == 7) {
                yea.UpdateMovX();
            }

        }
    }

    /**
     * Method UpdateEnemy :
     * Gets called from RunGame.
     * Here all enemies get checked if they need to get deleted, if so they get deleted.
     * The deletion happens with Monster1 when the platform were he is attached to is gone/to low.
     */
    public void UpdateEnemy() {

        ArrayList<Enemy> ToDelete = new ArrayList();

        for (Enemy enemy : enemies) {

            if (enemy.Update(lowDelDistance)) {        //upadates enemy already with the call

                ToDelete.add(enemy);

            }

        }

        enemies.removeAll(ToDelete);

    }


    public void UpdateProps(){

        ArrayList<Props> toDelete = new ArrayList();

        for (Props props1 : props){
            if(props1.getMovementComp() .getPosY() < lowDelDistance){
                toDelete.add(props1);
            }

        }

        props.removeAll(toDelete);

    }



    /**
     * Method UpdatePlatformsY :
     * Gets called from RunGame.
     * Here all entities exept the player get updated so the move down when the player jumps to high.
     * Also the entities that are to low get deleted.
     * @param Jumpcount this is the jumpcounter from the player that gets passed in, this will then get passed trough
     *                  the lua script from the player but in 'reverse' so the entities will move in opesite direction
     *                  but with the same speed
     *
     * @return  this jumpcounter gets updated(minus) and gets returned so the player gets to use it also.
     */

    public int UpdatePlatformsY(int JumpcountPlayer) {      //its more like update everything but player in Y dir

        System.out.println(platforms.size());
        ArrayList<PlatformEntity> PlatformsToRemove = new ArrayList();



        for (PlatformEntity yea : platforms) {

            yea.movementComp.setJumpCounter(JumpcountPlayer);
            updater.UpdatePlatform(yea.getMovementComp(), false );

            if (yea.movementComp.getPosY() < lowDelDistance) {          //check for platform deletion
                PlatformsToRemove.add(yea);
            }


        }
        platforms.removeAll(PlatformsToRemove); //delete to low platforms


        PlatformEntity scoreplatform = platforms.get(platforms.size()-2);

        //get score out of lua script //kind of weird but yea
        updater.UpdatePlatform(scoreplatform.getMovementComp(), true);

        int tempScore =scoreplatform.getMovementComp().getJumpCounter();
        //set new score
        player.setScore(player.getScore() + tempScore);

        //return jumpcounter for player to use
        return platforms.getLast().movementComp.getJumpCounter(); //jumpcount after
    }


    /**
     * Method DrawBullets :
     * Here all the bullets that are in the game (spawned by pressing arrow UP)
     * will be drawn, by calling their own method Draw one by one.
     */
    public void DrawBullets() {
        for (Bullet bullet : bullets) {
            bullet.Draw();
        }

    }


    /**
     * Method DrawPlatform :
     * Here all the platforms(+props) that are in the game will be drawn,
     * by calling their own method Draw one by one.
     */
    public void DrawPlatforms() {
        for (PlatformEntity yea : platforms) {
            yea.Draw();

        }
    }

    /**
     * Method DrawPlatform :
     * Here all the enemies that are in the game will be drawn,
     * by calling their own method Draw one by one.
     */
    public void DrawEnemies() {
        for (Enemy enemy : enemies) {
            enemy.Draw();
        }
    }

    public void DrawProps(){
        for (Props props1 : props){
            props1.Draw();
        }

    }




}


