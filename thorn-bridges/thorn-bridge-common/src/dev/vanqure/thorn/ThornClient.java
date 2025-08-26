package dev.vanqure.thorn;

import java.io.Closeable;

public interface ThornClient extends Closeable {

    @Override
    void close() throws ThornClientException;
}
