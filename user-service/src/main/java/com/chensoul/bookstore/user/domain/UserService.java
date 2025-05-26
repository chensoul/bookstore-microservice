package com.chensoul.bookstore.user.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public PagedResult<User> findUser(Pageable pageable) {
        Page<User> productsPage = userRepository.findAll(pageable).map(UserMapper::toUser);

        return new PagedResult<>(
                productsPage.getContent(),
                productsPage.getTotalElements(),
                productsPage.getNumber(),
                productsPage.getTotalPages(),
                productsPage.isFirst(),
                productsPage.isLast(),
                productsPage.hasNext(),
                productsPage.hasPrevious());
    }

    public User getById(Long id) {
        return userRepository.findById(id).map(UserMapper::toUser).orElseThrow(UserNotFoundException::new);
    }

    public User getByName(String name) {
        return userRepository
                .getByNameIgnoreCase(name)
                .map(UserMapper::toUser)
                .orElseThrow(() -> UserNotFoundException.forName(name));
    }

    public boolean nameExists(final String name) {
        return userRepository.existsByNameIgnoreCase(name);
    }
}
