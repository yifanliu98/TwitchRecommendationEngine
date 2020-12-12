package com.example.jupiter.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponseBody {
    @JsonProperty("user_id") // java -> json string
    private final String userId;

    @JsonProperty("name")
    private final String name;

    public LoginResponseBody(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}
