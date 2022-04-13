package tech.brtrndb.wedoogift.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity(name = "meal_deposit")
@Table(schema = "svc_wedoogift", name = "meal_deposit")
public class MealDepositEntity extends DepositEntity {

    public MealDepositEntity(@NotNull UUID companyId, @NotNull UUID userId, @NotNull BigDecimal amount, @NotNull LocalDate depositDate, @NotNull LocalDate expirationDate) {
        super(companyId, userId, amount, depositDate, expirationDate);
    }

}
