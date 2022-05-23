package client.about;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AboutUsPanel extends JPanel {
    JPanel parentPanel;

    JLabel heading = new JLabel("ABOUT US");
    JLabel content = new JLabel("We are a team of 8 passionate researchers and developers. We love creating games together.");
    JButton backButton = new JButton("Back to lobby");

    public AboutUsPanel(JPanel parentPanel) {
        this.parentPanel = parentPanel;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO perform LogOut
                CardLayout cardLayout = (CardLayout) parentPanel.getLayout();
                cardLayout.show(parentPanel, "lobbyPanel");
            }
        });

        backButton.setBackground(Color.BLUE);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusable(false);


        this.add(heading);
        this.add(Box.createRigidArea(new Dimension(0, 15)));
        this.add(content);
        this.add(Box.createRigidArea(new Dimension(0, 15)));
        this.add(backButton);
    }
}
