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
@Entity(name = "company")
@Table(schema = "svc_wedoogift", name = "company")
public class CompanyEntity extends BaseEntity {

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "gift_cash", nullable = false)
    private BigDecimal giftCash = BigDecimal.ZERO;

    @NotNull
    @Column(name = "meal_cash", nullable = false)
    private BigDecimal mealCash = BigDecimal.ZERO;

    //

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || this.getClass() != o.getClass())
            return false;

        CompanyEntity that = (CompanyEntity) o;

        return super.equals(that)
                && Objects.equals(this.name, that.name)
                && Objects.equals(this.giftCash, that.giftCash)
                && Objects.equals(this.mealCash, that.mealCash);
    }

}
