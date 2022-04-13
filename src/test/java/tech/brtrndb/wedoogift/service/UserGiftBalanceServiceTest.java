package tech.brtrndb.wedoogift.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import tech.brtrndb.wedoogift.BaseTest;
import tech.brtrndb.wedoogift.WedoogiftIntegrationTest;
import tech.brtrndb.wedoogift.domain.Company;
import tech.brtrndb.wedoogift.domain.GiftDeposit;
import tech.brtrndb.wedoogift.domain.User;
import tech.brtrndb.wedoogift.persistence.entity.GiftDepositEntity;
import tech.brtrndb.wedoogift.persistence.repository.GiftDepositRepository;

@Slf4j
@WedoogiftIntegrationTest
public class UserGiftBalanceServiceTest extends BaseTest {

    @Autowired
    private UserGiftBalanceService userGiftBalanceService;

    @Autowired
    private GiftDepositDistributionService giftDepositDistributionService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;

    @Autowired
    private DepositExpirationService depositExpirationService;

    @Autowired
    private GiftDepositService giftDepositService;


    //

    private @NotNull Company createCompanyWithGiftBalance(BigDecimal giftBalance) {
        Company company = new Company("Company Test", giftBalance, BigDecimal.ZERO);
        company = this.companyService.create(company);

        return company;
    }

    private @NotNull User createUser() {
        User user = new User("User Name", BigDecimal.ZERO, BigDecimal.ZERO);
        user = this.userService.create(user);

        return user;
    }

    private @NotNull GiftDeposit createDeposit(@NonNull UUID companyId, @NonNull UUID userId, @NonNull BigDecimal amount, @NonNull LocalDate depositDate, @NonNull LocalDate expirationDate) {
        GiftDeposit deposit = new GiftDeposit(companyId, userId, amount, depositDate, expirationDate);
        Set<GiftDeposit> deposits = this.giftDepositService.createGiftDeposits(Collections.singleton(deposit));

        return deposits.stream()
                .findFirst()
                .orElseThrow();
    }

    //

    @ParameterizedTest
    @ValueSource(ints = {0, 3})
    public void should_get_user_balance(int nbDeposits) {
        // Given:
        BigDecimal amount = BigDecimal.valueOf(100);
        UUID companyId = this.createCompanyWithGiftBalance(BigDecimal.valueOf(1000)).getId();
        UUID userId = this.createUser().getId();
        for (int i = 0; i < nbDeposits; i++)
            this.giftDepositDistributionService.distributeGiftDeposits(companyId, amount, userId);

        // When:
        BigDecimal balance = this.userGiftBalanceService.getUserGiftBalance(userId);

        // Then:
        Assertions.assertThat(balance).isEqualTo(amount.multiply(BigDecimal.valueOf(nbDeposits)));
    }

    @Test
    public void should_get_user_balance_without_expired_deposit() {
        // Given:
        BigDecimal amount = BigDecimal.valueOf(100);
        UUID companyId = this.createCompanyWithGiftBalance(BigDecimal.valueOf(1000)).getId();
        UUID userId = this.createUser().getId();
        LocalDate date = LocalDate.now();
        LocalDate expiredDate = LocalDate.parse("2000-01-01");
        this.createDeposit(companyId, userId, amount, date, this.depositExpirationService.getGiftExpirationDate(date));
        this.createDeposit(companyId, userId, amount, expiredDate, this.depositExpirationService.getGiftExpirationDate(expiredDate));

        // When:
        BigDecimal balance = this.userGiftBalanceService.getUserGiftBalance(userId);

        // Then:
        Assertions.assertThat(balance).isEqualTo(amount);
    }

}
