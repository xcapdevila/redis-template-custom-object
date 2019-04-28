# redis-template-custom-object

Proof of concept to experiment with the usage of a typed RedisTemplate.

When using RedisTemplate<K,V> and Jackson as a Seralizer tool, it adds a field named "@class" to be able to identify the object's class on Deserialize operation.

The aim of this project is to be able to use a RedisTemplate<K,V> independently whether the data has been serialized with Jackson or any other tool.
