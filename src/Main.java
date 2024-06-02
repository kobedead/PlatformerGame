import be.uantwerpen.fti.ei.Platformer.Game;
import Dependencies.GraphicsContext;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        GraphicsContext gr = new GraphicsContext() ; // here everything gets drawn in ig

        Game game = new Game(gr);
         game.MenuGame();

    }
}
