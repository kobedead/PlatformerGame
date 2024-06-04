package be.uantwerpen.fti.ei.Platformer;

import Dependencies.GraphicsContext;
import Dependencies.Input;

import java.io.*;
import java.util.ArrayList;


/**
 * Class GameMenu
 * Houses all the logic for browsing in the menu's.
 * If the player selects one of 2 gamemodes, the gamemode class
 * will be created and the game will be started.
 */
public class GameMenu {


    private GraphicsContext grCtx ;
    private Input input ;


    private int menutype;                   //in what menu we are located

    private int themesType = 1;             //what themescreen

    private boolean rendered = false;       //to render screens 1 time (go against flickering)


    private boolean menu = true;            //while(menu) for menu //could use while(true) ig

    private boolean gameOver = false;       //to get out of game into gameover screen

    private int treadSleep = 15 ; //how long the fixel delay is (tread.sleep)

    private int playerScore ;           //score that player got



    public GameMenu(GraphicsContext grCtx){

        this.grCtx = grCtx;
        grCtx.setGameDimensions(grCtx.getScreenWidth() / 3, grCtx.getScreenHeight() / 3);
        input = new Input(grCtx);

    }




    /**
     * Method MenuGame
     * Here the menu gets drawn, also the passing between selective screens is managed and drawn.
     * The player can choose between play, scoreboard and themes.
     * these get called as their own method.
     * to switch between options the left and right arrow keys are used.
     * to select an option enter is used
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
                        if (menutype == 1) {        //enter while on play -> playchoose
                            GameChoose();
                            rendered = false ;
                        } else if (menutype == 2) {//enter while on score -> to scoreboard
                            ScoreBoard();
                            rendered = false ;
                        } else if (menutype == 3) { //enter while on themes
                            Themes();
                            rendered = false ;
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
     * Method GameChoose
     * Gets called from MenuGame.
     * This method handels the choosing menu between gamemode 1 & 2.
     * when one gets chosen it makes an object of the gamemode and inits the game.
     * returns back to MenuGame with (gameover = true) if game is done.
     *
     * @throws InterruptedException
     * @throws IOException
     */
    public void GameChoose() throws InterruptedException, IOException {


        grCtx.getG2d().drawImage(grCtx.play1, 0, 0, null);    //draws background for gameover
        grCtx.render();

        boolean playType1 = true ;

        while (true) {


            if (input.inputAvailable()) {
                Input.Inputs KeyInput = input.getInput();

                if (KeyInput == Input.Inputs.LEFT || KeyInput == Input.Inputs.RIGHT) {

                    playType1 = !playType1;
                    rendered = false;
                }
                else if (KeyInput == Input.Inputs.ENTER){
                    if (playType1){

                        //start gamemode 1
                        GameMode1 gameMode1 = new GameMode1(grCtx , input) ;
                        playerScore =  gameMode1.RunGame();
                        if (playerScore != 0){
                            gameOver =true ;
                        }

                        return;
                    }
                    else{
                        //start gamemode 2
                        GameMode2 gameMode2 = new GameMode2(grCtx , input) ;
                        playerScore =  gameMode2.RunGame();
                        if (playerScore != 0){
                            gameOver =true ;
                        }
                        return;
                    }

                }else if (KeyInput == Input.Inputs.SPACE){
                    return;
                }
            }
            if(rendered == false){
                if (playType1){
                    grCtx.getG2d().drawImage(grCtx.play1, 0, 0, null);    //draws background for gameover
                }else{
                    grCtx.getG2d().drawImage(grCtx.play2, 0, 0, null);    //draws background for gameover
                }
                grCtx.render();
                rendered = true;

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
        grCtx.getG2d().drawString("Your Score  : " + String.valueOf(playerScore), grCtx.GameOverBack.getWidth() / 2 - 50, 100); //draws score
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
                grCtx.getG2d().drawString("Your Score  : " + String.valueOf(playerScore), grCtx.getScreenWidth() / 2 - 50, 100); //draws score
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
            printWriter.append(String.valueOf(playerScore));
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


}
