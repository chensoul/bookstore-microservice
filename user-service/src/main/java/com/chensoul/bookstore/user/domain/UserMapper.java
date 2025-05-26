package com.chensoul.bookstore.user.domain;

class UserMapper {

    static User toUser(UserEntity userEntity) {
        return new User(
                userEntity.getName(),
                userEntity.getPassword(),
                userEntity.getRole(),
                userEntity.getOpenid(),
                userEntity.getLoginType());
    }
}
