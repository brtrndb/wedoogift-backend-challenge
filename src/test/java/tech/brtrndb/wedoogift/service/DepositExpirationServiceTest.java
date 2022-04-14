package tech.brtrndb.wedoogift.service;

import java.time.LocalDate;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import tech.brtrndb.wedoogift.BaseTest;
import tech.brtrndb.wedoogift.WedoogiftIntegrationTest;

@Slf4j
@WedoogiftIntegrationTest
public class DepositExpirationServiceTest extends BaseTest {

    @Autowired
    private DepositExpirationService depositExpirationService;

    //

    @ParameterizedTest
    @CsvSource({
            "2021-06-15 , 2022-06-14",
            "2020-01-01 , 2020-12-30",
            "2020-02-29 , 2021-02-27",
            "2021-02-28 , 2022-02-27",
            "2022-01-01 , 2022-12-31",
    })
    public void should_get_gift_expiration_date(String dateStr, String expectedExpirationStr) {
        // Given:
        LocalDate date = LocalDate.parse(dateStr);
        LocalDate expectedExpiration = LocalDate.parse(expectedExpirationStr);

        // When:
        LocalDate expirationDate = this.depositExpirationService.getGiftExpirationDate(date);

        // Then:
        Assertions.assertThat(expirationDate).isEqualTo(expectedExpiration);
    }

    @ParameterizedTest
    @CsvSource({
            "2020-01-01 , 2021-02-28",
            "2020-02-29 , 2021-02-28",
            "2021-12-31 , 2022-02-28",
    })
    public void should_get_meal_expiration_date(String dateStr, String expectedExpirationStr) {
        // Given:
        LocalDate date = LocalDate.parse(dateStr);
        LocalDate expectedExpiration = LocalDate.parse(expectedExpirationStr);

        // When:
        LocalDate expirationDate = this.depositExpirationService.getMealExpirationDate(date);

        // Then:
        Assertions.assertThat(expirationDate).isEqualTo(expectedExpiration);
    }

}
