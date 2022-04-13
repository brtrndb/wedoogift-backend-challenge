package tech.brtrndb.wedoogift.config;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import tech.brtrndb.wedoogift.persistence.AuditingDateTimeProvider;

@NoArgsConstructor
@Configuration
@EnableTransactionManagement
@EnableJpaAuditing
public class JpaConfiguration {

    @Bean
    public @NotNull DateTimeProvider dateTimeProvider() {
        return new AuditingDateTimeProvider();
    }

}
