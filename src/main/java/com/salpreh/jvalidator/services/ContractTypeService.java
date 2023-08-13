package com.salpreh.jvalidator.services;

import com.salpreh.jvalidator.dtos.ContractTypeDto;
import com.salpreh.jvalidator.dtos.UpsertContractTypeDto;
import com.salpreh.jvalidator.entities.ContractTypeEntity;
import com.salpreh.jvalidator.repositories.ContractTypeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContractTypeService {
  private final ContractTypeRepository contractTypeRepository;
  private final ModelMapper modelMapper;

  public Page<ContractTypeDto> getAll(Pageable pageable) {
    return contractTypeRepository.findAll(pageable)
        .map(contractTypeEntity -> modelMapper.map(contractTypeEntity, ContractTypeDto.class));
  }

  public Optional<ContractTypeDto> get(long id) {
    return contractTypeRepository.findById(id)
        .map(contractTypeEntity -> modelMapper.map(contractTypeEntity, ContractTypeDto.class));
  }

  public ContractTypeDto create(UpsertContractTypeDto createCommand) {
    ContractTypeEntity createEntity = modelMapper.map(createCommand, ContractTypeEntity.class);
    createEntity = contractTypeRepository.save(createEntity);

    return modelMapper.map(createEntity, ContractTypeDto.class);
  }

  public ContractTypeDto update(long id, UpsertContractTypeDto updateCommand) {
    ContractTypeEntity updateEntity = modelMapper.map(updateCommand, ContractTypeEntity.class);
    updateEntity.setId(id);
    updateEntity = contractTypeRepository.save(updateEntity);

    return modelMapper.map(updateEntity, ContractTypeDto.class);
  }

  public void delete(long id) {
    contractTypeRepository.deleteById(id);
  }
}
