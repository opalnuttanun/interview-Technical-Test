package com.example.user.Model;

public class User {

    private final Long id;
    private final String name;
    private final String username;
    private final String email;
    private final String phone;
    private final String website;

    public User(
        Long id,
        String name,
        String username,
        String email,
        String phone,
        String website
    ) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.website = website;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }
}