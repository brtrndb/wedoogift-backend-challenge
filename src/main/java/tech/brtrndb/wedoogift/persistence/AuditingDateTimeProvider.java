package tech.brtrndb.wedoogift.persistence;

import java.time.Instant;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.auditing.DateTimeProvider;

public class AuditingDateTimeProvider implements DateTimeProvider {

    @Override
    public @NotNull Optional<TemporalAccessor> getNow() {
        return Optional.of(Instant.now());
    }

}
