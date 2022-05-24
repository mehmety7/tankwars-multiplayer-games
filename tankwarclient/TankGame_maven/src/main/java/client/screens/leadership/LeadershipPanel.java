package client.screens.leadership;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LeadershipPanel extends JPanel {
    JPanel parentPanel;

    JLabel heading = new JLabel("Leaderships");

    JTable scoreTable;

    JButton backButton = new JButton("Back to lobby");

    public LeadershipPanel(JPanel parentPanel) {
        this.parentPanel = parentPanel;

        String[] columnNames = { "Player", "Total Score"};

        String[][] data = {
                { "Enver", "1500",},
                { "Eda", "10"}
        };

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        scoreTable = new JTable(data, columnNames);
        scoreTable.setBounds(30, 40, 20, 30);
        scoreTable.setEnabled(false);
        scoreTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(scoreTable);

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
        backButton.setAlignmentX(JButton.CENTER_ALIGNMENT);

        heading.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        this.add(heading);
        this.add(scrollPane);
        this.add(backButton);

    }
}
