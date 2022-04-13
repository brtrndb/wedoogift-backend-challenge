package tech.brtrndb.wedoogift.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

@SuperBuilder
public class MealDeposit extends Deposit {

    public MealDeposit(@NonNull UUID companyId, @NonNull UUID userId, @NonNull BigDecimal amount, @NonNull LocalDate depositDate, @NonNull LocalDate expirationDate) {
        super(companyId, userId, amount, depositDate, expirationDate);
    }

    //

    @Override
    public @NotNull DepositType getType() {
        return DepositType.MEAL;
    }

}
