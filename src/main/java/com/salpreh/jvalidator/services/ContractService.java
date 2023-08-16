package com.salpreh.jvalidator.services;

import com.networknt.schema.ValidationMessage;
import com.salpreh.jvalidator.dtos.ContractDto;
import com.salpreh.jvalidator.dtos.UpsertContractDto;
import com.salpreh.jvalidator.entities.ContractEntity;
import com.salpreh.jvalidator.entities.ContractTypeEntity;
import com.salpreh.jvalidator.exceptions.NotFoundException;
import com.salpreh.jvalidator.exceptions.SchemaValidationException;
import com.salpreh.jvalidator.repositories.ContractRepository;
import com.salpreh.jvalidator.repositories.ContractTypeRepository;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContractService {

  private final ContractRepository contractRepository;
  private final ContractTypeRepository contractTypeRepository;
  private final JsonValidatorService jsonValidatorService;
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
    validateContractData(createCommand);
    ContractEntity contractEntity = modelMapper.map(createCommand, ContractEntity.class);

    contractEntity = contractRepository.save(contractEntity);

    return modelMapper.map(contractEntity, ContractDto.class);
  }

  public ContractDto update(long id, UpsertContractDto updateCommand) {
    validateContractData(updateCommand);

    ContractEntity updateEntity = modelMapper.map(updateCommand, ContractEntity.class);
    updateEntity.setId(id);
    // TODO: Validate against schema
    updateEntity = contractRepository.save(updateEntity);

    return modelMapper.map(updateEntity, ContractDto.class);
  }

  public void delete(long id) {
    contractRepository.deleteById(id);
  }

  private void validateContractData(UpsertContractDto upsertContract) {
    ContractTypeEntity contractType = contractTypeRepository.findById(upsertContract.getContractTypeId())
        .orElseThrow(() -> new NotFoundException("Contract type not found"));

    Set<ValidationMessage> errors =
        jsonValidatorService.validateJsonData(contractType.getSchema(), upsertContract.getData());

    if (!errors.isEmpty())
      throw new SchemaValidationException("Error validating contract data against schema", errors);
  }
}
