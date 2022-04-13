package tech.brtrndb.wedoogift.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;

import tech.brtrndb.wedoogift.domain.MealDeposit;
import tech.brtrndb.wedoogift.error.exception.InsufficientCashException;

public interface MealDepositDistributionService {

    /**
     * Distribute Meal deposits from a {@link tech.brtrndb.wedoogift.domain.Company} to {@link tech.brtrndb.wedoogift.domain.User}.
     *
     * @param companyId
     * @param amount
     * @param userIds
     * @return
     * @throws InsufficientCashException
     */
    public @NotNull Set<MealDeposit> distributeMealDeposits(@NotNull UUID companyId, @NotNull BigDecimal amount, @NotNull Set<UUID> userIds) throws InsufficientCashException;

    public default @NotNull Set<MealDeposit> distributeMealDeposits(@NotNull UUID companyId, @NotNull BigDecimal amount, @NotNull UUID userId) throws InsufficientCashException {
        return this.distributeMealDeposits(companyId, amount, Collections.singleton(userId));
    }

}
