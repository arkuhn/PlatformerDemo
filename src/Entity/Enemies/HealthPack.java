package Entity.Enemies;

import Entity.Animation;
import Entity.Enemy;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by ak101 on 4/20/2016.
 */
public class HealthPack extends Enemy{

    private BufferedImage[] sprites;
    private boolean item = true;

    public HealthPack(TileMap tm){
        super(tm);

        width = 30;
        height = 30;
        cwidth = 15;
        cheight = 15;



        try{
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Items/HealthPack.gif"));

            sprites = new BufferedImage[1];
            sprites[0] = spritesheet.getSubimage(0, 0, width, height);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(300);

        right = true;
        facingRight = true;
    }

    public boolean isItem(){
        return item;
    }

    public void update(){

        checkTileMapCollision();
        setPosition(xtemp, ytemp);
        animation.update();

    }

    public void draw(Graphics2D g){
        setMapPosition();
        super.draw(g);
    }
}
