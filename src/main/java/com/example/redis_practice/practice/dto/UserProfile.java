package com.example.redis_practice.practice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserProfile {
    private String name;
    private int age;

    @Builder
    public UserProfile(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
