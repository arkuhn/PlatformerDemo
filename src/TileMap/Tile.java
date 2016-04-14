package TileMap;

import java.awt.image.BufferedImage;

/**
 * Created by ak101 on 4/13/2016.
 */
public class Tile {
    private BufferedImage image;
    private int type;

    //tile types
    public static final int NORMAL = 0;
    public static final int BLOCKED = 0;

    public Tile(BufferedImage image, int type){
        this.image = image;
        this.type = type;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getType() {
        return type;
    }
}
