import client.auth.LoginPanel;
import client.auth.SignupPanel;

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
        LoginPanel loginPanel = new LoginPanel(basePanel);
        basePanel.add(loginPanel, "loginPanel");
        SignupPanel signupPanel = new SignupPanel(basePanel);
        basePanel.add(signupPanel, "signupPanel");

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
