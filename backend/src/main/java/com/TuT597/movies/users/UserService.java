package com.TuT597.movies.users;

import com.TuT597.movies.authorities.Authority;
import com.TuT597.movies.authorities.AuthorityRepository;
import com.TuT597.movies.authorities.MARole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    public User save(String username, String password, MARole role) {
        authorityRepository.save(new Authority(username, role.toString()));
        return userRepository.save(new User(username, passwordEncoder.encode(password)));
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
