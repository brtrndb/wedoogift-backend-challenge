package tech.brtrndb.wedoogift.persistence.repository;

import java.math.BigDecimal;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import tech.brtrndb.wedoogift.persistence.entity.DepositEntity;

@NoRepositoryBean
public interface DepositRepository<E extends DepositEntity> extends JpaRepository<E, UUID> {

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM #{#entityName} e WHERE e.userId = :userId AND CURRENT_DATE < e.expirationDate")
    public BigDecimal getUserBalance(@NotNull @Param("userId") UUID userId);

}
