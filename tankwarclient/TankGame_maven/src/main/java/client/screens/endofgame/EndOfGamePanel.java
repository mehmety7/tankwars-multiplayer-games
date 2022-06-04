package client.screens.endofgame;

import client.model.dto.Game;
import client.rabbitmq.RPCClient;
import client.services.SingletonSocketService;
import client.services.WaitingRoomService;
import client.socket.ClientSocket;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EndOfGamePanel extends JPanel {
    JPanel parentPanel;
    WaitingRoomService waitingRoomService;
    Game currentGame;
    JLabel heading = new JLabel("Game Statistics");
    JLabel winner = new JLabel();
    JTable scoreTable = new JTable();
    String[] columnNames = { "Player", "Score"};
    DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    JScrollPane scrollPane;


    JButton backButton = new JButton("Back to lobby");

    List<Integer> scores;
    List<String> usernames;

    /*
    You need to pass the gameId in order to get the statistics for the desired game when creating this pane.
    There is an example commented out on the Home.java
     */
    public EndOfGamePanel(JPanel parentPanel, Integer gameId) {
        this.waitingRoomService = new WaitingRoomService();

        this.parentPanel = parentPanel;

        RPCClient cs = SingletonSocketService.getInstance().rpcClient;

        this.currentGame = waitingRoomService.getGame(gameId);

        usernames = getUsernames();
        scores = getScores();

//        for (Integer score : scores) {
//            score *= scores.size();
//        }

        for (int i = 0; i < scores.size(); i++) {
            scores.set(i, scores.get(i)*scores.size());
        }




        cs.sendMessage("AS", currentGame);
        System.out.println(cs.response());






//        Map<String, Integer> statMap = IntStream.range(0, Math.min(usernames.size(), scores.size()))
//                .boxed()
//                .collect(Collectors.toMap(usernames::get, scores::get));
//
//        TreeMap<String, Integer> sortedStats = new TreeMap<String, Integer>(statMap);

//        for (Map.Entry<String, Integer> stat :
//                sortedStats.entrySet()) {
//            model.addRow(new Object[] { stat.getKey(), stat.getValue() });
//        }


        bubbleSort(usernames, scores);

        for(int i=0; i<usernames.size();i++){
            model.addRow(new Object[] { usernames.get(i), scores.get(i) });
        }



                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //model.setColumnIdentifiers(columnNames);
        scoreTable.setModel(model);
        scrollPane = new JScrollPane(scoreTable);

        scoreTable.setBounds(30, 40, 20, 30);
        scoreTable.setEnabled(false);
        scoreTable.getTableHeader().setReorderingAllowed(false);


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) parentPanel.getLayout();
                cardLayout.show(parentPanel, "lobbyPanel");
            }
        });

        backButton.setBackground(Color.BLUE);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusable(false);
        backButton.setAlignmentX(JButton.CENTER_ALIGNMENT);

//        winner.setText(sortedStats.firstKey() + " won!!");
        winner.setText(usernames.get(0) + " won!!");

        heading.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        winner.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        this.add(winner);
        this.add(heading);
        this.add(scrollPane);
        this.add(backButton);
    }

    private ArrayList<String> getUsernames() {
        Map<Integer, Integer> players = currentGame.getPlayers();
        List<Integer> playerIds =  players.keySet().stream().toList();
        return new ArrayList<String>(this.waitingRoomService.getUsernames(playerIds));
    }

    private ArrayList<Integer> getScores() {
        Map<Integer, Integer> players = currentGame.getPlayers();
        List<Integer> scores = players.values().stream().toList();
        return new ArrayList<Integer>(scores);
    }

    private void bubbleSort(List<String> usernames, List<Integer> scores) {

        int n = scores.size();
        int temp = 0;
        String temp2 = "";

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (scores.get(j - 1) < scores.get(j)) {
                    //swap elements
                    temp = scores.get(j - 1);
                    scores.set(j - 1, scores.get(j));
                    scores.set(j,temp);

                    temp2 = usernames.get(j - 1);
                    usernames.set(j - 1, usernames.get(j));
                    usernames.set(j,temp2);
                }
            }
        }
    }

}
