package consumer;

import com.rabbitmq.client.ConnectionFactory;

public class SimpleReceiverApp {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");


    }
}
