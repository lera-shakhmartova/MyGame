package Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import TileMap.*;

import javax.imageio.ImageIO;

/**
 * Created by Валерия Шахмартова on 17.08.2017.
 */
public class Teleporter extends Enemy {

        private BufferedImage[] sprites;

        public Teleporter(TileMap tm) {
            super(tm);

            moveSpeed = 0;
            maxSpeed = 0;
            fallSpeed = 0;
            maxFallSpeed = 0;

            width = 30;
            height = 30;
            cwidth = 20;
            cheight = 20;

            health = maximumHealth = 100000;
            damage = 0;

            try {

                BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/Teleporter.gif"));

                sprites = new BufferedImage[3];
                for(int i = 0; i < sprites.length; i++){
                    sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
                }

            } catch(Exception e){
                e.printStackTrace();
            }
            animation = new Animation();
            animation.setFrames(sprites);
            animation.setDelay(300);

            right = true;
            facingRight = true;
        }
        private void getNextPosition() {
            if(left) {
                dx -= moveSpeed;
                if(dx < -maxSpeed) {
                    dx = -maxSpeed;
                }
            }
            else if(right) {
                dx += moveSpeed;
                if(dx > maxSpeed) {
                    dx = maxSpeed;
                }
            }
            if(falling) {
                dy += fallSpeed;
            }

        }
        public void update() {
            getNextPosition();
            checkTileMapCollision();
            setPosition(xtemp, ytemp);

            if(flinching) {
                long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
                if(elapsed > 400) {
                    flinching = false;
                }
            }

            if(right && dx == 0) {
                right = false;
                left = true;
                facingRight = false;
            }
            else if(left && dx == 0) {
                left = false;
                right = true;
                facingRight = true;
            }

            animation.update();
        }

        public void draw(Graphics2D g) {

            setMapPosition();

            super.draw(g);
        }

    }
