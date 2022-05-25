package server.socket;

import lombok.Getter;
import lombok.Setter;
import server.bean.BeanHandler;
import server.service.navigator.ServiceOperationNavigator;

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
    private ServiceOperationNavigator serviceOperationNavigator;

    public ClientHandler(Socket clientSocket) {
        this.protocolFromClient = new Protocol();
        this.protocolToClient = new Protocol();
        this.clientSocket = clientSocket;
        this.serviceOperationNavigator = BeanHandler.serviceOperationNavigator;
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

                String response = serviceOperationNavigator.doOperation(protocolFromClient);

                sendResponse(response);
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
        String data;
        try {
            data = inputStream.readLine();
            if (Objects.nonNull((data))) {
                protocolFromClient.setMethodType(data.substring(0, 2));
                protocolFromClient.setMessage(data.substring(2));

                System.out.println(" Sent FROM the client DATA:" + clientSocket.getLocalSocketAddress() + " -> " + protocolFromClient.getMethodType() + protocolFromClient.getMessage());
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
    public void sendResponse(String response) {
        System.out.println(" Sent TO the client RESPONSE:" + clientSocket.getLocalSocketAddress() + " -> " + response);
        outputStream.println(response);
    }
}