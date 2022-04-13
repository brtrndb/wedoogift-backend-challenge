package tech.brtrndb.wedoogift.service;

import java.time.LocalDate;

import org.jetbrains.annotations.NotNull;

public interface DepositExpirationService {

    /**
     * Get a Gift deposit expiration date from a given date.
     *
     * @param giftDepositDate
     * @return
     */
    public @NotNull LocalDate getGiftExpirationDate(@NotNull LocalDate giftDepositDate);

    /**
     * Get a Meal deposit expiration date from a given date.
     *
     * @param mealDepositDate
     * @return
     */
    public @NotNull LocalDate getMealExpirationDate(@NotNull LocalDate mealDepositDate);

}
