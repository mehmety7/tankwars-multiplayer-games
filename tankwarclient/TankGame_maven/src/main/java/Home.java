import client.about.AboutUsPanel;
import client.auth.LoginPanel;
import client.auth.SignupPanel;
import client.lobby.LobbyPanel;
import client.lobby.NewGamePanel;

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
