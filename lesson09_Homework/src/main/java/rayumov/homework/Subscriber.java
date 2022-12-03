package rayumov.homework;

import com.rabbitmq.client.*;

import java.util.Scanner;

public class Subscriber {
    private static final String EXCHANGE_NAME = "BlogExchanger";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String message = scanner.nextLine();
            if (message.startsWith("set_topic")) {
                String routingKey = message.substring("set_topic".length() + 1);
                String queueName = message.substring("set_topic".length() + 1);
                channel.queueDeclare(queueName, false, false, true, null);
                channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
                System.out.println(" [*] You subscribe for: '" + routingKey + "'");

                channel.basicQos(3);

                DeliverCallback deliverCallback = ((consumerTag, delivery) -> {
                    String messageFromBlog = new String(delivery.getBody(), "UTF-8");
                    String keyFromBlog = delivery.getEnvelope().getRoutingKey();
                    System.out.printf("[X] Theme (%s): '%s'", keyFromBlog, messageFromBlog);
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                });

                channel.basicConsume(routingKey, false, deliverCallback, consumerTag -> {
                });
            }

            if (message.startsWith("unset_topic")) {
                String routingKey = message.substring("set_topic".length() + 1);
                String queueName = message.substring("set_topic".length() + 1);
                channel.queueUnbind(queueName, EXCHANGE_NAME, routingKey);
                System.out.printf("You are unsubscribe from '%s' theme");
            }
        }
    }


}
