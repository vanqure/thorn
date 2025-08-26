package dev.vanqure.thorn.codec;

public interface CacheCodec {

    <T> T deserialize(byte[] serializedData) throws CacheCodecException;

    <T> byte[] serialize(T data) throws CacheCodecException;
}
