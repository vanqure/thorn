package dev.vanqure.thorn;

public record RepositoryOptions(String prefix, boolean serializeKeys) {

    public static RepositoryOptions newOptions(final String prefix) {
        return new RepositoryOptions(prefix, true);
    }

    public RepositoryOptions withSerializeKeys(final boolean serializeKeys) {
        return new RepositoryOptions(prefix, serializeKeys);
    }
}
