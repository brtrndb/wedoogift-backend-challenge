package tech.brtrndb.wedoogift.service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import tech.brtrndb.wedoogift.domain.Company;
import tech.brtrndb.wedoogift.error.exception.InsufficientCashException;
import tech.brtrndb.wedoogift.error.exception.ModelNotFoundException;
import tech.brtrndb.wedoogift.persistence.entity.CompanyEntity;
import tech.brtrndb.wedoogift.persistence.repository.CompanyRepository;

@Slf4j
@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    //

    public CompanyServiceImpl(@NonNull CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    //

    @Override
    public @NotNull Company create(@NotNull Company company) {
        CompanyEntity entity = new CompanyEntity(company.getName(), company.getGiftCash(), company.getMealCash());

        Company created = Optional.of(entity)
                .map(this.companyRepository::saveAndFlush)
                .map(CompanyServiceImpl::toCompany)
                .orElseThrow();

        log.info("Company created : {}.", created);

        return created;
    }

    @Override
    public @NotNull Company getById(@NotNull UUID companyId) throws ModelNotFoundException {
        return this.companyRepository.findById(companyId)
                .map(CompanyServiceImpl::toCompany)
                .orElseThrow(() -> new ModelNotFoundException("Company with id=%s not found".formatted(companyId)));
    }

    @Override
    public @NotNull BigDecimal withdrawGiftCash(@NotNull UUID companyId, @NotNull BigDecimal amount) throws InsufficientCashException {
        CompanyEntity company = this.companyRepository.findById(companyId)
                .orElseThrow(() -> new ModelNotFoundException("Company with id=%s not found".formatted(companyId)));

        if (!this.hasEnoughGiftCash(companyId, amount)) {
            throw new InsufficientCashException("Company %s does not have enough money".formatted(companyId));
        }

        BigDecimal newCash = company.getGiftCash().min(amount);
        company.setGiftCash(newCash);
        this.companyRepository.saveAndFlush(company);

        log.info("Company Gift cash set to {} for company id={}.", newCash, companyId);

        return newCash;
    }

    @Override
    public @NotNull BigDecimal withdrawMealCash(@NotNull UUID companyId, @NotNull BigDecimal amount) throws InsufficientCashException {
        CompanyEntity company = this.companyRepository.findById(companyId)
                .orElseThrow(() -> new ModelNotFoundException("Company with id=%s not found".formatted(companyId)));

        if (!this.hasEnoughMealCash(companyId, amount)) {
            throw new InsufficientCashException("Company %s does not have enough money".formatted(companyId));
        }

        BigDecimal newCash = company.getMealCash().min(amount);
        company.setMealCash(newCash);
        this.companyRepository.saveAndFlush(company);

        log.info("Company Meal cash set to {} for company id={}.", newCash, companyId);

        return newCash;
    }

    @Override
    public boolean hasEnoughGiftCash(@NotNull UUID companyId, @NotNull BigDecimal amount) {
        Company company = this.getById(companyId);

        return 0 <= company.getGiftCash().compareTo(amount);
    }

    @Override
    public boolean hasEnoughMealCash(@NotNull UUID companyId, @NotNull BigDecimal amount) {
        Company company = this.getById(companyId);

        return 0 <= company.getMealCash().compareTo(amount);
    }

    //

    private static Company toCompany(@NotNull CompanyEntity entity) {
        return Company.builder()
                .id(entity.getId())
                .name(entity.getName())
                .giftCash(entity.getGiftCash())
                .mealCash(entity.getMealCash())
                .build();
    }

}
