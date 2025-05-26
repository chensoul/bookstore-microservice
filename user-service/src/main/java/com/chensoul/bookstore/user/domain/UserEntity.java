package com.chensoul.bookstore.user.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_generator")
    @SequenceGenerator(name = "user_id_generator", sequenceName = "user_id_seq")
    private Long id;

    @Column(nullable = false, unique = true)
    @Size(max = 255) @NotBlank @UserNameUnique
    private String name;

    @Column(nullable = false)
    private String password;

    private String role;

    @Column
    private String openid;

    @Column
    private String loginType;

    public UserEntity() {}

    public UserEntity(Long id, String name, String password, String role, String openid, String loginType) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
        this.openid = openid;
        this.loginType = loginType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
