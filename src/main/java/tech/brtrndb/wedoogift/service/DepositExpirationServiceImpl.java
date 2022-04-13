package tech.brtrndb.wedoogift.service;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DepositExpirationServiceImpl implements DepositExpirationService {

    private static final int GIFT_DEPOSIT_EXPIRATION_DELAY = 365;

    //

    @Override
    public @NotNull LocalDate getGiftExpirationDate(@NotNull LocalDate giftDepositDate) {
        return giftDepositDate.plusDays(GIFT_DEPOSIT_EXPIRATION_DELAY);
    }

    @Override
    public @NotNull LocalDate getMealExpirationDate(@NotNull LocalDate mealDepositDate) {
        return LocalDate.of(mealDepositDate.getYear() + 1, Month.FEBRUARY, 1).with(TemporalAdjusters.lastDayOfMonth());
    }

}
