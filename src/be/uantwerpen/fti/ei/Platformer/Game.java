package be.uantwerpen.fti.ei.Platformer;

import Dependencies.GraphicsContext;
import Dependencies.Input;
import be.uantwerpen.fti.ei.Platformer.Enemy.Enemy;
import be.uantwerpen.fti.ei.Platformer.Enemy.Monster1;
import be.uantwerpen.fti.ei.Platformer.Movement.MoveUpdater;
import be.uantwerpen.fti.ei.Platformer.Platforms.CrackingPlatform;
import be.uantwerpen.fti.ei.Platformer.Platforms.MovingPlatform;
import be.uantwerpen.fti.ei.Platformer.Platforms.PlatformEntity;
import be.uantwerpen.fti.ei.Platformer.Platforms.StaticPlatform;
import be.uantwerpen.fti.ei.Platformer.Props.Props;
import be.uantwerpen.fti.ei.Platformer.Props.Trampoline;

import java.io.*;
import java.util.ArrayList;

/**
 * Class that runs the game, and manages it.
 * Here the menu's and the actual running game are defined.
 */
public class Game {

    private GraphicsContext grCtx;


    private be.uantwerpen.fti.ei.Platformer.Player player;

    private ArrayList<PlatformEntity> platforms;
    private ArrayList<Enemy> enemies;
    private ArrayList<Bullet> bullets;
    private ArrayList<Props> props ;


    private int jumped = 0 ; //for jumping animation
    private boolean jump ; //for jumpstart

    private boolean propHit;    //for higher jump

    private Input input;

    private int triggerHeight = 50; //from what distance above screen new platform needs to be generated

    private int maxJumpHeight = 100; //for variation in platform compactness

    private int wasMonster = 0; //to help with collision of m0nster-platform

    private boolean wasTrampo = false ;

    private PlatformEntity currentPlatform;     //what platform the player last jumped on

    private int hightToScroll = -600 ;     //if the player gets to this hight the platforms will move istead of player

    private PlatformEntity highestPlatform;


    private int amountOfPlatformEntities;  //will use this to help with enemies spawning not near each other

    private boolean isRunning;              //while(isrunning) for game

    private boolean isPaused;               //while(ispaused) dor pause feature (thanks)

    private boolean gameOver = false;       //to get out of game into gameover screen

    private boolean menu = true;            //while(menu) for menu //could use while(true) ig

    private boolean begin = true;           //for begin of game (setup)

    private boolean rendered = false;       //to render screens 1 time (go against flickering)

    private int menutype;                   //in what menu we are located

    private int themesType = 1;             //what themescreen

    private MoveUpdater updater ;

    private int treadSleep = 15 ; //how long the fixel delay is (tread.sleep)

    /**
     * constuctor of the game
     * sets the graphicsContecs, gameDimentions and Input
     * @param grCtx  the graphicscontent for the sprites and drawing on screen.
     */
    public Game(GraphicsContext grCtx) {
        this.grCtx = grCtx;
        grCtx.setGameDimensions(grCtx.getScreenWidth() / 3, grCtx.getScreenHeight() / 3);
        input = new Input(grCtx);
        updater = new MoveUpdater();



    }

