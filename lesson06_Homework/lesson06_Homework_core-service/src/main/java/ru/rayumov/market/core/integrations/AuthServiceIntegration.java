//package ru.rayumov.market.core.integrations;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.client.WebClient;
//import ru.rayumov.market.api.UserDto;
//
//@Component
//@RequiredArgsConstructor
//public class AuthServiceIntegration {
//    private final WebClient authServiceWebClient;
//
//
//    public UserDto findUserByUsername(String username) {
//        return authServiceWebClient.get()
//                .uri("api/v1/products/ "+ id)
//                .retrieve()
//                .bodyToMono(ProductDto.class)
//                .block();
//    }
//
//
////    public void clear(String username) {
////        cartServiceWebClient.get()
////                .uri("/api/v1/cart/0/clear")
////                .header("username", username)
////                .retrieve()
////                .toBodillesEntity// 200, 400, 401, 500 ....
////                .block();
////    }
//
//
//}
//
//
///*
//
//    public Optional<User> findByUsername(String username) {
//        return userRepository.findByUsername(username);
//    }
//
//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = findByUsername(username).orElseThrow(() ->
//        new UsernameNotFoundException(String.format("User '%s' not found", username)));
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
//    }
//
//    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
//        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
//    }
// */
//
//
//
//
