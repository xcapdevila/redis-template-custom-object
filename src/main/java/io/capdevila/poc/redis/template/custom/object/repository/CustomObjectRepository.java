package io.capdevila.poc.redis.template.custom.object.repository;

import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CustomObjectRepository {

  private final RedisTemplate<Object, CustomObject> customObjectRedisTemplate;


  public CustomObjectRepository(
      RedisTemplate<Object, CustomObject> customObjectRedisTemplate) {
    this.customObjectRedisTemplate = customObjectRedisTemplate;
  }

  public CustomObject lpop(String key, long millis) {
    return customObjectRedisTemplate.opsForList().leftPop(key, millis, TimeUnit.MILLISECONDS);
  }

  public Long lpush(String key, CustomObject customObject) {
    return customObjectRedisTemplate.opsForList().leftPush(key, customObject);
  }


}
