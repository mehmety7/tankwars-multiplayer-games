package test;

import server.model.entity.Player;
import server.utilization.JsonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class TestClient {

    public static void main(String[] args)
    {

        try (Socket socket = new Socket("localhost", 12345)) {

            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Player player = Player.builder().username("player1").password("test").build();
            String data = JsonUtil.toJson(player);
            String request = "LG" + data;
            System.out.println(request);

            while (!"exit".equalsIgnoreCase(request)) {

                out.println(request);
                out.flush();

                System.out.println("Server replied " + in.readLine());
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
