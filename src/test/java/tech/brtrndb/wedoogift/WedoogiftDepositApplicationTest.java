package tech.brtrndb.wedoogift;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
@WedoogiftIntegrationTest
public class WedoogiftDepositApplicationTest extends BaseTest {

    @Test
    @DisplayName("Check context loading.")
    public void load_context() {
        log.info("Context loaded !");
    }

}
