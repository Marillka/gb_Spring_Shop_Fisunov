package ru.geekbrains.march.market.cart.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

// биндить поля класса будем через конструктор
@ConstructorBinding
// использование таких properties вашему приложению нужно разрешить. Для этого в конфиге добавляется аннотация.
@ConfigurationProperties(prefix = "integrations.product-service")
@Data
public class ProductServiceIntegrationProperties {

    private String url;
    private Integer connectTimeout;
    private Integer readTimeout;
    private Integer writeTimeout;
}
