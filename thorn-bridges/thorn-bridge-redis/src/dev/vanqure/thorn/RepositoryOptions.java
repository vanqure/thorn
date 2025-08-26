package dev.vanqure.thorn;

public record RepositoryOptions(
        /*
         * The prefix to use for all keys stored in the repository.
         */
        String prefix,
        /*
         * Whether to serialize keys before storing them.
         * If false, the keys will be stored as toString().
         */
        boolean serializeKeys) {

    public static RepositoryOptions newOptions(final String prefix) {
        return new RepositoryOptions(prefix, true);
    }

    /*
     * Whether to serialize keys before storing them.
     * If false, the keys will be stored as toString().
     */
    public RepositoryOptions withSerializeKeys(final boolean serializeKeys) {
        return new RepositoryOptions(prefix, serializeKeys);
    }
}