    /**
     * Method MenuGame
     * Here the menu gets drawn, also the passing between selective screens is managed and drawn.
     * the player can choose between play, scoreboard and themes.
     * these get called as their own method.
     * to switch between options the left and right arrow keys are used.
     * to select an option space is used
     * @throws IOException  inputExeption
     */
    public void MenuGame() throws IOException, InterruptedException {
        menutype = 1;
        rendered = false;

        Input.Inputs direction = Input.Inputs.NONE;

        while (menu == true) {

            if (gameOver == true) {

                GameOver();

            } else {

                if ( input.inputAvailable()) {

                    direction = input.getInput();


                    if (direction == Input.Inputs.RIGHT) {
                        rendered = false;
                        if (menutype != 3)
                            menutype += 1;
                        else
                            menutype = 1;
                    } else if (direction == Input.Inputs.LEFT) {
                        rendered = false;
                        if (menutype != 1)
                            menutype -= 1;
                        else
                            menutype = 3;
                    } else if (direction == Input.Inputs.ENTER) {     //spce while on run -> start game
                        if (menutype == 1) {
                            RunGame();
                        } else if (menutype == 2) {//space while on score -> to scoreboard
                            ScoreBoard();
                        } else if (menutype == 3) { //space while on themes
                            Themes();
                        }
                    }
                }

                if (rendered == false) {         // i do the rendering seperate to go against flickering

                    switch (menutype) {
                        case 1:    //menu with play selected
                            grCtx.getG2d().drawImage(grCtx.menuImgPlay, 0, 0, null);    //draws menu with play selected
                            grCtx.render();
                            rendered = true;
                            break;
                        case 2: //menu with score selected
                            grCtx.getG2d().drawImage(grCtx.menuImgScore, 0, 0, null);    //draws menu with score selected
                            grCtx.render();
                            rendered = true;
                            break;
                        case 3: //menu with theme selected
                            grCtx.getG2d().drawImage(grCtx.menuImgTheme, 0, 0, null);    //draws menu with theme selected
                            grCtx.render();
                            rendered = true;
                            break;
                    }

                }
            }

            //fixed intervall for the while loop //otherwise Inut didnt work properly
            Thread.sleep(treadSleep);

        }
    }

    /**
     * Method Themes
     * this method get called when the themes option is selected for the menu.
     * here the Themes menu get drawn, in this state of the game there are 2 themes.
     * these have no previeuw(good improvement)
     * when a theme is selected all sprited and the background will be changed,
     * this gets done by a method in the class GraphicsContext
     */
    public void Themes() throws InterruptedException {

        rendered = false;

        while (true) {

            if (input.inputAvailable()) {
                Input.Inputs direction = input.getInput();


                if (direction == Input.Inputs.RIGHT || direction == Input.Inputs.LEFT) {
                    if (themesType == 1) {
                        rendered = false;
                        themesType = 2;
                    } else if (themesType == 2) {
                        rendered = false;
                        themesType = 1;
                    } else if (themesType == 3) {
                        rendered = false;
                        themesType = 4;
                    } else if (themesType == 4) {
                        rendered = false;
                        themesType = 3;
                    }
                } else if (direction == Input.Inputs.ENTER) {
                    if (themesType == 2) {
                        rendered = false;
                        themesType = 4;
                        grCtx.ThemeChange(2);
                    } else if (themesType == 3) {
                        rendered = false;
                        themesType = 1;
                        grCtx.ThemeChange(1);
                    }
                } else if (direction == Input.Inputs.SPACE) {
                    rendered = false;
                    return;
                }

            }

            if (rendered == false) {

                if (themesType == 1) {
                    grCtx.getG2d().drawImage(grCtx.themes1, 0, 0, null);
                    grCtx.render();
                    rendered = true;

                } else if (themesType == 2) {
                    grCtx.getG2d().drawImage(grCtx.themes2, 0, 0, null);
                    grCtx.render();
                    rendered = true;

                } else if (themesType == 3) {
                    grCtx.getG2d().drawImage(grCtx.themes3, 0, 0, null);
                    grCtx.render();
                    rendered = true;

                } else if (themesType == 4) {
                    grCtx.getG2d().drawImage(grCtx.themes4, 0, 0, null);
                    grCtx.render();
                    rendered = true;

                }


            }


            Thread.sleep(treadSleep);


        }


    }

