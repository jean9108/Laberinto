package arsw.laberinto;

import java.net.*;
import arsw.laberinto.utils.*;
import java.util.logging.*;
import javax.swing.*;
import org.springframework.beans.*;
import org.springframework.context.*;
import org.springframework.context.support.*;

/**
 *
 * @author pegasusmax
 */
public class Server {

    public static void main(String[] args) {
        try {
            setLookAndFeel("Nimbus");
            String ip = NetUtils.getIPAddress();
            System.setProperty("java.rmi.server.hostname", ip);
            ApplicationContext ac = new ClassPathXmlApplicationContext("serverContext.xml");
            ServerFrame frame = new ServerFrame("Server ready", ip);
        } catch (ConnectException | BeansException ex) {
            System.err.println("Server exception: " + ex.toString());
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
