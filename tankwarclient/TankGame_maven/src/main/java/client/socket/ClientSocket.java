package client.socket;

import client.util.JsonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientSocket {

    public ClientSocket(String host, int port) {
        try {
            socket = new Socket(host, port);
            socketWriter = new PrintWriter(socket.getOutputStream(), true);
            socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> void sendMessage(String methodType, T data) {
        String request = methodType + JsonUtil.toJson(data);
        socketWriter.println(request);
        socketWriter.flush();
        try {
            response = socketReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String response() {
        return response;
    }

    Socket socket;
    PrintWriter socketWriter;
    BufferedReader socketReader;
    String response;
}
