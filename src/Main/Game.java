package Main;

import javax.swing.JFrame;


/**
 * Created by ak101 on 4/13/2016.
 */
public class Game {

    public static void main(String[] args){
        JFrame window = new JFrame("Testing");
        window.setContentPane(new GamePanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);



    }
}
