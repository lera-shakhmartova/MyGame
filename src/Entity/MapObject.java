package Entity;

import Main.GamePanel;
import TileMap.*;

import java.awt.*;

/**
 * Created by Валерия Шахмартова on 18.06.2017.
 */
public abstract class MapObject {

    protected TileMap tileMap;
    protected int tileSize;
    protected double xmap;
    protected double ymap;

    protected double x;
    protected double y;
    protected double dx;
    protected double dy;

    protected int width;
    protected int height;

    protected int cwidth;
    protected int cheight;

    protected int currentRow;
    protected int currentCol;
    protected double xdest;
    protected double ydest;
    protected double xtemp;
    protected double ytemp;
    protected boolean topLeft;
    protected boolean topRight;
    protected boolean bottomLeft;
    protected boolean bottomRight;

    //animation
    protected Animation animation;
    protected int currentAction;
    protected int previousAction;
    protected boolean facingRight;

    //movement
    protected boolean left;
    protected boolean right;
    protected boolean up;
    protected boolean down;
    protected boolean jumping;
    protected boolean falling;

    //movement attributes
    protected double moveSpeed;
    protected double maxSpeed;
    protected double stopSpeed;
    protected double fallSpeed;
    protected double maxFallSpeed;
    protected double jumpStart;
    protected double stopJumpSpeed;

    //constructor
    public MapObject(TileMap tm) {
        tileMap=tm;
        tileSize=tm.getTileSize();

    }

    public boolean intersects(MapObject o) {
        Rectangle r1= getRectangle();
        Rectangle r2=o.getRectangle();
        return r1.intersects(r2);
    }

    public Rectangle getRectangle(){
        return new Rectangle((int)x-cwidth,(int)y-cheight,cwidth,cheight);
    }

    public void calculateCorners(double x, double y) {
        int leftTile = (int)(x - cwidth / 2) / tileSize;
        int rightTile = (int)(x + cwidth / 2 - 1) / tileSize;
        int topTile = (int)(y - cheight / 2) / tileSize;
        int bottomTile = (int)(y + cheight / 2 - 1) / tileSize;
        if(topTile < 0 || bottomTile >= tileMap.getNumRows() ||
                leftTile < 0 || rightTile >= tileMap.getNumCols()) {
            topLeft = topRight = bottomLeft = bottomRight = false;
            return;
        }
        int tl = tileMap.getType(topTile, leftTile);
        int tr = tileMap.getType(topTile, rightTile);
        int bl = tileMap.getType(bottomTile, leftTile);
        int br = tileMap.getType(bottomTile, rightTile);
        topLeft = tl == Tile.BLOCKED;
        topRight = tr == Tile.BLOCKED;
        bottomLeft = bl == Tile.BLOCKED;
        bottomRight = br == Tile.BLOCKED;
    }

    public void checkTileMapCollision(){
        currentCol =(int)x/tileSize;
        currentRow =(int)y/tileSize;

        xdest=x+dx;
        ydest=y+dy;

        xtemp=x;
        ytemp=y;

        calculateCorners(x,ydest);
        if(dy<0){
            if(topLeft||topRight){
                dy=0;
                ytemp= currentRow *tileSize+cheight/2;
            }
            else {
                ytemp+=dy;
            }
        }
        if(dy>0){
            if(bottomLeft||bottomRight){
                dy=0;
                falling=false;
                ytemp=(currentRow +1)*tileSize-cheight/2;
            }
            else{
                ytemp+=dy;
            }
        }
        calculateCorners(xdest,y);
        if(dx<0){
            if(topLeft||bottomLeft){
                dx=0;
                xtemp= currentCol *tileSize+cwidth/2;
            }
            else {
                xtemp+=dx;
            }
        }
        if(dx>0){
            if(topRight||bottomRight){
                dx=0;
                xtemp=(currentCol +1)*tileSize-cwidth/2;
            }
            else {
                xtemp += dx;
            }
        }
        if(!falling){
            calculateCorners(x,ydest+1);
            if(!bottomLeft&&!bottomRight){
                falling=true;
            }
        }
    }



    public int getX() {
        return (int)x;
    }

    public int getY() {
        return (int)y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getCwidth() {
        return cwidth;
    }

    public int getCheight() {
        return cheight;
    }

    public void setPosition(double x,double y){
        this.x=x;
        this.y=y;
    }

    public void setVector(double dx,double dy){
        this.dx=dx;
        this.dy=dy;
    }
    public void setMapPosition(){
        xmap=tileMap.getX();
        ymap=tileMap.getY();
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public boolean notOnScreen(){
        return x+xmap+width<0||x+xmap-width> GamePanel.WIDTH||y+ymap+height<0||y+ymap-height>GamePanel.HEIGHT;
    }

    public void draw(Graphics2D g){
        if(facingRight){
            g.drawImage(animation.getImage(),(int)(x+xmap-width/2),(int)(y+ymap-height/2),null);
        }
        else{
            g.drawImage(animation.getImage(),(int)(x+xmap-width/2+width),(int)(y+ymap-height/2),-width,height,null);
        }
    }
}
