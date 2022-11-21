package ru.geekbrains.march.market.cart.configs;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;
import ru.geekbrains.march.market.cart.properties.ProductServiceIntegrationProperties;

import java.util.concurrent.TimeUnit;


// RestTemplate - это синхронный клиент.
// Если вы посылаете запрос, то вы ждете пока вам микросервис ответит. Если не ответит, то вы вешаете полностью поток, который здесь используете.
// Это первая проблема - что работает в синхронном режиме.
// Второе - это постоянно таскать за собой Url адреса.

// В спринге помиио RestTemplate добавилась такая штука как Web Client.
// И он умеет работать как в синхронном режиме, так и в асинхронном режиме.
// Вы можете потенциально послать запрос и сказать когда ему придет ответ от сервиса - тогда он выполнит какое то действие (10, 15, 20 секунд и т.д.). Вы вешаете callback на запрос.
// И тем самым вы немного разгружаете pull потоков, который отвечает за обработку запросов.


// Для каждого микросевиса с которым вы общаетесь вы можете создавать WebClient.
// Это позволяет тонко настраивать взаимодействие с каждый микросервисом.
// То есть у вам есть какие то быстрые микросервисы и для них должны быть маленькие timeout
// Медленные микросервисы чуть дольше отвечают - и timeout соответственно больше.
// Кроме того в web-client сразу зашивается адресс микросервиса.
//    @Bean
//    public WebClient cartServiceWebClient() {
//        // созадется TcpClient и настраивается несколько видов timeout
//        TcpClient tcpClient = TcpClient
//                .create()
//                // timeout на подключение, timeout на чтение и timeout на запись.
//                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
//                .doOnConnected(connection -> {
//                    connection.addHandler(new ReadTimeoutHandler(15000, TimeUnit.MILLISECONDS));
//                    connection.addHandlerLast(new WriteTimeoutHandler(2000, TimeUnit.MILLISECONDS));
//                });
//
//        // Куда мы будем стучаться? Должен быть какой то адрес.
//                return WebClient
//                        .builder()
//                        .baseUrl("http://localhost:8190/market-cart")
//                        .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
//                        .build();
//
//    }
//}

// Какие минусы. Если вот так настраивать интеграции. Вот если вы работаете с каким то микросервисов.
// Он отвечал за 15 секунд стабильно, начал отвечать за 20 секунд, а у вас это все запрятанно в коде.
// Получается вам придется создать отдельную ветку. В этой ветке подправить значение, запушить это все на github, убрать из этого образ вашего микросервиса, задеполоить его и просто чтобы изменить конфиг. -Звучит не очень.

// Если же  все эти настройки вынести в yaml файл, то вы можете зайти в OpenShift(специальная панель управления микросервисами), поменять значения и сказать я хочу этот микросервис свой перезапустить.

// ВАШЕ ПРИЛОЖЕНИЕ НЕ ДОЛЖНО БЫТЬ ЖЕСТКО НАСТРОЕННЫМ ВНУТРИ КОДА.
// Это делает его неповоротливым.
// Так что все что мы можете вынести в конфиги - туда желательно выносить.


@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(
        ProductServiceIntegrationProperties.class
)
public class AppConfig {

//    // Так делать неудобно
//
//    @Value("integrations.cart-service.url")
//    private String cartServiceIntegrationUrl;
//
//    @Value("integrations.cart-service.read-timeout")
//    private Integer cartServiceIntegrationReadTimeout;

    private final ProductServiceIntegrationProperties productServiceIntegrationProperties;

    //WebClient умеет отправлять запросы и получать ответы
    @Bean
    public WebClient productServiceWebClient() {
        // Настраиваем сетевой уровень TCP/IP
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, productServiceIntegrationProperties.getConnectTimeout())
                .doOnConnected(connection -> {
                    connection.addHandler(new ReadTimeoutHandler(productServiceIntegrationProperties.getReadTimeout(), TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(productServiceIntegrationProperties.getWriteTimeout(), TimeUnit.MILLISECONDS));
                });

        return WebClient
                .builder()// паттерн builder
                .baseUrl(productServiceIntegrationProperties.getUrl())// url
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))// устанавливаем как будет осуществлятся передача на сетевом уровне.
                .build();

    }
    /*
    Сам по себе сокет в Java никакого смысла не несет. Есть соединение и есть.
    А что с ним делать?
    Если пилим REST клиент, то видимо мы хотим посылать какие то http запросы.
    Оборачиваем Сеть (tcp) в http протокол.
    Работаем с асинхронным клиентом - это реактивное программирование.
    Мы посылваем какое то событие, когда оно выполнится мы хотим что то сделать.
    Рекативное программирование - когда выполнится наше действие.
    Добавлям ReactorClientHttpConnector для работы в асинхронном режиме, который позволяет выстраивать вот эту цепочку действий (пошлем запрос, когда придет ответ - обработаем).
     */
}
