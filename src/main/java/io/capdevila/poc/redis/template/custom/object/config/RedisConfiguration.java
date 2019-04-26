package io.capdevila.poc.redis.template.custom.object.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import io.capdevila.poc.redis.template.custom.object.repository.CustomObject;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration extends CachingConfigurerSupport {

  @Bean
  public RedisTemplate<Object, CustomObject> customObjectRedisTemplate(
      RedisConnectionFactory connectionFactory) {
    RedisTemplate<Object, CustomObject> template = new RedisTemplate<>();
    template.setConnectionFactory(connectionFactory);
    template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
    template.setKeySerializer(new StringRedisSerializer());
    template.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
    template.setValueSerializer(new CustomObjectRedisSerializer());
    return template;
  }

  /**
   * <pre>
   * Jackson needs a @class field to be able to Serialize objects.
   * As we insert directly object's fields we need to add the porperty @class to be able
   * to Deserialize the received object.
   * If the entity is Serialized by Jackson, it will add the @class field.
   * </pre>
   */
  static class CustomObjectRedisSerializer implements RedisSerializer<CustomObject> {

    private static final String CLASS_FIELD_VALUE = "@class";
    private static final String CLASS_FIELD =
        "{\"" + CLASS_FIELD_VALUE + "\":\"io.capdevila.poc.redis.template.custom.object"
            + ".repository.CustomObject\",";

    private final ObjectMapper om;

    public CustomObjectRedisSerializer() {
      this.om = new ObjectMapper().enableDefaultTyping(DefaultTyping.NON_FINAL, As.PROPERTY);
    }

    @Override
    public byte[] serialize(CustomObject t) throws SerializationException {
      try {
        return om.writeValueAsBytes(t);
      } catch (JsonProcessingException e) {
        throw new SerializationException(e.getMessage(), e);
      }
    }

    /**
     * Deserialize fixing the @class field just in case it's missing.
     *
     * @param bytes received object bytes
     * @return CustomObject instance
     * @throws SerializationException serializationException
     */
    @Override
    public CustomObject deserialize(byte[] bytes) throws SerializationException {

      if (bytes == null) {
        return null;
      }

      try {
        String test = new String(bytes);
        test = addClassField(test);
        return om.readValue(test, CustomObject.class);
      } catch (Exception e) {
        throw new SerializationException(e.getMessage(), e);
      }
    }

    /**
     * Adds to the given JSON String the @class field just in case it's missing.
     */
    private String addClassField(String test) {
      if (!test.contains(CLASS_FIELD_VALUE)) {
        test = CLASS_FIELD.concat(test.substring(1));
      }
      return test;
    }
  }

}

