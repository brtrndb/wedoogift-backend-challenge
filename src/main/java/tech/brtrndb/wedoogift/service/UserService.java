package tech.brtrndb.wedoogift.service;

import java.util.Collection;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;

import tech.brtrndb.wedoogift.domain.User;
import tech.brtrndb.wedoogift.error.exception.ModelNotFoundException;

public interface UserService {

    /**
     * Create a new {@link User}.
     *
     * @param user
     * @return
     */
    public @NotNull User create(@NotNull User user);

    /**
     * Get a {@link User}.
     *
     * @param userId
     * @return
     */
    public @NotNull User getById(@NotNull UUID userId) throws ModelNotFoundException;

    /**
     * Test existence of a {@link User}.
     *
     * @param userId
     * @return
     */
    public boolean existsById(@NotNull UUID userId);

    /**
     * Test existence of all {@link User}.
     *
     * @param userIds
     * @return
     */
    public boolean existsById(@NotNull Collection<UUID> userIds);

}
