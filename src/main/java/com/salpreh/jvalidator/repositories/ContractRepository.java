package com.salpreh.jvalidator.repositories;

import com.salpreh.jvalidator.entities.ContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<ContractEntity, Long> {
}
