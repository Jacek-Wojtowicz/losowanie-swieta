package com.jwojtowicz.losowanie.user;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private UserRepository repository;
    private PasswordEncoder passwordEncoder;

    public UserDTO createUser(UserDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setDrawName(dto.getName());
        User created = repository.save(user);

        return new UserDTO(created);
    }

    public Optional<User> getUserByName(String name) {
        return Optional.ofNullable(repository.findUserByNameIgnoreCase(name));
    }

    public List<User> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).toList();
    }

    public void deleteUser(Integer id) {
        repository.deleteById(id);
    }

    public void updateDrawName(Integer id, String drawName) {
        Optional<User> user = repository.findById(id);
        user.ifPresent(value -> {
            value.setDrawName(drawName);
            repository.save(value);
        });
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findUserByNameIgnoreCase(username);
        List<SimpleGrantedAuthority> permissions =
                user.getName().equalsIgnoreCase("Jacek") || user.getName().equalsIgnoreCase("admin") ? List.of(
                        new SimpleGrantedAuthority("ROLE_ADMIN")) : List.of();
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), permissions);
    }

    public void updatePassword(Integer id, String password) {
        Optional<User> user = repository.findById(id);
        user.ifPresent(value -> {
            value.setPassword(passwordEncoder.encode(password));
            repository.save(value);
        });
    }
}
