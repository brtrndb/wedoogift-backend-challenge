package tech.brtrndb.wedoogift.domain;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Company {

    @NonNull
    private UUID id;

    @NonNull
    private String name;

    @NonNull
    private BigDecimal giftCash;

    @NonNull
    private BigDecimal mealCash;

    //

    public Company(@NonNull String name, @NonNull BigDecimal giftCash, @NonNull BigDecimal mealCash) {
        this.name = name;
        this.giftCash = giftCash;
        this.mealCash = mealCash;
    }

}
