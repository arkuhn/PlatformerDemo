package GameState;

import Main.Game;
import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by ak101 on 4/13/2016.
 */
public class MenuState extends GameState{

    private Background bg;
    private int currentChoice = 0;
    private String[] options = {
            "Sstart",
            "Help",
            "Quit"
    };
    private Color titleColor;
    private Font titleFont;
    private Font font;

    public MenuState(GameStateController gsc){
        this.gsc = gsc;

        try{
            bg = new Background("/Backgrounds/menubg.gif", 1);
            bg.setVector(-.1, 0);
            titleColor = new Color(41, 41, 128);
            titleFont = new Font("Century Gothic", Font.PLAIN, 28);
            font = new Font("Arial", Font.PLAIN, 12);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


public void init(){}
public void update(){
    bg.update();
}
public void draw(Graphics2D g) {
    bg.draw(g);
    g.setColor((titleColor));
    g.setFont((titleFont));
    g.drawString("Testing", 80, 70);

    g.setFont(font);

    for(int i = 0; i < options.length; i++){
        if(i == currentChoice){
            g.setColor(Color.LIGHT_GRAY);
        }
        else{
            g.setColor(Color.CYAN);
        }
        g.drawString(options[i], 145, 140 + i * 15);
    }
}

private void select(){
    if (currentChoice == 0){
        //start
    }
    if (currentChoice == 1){
        //help
    }
    if (currentChoice == 2){
        System.exit(0);
    }
}

public void keyPressed(int k) {
    if (k == KeyEvent.VK_ENTER){
        select();
    }
    if (k == KeyEvent.VK_UP){
        currentChoice--;
        if (currentChoice == -1){
            currentChoice = options.length-1;
        }
    }
    if (k == KeyEvent.VK_DOWN){
        currentChoice++;
        if (currentChoice == options.length){
            currentChoice = 0;
        }
    }

}
public void keyReleased(int k) {}

}