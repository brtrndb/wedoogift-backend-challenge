package tech.brtrndb.wedoogift.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;

import tech.brtrndb.wedoogift.domain.GiftDeposit;
import tech.brtrndb.wedoogift.error.exception.InsufficientCashException;

public interface GiftDepositDistributionService {

    public @NotNull Set<GiftDeposit> distributeGiftDeposits(@NotNull UUID companyId, @NotNull BigDecimal amount, @NotNull Set<UUID> userIds, @NotNull LocalDate date) throws InsufficientCashException;

    public default @NotNull Set<GiftDeposit> distributeGiftDeposits(@NotNull UUID companyId, @NotNull BigDecimal amount, @NotNull UUID userId, @NotNull LocalDate date) throws InsufficientCashException {
        return this.distributeGiftDeposits(companyId, amount, Collections.singleton(userId), date);
    }

    public default @NotNull Set<GiftDeposit> distributeGiftDeposits(@NotNull UUID companyId, @NotNull BigDecimal amount, @NotNull Set<UUID> userIds) throws InsufficientCashException {
     return this.distributeGiftDeposits(companyId, amount, userIds, LocalDate.now());
    }

    public default @NotNull Set<GiftDeposit> distributeGiftDeposits(@NotNull UUID companyId, @NotNull BigDecimal amount, @NotNull UUID userId) throws InsufficientCashException {
        return this.distributeGiftDeposits(companyId, amount, Collections.singleton(userId));
    }

}
