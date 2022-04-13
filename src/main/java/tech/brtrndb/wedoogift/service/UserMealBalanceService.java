package tech.brtrndb.wedoogift.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;

import tech.brtrndb.wedoogift.error.exception.ModelNotFoundException;

public interface UserMealBalanceService {

    /**
     * Get Meal deposit balance of a given {@link tech.brtrndb.wedoogift.domain.User}.
     *
     * @param userId
     * @return
     * @throws ModelNotFoundException
     */
    public @NotNull BigDecimal getUserMealBalance(@NotNull UUID userId) throws ModelNotFoundException;

}
