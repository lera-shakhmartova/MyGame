package TileMap;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Валерия Шахмартова on 18.06.2017.
 */
public class BackGround {
    private BufferedImage image;
    private double x;
    private double y;
    private double dx;
    private double dy;

    private double moveScale;

    public BackGround(String s,double ms) {
        try {
            image= ImageIO.read(getClass().getResourceAsStream(s));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPosition(double x, double y){
        this.x=(x*moveScale)%GamePanel.WIDTH;
        this.y=(y*moveScale)%GamePanel.HEIGHT;
    }

    public void setVector(double dx, double dy){
        this.dx=dx;
        this.dy=dy;
    }

    public void update(){
        x+=dx;
        y+=dy;
    }

    public void draw(Graphics2D g){
        g.drawImage(image,(int)x,(int)y,null);
        if(x<0){
            g.drawImage(image,(int)x+ GamePanel.WIDTH,(int)y,null);
        }
        if(x>0){
            g.drawImage(image,(int)x- GamePanel.WIDTH,(int)y,null);
        }
    }
}
