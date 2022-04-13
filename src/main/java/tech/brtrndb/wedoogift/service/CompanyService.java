package tech.brtrndb.wedoogift.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;

import tech.brtrndb.wedoogift.domain.Company;
import tech.brtrndb.wedoogift.error.exception.InsufficientCashException;
import tech.brtrndb.wedoogift.error.exception.ModelNotFoundException;

public interface CompanyService {

    /**
     * Create a new {@link Company}.
     * @param company
     * @return
     */
    public @NotNull Company create(@NotNull Company company);

    /**
     * Get a {@link Company} by its id.
     *
     * @param companyId
     * @return
     * @throws ModelNotFoundException
     */
    public @NotNull Company getById(@NotNull UUID companyId) throws ModelNotFoundException;

    /**
     * Withdraw gift cash from a {@link Company}.
     *
     * @param companyId
     * @param amount
     * @return
     * @throws InsufficientCashException
     */
    public @NotNull BigDecimal withdrawGiftCash(@NotNull UUID companyId, @NotNull BigDecimal amount) throws InsufficientCashException;

    /**
     * Withdrow Meal cash from a {@link Company}.
     *
     * @param companyId
     * @param amount
     * @return
     * @throws InsufficientCashException
     */
    public @NotNull BigDecimal withdrawMealCash(@NotNull UUID companyId, @NotNull BigDecimal amount) throws InsufficientCashException;

    /**
     * Test if a {@link Company} has enough Gift cash.
     *
     * @param companyId
     * @param amount
     * @return
     */
    public boolean hasEnoughGiftCash(@NotNull UUID companyId, @NotNull BigDecimal amount);

    /**
     * Test if a {@link Company} has enough Meal cash.
     *
     * @param companyId
     * @param amount
     * @return
     */
    public boolean hasEnoughMealCash(@NotNull UUID companyId, @NotNull BigDecimal amount);

}
