package io.capdevila.poc.redis.template.custom.object;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.capdevila.poc.redis.template.custom.object.repository.CustomObject;
import io.capdevila.poc.redis.template.custom.object.repository.CustomObjectRepository;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import redis.embedded.RedisServer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomObjectRedisTests {

  private static final Gson GSON = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
  @Autowired
  private StringRedisTemplate stringRedisTemplate;
  @Autowired
  private CustomObjectRepository customObjectRepository;

  @Test
  public void testCustomObjectRepository() {
    final String redisKey = "TEST";

    CustomObject customObject = new CustomObject();
    customObject.setField1("field1");
    customObject.setField2("field2");
    customObjectRepository.lpush(redisKey, customObject);

    CustomObject poppedCustomObject = customObjectRepository.lpop(redisKey, 5000);
    Assert.assertEquals(customObject, poppedCustomObject);

  }

  @Test
  public void testGsonPushAandCustomObjectRepositoryPop() {
    final String redisKey = "TEST";

    CustomObject customObject = new CustomObject();
    customObject.setField1("field1");
    customObject.setField2("field2");
    stringRedisTemplate.opsForList().leftPush(redisKey, GSON.toJson(customObject));

    CustomObject poppedCustomObject = customObjectRepository.lpop(redisKey, 5000);
    Assert.assertEquals(customObject, poppedCustomObject);

  }

  @Test
  public void testRawJsonPushAandCustomObjectRepositoryPop() {
    final String redisKey = "TEST";

    CustomObject customObject = new CustomObject();
    customObject.setField1("field1");
    customObject.setField2("field2");
    stringRedisTemplate.opsForList().leftPush(redisKey, customObject.toRawJsonOnlyFields());

    CustomObject poppedCustomObject = customObjectRepository.lpop(redisKey, 5000);
    Assert.assertEquals(customObject, poppedCustomObject);

  }

  @TestConfiguration
  public static class EmbeddedRedis {

    @Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void startRedis() throws IOException {
      redisServer = new RedisServer(redisPort);
      redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
      redisServer.stop();
    }
  }

}
