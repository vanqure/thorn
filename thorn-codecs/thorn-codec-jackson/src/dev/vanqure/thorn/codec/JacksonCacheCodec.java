package dev.vanqure.thorn.codec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;

final class JacksonCacheCodec implements CacheCodec {

    private final ObjectMapper mapper;

    JacksonCacheCodec(final ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public <T> T deserialize(final byte[] serializedData) throws CacheCodecException {
        try {
            return mapper.readValue(serializedData, new TypeReference<>() {});
        } catch (final Exception exception) {
            throw new CacheCodecException(
                    "Couldn't deserialize data %s using Jackson codec".formatted(Arrays.toString(serializedData)),
                    exception);
        }
    }

    @Override
    public <T> byte[] serialize(final T data) throws CacheCodecException {
        try {
            return mapper.writeValueAsBytes(data);
        } catch (final JsonProcessingException exception) {
            throw new CacheCodecException("Couldn't serialize %s using Jackson codec".formatted(data), exception);
        }
    }
}
