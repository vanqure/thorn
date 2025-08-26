package dev.vanqure.thorn.demo;

import dev.vanqure.thorn.InvalidRepositoryOptionsException;
import dev.vanqure.thorn.InvalidThornClientException;
import dev.vanqure.thorn.RedisBaseRepository;
import dev.vanqure.thorn.RepositoryOperationException;
import dev.vanqure.thorn.RepositoryOptions;
import dev.vanqure.thorn.ThornClient;

public final class UserRepository extends RedisBaseRepository<String, User> {

    private UserRepository(final RepositoryOptions options, final ThornClient thornClient)
            throws InvalidThornClientException, InvalidRepositoryOptionsException {
        super(User.class, options, thornClient);
    }

    public static UserRepository newRepository(final RepositoryOptions options, final ThornClient thornClient)
            throws InvalidThornClientException, InvalidRepositoryOptionsException {
        return new UserRepository(options, thornClient);
    }

    public void set(final User user) throws RepositoryOperationException {
        set(user.id(), user);
    }
}
