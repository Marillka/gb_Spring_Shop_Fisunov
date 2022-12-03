package rayumov.homework;

//1. Сделайте два консольных приложения (не Спринг):
//        а. IT-блог, который публикует статьи по языукам программирования.
//        б. Подписчик, которого интересуют статьи по определенным языкам
//
//        Детали а. Если IT-блог в консоли пишет 'php some message', то 'some message' отправляет в RabbitMQ с темой 'php', и это сообщение получают подписчики этой темы.
//
//        Детали b. Подписчик при запуске должен ввести команду 'set_topic php', после чего начать получать сообщения из очереди с соответствующей темой 'php'.
//
//        2. * Сделайте возможность подписчикам подписываться и отписываться от статей по темам в процессе работы приложения.

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Scanner;

public class Blog {
    private static final String EXCHANGE_NAME = "BlogExchanger";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

            Scanner scanner = new Scanner(System.in);

            while (true) {
                try {
                    String message = scanner.nextLine();
                    String[] s = message.split(" ");
                    String key = s[0];
                    String substring = message.substring(s[0].length() + 1);
                    if (!message.isEmpty()) {
                        channel.basicPublish(EXCHANGE_NAME, key, null, substring.getBytes("UTF-8"));
                        System.out.printf("[x] (%s): %s", key, substring);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }


        }

    }

}


