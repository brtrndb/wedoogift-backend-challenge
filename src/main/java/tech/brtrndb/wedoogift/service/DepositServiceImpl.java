package tech.brtrndb.wedoogift.service;

import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import tech.brtrndb.wedoogift.domain.DepositType;
import tech.brtrndb.wedoogift.domain.GiftDeposit;
import tech.brtrndb.wedoogift.domain.MealDeposit;
import tech.brtrndb.wedoogift.persistence.entity.GiftDepositEntity;
import tech.brtrndb.wedoogift.persistence.entity.MealDepositEntity;
import tech.brtrndb.wedoogift.persistence.repository.GiftDepositRepository;
import tech.brtrndb.wedoogift.persistence.repository.MealDepositRepository;

@Slf4j
@Service
@Transactional
public class DepositServiceImpl implements DepositService, GiftDepositService, MealDepositService {

    private final GiftDepositRepository giftDepositRepository;
    private final MealDepositRepository mealDepositRepository;

    //

    public DepositServiceImpl(@NonNull GiftDepositRepository giftDepositRepository, @NonNull MealDepositRepository mealDepositRepository) {
        this.giftDepositRepository = giftDepositRepository;
        this.mealDepositRepository = mealDepositRepository;
    }

    //

    @Override
    public @NotNull Set<GiftDeposit> createGiftDeposits(@NotNull Set<GiftDeposit> giftDeposits) {
        Set<GiftDepositEntity> entities = giftDeposits.stream()
                .map(deposit -> new GiftDepositEntity(deposit.getCompanyId(), deposit.getUserId(), deposit.getAmount(), deposit.getDepositDate(), deposit.getExpirationDate()))
                .collect(Collectors.toSet());

        Set<GiftDeposit> deposits = this.giftDepositRepository.saveAllAndFlush(entities)
                .stream()
                .map(DepositServiceImpl::toGiftDeposit)
                .collect(Collectors.toSet());

        log.info("{} {} deposits created.", deposits.size(), DepositType.GIFT);

        return deposits;
    }

    @Override
    public @NotNull Set<MealDeposit> createMealDeposits(@NotNull Set<MealDeposit> mealDeposits) {
        Set<MealDepositEntity> entities = mealDeposits.stream()
                .map(deposit -> new MealDepositEntity(deposit.getCompanyId(), deposit.getUserId(), deposit.getAmount(), deposit.getDepositDate(), deposit.getExpirationDate()))
                .collect(Collectors.toSet());

        Set<MealDeposit> deposits = this.mealDepositRepository.saveAllAndFlush(entities)
                .stream()
                .map(DepositServiceImpl::toMealDeposit)
                .collect(Collectors.toSet());

        log.info("{} {} deposits created.", deposits.size(), DepositType.MEAL);

        return deposits;
    }

    //

    private static GiftDeposit toGiftDeposit(@NotNull GiftDepositEntity entity) {
        return GiftDeposit.builder()
                .id(entity.getId())
                .companyId(entity.getCompanyId())
                .userId(entity.getUserId())
                .amount(entity.getAmount())
                .depositDate(entity.getDepositDate())
                .expirationDate(entity.getExpirationDate())
                .build();
    }

    private static MealDeposit toMealDeposit(@NotNull MealDepositEntity entity) {
        return MealDeposit.builder()
                .id(entity.getId())
                .companyId(entity.getCompanyId())
                .userId(entity.getUserId())
                .amount(entity.getAmount())
                .depositDate(entity.getDepositDate())
                .expirationDate(entity.getExpirationDate())
                .build();
    }

}
