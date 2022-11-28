package producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SimpleSenderApp {

    /*
Созадем Connection factory - фабрика для создания подключений к rabbit.
Задаем host, где живет rabbitmq. (Если порт не задан, то используется по дефолту 15672).
Открываем соединение и открываем канал.
Есть какое то количество обменников и какое то количество очередей, но вы о них не знаем.
exchangeDeclare - проверяет что такой обменник существует, иначе создает его.
queueDeclare - проверяет что такая очередь существет, иначе создает ее.
queueBind - создаем связь между очередью и обменников и задаем ей ключ 'java'.
Формируем сообщение.
Очереди сообщений без разницы что вы отправляете, это просто набор байтов.
basicPublic - отправляем байты в exchanger по ключю


     */
    // название целевой очереди
    private final static String QUEUE_NAME = "hello";
    // название обменника
    private final static String EXCHANGER_NAME = "hello_exchanger";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGER_NAME, BuiltinExchangeType.DIRECT);
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.queueBind(QUEUE_NAME, EXCHANGER_NAME, "java");

            String message = "Hello World";
//            channel.basicPublish(EXCHANGER_NAME, "c++", null, message.getBytes());// не сработает, потому что ключа 'c++' не существет.
            channel.basicPublish(EXCHANGER_NAME, "java", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }

    }
}
