package tech.brtrndb.wedoogift.service;

import java.util.Set;

import org.jetbrains.annotations.NotNull;

import tech.brtrndb.wedoogift.domain.GiftDeposit;
import tech.brtrndb.wedoogift.domain.MealDeposit;

public interface MealDepositService {

    public @NotNull Set<MealDeposit> createMealDeposits(@NotNull Set<MealDeposit> mealDeposits);

}
