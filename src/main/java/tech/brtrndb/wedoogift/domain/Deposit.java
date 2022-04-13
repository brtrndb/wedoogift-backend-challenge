package tech.brtrndb.wedoogift.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public abstract class Deposit {

    @NonNull
    private UUID id;

    @NonNull
    private UUID companyId;

    @NonNull
    private UUID userId;

    @NonNull
    private BigDecimal amount;

    @NonNull
    private LocalDate depositDate;

    @NonNull
    private LocalDate expirationDate;

    //

    protected Deposit(@NonNull UUID companyId, @NonNull UUID userId, @NonNull BigDecimal amount, @NonNull LocalDate depositDate, @NonNull LocalDate expirationDate) {
        this.companyId = companyId;
        this.userId = userId;
        this.amount = amount;
        this.depositDate = depositDate;
        this.expirationDate = expirationDate;
    }

    //

    public abstract @NotNull DepositType getType();

}
