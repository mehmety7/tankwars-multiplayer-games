package server.dao;

import lombok.Getter;
import server.model.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class PlayerDao {

    private static PlayerDao playerDao;
    private DataPersistence dataPersistence;

    private PlayerDao(){
        dataPersistence = DataPersistence.getDataPersistence();
    }

    public static PlayerDao getInstance(){
        if (Objects.isNull(playerDao)){
            playerDao = new PlayerDao();
        }
        return playerDao;
    }

    public Integer createPlayer(Player player) {
        String sql = "INSERT INTO player (username, password) VALUES (?, ?)";

        PreparedStatement statement = null;
        int rowsInserted = 0;
        try {
            statement = dataPersistence.getConnection().prepareStatement(sql);
            statement.setString(1, player.getUsername());
            statement.setString(2, player.getPassword());

            rowsInserted = statement.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("createPlayer failed playerDao!");
            throwables.printStackTrace();
        }

        if (rowsInserted > 0) {
            System.out.println("A new user was inserted successfully!");
            return getLastInsertId();
        }
        return -1;
    }

    public Player getPlayer(Integer playerId) {
        String sql = "SELECT * FROM player WHERE id=" + playerId;

        Statement statement;
        Player player = null;
        try {
            statement = dataPersistence.getConnection().prepareStatement(sql);
            ResultSet result = statement.executeQuery(sql);

            result.next();
            Integer id = result.getInt("id");
            String username = result.getString("username");
            String password = result.getString("password");
            Boolean isActive = result.getBoolean("isActive");
            player = Player.builder().id(id).username(username).password(password).isActive(isActive).build();
        } catch (SQLException throwables) {
            System.out.println("getPlayerById failed playerDao!");
            throwables.printStackTrace();
        }

        return player;
    }

    public Player getPlayer(String username) {
        String sql = "SELECT * FROM player WHERE username='" + username + "'";

        Statement statement;
        Player player = null;
        try {
            statement = dataPersistence.getConnection().prepareStatement(sql);
            ResultSet result = statement.executeQuery(sql);

            result.next();
            Integer id = result.getInt("id");
            String nickname = result.getString("username");
            String password = result.getString("password");
            Boolean isActive = result.getBoolean("isActive");
            player = Player.builder().id(id).username(nickname).password(password).isActive(isActive).build();
        } catch (SQLException throwables) {
            System.out.println("getPlayerByUserName failed playerDao!");
            throwables.printStackTrace();
        }

        return player;
    }

    public List<Player> getActivePlayers() {
        String sql = "SELECT * FROM player WHERE isActive=TRUE";

        Statement statement;
        List<Player> activePlayers = new ArrayList<>();
        try {
            statement = dataPersistence.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()){
                Integer id = result.getInt("id");
                String username = result.getString("username");
                String password = result.getString("password");
                Boolean isActive = result.getBoolean("isActive");
                activePlayers.add(Player.builder().id(id).username(username).password(password).isActive(isActive).build());
            }
        } catch (SQLException throwables) {
            System.out.println("getActivePlayers failed playerDao!");
            throwables.printStackTrace();
        }

        return activePlayers;
    }

    public Boolean updateActive(Integer id) {
        Player player = getPlayer(id);
        Boolean isActive = ! player.getIsActive();

        String sql = "UPDATE player SET isActive=" + isActive + " WHERE id=" + id;
        Statement statement = null;
        int rowsUpdated = 0;
        try {
            statement = dataPersistence.getConnection().prepareStatement(sql);
            rowsUpdated = statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            System.out.println("updateActive failed playerDao!");
            throwables.printStackTrace();
        }

        if (rowsUpdated > 0) {
            System.out.println("An existing user's activation was updated successfully: " + isActive);
            return true;
        }
        return false;
    }

    public Boolean deletePlayer(Integer id) {
        String sql = "DELETE FROM player WHERE id=" + id;

        int rowsDeleted = 0;
        Statement statement = null;
        try {
            statement = dataPersistence.getConnection().prepareStatement(sql);
            rowsDeleted = statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            System.out.println("deletePlayer failed playerDao!");
            throwables.printStackTrace();
        }

        if (rowsDeleted > 0) {
            System.out.println("A user was deleted successfully!");
            return true;
        }
        return false;
    }

    private Integer getLastInsertId() {
        String sql = "SELECT LAST_INSERT_ID() as last";

        Integer lastId = -1;
        Statement statement = null;
        try {
            statement = dataPersistence.getConnection().prepareStatement(sql);
            ResultSet result = statement.executeQuery(sql);
            result.next();

            lastId = result.getInt("last");
        } catch (SQLException throwables) {
            System.out.println("getLastInsertId failed playerDao!");
            throwables.printStackTrace();
        }

        return lastId;
    }

}
