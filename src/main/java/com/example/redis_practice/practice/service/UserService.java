package com.example.redis_practice.practice.service;

import com.example.redis_practice.practice.dto.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class UserService {

    private final ExternalApiService externalApiService;
    private final StringRedisTemplate redisTemplate;

    public UserProfile getUserProfile(String userId) {
        String userName = null;

        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String cachedName = ops.get("nameKey" + userId);
        if (cachedName != null) {
            userName = cachedName;
        }
        else {
            userName = externalApiService.getUserName(userId);
            ops.set("nameKey" + userId, userName, 5, TimeUnit.SECONDS);
        }

        int userAge = externalApiService.getUserAge(userId);

        return UserProfile.builder()
                .name(userName)
                .age(userAge)
                .build();
    }
}
