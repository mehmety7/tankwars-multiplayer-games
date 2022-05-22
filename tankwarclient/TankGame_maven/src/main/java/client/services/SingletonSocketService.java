package client.services;

import client.model.entity.Player;
import client.socket.ClientSocket;

public class SingletonSocketService {
    private static SingletonSocketService socket_instance = null;

    private static final String host = "localhost";

    private static final Integer port = 12345;

    public ClientSocket clientSocket;

    private SingletonSocketService() {
        this.clientSocket = new ClientSocket(host, port);
    }

    public static SingletonSocketService getInstance() {
        if (socket_instance == null)
            socket_instance = new SingletonSocketService();

        return socket_instance;
    }
}