package client.screens.leadership;


import client.model.dto.Game;
import client.model.dto.Statistic;
import client.services.SingletonSocketService;
import client.socket.ClientSocket;
import client.util.JsonUtil;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class LeadershipPanel extends JPanel {
    JPanel parentPanel;
    JLabel heading = new JLabel("Leaderships");
    JButton backButton = new JButton("Back to lobby");
    JTable scoreTable = new JTable();
    DefaultTableModel model = new DefaultTableModel();
    JScrollPane scrollPane;
    String[] columnNames = {"Player", "Total Score"};

    List<Statistic> listOfStatistics;

    public LeadershipPanel(JPanel parentPanel) {
        this.parentPanel = parentPanel;

        ClientSocket cs = SingletonSocketService.getInstance().clientSocket;
        cs.sendMessage("GL", null);
        System.out.println(cs.response());

        if (cs.response().contains("OK")) {
            listOfStatistics = new ArrayList<Statistic>();
            listOfStatistics = JsonUtil.fromListJson(cs.response().substring(2), Statistic[].class);
            for (int i = 0; i < listOfStatistics.size(); i++) {
                model.addRow(new Object[] { listOfStatistics.get(i).getPlayerUserName(),
                        listOfStatistics.get(i).getScore() });
            }
        } else {
            System.out.println("Failed getting statistics");
        }


        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        model.setColumnIdentifiers(columnNames);
        scoreTable.setModel(model);
        scrollPane = new JScrollPane(scoreTable);


        scoreTable.setBounds(30, 40, 20, 30);
        scoreTable.setEnabled(false);
        scoreTable.getTableHeader().setReorderingAllowed(false);


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
