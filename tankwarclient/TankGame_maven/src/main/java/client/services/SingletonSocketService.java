package client.services;

import client.model.entity.Player;
import client.socket.ClientSocket;

public class SingletonSocketService {
    private static SingletonSocketService socket_instance = null;

    public ClientSocket clientSocket;

    private SingletonSocketService() {
        this.clientSocket = new ClientSocket("localhost", 12345);
    }

    public static SingletonSocketService getInstance() {
        if (socket_instance == null)
            socket_instance = new SingletonSocketService();

        return socket_instance;
    }
}