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
public class User {

    @NonNull
    private UUID id;

    @NonNull
    private String name;

    @NonNull
    private BigDecimal giftBalance;

    @NonNull
    private BigDecimal mealBalance;

    //

    public User(@NonNull String name, @NonNull BigDecimal giftBalance, @NonNull BigDecimal mealBalance) {
        this.name = name;
        this.giftBalance = giftBalance;
        this.mealBalance = mealBalance;
    }

}
