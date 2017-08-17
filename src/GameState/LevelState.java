package GameState;
import Entity.*;
import Entity.Entity.Enemies.Slugger;
import Main.GamePanel;
import TileMap.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by Валерия Шахмартова on 18.06.2017.
 */
public class LevelState extends GameState {

    private TileMap tileMap;
    private BackGround bg;

    private Player player;

    private ArrayList<Enemy> enemies;
    private ArrayList<Explosion> explosions;

    private HUD hud;

    private Teleporter t;

    public LevelState(GameStateManager gsm) {
        this.gsm=gsm;
        init();
    }

    @Override
    public void init() {
        tileMap=new TileMap(30);
        tileMap.loadTiles("/Tilesets/grasstileset.gif");
        tileMap.loadMap("/Maps/level1-1.map");
        tileMap.setPosition(0,0);
        tileMap.setTween(1);

        bg=new BackGround("/Backgrounds/grassbg1.gif",0.1);

        player=new Player(tileMap);
        player.setPosition(100,100);

        populateEnemies();

        explosions=new ArrayList<Explosion>();

        hud=new HUD(player);
    }

    private void populateEnemies() {
        enemies = new ArrayList<Enemy>();

        Slugger s;
        Point[] points = new Point[] {
                new Point(200, 100),
                new Point(860, 200),
                new Point(1525, 200),
                new Point(1680, 200),
                new Point(1800, 200)

        };
        for(int i = 0; i < points.length; i++) {
            s = new Slugger(tileMap);
            s.setPosition(points[i].x, points[i].y);
            enemies.add(s);
        }
        Point[] pointsT = new Point[] {
                new Point(3050, 200)
        };
        for(int i = 0; i < pointsT.length; i++) {
            t = new Teleporter(tileMap);
            t.setPosition(pointsT[i].x, pointsT[i].y);
            enemies.add(t);
        }
    }

    @Override
    public void update() {

        player.update();
        tileMap.setPosition(GamePanel.WIDTH/2-player.getX(),GamePanel.HEIGHT/2-player.getY());

        bg.setPosition(tileMap.getX(),tileMap.getY());

        player.checkAttack(enemies);

        for(int i=0;i<enemies.size();i++){
            Enemy e=enemies.get(i);
            e.update();
            if(e.isDead()){
                enemies.remove(i);
                i--;
                explosions.add(new Explosion(e.getX(),e.getY()));
            }
        }

        for(int i=0;i<explosions.size();i++){
            explosions.get(i).update();
            if(explosions.get(i).shouldRemove()){
                explosions.remove(i);
                i--;
            }
        }

        if(player.getY() > 540) {
            player.hit(player.getHealth() + 1);
        }

        if(player.isDead()) {
            gsm.setState(GameStateManager.PLAYERISDEAD);
        }

        if(player.intersects(t)){
            gsm.setState(GameStateManager.PLAYERWON);
        }
    }

    @Override
    public void draw(Graphics2D g) {

        bg.draw(g);

        tileMap.draw(g);

        player.draw(g);

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g);
        }
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).setMapPosition((int)tileMap.getX(), (int)tileMap.getY());
            explosions.get(i).draw(g);
        }

        hud.draw(g);
    }

    @Override
    public void keyPressed(int k) {
        if(k== KeyEvent.VK_LEFT) player.setLeft(true);
        if(k==KeyEvent.VK_RIGHT) player.setRight(true);
        if(k== KeyEvent.VK_UP) player.setUp(true);
        if(k==KeyEvent.VK_DOWN) player.setDown(true);
        if(k==KeyEvent.VK_SPACE) player.setJumping(true);
        if(k==KeyEvent.VK_E) player.setGliding(true);
        if(k==KeyEvent.VK_R) player.setScratching();
        if(k==KeyEvent.VK_F) player.setFiring();
    }

    @Override
    public void keyReleased(int k) {
        if(k== KeyEvent.VK_LEFT) player.setLeft(false);
        if(k==KeyEvent.VK_RIGHT) player.setRight(false);
        if(k== KeyEvent.VK_UP) player.setUp(false);
        if(k==KeyEvent.VK_DOWN) player.setDown(false);
        if(k==KeyEvent.VK_SPACE) player.setJumping(false);
        if(k==KeyEvent.VK_E) player.setGliding(false);
    }
}
