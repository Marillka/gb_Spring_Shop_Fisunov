package consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class SimpleReceiverApp {
    /*
    Получатель должен подключиться к какой то очереди, поэтому прописываем ее имя.
     */
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        // открываем соединение, открываем канал.
        // Обратите внимание что в сендере мы используем try with resources. Открываем соединение, отправляем сообщение и закрываем соединение.
        // В случае с ресивером мы их в конце не закрываем.
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // Проверяем есть ли такая очередь. Если есть - ок, если нет - создаем ее.
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("[*] Waiting for messages");
        /*
        Создаем обработчик сообщений.
        Говорим,что если когда нибудь в целевую очередь придет сообщение, то хотим на него как то среагировать.
        Как только приходит сообщение, получаем из него пачку байтов, собираем из байтов строку с преобразованием в UTF-8.
        Отпечатываем в консоль
        Сам по себе обработчик сообщений работать не может. Вы указываете как вы будете обрабатывать поссылки.
        Как обработчик повесить на очередь? Для этого делается basicConsume.
        То есть берем очередь с таким то именем и отдаем обработчик такой то.
        Соответсвенно если закрыть connection и channel то все это поломается.
        Не получится слушать очередь и обрубить connection к rabbitmq.
         */
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            // Клиент для rabbitmq создает пул потоков и обязательно обрабатывает поссылки в пуле потоков. Ни в коем случае не блокирует main поток.
            System.out.println(Thread.currentThread().getName()+" [x] Receive '" + message + "'");
        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {

        });


    }
}
