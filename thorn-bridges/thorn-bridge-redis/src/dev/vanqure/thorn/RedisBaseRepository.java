package dev.vanqure.thorn;

import dev.vanqure.thorn.codec.CacheCodec;
import io.lettuce.core.api.StatefulRedisConnection;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RedisBaseRepository<K, V> {

    private final Class<V> valueType;
    private final CacheCodec cacheCodec;
    private final RepositoryOptions options;
    private final StatefulRedisConnection<String, byte[]> connection;

    protected RedisBaseRepository(
            final Class<V> valueType,
            final RepositoryOptions options,
            final ThornClient thornClient) throws InvalidThornClientException, InvalidRepositoryOptionsException {
        if (!(thornClient instanceof final RedisThorn redisThorn)) {
            throw new InvalidThornClientException("%s isn't a RedisThorn instance");
        }
        if (options.prefix() == null || options.prefix().isBlank()) {
            throw new InvalidRepositoryOptionsException("Prefix must be non-null and non-blank");
        }
        this.valueType = valueType;
        this.options = options;
        this.cacheCodec = redisThorn.cacheCodec;
        this.connection = redisThorn.connection;
    }

    public static <K, V> RedisBaseRepository<K, V> create(
            final Class<V> valueType,
            final RepositoryOptions options,
            final ThornClient thornClient) throws InvalidThornClientException, InvalidRepositoryOptionsException {
        return new RedisBaseRepository<>(valueType, options, thornClient);
    }

    public void set(final @NotNull K key, final @NotNull V value) throws RepositoryOperationException {
        Objects.requireNonNull(key, "Key must not be null");
        Objects.requireNonNull(value, "Value must not be null");
        try {
            final byte[] serializedValue = cacheCodec.serializeBytes(value);

            if (options.serializeKeys()) {
                final String serializedKey = cacheCodec.serialize(key);
                connection.sync().set(options.prefix() + serializedKey, serializedValue);
            } else {
                connection.sync().set(options.prefix() + key, serializedValue);
            }
        } catch (final Exception exception) {
            throw new RepositoryOperationException("Couldn't set key %s to value %s".formatted(key, value), exception);
        }
    }

    public @Nullable V get(final @NotNull K key) throws RepositoryOperationException {
        Objects.requireNonNull(key, "Key must not be null");
        try {
            if (options.serializeKeys()) {
                final String serializedKey = cacheCodec.serialize(key);
                final byte[] serializedValue = connection.sync().get(options.prefix() + serializedKey);
                return cacheCodec.deserialize(serializedValue, valueType);
            } else {
                final byte[] serializedValue = connection.sync().get(options.prefix() + key);
                return cacheCodec.deserialize(serializedValue, valueType);
            }
        } catch (final Exception exception) {
            throw new RepositoryOperationException("Couldn't get value by key %s".formatted(key), exception);
        }
    }

    public void delete(final @NotNull K key) throws RepositoryOperationException {
        Objects.requireNonNull(key, "Key must not be null");
        try {
            if (options.serializeKeys()) {
                final String serializedKey = cacheCodec.serialize(key);
                connection.sync().del(options.prefix() + serializedKey);
            } else {
                connection.sync().del(options.prefix() + key);
            }
        } catch (final Exception exception) {
            throw new RepositoryOperationException("Couldn't delete key %s".formatted(key), exception);
        }
    }
}
