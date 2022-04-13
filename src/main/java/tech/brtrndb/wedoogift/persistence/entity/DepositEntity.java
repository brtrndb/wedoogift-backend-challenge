package tech.brtrndb.wedoogift.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class DepositEntity extends BaseEntity {

    @NotNull
    @Column(name = "companyId", nullable = false, updatable = false)
    private UUID companyId;

    @NotNull
    @Column(name = "userId", nullable = false, updatable = false)
    private UUID userId;

    @NotNull
    @Column(name = "amount", nullable = false, updatable = false)
    private BigDecimal amount;

    @NotNull
    @Column(name = "depositDate", nullable = false, updatable = false)
    private LocalDate depositDate;

    @Setter(AccessLevel.PROTECTED)
    @NotNull
    @Column(name = "expirationDate", nullable = false, updatable = false)
    private LocalDate expirationDate;

    //

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || this.getClass() != o.getClass()) return false;

        DepositEntity that = (DepositEntity) o;

        return super.equals(that)
                && Objects.equals(this.companyId, that.companyId)
                && Objects.equals(this.userId, that.userId)
                && Objects.equals(this.amount, that.amount)
                && Objects.equals(this.depositDate, that.depositDate)
                && Objects.equals(this.expirationDate, that.expirationDate);
    }

}
