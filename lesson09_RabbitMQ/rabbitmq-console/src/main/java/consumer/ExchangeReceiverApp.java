package consumer;

import com.rabbitmq.client.*;

public class ExchangeReceiverApp {
    private static final String EXCHANGE_NAME = "directExchanger";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // проверяем что существует такой exchanger, если нет - создаем.
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        // rabbitmq объяви очередь и дай нам ссылку на нее.
        String queueName = channel.queueDeclare().getQueue();
        System.out.println("My queue name: " + queueName);

        // привязываем очередь к центру сообщений (exchanger) и задаем ключ
        channel.queueBind(queueName, EXCHANGE_NAME, "php");

        System.out.println(" [*] Waiting for messages");

        // вешаем обработчик событий на очередь
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {

        });

        /*
AD - auto-delete - Эта очередь создана для какого то приложения и как только канал связи с этимм приложением закроется, эта очередь будет удалена автоматически.
Excl - exclusive true - к данной очереди может быть открыто только одно соединение (висеть только один listener). Если попытаться подключиться с другого приложения по этому имени, то будет ошибка
D - durable - долговечность. Очереди без этой буквы будут удалены после перезапуска сервера. А долговечные востановятся с диска.
         */


    }
}
