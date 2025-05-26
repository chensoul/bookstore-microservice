package com.chensoul.bookstore.user.domain;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FormUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(FormUserDetailsService.class);

    private final UserRepository userRepository;

    public FormUserDetailsService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public FormUserDetails loadUserByUsername(final String username) {
        final Optional<UserEntity> userOptional = userRepository.getByNameIgnoreCase(username);
        if (!userOptional.isPresent()) {
            log.warn("user not found: {}", username);
            throw new UsernameNotFoundException("User " + username + " not found");
        }
        UserEntity userEntity = userOptional.get();

        final List<SimpleGrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority(userEntity.getRole().toUpperCase(Locale.ROOT)));
        return new FormUserDetails(userEntity.getId(), username, userEntity.getPassword(), authorities);
    }
}
