package Dependencies;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class Input {
    public enum Inputs {LEFT, RIGHT, UP, DOWN, SPACE, NONE , ENTER , EEN , TWEE , DRIE , VIER , VIJF , ZES , ZEVEN , ACHT , NEGEN , NUL , ESC };
    private LinkedList<Inputs> keyInputs;



    public Input(GraphicsContext gr) {
        gr.getFrame().addKeyListener(new KeyInputAdapter());
        keyInputs = new LinkedList<Inputs>();
    }
    public boolean inputAvailable() {

        //had some issues with this one, i realised that the tread.sleep was nessesary gor
        //this funtion to work properly

        //could also use a System.out.println to slow down this process and i would work.


        //return keyInputs.size() > 0;
        return !keyInputs.isEmpty() ;
    }
    public Inputs getInput() {
        return keyInputs.poll();
    }



    class KeyInputAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            //System.out.println(keyInputs);
            int keycode = e.getKeyCode();
            switch (keycode) {
                case KeyEvent.VK_LEFT : keyInputs.add(Inputs.LEFT);  break;
                case KeyEvent.VK_RIGHT: keyInputs.add(Inputs.RIGHT); break;
                case KeyEvent.VK_DOWN : keyInputs.add(Inputs.DOWN);  break;
                case KeyEvent.VK_UP   : keyInputs.add(Inputs.UP);    break;
                case KeyEvent.VK_SPACE: keyInputs.add(Inputs.SPACE); break;
                case KeyEvent.VK_ENTER: keyInputs.add(Inputs.ENTER); break;
                case KeyEvent.VK_1: keyInputs.add(Inputs.EEN); break;
                case KeyEvent.VK_2: keyInputs.add(Inputs.TWEE); break;
                case KeyEvent.VK_3: keyInputs.add(Inputs.DRIE); break;
                case KeyEvent.VK_4: keyInputs.add(Inputs.VIER); break;
                case KeyEvent.VK_5: keyInputs.add(Inputs.VIJF); break;
                case KeyEvent.VK_6: keyInputs.add(Inputs.ZES); break;
                case KeyEvent.VK_7: keyInputs.add(Inputs.ZEVEN); break;
                case KeyEvent.VK_8: keyInputs.add(Inputs.ACHT); break;
                case KeyEvent.VK_9: keyInputs.add(Inputs.NEGEN); break;
                case KeyEvent.VK_0: keyInputs.add(Inputs.NUL); break;
                case KeyEvent.VK_ESCAPE: keyInputs.add(Inputs.ESC); break;


            }
        }
    }
}
