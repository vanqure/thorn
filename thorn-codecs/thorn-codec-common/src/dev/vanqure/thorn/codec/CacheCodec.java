package dev.vanqure.thorn.codec;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface CacheCodec {

    @Contract("null -> null")
    <T> @Nullable T deserialize(@Nullable byte[] serializedData) throws CodecDeserializingException;

    <T> @NotNull byte[] serializeBytes(@NotNull T data) throws CodecSerializingException;

    <T> @NotNull String serialize(@NotNull T data) throws CodecSerializingException;
}
