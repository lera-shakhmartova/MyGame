package GameState;

import java.util.ArrayList;

/**
 * Created by Валерия Шахмартова on 18.06.2017.
 */
public class GameStateManager {
    public GameState[] gameStates;
    private int currentState;
    public static int MENUSTATE = 0;
    public static int LEVELSTATE = 1;
    public static int PLAYERISDEAD = 2;
    public static int PLAYERWON=3;
    public static int NUMGAMESTATES = 4;

    public GameStateManager() {

        gameStates = new GameState[NUMGAMESTATES];

        currentState = MENUSTATE;

        loadState(currentState);

    }

    private void loadState(int state) {
        if (state == MENUSTATE)
            gameStates[state] = new MenuState(this);
        if (state == LEVELSTATE)
            gameStates[state] = new LevelState(this);
        if (state == PLAYERISDEAD)
            gameStates[state] = new PlayerIsDead(this);
        if(state==PLAYERWON)
            gameStates[state]=new PlayerWon(this);

    }

    private void unloadState(int state) {
        gameStates[state] = null;
    }

    public void setState(int state) {
        unloadState(currentState);
        currentState = state;
        loadState(currentState);
    }


    public void update() {
        try {
            gameStates[currentState].update();
        } catch (Exception e) {
        }
    }

    public void draw(java.awt.Graphics2D g) {
        try {
            gameStates[currentState].draw(g);
        } catch (Exception e) {
        }
    }

    public void keyPressed(int k) {
        gameStates[currentState].keyPressed(k);
    }

    public void keyReleased(int k) {
        gameStates[currentState].keyReleased(k);
    }
}
