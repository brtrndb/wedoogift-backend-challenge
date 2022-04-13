package tech.brtrndb.wedoogift.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import tech.brtrndb.wedoogift.domain.GiftDeposit;
import tech.brtrndb.wedoogift.domain.MealDeposit;
import tech.brtrndb.wedoogift.error.exception.IllegalAmountException;
import tech.brtrndb.wedoogift.error.exception.InsufficientCashException;
import tech.brtrndb.wedoogift.error.exception.ModelNotFoundException;

@Slf4j
@Service
@Transactional
public class DepositDistributionServiceImpl implements GiftDepositDistributionService, MealDepositDistributionService {

    private final CompanyService companyService;
    private final UserService userService;
    private final DepositExpirationService depositExpirationService;
    private final DepositService depositService;

    //

    public DepositDistributionServiceImpl(@NonNull CompanyService companyService, @NonNull UserService userService, @NonNull DepositExpirationService depositExpirationService, @NonNull DepositService depositService) {
        this.companyService = companyService;
        this.userService = userService;
        this.depositExpirationService = depositExpirationService;
        this.depositService = depositService;
    }

    //

    @Override
    public @NotNull Set<GiftDeposit> distributeGiftDeposits(@NotNull UUID companyId, @NotNull BigDecimal amount, @NotNull Set<UUID> userIds, @NotNull LocalDate date) throws InsufficientCashException {
        this.depositPreconditions(companyId, amount, userIds);

        BigDecimal totalAmount = amount.multiply(BigDecimal.valueOf(userIds.size()));
        this.companyService.withdrawGiftCash(companyId, totalAmount);

        LocalDate expirationDate = this.depositExpirationService.getGiftExpirationDate(date);

        Set<GiftDeposit> deposits = userIds.stream()
                .map(id -> new GiftDeposit(companyId, id, amount, date, expirationDate))
                .collect(Collectors.toSet());

        deposits = this.depositService.createGiftDeposits(deposits);

        log.info("{} Gift deposits of {} were distributed.", deposits.size(), amount);

        return deposits;
    }

    @Override
    public @NotNull Set<MealDeposit> distributeMealDeposits(@NotNull UUID companyId, @NotNull BigDecimal amount, @NotNull Set<UUID> userIds) throws InsufficientCashException {
        this.depositPreconditions(companyId, amount, userIds);

        BigDecimal totalAmount = amount.multiply(BigDecimal.valueOf(userIds.size()));
        this.companyService.withdrawMealCash(companyId, totalAmount);

        LocalDate date = LocalDate.now();
        LocalDate expirationDate = this.depositExpirationService.getMealExpirationDate(date);

        Set<MealDeposit> deposits = userIds.stream()
                .map(id -> new MealDeposit(companyId, id, amount, date, expirationDate))
                .collect(Collectors.toSet());

        deposits = this.depositService.createMealDeposits(deposits);

        log.info("{} Meal deposits of {} were distributed.", deposits.size(), amount);

        return deposits;
    }

    //

    private void depositPreconditions(@NotNull UUID companyId, @NotNull BigDecimal amount, @NotNull Collection<UUID> userIds) throws IllegalAmountException, ModelNotFoundException, InsufficientCashException {
        this.throwIfAmountIsInvalid(amount);
        this.companyService.getById(companyId);
        this.throwIfAnyUserDoesNotExist(userIds);
    }

    private void throwIfAmountIsInvalid(@NotNull BigDecimal amount) throws IllegalAmountException {
        if (0 <= BigDecimal.ZERO.compareTo(amount)) {
            throw new IllegalAmountException("Deposit amount is lower or equal to 0");
        }
    }

    private void throwIfAnyUserDoesNotExist(@NotNull Collection<UUID> userIds) throws ModelNotFoundException {
        if (!this.userService.existsById(userIds)) {
            throw new ModelNotFoundException("At least one user does not exist");
        }
    }

}
