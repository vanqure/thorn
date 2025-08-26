package dev.vanqure.thorn.demo;

import dev.vanqure.thorn.RedisThornProducer;
import dev.vanqure.thorn.RepositoryOptions;
import dev.vanqure.thorn.ThornClient;
import dev.vanqure.thorn.codec.CacheCodec;
import dev.vanqure.thorn.codec.JacksonCacheCodecProducer;
import dev.vanqure.thorn.codec.MsgpackBasedObjectMapperProducer;
import io.lettuce.core.RedisClient;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public final class Main {

    private Main() {}

    public static void main(final String[] args) {
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
        userRepository.set(user.id(), user);

        final User retrievedUser = userRepository.get(userId);
        System.out.println("Retrieved user from repository: " + retrievedUser);

        userRepository.delete(userId);
        final User deletedUser = userRepository.get(userId);
        System.out.println("Deleted user from repository: " + deletedUser);

        final User nonExistentUser = userRepository.get(UUID.randomUUID().toString());
        System.out.println("Retrieved non-existent user from repository: " + nonExistentUser);

        thornClient.close();
    }
}
