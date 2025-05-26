package com.chensoul.bookstore.user.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> getByNameIgnoreCase(String name);

    Optional<UserEntity> getByOpenid(String openid);

    boolean existsByNameIgnoreCase(String name);
}
