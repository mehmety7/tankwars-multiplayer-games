package client.services;

import client.rabbitmq.RPCClient;

public class SingletonSocketService {
    private static SingletonSocketService socket_instance = null;

    public RPCClient rpcClient;

    private SingletonSocketService() {
        this.rpcClient = new RPCClient();
    }

    public static SingletonSocketService getInstance() {
        if (socket_instance == null)
            socket_instance = new SingletonSocketService();

        return socket_instance;
    }
}