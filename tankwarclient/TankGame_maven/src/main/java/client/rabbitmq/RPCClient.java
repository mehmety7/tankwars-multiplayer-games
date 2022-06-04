package client.rabbitmq;

import client.util.JsonUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

@Getter
@Setter
public class RPCClient implements AutoCloseable {

    private Connection connection;
    private Channel channel;
    private String requestQueueName = "rpc_queue";
    private String response;

    public RPCClient() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public <T> void sendMessage(String methodType, T data) {
        final String corrId = UUID.randomUUID().toString();

        String message = data != null ? methodType + JsonUtil.toJson(data) : methodType ;

        String replyQueueName = null;
        String result = null;
        try {
            replyQueueName = channel.queueDeclare().getQueue();
            AMQP.BasicProperties props = new AMQP.BasicProperties
                    .Builder()
                    .correlationId(corrId)
                    .replyTo(replyQueueName)
                    .build();

            channel.basicPublish("", requestQueueName, props, message.getBytes("UTF-8"));

            final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

            String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
                if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                    response.offer(new String(delivery.getBody(), "UTF-8"));
                }
            }, consumerTag -> {
            });

            result = response.take();
            channel.basicCancel(ctag);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.response = result;
    }

    public String response() {
        return this.response;
    }

    public void close() throws IOException {
        connection.close();
    }
}
