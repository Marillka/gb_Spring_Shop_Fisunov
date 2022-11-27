package ru.rayumov.market.auth.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.rayumov.market.api.RegisterUserDto;
import ru.rayumov.market.auth.entities.Role;
import ru.rayumov.market.auth.entities.User;
import ru.rayumov.market.auth.exceptions.RegistrationException;
import ru.rayumov.market.auth.repositories.RoleRepository;
import ru.rayumov.market.auth.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;



    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }


    @Transactional
    public void registerNewUser(RegisterUserDto registerUserDto) {
        if (isUserOk(registerUserDto)) {
            String username = registerUserDto.getUsername();
            String password = passwordEncoder.encode(registerUserDto.getPassword());
            String email = registerUserDto.getEmail();

            Role role = roleRepository.findByName("ROLE_USER").get();
            Collection<Role> roles = new ArrayList<>(List.of(role));

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setRoles(roles);

            userRepository.save(user);
        }
    }

    public boolean isUserOk(RegisterUserDto registerUserDto) {
        if (registerUserDto.getUsername().isEmpty() || registerUserDto.getPassword().isEmpty() || registerUserDto.getConfirmPassword().isEmpty() || registerUserDto.getEmail().isEmpty()) {
            throw new RegistrationException("Одно из полей пустое");
        }

        if (!registerUserDto.getPassword().equals(registerUserDto.getConfirmPassword())) {
            throw new RegistrationException("Пароли не совпадают");
        }
        if (userRepository.findByUsername(registerUserDto.getUsername()).isPresent()) {
            throw new RegistrationException(String.format("User: '%s' already exists", registerUserDto.getUsername()));
        }
        if (userRepository.findByEmail(registerUserDto.getEmail()).isPresent()) {
            throw new RegistrationException(String.format("Email: '%s' already exists", registerUserDto.getEmail()));
        }
        return true;
    }


}
