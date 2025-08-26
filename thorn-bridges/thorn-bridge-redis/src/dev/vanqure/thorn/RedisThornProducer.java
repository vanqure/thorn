package dev.vanqure.thorn;

import dev.vanqure.thorn.codec.CacheCodec;
import io.lettuce.core.RedisClient;

public final class RedisThornProducer {

    private RedisThornProducer() {}

    public static ThornClient produceThorn(final RedisClient redisClient, final CacheCodec cacheCodec) {
        return new RedisThorn(redisClient, cacheCodec);
    }
}
