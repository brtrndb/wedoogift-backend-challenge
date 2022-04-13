package tech.brtrndb.wedoogift.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import tech.brtrndb.wedoogift.BaseTest;
import tech.brtrndb.wedoogift.WedoogiftIntegrationTest;
import tech.brtrndb.wedoogift.domain.Company;
import tech.brtrndb.wedoogift.domain.GiftDeposit;
import tech.brtrndb.wedoogift.domain.User;
import tech.brtrndb.wedoogift.error.exception.IllegalAmountException;
import tech.brtrndb.wedoogift.error.exception.InsufficientCashException;
import tech.brtrndb.wedoogift.error.exception.ModelNotFoundException;
import tech.brtrndb.wedoogift.persistence.entity.CompanyEntity;
import tech.brtrndb.wedoogift.persistence.entity.UserEntity;

@Slf4j
@WedoogiftIntegrationTest
public class GiftDepositDistributionServiceTest extends BaseTest {

    @Autowired
    private GiftDepositDistributionService giftDepositDistributionService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;

    //

    private @NotNull Company createCompanyWithGiftBalance(BigDecimal giftBalance) {
        Company company = new Company("Company Test", giftBalance, BigDecimal.ZERO);
        company = this.companyService.create(company);

        return company;
    }

    private @NotNull List<User> createUsers(int nb) {
        List<User> users = IntStream.range(0, nb)
                .mapToObj(i -> new User("Name " + i, BigDecimal.ZERO, BigDecimal.ZERO))
                .map(this.userService::create)
                .toList();

        return users;
    }

    //

    @Test
    public void should_create_deposits() {
        // Given:
        BigDecimal initialCash = BigDecimal.valueOf(100);
        BigDecimal giftAmount = BigDecimal.TEN;
        int nbUsers = 5;
        UUID companyId = this.createCompanyWithGiftBalance(initialCash).getId();
        Set<UUID> userIds = this.createUsers(nbUsers)
                .stream()
                .map(User::getId)
                .collect(Collectors.toSet());
        BigDecimal expectedFinalCash = initialCash.min(giftAmount.multiply(BigDecimal.valueOf(nbUsers)));

        // When:
        Set<GiftDeposit> created = this.giftDepositDistributionService.distributeGiftDeposits(companyId, giftAmount, userIds);
        Company company = this.companyService.getById(companyId);

        // Then:
        Assertions.assertThat(created)
                .isNotNull()
                .isNotEmpty()
                .hasSameSizeAs(userIds)
                .allSatisfy(deposit -> {
                    Assertions.assertThat(deposit.getCompanyId()).isEqualTo(companyId);
                    Assertions.assertThat(deposit.getUserId()).isIn(userIds);
                    Assertions.assertThat(deposit.getAmount()).isEqualTo(giftAmount);
                });
        Assertions.assertThat(company.getGiftCash()).isEqualTo(expectedFinalCash);
    }

    @Test
    public void should_throw_given_company_not_enough_cash() {
        // Given:
        BigDecimal initialCash = BigDecimal.valueOf(1);
        BigDecimal giftAmount = BigDecimal.TEN;
        int nbUsers = 5;
        UUID companyId = this.createCompanyWithGiftBalance(initialCash).getId();
        Set<UUID> userIds = this.createUsers(nbUsers)
                .stream()
                .map(User::getId)
                .collect(Collectors.toSet());

        // When:
        // Then:
        Assertions.assertThatThrownBy(() -> this.giftDepositDistributionService.distributeGiftDeposits(companyId, giftAmount, userIds))
                .isExactlyInstanceOf(InsufficientCashException.class);
    }

    @Test
    public void should_throw_given_invalid_deposit_amount() {
        // Given:
        BigDecimal initialCash = BigDecimal.valueOf(100);
        BigDecimal giftAmount = BigDecimal.TEN.negate();
        int nbUsers = 5;
        UUID companyId = this.createCompanyWithGiftBalance(initialCash).getId();
        Set<UUID> userIds = this.createUsers(nbUsers)
                .stream()
                .map(User::getId)
                .collect(Collectors.toSet());

        // When:
        // Then:
        Assertions.assertThatThrownBy(() -> this.giftDepositDistributionService.distributeGiftDeposits(companyId, giftAmount, userIds))
                .isExactlyInstanceOf(IllegalAmountException.class);
    }

    @Test
    public void should_throw_given_non_existent_company() {
        // Given:
        BigDecimal giftAmount = BigDecimal.TEN;
        int nbUsers = 5;
        UUID companyId = UUID.randomUUID();
        Set<UUID> userIds = this.createUsers(nbUsers)
                .stream()
                .map(User::getId)
                .collect(Collectors.toSet());

        // When:
        // Then:
        Assertions.assertThatThrownBy(() -> this.giftDepositDistributionService.distributeGiftDeposits(companyId, giftAmount, userIds))
                .isExactlyInstanceOf(ModelNotFoundException.class);
    }

    @Test
    public void should_throw_given_non_existent_user() {
        // Given:
        BigDecimal initialCash = BigDecimal.valueOf(100);
        BigDecimal giftAmount = BigDecimal.TEN;
        int nbUsers = 5;
        UUID companyId = this.createCompanyWithGiftBalance(initialCash).getId();
        Set<UUID> userIds = Stream.generate(UUID::randomUUID)
                .limit(nbUsers)
                .collect(Collectors.toSet());

        // When:
        // Then:
        Assertions.assertThatThrownBy(() -> this.giftDepositDistributionService.distributeGiftDeposits(companyId, giftAmount, userIds))
                .isExactlyInstanceOf(ModelNotFoundException.class);
    }

}
