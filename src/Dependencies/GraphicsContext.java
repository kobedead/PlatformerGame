package Dependencies;

import be.uantwerpen.fti.ei.Platformer.Props.Props;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GraphicsContext {
    private int screenWidth;
    private int screenHeight;


    private int platformHeight ;
    private int platformWidth ;
    private int playerHeight;
    private int playerWidth ;
    private int bulletsize ; //keep it rectangular
    private int trampolinesize ; //also rectangle
    private int jetterSize;         //rect
    private int monster1Height;
    private int monster1Width;


    private JFrame frame;
    private JPanel panel;
    private BufferedImage g2dimage;
    private Graphics2D g2d;



    public BufferedImage backgroundImg;

    public BufferedImage GameOverBack;
    public BufferedImage ScoreInvul ;

    public BufferedImage menuImgPlay;
    public BufferedImage menuImgScore;
    public BufferedImage menuImgTheme;

    public BufferedImage themes1;
    public BufferedImage themes2;
    public BufferedImage themes3;
    public BufferedImage themes4;

    public BufferedImage play1 ;
    public BufferedImage play2 ;


    public BufferedImage playerSprite;
    public BufferedImage playerJumping ;

    public BufferedImage ground ;

    public BufferedImage staticPlatformSprite;
    public BufferedImage crackingPlatformSprite;
    public BufferedImage movingPlatformSprite;
    public BufferedImage trampolineSprite;
    public BufferedImage jetterSprite ;

    public BufferedImage monster1Sprite;


    public BufferedImage bulletSprite;









/*
 gameHeight = grCtx.backgroundImg.getHeight();
         gameWidth = grCtx.backgroundImg.getWidth();
         platformHeight = grCtx.StaticPlatformSprite.getHeight();
         platformWidth = grCtx.StaticPlatformSprite.getWidth();
        playerHeight = grCtx.PlayerSprite.getHeight();
        playerWidth = grCtx.PlayerSprite.getWidth();
*/
    private int size;                   // cel size

    public Graphics2D getG2d() {
        return g2d;
    }
    public JFrame getFrame() {
        return frame;
    }
    public int getSize() {
        return size;
    }

    public BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight){
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_4BYTE_ABGR_PRE);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }


    public GraphicsContext() {
        //read resolution out of config file (previeuw)
        ArrayList<String> lines = new ArrayList();

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("Config.txt"));
            String line = reader.readLine();

            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        screenHeight = Integer.valueOf(lines.get(1)) ;
        screenWidth = screenHeight /16*9;

        frame = new JFrame();
        panel = new JPanel(true) {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                doDrawing(g);
            }
        };
        frame.setFocusable(true);
        frame.add(panel);
        frame.setTitle("2D_platformer");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(screenWidth, screenHeight);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    public void Resize(int GM){

        if (GM == 1){
            frame.resize(screenWidth , screenHeight);
        }
        else
            frame.resize(screenHeight , screenWidth);

    }




    public void render() {                          //repaints all the things drawn before ig
        panel.repaint();
    }

    private void doDrawing(Graphics g) {                    //i guess this paints the background when Graphicscontent is created
        Graphics2D graph2d = (Graphics2D) g;
        Toolkit.getDefaultToolkit().sync();
        graph2d.drawImage(g2dimage, 0, 0, null);   // copy buffered image
        graph2d.dispose();
        if (g2d != null)
            g2d.drawImage(backgroundImg,0, 0, null);
    }

    private void loadImages(int theme ) {                    //this loads the images for the start of the game
        backgroundImg = null;
        try {
            GameOverBack = ImageIO.read(new File("Sources/Menu/GameOver.png"));
            ScoreInvul = ImageIO.read(new File("Sources/Black.png"));

            menuImgPlay = ImageIO.read(new File("Sources/Menu/Play_selected2.0.png"));
            menuImgScore = ImageIO.read(new File("Sources/Menu/Scoreboard_selected2.0.png"));
            menuImgTheme = ImageIO.read(new File("Sources/Menu/Theme_selected2.0.png"));

            themes1 = ImageIO.read(new File("Sources/Menu/Theme/Theme1_select+theme1_2.0.png"));
            themes2 = ImageIO.read(new File("Sources/Menu/Theme/Theme2_select+theme1_2.png"));
            themes3 = ImageIO.read(new File("Sources/Menu/Theme/Theme1_select+theme2_2.0.png"));
            themes4 = ImageIO.read(new File("Sources/Menu/Theme/Theme2_select+theme2_2.0.png"));

            play1 = ImageIO.read(new File("Sources/Menu/Play/Play1.png"));
            play2 = ImageIO.read(new File("Sources/Menu/Play/Play2.png"));

            if (theme == 1 ) {

                backgroundImg = ImageIO.read(new File("Sources/Theme1/space-background.png"));

                playerSprite = ImageIO.read(new File("Sources/Theme1/Cowboygirl/Full.png"));
                playerJumping = ImageIO.read(new File("Sources/Theme1/Cowboygirl/Jump (3).png"));

                staticPlatformSprite = ImageIO.read(new File("Sources/Theme1/Platforms/StaticPlatform.png"));
                crackingPlatformSprite = ImageIO.read(new File("Sources/Theme1/Platforms/CrackingPlatform.png"));
                movingPlatformSprite = ImageIO.read(new File("Sources/Theme1/Platforms/MovingPlatform.png"));

                trampolineSprite = ImageIO.read(new File("Sources/Theme1/Props/Trampline.png"));
                jetterSprite = ImageIO.read(new File("Sources/Theme1/Props/BoostPlat.png"));


                monster1Sprite = ImageIO.read(new File("Sources/Theme1/Enemies/Monster1.png"));

                bulletSprite = ImageIO.read(new File("Sources/Theme1/Bullet.png"));
            }
            else if (theme == 2 ){
                backgroundImg = ImageIO.read(new File("Sources/Theme2/hop-bck.png"));

                playerSprite = ImageIO.read(new File("Sources/Theme2/Player/Robotfull.png"));
                playerJumping =  ImageIO.read(new File("Sources/Theme2/Player/bot.png"));

                staticPlatformSprite = ImageIO.read(new File("Sources/Theme2/Platforms/StaticPlatform.png"));
                crackingPlatformSprite = ImageIO.read(new File("Sources/Theme2/Platforms/Cracking.png"));
                movingPlatformSprite = ImageIO.read(new File("Sources/Theme2/Platforms/MovingPlatform.png"));

                trampolineSprite = ImageIO.read(new File("Sources/Theme2/Props/Trampoline.png"));

                monster1Sprite = ImageIO.read(new File("Sources/Theme2/Monsters/Monster1.png"));

                bulletSprite = ImageIO.read(new File("Sources/Theme2/Bullet.png"));



            }



        } catch (IOException e) {
            System.out.println("Unable to load grahics");
        }
    }



    public void ThemeChange(int theme){
        loadImages(theme);
        try {
            backgroundImg = resizeImage(backgroundImg, screenWidth, screenHeight);//resize background to fit whole screen

            GameOverBack = resizeImage(GameOverBack , screenWidth , screenHeight);
            ScoreInvul = resizeImage(ScoreInvul, screenWidth , screenHeight);

            menuImgTheme = resizeImage(menuImgTheme , screenWidth , screenHeight);
            menuImgScore = resizeImage(menuImgScore , screenWidth , screenHeight);
            menuImgPlay = resizeImage(menuImgPlay, screenWidth , screenHeight);

            themes1 = resizeImage(themes1, screenWidth , screenHeight);
            themes2 = resizeImage(themes2, screenWidth , screenHeight);
            themes3 = resizeImage(themes3, screenWidth, screenHeight);
            themes4 = resizeImage(themes4, screenWidth , screenHeight);

            play1 = resizeImage(play1, screenWidth , screenHeight);
            play2 = resizeImage(play2, screenWidth , screenHeight);



            playerSprite = resizeImage(playerSprite, playerWidth*5, playerHeight*2);         //rezise the player sprite
            playerJumping = resizeImage(playerJumping, size*20, size*20);         //rezise the player sprite




            monster1Sprite = resizeImage(monster1Sprite , monster1Width , monster1Height);

            ground = resizeImage(staticPlatformSprite , screenWidth , playerHeight) ;

            staticPlatformSprite = resizeImage(staticPlatformSprite , platformWidth , platformHeight);
            crackingPlatformSprite = resizeImage(crackingPlatformSprite , platformWidth , platformHeight);
            movingPlatformSprite = resizeImage(movingPlatformSprite , platformWidth , platformHeight);

            jetterSprite = resizeImage(jetterSprite , jetterSize ,jetterSize) ;
            trampolineSprite = resizeImage(trampolineSprite , trampolinesize, trampolinesize);
            bulletSprite = resizeImage(bulletSprite , bulletsize , bulletsize);

        } catch(Exception e) {
            System.out.println(e.getStackTrace());
        }


    }





    //called at begin do if a menu is called here is the time
    public void setGameDimensions(int GameCellsX, int GameCellsY) {
        size = Math.min(screenWidth/GameCellsX, screenHeight/GameCellsY); //size = size of 1 zone in game
        System.out.println("size: "+size);

        playerHeight = size*22 ;
        playerWidth = size*13;
        platformHeight = size*10 ;
        platformWidth = size*21 ;
        trampolinesize = size *10 ;
        jetterSize = size * 10 ;
        bulletsize= size *6 ;
        monster1Height = size*30 ;
        monster1Width = size*20 ;

        frame.setLocation(50,50);
        frame.setSize(screenWidth, screenHeight);

        ThemeChange(1);                                       //gets images from files

        g2dimage = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_4BYTE_ABGR_PRE);
        g2d = g2dimage.createGraphics();
        g2d.setFont((new Font("Comic Sans MS", Font.PLAIN, 20)));
       // g2d.drawImage(backgroundImg,0, 0, null);    //draws background if playing                                    //draws background for start
