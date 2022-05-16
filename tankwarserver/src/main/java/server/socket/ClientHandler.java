package server.socket;

import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

@Getter
@Setter
public class ClientHandler implements Runnable, SocketDataOperation {

    private Socket clientSocket;
    private BufferedReader inputStream = null;
    private PrintWriter outputStream = null;
    private Protocol protocolFromClient;
    private Protocol protocolToClient;

    public ClientHandler(Socket clientSocket) {
        this.protocolFromClient = new Protocol();
        this.protocolToClient = new Protocol();
        this.clientSocket = clientSocket;
        try {
            inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outputStream = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run()
    {
        try {
            while (readMessage()) {

                // burada servis metodlarına yönlendirme yapılacak (Navigation)

                sendResponse();
            }
        } finally {
                try {
                    if (Objects.nonNull(outputStream)) {
                        outputStream.close();
                    }
                    if (Objects.nonNull(inputStream)) {
                        inputStream.close();
                        clientSocket.close();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public Boolean readMessage() {
        System.out.println(" Sent FROM the client DATA:" + clientSocket.getLocalSocketAddress() + " -> " + protocolFromClient.getMethodType() + protocolFromClient.getMessage());
        String data;
        try {
            data = inputStream.readLine();
            System.out.println(data);
            if (Objects.nonNull((data))) {
                protocolFromClient.setMethodType(data.substring(0, 2));
                protocolFromClient.setStatus(data.substring(2, 4));
                protocolFromClient.setMessage(data.substring(4));
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    @Override
    public void sendResponse() {
        System.out.println(" Sent TO the client RESPONSE:" + clientSocket.getLocalSocketAddress() + " -> " + protocolToClient.getMethodType() + protocolToClient.getStatus() + protocolToClient.getMessage());
        protocolToClient.setMethodType(protocolFromClient.getMethodType());
        protocolToClient.setMessage(protocolFromClient.getMessage());
        protocolToClient.setStatus(protocolFromClient.getStatus());
        outputStream.println(protocolToClient.getMethodType() + protocolToClient.getStatus() + protocolToClient.getMessage());
    }
}