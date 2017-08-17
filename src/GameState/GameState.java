package GameState;

/**
 * Created by Валерия Шахмартова on 18.06.2017.
 */
public abstract class GameState {
    protected GameStateManager gsm;
    public abstract void init();
    public abstract void update();
    public abstract void draw(java.awt.Graphics2D g);
    public abstract void keyPressed(int k);
    public abstract void keyReleased(int k);
}