//enables the drawing ig
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }


    public int getPlatformHeight() {
        return platformHeight;
    }

    public void setPlatformHeight(int platformHeight) {
        this.platformHeight = platformHeight;
    }

    public int getPlatformWidth() {
        return platformWidth;
    }

    public void setPlatformWidth(int platformWidth) {
        this.platformWidth = platformWidth;
    }

    public int getPlayerHeight() {
        return playerHeight;
    }

    public void setPlayerHeight(int playerHeight) {
        this.playerHeight = playerHeight;
    }

    public int getPlayerWidth() {
        return playerWidth;
    }

    public void setPlayerWidth(int playerWidth) {
        this.playerWidth = playerWidth;
    }

    public int getBulletsize() {
        return bulletsize;
    }

    public void setBulletsize(int bulletsize) {
        this.bulletsize = bulletsize;
    }

    public int getTrampolinesize() {
        return trampolinesize;
    }

    public void setTrampolinesize(int trampolinesize) {
        this.trampolinesize = trampolinesize;
    }

    public int getJetterSize() {
        return jetterSize;
    }

    public void setJetterSize(int jetterSize) {
        this.jetterSize = jetterSize;
    }

    public int getMonster1Height() {
        return monster1Height;
    }

    public void setMonster1Height(int monsterHeight) {
        this.monster1Height = monsterHeight;
    }

    public int getMonster1Width() {
        return monster1Width;
    }

    public void setMonster1Width(int monsterWidth) {
        this.monster1Width = monsterWidth;
    }
}

