package consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class TaskReceiverApp {
    private static final String TASK_QUEUE_NAME = "task_queue";
    private static final String TASK_EXCHANGER = "task_exchanger";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        // проверили что такая очередь существует
        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        // проверили что такая очередь привязана к определенному exchanger
        channel.queueBind(TASK_QUEUE_NAME, TASK_EXCHANGER, "");
        System.out.println(" [*] Waiting for messages");

        // сколько объектов может забирать задачи из очереди
//        channel.basicQos(3);

        DeliverCallback deliverCallback = ((consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");

            System.out.println(" [x] Received '" + message + "'");
//            if (1 < 10) {
//                throw new RuntimeException("Oops");
//            }
            doWork(message);
            System.out.println(" [x] Done");

            //Запрашиваем у посылки ее id и подтверждаем выполнение.
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        });

        /*
        Когда вешается листенер, то флаг autoAck указывает на то, что если ваш листенер достал из очереди объект, то объект считается на автомате обработанным и очередь ему больше никто не дает.
        Но мы то считаем что у нас могут быть какие то ошибки.
        В этом случае выключаем автоподтверждение (autoAck false), сами будем им управлять.
        Каким образом?
        В DeliveryCallback мы получили delivery (сслыку на нашу посылку) и будем считать если мы дошли дос троки sout(Done) - значит задача выполнена.
        В этом случае делаем подтверждение channel.basicAck().
         */
        channel.basicConsume(TASK_QUEUE_NAME, false, deliverCallback, consumerTag -> {

        });

    }

    public static void doWork(String task) {
        for (char ch : task.toCharArray()) {
            if (ch == '.') {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }


}
