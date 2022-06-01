package server.rabbitmq;

import com.rabbitmq.client.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import server.bean.BeanHandler;
import server.service.navigator.ServiceOperationNavigator;
import server.socket.Protocol;

@Getter
@Setter
@RequiredArgsConstructor
public class RPCServer {

    private static final String RPC_QUEUE_NAME = "rpc_queue";

    public static void run() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        ServiceOperationNavigator serviceOperationNavigator = BeanHandler.serviceOperationNavigator;

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
            channel.queuePurge(RPC_QUEUE_NAME);

            channel.basicQos(1);

            System.out.println(" [x] Awaiting RPC requests");

            Object monitor = new Object();
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                        .Builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();

                String response = "";

                try {
                    String message = new String(delivery.getBody(), "UTF-8");
                    System.out.println(" [.] " + message);
                    Protocol protocol = Protocol.builder().methodType(message.substring(0,2)).message(message.substring(2)).build();
                    response += serviceOperationNavigator.doOperation(protocol);
                } catch (RuntimeException e) {
                    System.out.println(" [.] " + e.toString());
                } finally {
                    channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps, response.getBytes("UTF-8"));
                    System.out.println(" [x] " + response);
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                    // RabbitMq consumer worker thread notifies the RPC server owner thread
                    synchronized (monitor) {
                        monitor.notify();
                    }
                }
            };

            channel.basicConsume(RPC_QUEUE_NAME, false, deliverCallback, (consumerTag -> { }));
            // Wait and be prepared to consume the message from RPC client.
            while (true) {
                synchronized (monitor) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
