package tech.brtrndb.wedoogift.service;

import java.util.Set;

import org.jetbrains.annotations.NotNull;

import tech.brtrndb.wedoogift.domain.GiftDeposit;
import tech.brtrndb.wedoogift.domain.MealDeposit;

public interface GiftDepositService {

    public @NotNull Set<GiftDeposit> createGiftDeposits(@NotNull Set<GiftDeposit> giftDeposits);

}
