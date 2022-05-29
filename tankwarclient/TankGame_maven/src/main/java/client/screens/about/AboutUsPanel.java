package client.screens.about;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AboutUsPanel extends JPanel {
    JPanel parentPanel;

    JLabel heading = new JLabel("ABOUT US");
    JLabel content = new JLabel("<html>Welcome to Tankwars! Developed by engineers in Istanbul University Cerrahpasa within Network Programming Course <br/>We are a team of 8 passionate researchers and developers. We love creating games together.<br/> Arrow keys for movement space for shoot <br/>Enver Usta<br/>Ömer Erten<br/>Berke Yavaş<br/>Mehmet Yazıcı<br/>Ahmet Hüzeyfe Demir<br/>Edanur Var<br/>Burak Ekinci<br/>Alihan Ataş</html>");
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
