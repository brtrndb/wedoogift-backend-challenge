package tech.brtrndb.wedoogift.persistence.entity;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
@Table(schema = "svc_wedoogift", name = "user")
public class UserEntity extends BaseEntity {

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "gift_balance", nullable = false)
    private BigDecimal giftBalance = BigDecimal.ZERO;

    @NotNull
    @Column(name = "meal_balance", nullable = false)
    private BigDecimal mealBalance = BigDecimal.ZERO;

    //

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || this.getClass() != o.getClass())
            return false;

        UserEntity that = (UserEntity) o;

        return super.equals(that)
                && Objects.equals(this.name, that.name)
                && Objects.equals(this.giftBalance, that.giftBalance)
                && Objects.equals(this.mealBalance, that.mealBalance);
    }

}
