package ru.geekbrains.march.market.cart.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories// Если хотим работать с redis как со спринговым репозиторием.
public class RedisConfig {

    // Фабрика соединений с redis.
    // Он никак не настраивается. Его нет необходимости настраивать, если вы используете стандартные конфиги redis или memurai.
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    /*
        Нам хотелось бы общаться с redis.
        Мы посылаем по соединению какую то пачку байтов и получаем ответ.
        И использовать стандартную сериализацию не принято. Либо используем какие то хитрые библиотеки, либо используем JSON как и в REST.
        И чтобы наше приложение понимало как общаться с redis - создаем RedisTemplate.
        В качестве ключа используем строки, в качестве значения используем объект какого то типа.
        Создается экземпляр RedisTemplate и задаются сериализаторы для ключа и для значения.
        Ключ это строка - поэтому StringRedisSerializer
        Значение JSON - соответственно Jackson.
        Отдаем фабрику для открытия соединения.
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
