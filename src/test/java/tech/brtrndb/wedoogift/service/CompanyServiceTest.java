package tech.brtrndb.wedoogift.service;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import tech.brtrndb.wedoogift.BaseTest;
import tech.brtrndb.wedoogift.WedoogiftIntegrationTest;
import tech.brtrndb.wedoogift.domain.Company;
import tech.brtrndb.wedoogift.error.exception.ModelNotFoundException;

@Slf4j
@WedoogiftIntegrationTest
public class CompanyServiceTest extends BaseTest {

    @Autowired
    private CompanyService service;

    //

    private @NotNull Company create(String name, BigDecimal giftCash, BigDecimal mealCash) {
        Company company = new Company(name, giftCash, mealCash);
        company = this.service.create(company);

        return company;
    }

    //

    @Test
    public void should_get_by_id() {
        // Given:
        Company company = this.create("Company Test", BigDecimal.TEN, BigDecimal.TEN);

        // When:
        Company got = this.service.getById(company.getId());

        // Then:
        Assertions.assertThat(got.getId()).isEqualTo(company.getId());
        Assertions.assertThat(got.getName()).isEqualTo(company.getName());
        Assertions.assertThat(got.getGiftCash()).isEqualTo(company.getGiftCash());
        Assertions.assertThat(got.getMealCash()).isEqualTo(company.getMealCash());
    }

    @Test
    public void should_throw_when_company_not_exist() {
        // Given:
        // When:
        // Then:
        Assertions.assertThatThrownBy(() -> this.service.getById(UUID.randomUUID()))
                .isExactlyInstanceOf(ModelNotFoundException.class);
    }

    @Test
    public void should_have_enough_gift_cash() {
        // Given:
        BigDecimal cash = BigDecimal.TEN;
        Company company = this.create("Company Test", cash, BigDecimal.ZERO);

        // When:
        boolean hasEnoughCash = this.service.hasEnoughGiftCash(company.getId(), cash);

        // Then:
        Assertions.assertThat(hasEnoughCash).isTrue();
    }

    @Test
    public void should_not_have_enough_gift_cash() {
        // Given:
        BigDecimal cash = BigDecimal.TEN;
        Company company = this.create("Company Test", BigDecimal.ZERO, BigDecimal.TEN);

        // When:
        boolean hasEnoughCash = this.service.hasEnoughGiftCash(company.getId(), cash);

        // Then:
        Assertions.assertThat(hasEnoughCash).isFalse();
    }

    @Test
    public void should_have_enough_meal_cash() {
        // Given:
        BigDecimal cash = BigDecimal.TEN;
        Company company = this.create("Company Test", BigDecimal.ZERO, cash);

        // When:
        boolean hasEnoughCash = this.service.hasEnoughMealCash(company.getId(), cash);

        // Then:
        Assertions.assertThat(hasEnoughCash).isTrue();
    }

    @Test
    public void should_not_have_enough_meal_cash() {
        // Given:
        BigDecimal cash = BigDecimal.TEN;
        Company company = this.create("Company Test", BigDecimal.TEN, BigDecimal.ZERO);

        // When:
        boolean hasEnoughCash = this.service.hasEnoughMealCash(company.getId(), cash);

        // Then:
        Assertions.assertThat(hasEnoughCash).isFalse();
    }

}
