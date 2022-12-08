package ru.geekbrains.march.market.gateway;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/*
Класс - фильтр.
Мы знаем что GateWay - единственная точка входа в приложение.
Соответственно мы в этих воротах можем получить запрос, в котором есть заголовок Authorization, в котором есть токен.

 */
@Component
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {
    @Autowired
    private JwtUtil jwtUtil;

    public JwtAuthFilter() {
        super(Config.class);
    }

    /*
    Создаем фильтр.
    Аналогичен обычный java фильтрам (enterprise),
 */
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // получаем запрос, чтобы из него достать токен.
            ServerHttpRequest request = exchange.getRequest();

            // С фронта на Gateway прилетает JWT, он его преобразует в username и дальше отдаем другим микросервисам.
            // Получается что внутренний микросервис по этому заголовку может понять: пользователь вошедший или не вошедший (гость или не гость).
            // Но вот другой момент, кто мешает фронту не посылать JWT, а послать сразу заголовок username. При этом указать имя пользователя какого нибудь суперадмина.
            // Можно и роли сразу туда закинуть. И поскольку микросервисы ожидают что к ним придет готовый набор данных, то они будут этому доверять.
            // Чтобы Gateway не пропускал эти предзаполненные данные, тут стоит проверка.
            // Если в заголовке сразу кто то Username прислал -такого быть не может - кидаем ошибку.
            if (request.getHeaders().containsKey("username")) {
                return this.onError(exchange, "Invalid header username", HttpStatus.BAD_REQUEST);
            }
            // проверяем есть ли в запросе токен
            if (!isAuthMissing(request)) {
                // если есть - достаем токен
                final String token = getAuthHeader(request);
                // проверяем токен на валидность. Если не валидный - кидаем Unauthorized
                if (jwtUtil.isInvalid(token)) {
                    return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
                }
                // Если данные в токене корректные, то мы эти данные перебрасываем в загловки.
                populateRequestWithHeaders(exchange, token);
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0).substring(7);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        // если в запросе нет заголовка - Authorization.
        if (!request.getHeaders().containsKey("Authorization")) {
            return true;
        }
        // если в заголовке Authorization значение начинается не на 'Bearer '.
        if (!request.getHeaders().getOrEmpty("Authorization").get(0).startsWith("Bearer ")) {
            return true;
        }
        return false;
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        // Из токена запрашиваем все Claims.
        Claims claims = jwtUtil.getAllClaimsFromToken(token);
        // к запросу добавляем header 'username', и в него подшиваем имя пользователя из токена.
        exchange.getRequest().mutate()
                .header("username", claims.getSubject())
//                .header("role", String.valueOf(claims.get("role")))
                .build();
    }
}
