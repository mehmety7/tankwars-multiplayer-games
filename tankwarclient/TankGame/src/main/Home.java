package main;

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

//        Page1 win1 = new Page1(contentPane);
//        contentPane.add(win1, "Page1");
//        Page2 win2 = new Page2();
//        contentPane.add(win2, "Page2");
//        Page3 win3 = new Page3();
//        contentPane.add(win3, "Page3");

        /* We need two JButtons to go to the next Card
         * or come back to the previous Card, as and when
         * desired by the User.
         */
//        JPanel buttonPanel = new JPanel();
//        final JButton previousButton = new JButton("PREVIOUS");
//        previousButton.setBackground(Color.BLACK);
//        previousButton.setForeground(Color.WHITE);
//        final JButton nextButton = new JButton("NEXT");
//        nextButton.setBackground(Color.RED);
//        nextButton.setForeground(Color.WHITE);
//        buttonPanel.add(previousButton);
//        buttonPanel.add(nextButton);

        /* Adding the ActionListeners to the JButton,
         * so that the user can see the next Card or
         * come back to the previous Card, as desired.
         */

//        previousButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent ae) {
//                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
//                cardLayout.previous(contentPane);
//            }
//        });
//        nextButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent ae) {
//                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
//                cardLayout.next(contentPane);
//            }
//        });

        // Adding the contentPane (JPanel) and buttonPanel to JFrame.
        frame.add(basePanel, BorderLayout.CENTER);
//        frame.add(buttonPanel, BorderLayout.PAGE_END);

        frame.pack();
//        frame.setSize(600, 800);
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
