import client.screens.about.AboutUsPanel;
import client.screens.auth.LoginPanel;
import client.screens.auth.SignupPanel;
import client.screens.endofgame.EndOfGamePanel;
import client.screens.lobby.LobbyPanel;
import client.screens.lobby.NewGamePanel;

import javax.swing.*;
import java.awt.*;

public class Home {
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Tank Wars");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // This JPanel is the base for CardLayout for other JPanels.
        final JPanel basePanel = new JPanel();
        basePanel.setLayout(new CardLayout(20, 20));

        /* Here we be making objects of the Window Series classes
         * so that, each one of them can be added to the JPanel
         * having CardLayout.
         */

        //auth panels
        LoginPanel loginPanel = new LoginPanel(basePanel);
        basePanel.add(loginPanel, "loginPanel");
        SignupPanel signupPanel = new SignupPanel(basePanel);
        basePanel.add(signupPanel, "signupPanel");

        //lobby panels
        LobbyPanel lobbyPanel = new LobbyPanel(basePanel);
        basePanel.add(lobbyPanel,"lobbyPanel");
        NewGamePanel newGamePanel = new NewGamePanel(basePanel);
        basePanel.add(newGamePanel,"newGamePanel");

        //about us panel
        AboutUsPanel aboutUsPanel = new AboutUsPanel(basePanel);
        basePanel.add(aboutUsPanel, "aboutUsPanel");
        /*
        //end of game panel
        EndOfGamePanel endOfGamePanel = new EndOfGamePanel(basePanel, 1);
        basePanel.add(endOfGamePanel, "endOfGamePanel");
        */

        // Adding the basePanel to JFrame.
        frame.add(basePanel, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String... args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
