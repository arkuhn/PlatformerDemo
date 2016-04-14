package GameState;

import Main.GamePanel;
import TileMap.TileMap;

import java.awt.*;

/**
 * Created by ak101 on 4/13/2016.
 */
public class Level1State extends GameState {

    private TileMap tileMap;

    public Level1State(GameStateController gsc) {
        this.gsc = gsc;
        init();

    }

    public void init() {
        tileMap = new TileMap(30);
        tileMap.loadTiles("/Tilesets/grasstileset.gif");
        tileMap.loadMap("/Maps/level1-1.map");
        tileMap.setPosition(0,0);
    }


    public void update() {

    }


    public void draw(Graphics2D g) {
        //draw tilemap
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        tileMap.draw(g);

    }


    public void keyPressed(int k) {

    }


    public void keyReleased(int k) {

    }
}
