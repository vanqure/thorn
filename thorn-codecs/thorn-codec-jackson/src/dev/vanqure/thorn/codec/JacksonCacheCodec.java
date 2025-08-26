package dev.vanqure.thorn.codec;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Objects;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class JacksonCacheCodec implements CacheCodec {

    private final ObjectMapper mapper;

    JacksonCacheCodec(final ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    @Contract("null -> null")
    public <T> @Nullable T deserialize(final @Nullable byte[] serializedData) throws CodecDeserializingException {
        if (serializedData == null) {
            return null;
        }
        try {
            return mapper.readValue(serializedData, new TypeReference<>() {});
        } catch (final Exception exception) {
            throw new CodecDeserializingException(
                    "Couldn't deserialize data %s using Jackson codec".formatted(Arrays.toString(serializedData)),
                    exception);
        }
    }

    @Override
    public <T> @NotNull byte[] serializeBytes(final @NotNull T data) throws CodecSerializingException {
        Objects.requireNonNull(data, "Data to serialize cannot be null");
        try {
            return mapper.writeValueAsBytes(data);
        } catch (final Exception exception) {
            throw new CodecSerializingException(
                    "Couldn't serialize %s to bytes using Jackson codec".formatted(data),
                    exception);
        }
    }

    @Override
    public <T> @NotNull String serialize(final @NotNull T data) throws CodecSerializingException {
        Objects.requireNonNull(data, "Data to serialize cannot be null");
        try {
            return mapper.writeValueAsString(data);
        } catch (final Exception exception) {
            throw new CodecSerializingException("Couldn't serialize %s using Jackson codec".formatted(data), exception);
        }
    }
}