    /**
     * Method GameOver:
     * Gets called when the player dies ingame.(from the menu)
     * Here the gameover screen is drawn, together with the players score.
     * From here you can go back to the menu by hitting space(return).
     * If you want to save you score you can hit enter and the StoreScore method will get called.
     */
    public void GameOver() throws InterruptedException {


        grCtx.getG2d().drawImage(grCtx.GameOverBack, 0, 0, null);    //draws background for gameover
        grCtx.getG2d().drawString("Your Score  : " + String.valueOf(player.getScore()), grCtx.GameOverBack.getWidth() / 2 - 50, 100); //draws score
        grCtx.render();


        while (gameOver == true) {


            if (input.inputAvailable()) {
                Input.Inputs KeyInput = input.getInput();
                if (KeyInput == Input.Inputs.SPACE) {
                    gameOver = false;
                    rendered = false;
                    return;                     //goes back to menu
                } else if (KeyInput == Input.Inputs.ENTER) {
                    StoreScore();               //goes to screen to store your score
                    gameOver = false;
                    rendered = false;
                    return;                     //goes back to menu
                }
            }

            Thread.sleep(treadSleep);

        }


    }

    /**
     * Method StoreScore :
     * Gets called from method GameOver if enter is pressed.
     * Here the StoreScore screen is drawn(black), with the score and a prompt to enter your id.
     * The id is a substitute for the players name cause all the letters would be a lot of work :).
     * When the id is entered and enter is pressed the score + the id will be stored in the Score.txt file.
     * When this is done it will return to the GameOver method to return to the Menu.
     */

