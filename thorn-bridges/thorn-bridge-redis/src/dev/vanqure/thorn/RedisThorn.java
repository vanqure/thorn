package dev.vanqure.thorn;

import dev.vanqure.thorn.codec.CacheCodec;
import dev.vanqure.thorn.codec.RedisBinaryCodec;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;

final class RedisThorn implements ThornClient {

    final CacheCodec cacheCodec;
    final RedisClient redisClient;
    final StatefulRedisConnection<String, byte[]> connection;

    RedisThorn(final RedisClient redisClient, final CacheCodec cacheCodec) {
        this.redisClient = redisClient;
        this.connection = redisClient.connect(RedisBinaryCodec.redisBinaryCodec());
        this.cacheCodec = cacheCodec;
    }

    @Override
    public void close() throws ThornClientException {
        try {
            connection.close();
            redisClient.shutdown();
        } catch (final Exception exception) {
            throw new ThornClientException("Couldn't close RedisThorn client", exception);
        }
    }
}
