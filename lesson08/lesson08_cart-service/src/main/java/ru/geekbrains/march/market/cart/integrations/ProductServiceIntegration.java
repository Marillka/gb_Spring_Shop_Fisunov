package ru.geekbrains.march.market.cart.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.geekbrains.march.market.api.ProductDto;

@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {

//    private final RestTemplate restTemplate;
    private final WebClient productServiceWebClient;

    public ProductDto findById(Long id) {
        // хотим послать get запрос
        return productServiceWebClient.get()
                // в веб клиенте уже зашит url. Дописываем только uri
                .uri("api/v1/products/ "+ id)
                // Все что идет до retrieve() - это настройка запроса.
                // retrieve- означает что хотим послать запрос и получить ответ.
                // веб клиент по умолчанию работает как асинхронный клиент
                // То есть вы получаете ссылку на какой то объект и потом вы можете с ним что то сделать
                .retrieve()
                // Нам нужно получить тело ответа.
                // bodyToMono() - тело ответа преобразует к объекту типа Mono
                // При этом тело ответа имеет какой то тип - мы указываем что это ProductDto
                // Тип Mono - это относится к реактивному программированию
                // То есть вы говорите что я выполняю какое то действие и оно в будущем когда то будет выполнено и в будущем мне может прилететь один объект, либо n-оу количество объектов.
                // Вот если в будущем вам вернется один какой то ответ ,то это mono
                // Прилетит набор объектов (какая нибудь коллекция), то это тип flux().
                // Это значит что на mono объект вы можете повесить какое то действие
                // Когда мне ответ придет, я хочу с ним сделать то то и то.
                .bodyToMono(ProductDto.class)
                // делаем block() - в будущем объект придет, но давайте подождем пока он придет.
                // block() - типо ждем исполнения задачи и потом возвращаем результат.
                // В нашем случаеи из Mono получим сам объект (ProductDto).
                .block();
    }


//    public void clear(String username) {
//        cartServiceWebClient.get()
//                .uri("/api/v1/cart/0/clear")
//                .header("username", username)
//                .retrieve()
//                .toBodillesEntity// 200, 400, 401, 500 ....
//                .block();
//    }


}
