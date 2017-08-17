package Main;

/**
 * Created by Валерия Шахмартова on 18.06.2017.
 */
import javax.swing.JFrame;
import java.awt.*;

public class Game {
    public static void main(String[] args){
        JFrame window=new JFrame("Dragon Tale");
        window.setContentPane(new GamePanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);

    }

}
