package server.socket;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Getter
@Setter
public final class Server {

    private ClientHandler clientSocket;

    public static void startServer(final Integer port) {

        ServerSocket serverSocket = null;

        try {
            System.out.println("Server started!");
            serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(Boolean.TRUE);

            while (true) {
                Socket client = serverSocket.accept();
                ClientHandler clientSock = new ClientHandler(client);

                System.out.println("New client connected: " + client.getLocalSocketAddress());

                new Thread(clientSock).start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