    public void StoreScore() throws InterruptedException {
        String ID = "";
        rendered = false;

        Input.Inputs KeyInput = null;
        while (KeyInput != Input.Inputs.ENTER) {

            if (input.inputAvailable()) {
                KeyInput = input.getInput();
                switch (KeyInput) {
                    case Input.Inputs.EEN:
                        ID += "1";
                        rendered = false;
                        break;
                    case Input.Inputs.TWEE:
                        ID += "2";
                        rendered = false;
                        break;
                    case Input.Inputs.DRIE:
                        ID += "3";
                        rendered = false;
                        break;
                    case Input.Inputs.VIER:
                        ID += "4";
                        rendered = false;
                        break;
                    case Input.Inputs.VIJF:
                        ID += "5";
                        rendered = false;
                        break;
                    case Input.Inputs.ZES:
                        ID += "6";
                        rendered = false;
                        break;
                    case Input.Inputs.ZEVEN:
                        ID += "7";
                        rendered = false;
                        break;
                    case Input.Inputs.ACHT:
                        ID += "8";
                        rendered = false;
                        break;
                    case Input.Inputs.NEGEN:
                        ID += "9";
                        rendered = false;
                        break;
                    case Input.Inputs.NUL:
                        ID += "0";
                        rendered = false;
                        break;
                }

            }
            if (rendered == false) {
                grCtx.getG2d().drawImage(grCtx.ScoreInvul, 0, 0, null);    //draws background for score
                grCtx.getG2d().drawString("Your Score  : " + String.valueOf(player.getScore()), grCtx.getScreenWidth() / 2 - 50, 100); //draws score
                grCtx.getG2d().drawString("Type your ID and hit enter: ", grCtx.getScreenWidth() / 2 - 200, 500); //draws score

                grCtx.getG2d().drawString(ID, grCtx.getScreenWidth()/ 2 + 80, 500); //draws score
                grCtx.render();
                rendered = true;
            }

            Thread.sleep(treadSleep);
        }
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter("Score.txt", true));
            printWriter.append("\n");
            printWriter.append(ID);
            printWriter.append("\n");
            printWriter.append(String.valueOf(player.getScore()));
            printWriter.close();
            System.out.println("Successfully wrote score & id to the Score.txt.");
        } catch (IOException e) {
            System.out.println("An error occurred with writing to Score.txt");
            e.printStackTrace();
        }
    }


    /**
     * Method Scoreboard :
     * Gets called from menu if the scorebord option is selected.
     * Here the file Score.txt gets read and assigned to a player(local class Member).
     * These members get sorted and drawn on the screen with their id and score.
     * Also when there are more than 10 scored in the Score.txt file the lowest one gets deleted.(not is the file tho -> improvement)
     * Returns to menu when spece is pressed.
     */
    public void ScoreBoard() throws InterruptedException {
        //class member to display on screen
        class Member {
            public String ID;
            public int Score;

            public Member(String id) {
                ID = id;
            }

            public void setScore(int score) {
                Score = score;
            }
        }

        //array for all saved players
        ArrayList<Member> members = new ArrayList();
        boolean ID = false;

        //read players out of Score.txt
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("Score.txt"));
            String line = reader.readLine();

            while (line != null) {
                if (ID == false) {
                    members.add(new Member(line));
                    ID = true;
                } else {
                    members.getLast().setScore(Integer.valueOf(line));
                    ID = false;
                }
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //sorting the players by score
        for (int i = 0; i < members.size(); i++) {

            // Inner nested loop pointing 1 index ahead
            for (int j = i + 1; j < members.size(); j++) {
                // Checking elements
                Member temp = null;
                if (members.get(j).Score > members.get(i).Score) {

                    // Swapping
                    temp = members.get(i);
                    members.set(i, members.get(j));
                    members.set(j, temp);
                }
            }

        }
        //remove players so only top 10 stay to get displayed   //not in file tho -> improvement
        while (members.size() > 10) {
            members.removeLast();
        }

        //draws background for score
        grCtx.getG2d().drawImage(grCtx.ScoreInvul, 0, 0, null);


        //draw labels above score & id
        grCtx.getG2d().drawString("       ID" + "                " + "  Score ", grCtx.GameOverBack.getWidth() / 2 - 200, 170); //draws score


        //draw ranked list of players score next to their id
        for (int i = 0; i < members.size(); i++) {

            grCtx.getG2d().drawString(String.valueOf(i + 1) + ".   " + members.get(i).ID + "........................" + members.get(i).Score, grCtx.GameOverBack.getWidth() / 2 - 200, 200 + (i * 30)); //draws score

        }

        //buffer switch
        grCtx.render();


        //waiting for keypress to get back to menu
        Input.Inputs KeyInput = null;
        while (KeyInput != Input.Inputs.SPACE) {

            if (input.inputAvailable()) {
                KeyInput = input.getInput();

            }
            Thread.sleep(treadSleep);
        }

        //go back to menu
        rendered = false;
        return;
    }

    /**Menthod RunGame :
     * Gets called from menu when play is selected.
     * Here the actual game gets run, you can pause or resume by pressing space.
     * A lot of methods get called from here, to check for collisions and generate platforms, ... .
     * When the player dies this method will return to GameMenu, where gameover will be handeled.
     * @throws IOException
     */

    public void RunGame() throws IOException {
        grCtx.getG2d().drawImage(grCtx.backgroundImg, 0, 0, null);

        player = new Player((grCtx.getScreenWidth() - grCtx.getPlayerWidth()) / 2, -(grCtx.getScreenHeight() - grCtx.getPlayerHeight() - 50), grCtx);
        platforms = new ArrayList();
        enemies = new ArrayList();
        bullets = new ArrayList();
        props = new ArrayList();
        GenBegin();             //generates platforms in first frame
        DrawPlatforms();             //draws platform in first frame
        player.Draw(false);  //draw player
        grCtx.getG2d().drawString("Press space to start", grCtx.getScreenWidth() / 2 - 100, 100);

        grCtx.render();

        isRunning = true;
        isPaused = true;


        while (isRunning) {
            if (input.inputAvailable()) {
                Input.Inputs direction = input.getInput();
                if (direction == Input.Inputs.SPACE)
                    isPaused = !isPaused;
                else if (direction == Input.Inputs.UP) {
                    bullets.add(new Bullet(player, grCtx.getBulletsize(), grCtx.getBulletsize(), grCtx));
                    player.UpdateDirection(direction);
                }
                else
                    player.UpdateDirection(direction);

            }
            try {
                if (!isPaused) {


                    UpdateMovePlatform();  //for the moving platform
                    UpdateProps();
                    UpdateEnemy();
                    UpdateBullets();

                    //if player gets to high -> let platforms move down
                    if ((currentPlatform.movementComp.getPosY() > hightToScroll && player.movementComp.getJumpCounter() > 0) || (propHit && player.movementComp.getJumpCounter() > 0)) {

                            //update platforms and bring jumpcountr back to player
                            player.movementComp.setJumpCounter(UpdatePlatformsY(player.movementComp.getJumpCounter()));

                            //let player move left and right
                            player.setMovementComp(updater.UpdatePlayer(player.getMovementComp(), grCtx.getScreenWidth() , false , true));       //update player x coordinate only
                            //without the data-oriented it was just : Player.Update(false , true);

                    } else {
                            propHit = false;
                            //check if player collide to jump
                            jump = CheckCollision();
                            //update player movement
                            player.setMovementComp(updater.UpdatePlayer(player.getMovementComp(), grCtx.getScreenWidth() , jump , false));
                            //for jumping animation
                            if (jump) {
                                jumped = 10 ;
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
                    if (gameOver == true) return;


                    //could be done in 1 function ig
                    DrawPlatforms();
                    DrawProps();
                    DrawEnemies();
                    DrawBullets();


                    //for the jumping animation of the player
                    if (jumped > 0 ){
                        player.Draw(true);
                        jumped -- ;
                    }
                    else {
                        player.Draw(false  );
                    }

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

                    if (platforms.getLast().CollisionY(platforms.get(j)) && (platforms.get(j) != platforms.getLast())) {
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
                wasMonster--;
            } else {
                xp = (int) (Math.random() * ((grCtx.getScreenWidth() / 2) - grCtx.getPlatformWidth()));
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
    public boolean CheckCollision() { //check all collision with player

        //colision between player and platform
        for (PlatformEntity yea : platforms) {

            if (player.collisionPlayerPlatform(yea) && player.movementComp.getJumpCounter() < 0) {          ///falling onto platform
                if (yea.GetId() == 6) {                  //cracklingplatform
                    platforms.remove(yea);
                    return false;
                } else {                                //all other platforms
                    currentPlatform = yea;
                    return true;
                }
            }
        }

        //collirsion between player and props
        for (Props prop : props) {
            if (player.collisionPlayerPlatform(prop) && player.movementComp.getJumpCounter() < 0) {  ///falling onto prop
                player.movementComp.setJumpCounter(prop.getPower());
                propHit = true;
                return true;

            }
        }

        //collision between player and enemies
        for (int i = 0; i < enemies.size(); i++) {
            if (player.movementComp.getJumpCounter() > 0 && player.collisionFull(enemies.get(i))) {
                gameOver = true;
                return false;
            } else if (player.movementComp.getJumpCounter() < 0 && enemies.get(i).PlayerOnTop(player) && player.collisionFull(enemies.get(i))) { //idk of laatste && moet
                enemies.remove(i);
                wasMonster = 0 ;
                return true;
                //mss true nog zetten voor jump na kill
            } else if (player.movementComp.getJumpCounter() < 0 && player.collisionFull(enemies.get(i))) {
                gameOver = true;
                return false;
            }

        }
        return false;
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
                    if (enemy.collisionFull(bullet)) {
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

            if (enemy.Update()) {        //upadates enemy already with the call

                ToDelete.add(enemy);

            }

        }

        enemies.removeAll(ToDelete);

    }


    public void UpdateProps(){

        ArrayList<Props> toDelete = new ArrayList();

        for (Props props1 : props){
            if(props1.Update()){
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


        ArrayList<PlatformEntity> PlatformsToRemove = new ArrayList();



        for (PlatformEntity yea : platforms) {

            yea.movementComp.setJumpCounter(JumpcountPlayer);
            yea.setMovementComp(updater.UpdatePlatform(yea.getMovementComp(), false ));

            if (yea.movementComp.getPosY() < yea.getLowDelDistance()) {          //check for platform deletion
                PlatformsToRemove.add(yea);
            }


        }
        platforms.removeAll(PlatformsToRemove); //delete to low platforms

        //get score out of lua script //kind of weird but yea
        int tempScore = updater.UpdatePlatform(platforms.get(platforms.size()-2).getMovementComp(), true).getJumpCounter();
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


