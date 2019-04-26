package io.capdevila.poc.redis.template.custom.object.service;

import static java.lang.System.exit;

import io.capdevila.poc.redis.template.custom.object.repository.CustomObject;
import io.capdevila.poc.redis.template.custom.object.repository.CustomObjectRepository;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class TestService {

  private final CustomObjectRepository customObjectRepository;


  public TestService(
      CustomObjectRepository customObjectRepository) {
    this.customObjectRepository = customObjectRepository;
  }

  @PostConstruct
  public void test() {
    // Manual insert
    // LPUSH TEST "{\"field1\":\"YUHUUUUU\",\"field2\":\"WOOORKS\"}"

    String redisKey = "TEST";

    CustomObject customObject = new CustomObject();

   //Long res = null;
   //for (int i = 0; i < 100; i++) {
   //  customObject = new CustomObject();
   //  customObject.setField1(String.valueOf(i));
   //  customObject.setField2(String.valueOf(i * 2));
   //  System.out.println("*********** PUSH ***********");
   //  res = customObjectRepository.lpush(redisKey, customObject);
   //  System.out.println(res);
   //  System.out.println(customObject);
   //  System.out.println("****************************");
   //}

    CustomObject poppedCustomObject = null;
    do {
      System.out.println("*********** POP ***********");
      poppedCustomObject = customObjectRepository.lpop(redisKey, 5000);
      System.out.println(poppedCustomObject);
      System.out.println("***************************");
    }
    while (poppedCustomObject != null);

    System.out.println("BYE!!");
    exit(0);
  }


}
