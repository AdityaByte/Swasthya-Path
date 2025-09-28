package in.ayush.swasthyapath.service;

import in.ayush.swasthyapath.dto.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    // userId would be the email
    public void cacheDietPlan(String userId, ResponseData responseData) {
        String key = "dietplan:" + userId;
        redisTemplate.opsForValue().set(key, responseData, Duration.ofHours(24));
        log.info("Diet plan has been cached.");
    }

    public ResponseData getCachedDietPlan(String userId) {
        String key = "dietplan:" + userId;
        ResponseData responseData = (ResponseData) redisTemplate.opsForValue().get(key);
        return responseData;
    }

}
