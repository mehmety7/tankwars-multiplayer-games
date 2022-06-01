package server;

import server.rabbitmq.RPCServer;

public class MainApplication {

    public static void main(String[] args) throws Exception {

        RPCServer.run();

    }
}
