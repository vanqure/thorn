## thorn

An elegant Redis cache abstraction, built on Lettuce. Designed for speed, simplicity, and expressive caching APIs.

### Why Thorn?

- **Agnostic**: Use JSON, MsgPack, or plug in your own.
- **Simple & Expressive**: Clean, no boilerplate.

### Get started

You can build dependency and append it to your local .m2 directory, by using: `./gradlew publishToMavenLocal`

### Using Thorn

Thorn in action:

```java
final CacheCodec cacheCodec =
        JacksonCacheCodecProducer.produceCodec(MsgpackBasedObjectMapperProducer.produceMapper());
final ThornClient thornClient =
        RedisThornProducer.produceThorn(RedisClient.create("redis://localhost:6379"), cacheCodec);

final UserRepository userRepository =
        UserRepository.newRepository(
                RepositoryOptions.newOptions("survival:users:").withSerializeKeys(true),
                thornClient);

final String userId = UUID.randomUUID().toString();
final User user = new User(
        userId,
        "hello-" + ThreadLocalRandom.current().nextInt(),
        ThreadLocalRandom.current().nextLong());
System.out.println("Setting user in repository to " + user); 
userRepository.set(user); // under mask calls set(Key, Value) 
// userRepository.set(user.id(), user);

final User retrievedUser = userRepository.get(userId); 
System.out.println("Retrieved user from repository: " + retrievedUser);
 
userRepository.delete(userId);
final User deletedUser = userRepository.get(userId); 
System.out.println("Deleted user from repository: " + deletedUser);

final User nonExistentUser = userRepository.get(UUID.randomUUID().toString()); 
System.out.println("Retrieved non-existent user from repository: " + nonExistentUser); 

thornClient.close();

public record User(String id, String username, long level) {}

public final class UserRepository extends RedisBaseRepository<String, User> {

    private UserRepository(final RepositoryOptions options, final ThornClient thornClient)
            throws InvalidThornClientException, InvalidRepositoryOptionsException {
        super(options, thornClient);
    }

    public static UserRepository newRepository(final RepositoryOptions options, final ThornClient thornClient)
            throws InvalidThornClientException, InvalidRepositoryOptionsException {
        return new UserRepository(options, thornClient);
    }

    public void set(final User user) throws RepositoryOperationException {
        set(user.id(), user);
    }
}
```

---

![Visitor Count](https://visitor-badge.laobi.icu/badge?page_id=vanqure.thorn)
