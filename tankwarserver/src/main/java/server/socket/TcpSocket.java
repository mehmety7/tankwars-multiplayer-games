package server.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class TcpSocket {
    private Socket socket;
    private ServerSocket serverSocket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private final Protocol protocol;


    public TcpSocket(int port) {
        protocol = new Protocol();
        try {
            serverSocket = new ServerSocket(port);
            socket = serverSocket.accept();
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMessage() {
        byte[] init = new byte[2];
        try {
            int readByteCount = inputStream.read(init);
            if(readByteCount != 2){
                throw new IOException("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

            protocol.setMessageSize(ByteBuffer.wrap(init).order(ByteOrder.BIG_ENDIAN).getShort());

        try {
            int readByteCount = inputStream.read(init);
            if(readByteCount != 2){
                throw new IOException("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        protocol.setMethodType(new String(init, StandardCharsets.UTF_8));

        int protocolMessageSize = protocol.getMessageSize();
        if(protocolMessageSize != 0) {
            try {
                init = new byte[protocolMessageSize];
                int readByteCount = inputStream.read(init);
                if(readByteCount != protocolMessageSize){
                    throw new IOException("");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        protocol.setMessage(new String(init, StandardCharsets.UTF_8));
    }

    public void sendMessage(String methodType, String msg) {
        short msgLength = (short) msg.length();
        methodType += msg;
        byte[] bytes = new byte[4 + msgLength];
        bytes[0] = (byte) (msgLength >> 8);
        bytes[1] = (byte) msgLength;
        bytes[2] = (byte) methodType.charAt(0);
        bytes[3] = (byte) methodType.charAt(1);
        for (int i = 0; i < msgLength; i++) bytes[i+4] = (byte) msg.charAt(i);
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendResponse(boolean success) {
        byte[] bytes;
        if (success) {
            bytes = okResponse();
        } else {
            bytes = failResponse();
        }
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] okResponse() {
        byte[] bytes = new byte[4];
        bytes[0] = 0;
        bytes[1] = 0;
        bytes[2] = 'O';
        bytes[3] = 'K';
        return bytes;
    }

    private byte[] failResponse() {
        byte[] bytes = new byte[4];
        bytes[0] = 0;
        bytes[1] = 0;
        bytes[2] = 'F';
        bytes[3] = 'L';
        return bytes;
    }
}
