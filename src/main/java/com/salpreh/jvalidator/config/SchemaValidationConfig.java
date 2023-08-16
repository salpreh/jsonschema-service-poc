package com.salpreh.jvalidator.config;

import com.salpreh.jvalidator.constants.EntityType;
import com.salpreh.jvalidator.constants.ValidationType;
import com.salpreh.jvalidator.repositories.PartnerRepository;
import com.salpreh.jvalidator.repositories.SalesmanRepository;
import com.salpreh.jvalidator.validators.MetaValidator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

@Configuration
@RequiredArgsConstructor
public class SchemaValidationConfig {

  public final PartnerRepository partnerRepository;
  public final SalesmanRepository salesmanRepository;

  @Bean
  public Map<EntityType, JpaRepository<?, Long>> entityRepositoriesMap() {
    Map<EntityType, JpaRepository<?, Long>> repositoriesMap = new EnumMap<>(EntityType.class);

    repositoriesMap.put(EntityType.SALES_AGENT, salesmanRepository);
    repositoriesMap.put(EntityType.PARTNER, partnerRepository);

    return repositoriesMap;
  }

  @Bean
  public Map<ValidationType, MetaValidator> validatorsMap(List<MetaValidator> validators) {
    return validators.stream()
        .collect(Collectors.toMap(MetaValidator::getValidationType, Function.identity()));
  }
}
