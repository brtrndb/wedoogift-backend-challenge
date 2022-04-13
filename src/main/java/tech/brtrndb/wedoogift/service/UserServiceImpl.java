package tech.brtrndb.wedoogift.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import tech.brtrndb.wedoogift.domain.User;
import tech.brtrndb.wedoogift.error.exception.ModelNotFoundException;
import tech.brtrndb.wedoogift.persistence.entity.UserEntity;
import tech.brtrndb.wedoogift.persistence.repository.GiftDepositRepository;
import tech.brtrndb.wedoogift.persistence.repository.MealDepositRepository;
import tech.brtrndb.wedoogift.persistence.repository.UserRepository;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService, UserGiftBalanceService, UserMealBalanceService {

    private final UserRepository userRepository;
    private final GiftDepositRepository giftDepositRepository;
    private final MealDepositRepository mealDepositRepository;

    //

    public UserServiceImpl(@NonNull UserRepository userRepository, GiftDepositRepository giftDepositRepository, MealDepositRepository mealDepositRepository) {
        this.userRepository = userRepository;
        this.giftDepositRepository = giftDepositRepository;
        this.mealDepositRepository = mealDepositRepository;
    }

    //

    @Override
    public @NotNull User create(@NotNull User user) {
        UserEntity entity = new UserEntity(user.getName(), user.getGiftBalance(), user.getMealBalance());

        User created = Optional.of(entity)
                .map(this.userRepository::saveAndFlush)
                .map(UserServiceImpl::toUser)
                .orElseThrow();

        log.info("User created : {}.", created);

        return created;
    }

    @Override
    public @NotNull User getById(@NotNull UUID userId) throws ModelNotFoundException {
        return this.userRepository.findById(userId)
                .map(UserServiceImpl::toUser)
                .orElseThrow(() -> new ModelNotFoundException("User with id=%s not found".formatted(userId)));
    }

    @Override
    public boolean existsById(@NotNull UUID userId) {
        return this.userRepository.existsById(userId);
    }

    @Override
    public boolean existsById(@NotNull Collection<UUID> userIds) {
        return userIds.stream()
                .allMatch(this::existsById);
    }

    //

    private static @NotNull User toUser(@NotNull UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .name(entity.getName())
                .giftBalance(entity.getGiftBalance())
                .mealBalance(entity.getMealBalance())
                .build();
    }

    //

    @Override
    public @NotNull BigDecimal getUserGiftBalance(@NotNull UUID userId) throws ModelNotFoundException {
        boolean exists = this.existsById(userId);

        if (!exists)
            throw new ModelNotFoundException("User with id=%s not found".formatted(userId));

        return this.giftDepositRepository.getUserBalance(userId);
    }

    @Override
    public @NotNull BigDecimal getUserMealBalance(@NotNull UUID userId) throws ModelNotFoundException {
        boolean exists = this.existsById(userId);

        if (!exists)
            throw new ModelNotFoundException("User with id=%s not found".formatted(userId));

        return this.mealDepositRepository.getUserBalance(userId);
    }

}
