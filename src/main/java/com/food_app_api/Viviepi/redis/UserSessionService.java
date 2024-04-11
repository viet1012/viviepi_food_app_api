//package com.food_app_api.Viviepi.redis;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserSessionService {
//
//    private static final String REDIS_KEY_PREFIX = "User_Session:";
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    public void saveUserSession(UserSession userSession) {
//        String key = REDIS_KEY_PREFIX + userSession.getUserId();
//        redisTemplate.opsForValue().set(key, userSession);
//    }
//
//    public UserSession getUserSession(String userId) {
//        String key = REDIS_KEY_PREFIX + userId;
//        Object value = redisTemplate.opsForValue().get(key);
//        if (value != null && value instanceof UserSession) {
//            return (UserSession) value;
//        } else {
//            System.out.println("User is null");
//            return null;
//        }
//    }
//
//    public void deleteUserSession(String token) {
//        String key = REDIS_KEY_PREFIX + token;
//        redisTemplate.delete(key);
//    }
//}
