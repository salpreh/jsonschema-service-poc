package com.salpreh.jvalidator.services;

import com.salpreh.jvalidator.dtos.ContractDto;
import com.salpreh.jvalidator.dtos.UpsertContractDto;
import com.salpreh.jvalidator.entities.ContractEntity;
import com.salpreh.jvalidator.repositories.ContractRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContractService {

  private final ContractRepository contractRepository;
  private final ModelMapper modelMapper;

  public Page<ContractDto> getAll(Pageable pageable) {
    return contractRepository.findAll(pageable)
        .map(contractEntity -> modelMapper.map(contractEntity, ContractDto.class));
  }

  public Optional<ContractDto> get(long id) {
    return contractRepository.findById(id)
        .map(contractEntity -> modelMapper.map(contractEntity, ContractDto.class));
  }

  public ContractDto create(UpsertContractDto createCommand) {
    ContractEntity contractEntity = modelMapper.map(createCommand, ContractEntity.class);
    // TODO: Validate against schema
    contractEntity = contractRepository.save(contractEntity);

    return modelMapper.map(contractEntity, ContractDto.class);
  }

  public ContractDto update(long id, UpsertContractDto updateCommand) {
    ContractEntity updateEntity = modelMapper.map(updateCommand, ContractEntity.class);
    updateEntity.setId(id);
    // TODO: Validate against schema
    updateEntity = contractRepository.save(updateEntity);

    return modelMapper.map(updateEntity, ContractDto.class);
  }

  public void delete(long id) {
    contractRepository.deleteById(id);
  }
}
