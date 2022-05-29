package server;

import server.socket.Server;

public class MainApplication {

    public static void main(String[] args) {

        Server.startServer(12345);

    }
}
