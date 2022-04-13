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
@Entity(name = "gift_deposit")
@Table(schema = "svc_wedoogift", name = "gift_deposit")
public class GiftDepositEntity extends DepositEntity {

    public GiftDepositEntity(@NotNull UUID companyId, @NotNull UUID userId, @NotNull BigDecimal amount, @NotNull LocalDate depositDate, @NotNull LocalDate expirationDate) {
        super(companyId, userId, amount, depositDate, expirationDate);
    }

}
