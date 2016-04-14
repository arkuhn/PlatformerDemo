package TileMap;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by ak101 on 4/13/2016.
 */
public class Background {
    private BufferedImage image;
    private double x;
    private double y;
    private double dx;
    private double dy;

    private double moveScale;

    public Background( String s, double ms){
        try {
            image = ImageIO.read(getClass().getResourceAsStream(s));
            moveScale = ms;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setPosition (double x, double y){
        this.x = x * moveScale % GamePanel.WIDTH;
        this.y = y * moveScale % GamePanel.HEIGHT;
    }

    public void setVector (double dx, double dy){
        this.dx = dx;
        this.dy = dy;
    }

    public void update() {
        x += dx % GamePanel.WIDTH;
        y += dy % GamePanel.HEIGHT;
    }

    public void draw(Graphics2D g){
        g.drawImage(image, (int)x, (int)y, null);

        if (x < 0){
            g.drawImage(image, (int)x + GamePanel.WIDTH, (int)y, null);
        }
        else{
            g.drawImage(image, (int)x - GamePanel.WIDTH, (int) y, null);
        }

    }


}
