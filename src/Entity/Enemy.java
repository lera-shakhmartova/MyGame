package Entity;

import TileMap.TileMap;

/**
 * Created by Валерия Шахмартова on 19.06.2017.
 */
public class Enemy extends MapObject {

    protected int health;
    protected int maximumHealth;
    protected boolean isDead;
    protected int damage;

    protected boolean flinching;
    protected long flinchTimer;

    public Enemy(TileMap tm) {
        super(tm);
    }

    public boolean isDead(){return isDead;}

    public int getDamage() {
        return damage;
    }

    public void hit(int damage){
        if(isDead ||flinching) return;
        health-=damage;
        if(health<0) health=0;
        if(health==0) isDead =true;
        flinching=true;
        flinchTimer=System.nanoTime();
    }

    public void update(){

    }
}
