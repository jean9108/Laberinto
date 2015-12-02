package arsw.laberinto;

import arsw.laberinto.gui.*;
import static arsw.laberinto.gui.Game.*;
import java.awt.*;
import java.util.logging.*;
import javax.swing.*;
import org.springframework.context.*;
import org.springframework.context.support.*;

/**
 *
 * @author pegasusmax
 */
public class Client {

    public static void main(String args[]) {
        setLookAndFeel("Nimbus");
        ApplicationContext ac = new ClassPathXmlApplicationContext("clientContext.xml");
        Game game = ac.getBean(Game.class);
        game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        ClientFrame frame = new ClientFrame(game.TITLE, game);
        boolean named = false;
        String playerName = "";
        while (!named) {
            playerName = JOptionPane.showInputDialog("Ingrese su nombre.");
            if (playerName == null) {
                frame.exit();
            } else if (playerName.equals("")) {
                JOptionPane.showMessageDialog(frame, "No ha ingresado un nombre.", "No ha ingresado nada", JOptionPane.ERROR_MESSAGE);
            } else {
                named = true;
            }
        }
        if (named) {
            try {
                game.assignName(playerName);
                frame.setIdPlayer(game.getPlayer());
                frame.setTitle(game.TITLE + " - Player: " + frame.getIdPlayer());
                game.start();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), ex.getMessage(), JOptionPane.ERROR_MESSAGE);
                frame.exit();
            }
        }
    }

    private static void setLookAndFeel(String lookAndFeel) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (lookAndFeel.equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
