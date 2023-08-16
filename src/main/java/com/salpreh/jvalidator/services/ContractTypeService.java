package com.salpreh.jvalidator.services;

import com.salpreh.jvalidator.dtos.ContractTypeDto;
import com.salpreh.jvalidator.dtos.UpsertContractTypeDto;
import com.salpreh.jvalidator.entities.ContractTypeEntity;
import com.salpreh.jvalidator.exceptions.NotFoundException;
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
  private final JsonValidatorService jsonValidatorService;
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
    jsonValidatorService.validateSchema(createEntity.getSchema());
    createEntity = contractTypeRepository.save(createEntity);

    return modelMapper.map(createEntity, ContractTypeDto.class);
  }

  public ContractTypeDto update(long id, UpsertContractTypeDto updateCommand) {
    if (!contractTypeRepository.existsById(id)) throw new NotFoundException("Contract type not found");

    ContractTypeEntity updateEntity = modelMapper.map(updateCommand, ContractTypeEntity.class);
    updateEntity.setId(id);
    jsonValidatorService.validateSchema(updateEntity.getSchema());
    updateEntity = contractTypeRepository.save(updateEntity);

    return modelMapper.map(updateEntity, ContractTypeDto.class);
  }

  public void delete(long id) {
    contractTypeRepository.deleteById(id);
  }
}
