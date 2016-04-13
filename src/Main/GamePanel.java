package Main;

import GameState.GameStateController;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

/**
 * Created by ak101 on 4/13/2016.
 */
public class GamePanel extends JPanel implements Runnable, KeyListener {

    //Window dimensions
    public static final int WIDTH = 600;
    public static final int HEIGHT = 800;


    private Thread thread;
    private boolean running;
    private int FPS = 60;
    private long targetTime = 1000 / FPS;

    private BufferedImage image;
    private Graphics2D g;
    private GameStateController gsc;

    private GameStateController gc;

    public GamePanel(){
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
    }

    public void addNotify(){
        super.addNotify();
        if (thread == null){
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
        }
    }

    public void init() {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) g;
        running = true;
        gsc = new GameStateController();
    }

    public void run(){
        init();

        long start;
        long elapsed;
        long wait;

        //game loop
        while (running){

            start = System.nanoTime();

            update();
            draw();
            drawToScreen();

            elapsed = System.nanoTime() - start;
            wait = targetTime - elapsed / 1000000;

            try {
                Thread.sleep(wait);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
            gsc.update();

    }

    private void draw() {
        gsc.draw(g);
    }

    private void drawToScreen() {
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
    }

    public void keyTyped(KeyEvent key) {}

    public void keyPressed(KeyEvent key) {
        gsc.keyPressed(key.getKeyCode());
        }

    public void keyReleased(KeyEvent key) {
        gsc.keyReleased(key.getKeyCode());
    }

}
