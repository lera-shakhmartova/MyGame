package GameState;





import java.awt.*;
import java.awt.event.KeyEvent;
import TileMap.BackGround;
/**
 * Created by Валерия Шахмартова on 17.08.2017.
 */
public class PlayerIsDead extends GameState{

    private BackGround bg;

    private int currentChoice = 0;
    private String[] options = {
            "Try again",
            "Main Menu",
            "Quit"
    };

    private Color titleColor;
    private Font titleFont;

    private Font font;

    public PlayerIsDead(GameStateManager gsm) {

        this.gsm = gsm;

        try {

            bg = new BackGround("/Backgrounds/deathbg.gif", 1);
            bg.setVector(0, 0);

            titleColor = new Color(128, 0, 0);
            titleFont = new Font(
                    "Century Gothic",
                    Font.PLAIN,
                    28);

            font = new Font("Arial", Font.PLAIN, 12);

        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void init() {}

    public void update() {
        bg.update();
    }

    public void draw(Graphics2D g) {

        // draw bg
        bg.draw(g);

        // draw title
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("Game Over!", 60, 70);

        // draw menu options
        g.setFont(font);
        for(int i = 0; i < options.length; i++) {
            if(i == currentChoice) {
                g.setColor(Color.RED);
            }
            else {
                g.setColor(Color.BLACK);
            }
            g.drawString(options[i], 127, 140 + i * 15);
        }

    }

    private void select() {
        if(currentChoice == 0) {
            gsm.setState(GameStateManager.LEVELSTATE);
        }
        if(currentChoice == 1) {
            // help
            gsm.setState(GameStateManager.MENUSTATE);
        }
        if(currentChoice == 2) {
            System.exit(0);
        }
    }

    public void keyPressed(int k) {
        if(k == KeyEvent.VK_ENTER){
            select();
        }
        if(k == KeyEvent.VK_UP) {
            currentChoice--;
            if(currentChoice == -1) {
                currentChoice = options.length - 1;
            }
        }
        if(k == KeyEvent.VK_DOWN) {
            currentChoice++;
            if(currentChoice == options.length) {
                currentChoice = 0;
            }
        }
    }
    public void keyReleased(int k) {}

}
