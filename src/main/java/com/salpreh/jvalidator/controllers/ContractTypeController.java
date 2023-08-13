package com.salpreh.jvalidator.controllers;

import com.salpreh.jvalidator.dtos.ContractTypeDto;
import com.salpreh.jvalidator.dtos.UpsertContractTypeDto;
import com.salpreh.jvalidator.services.ContractTypeService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contract-types")
@RequiredArgsConstructor
public class ContractTypeController {

  private final ContractTypeService contractTypeService;

  @GetMapping
  public Page<ContractTypeDto> getContractTypes(Pageable pageable) {
    return contractTypeService.getAll(pageable);
  }

  @GetMapping("{id}")
  public Optional<ContractTypeDto> getContractType(@PathVariable long id) {
    return contractTypeService.get(id);
  }

  @PostMapping
  public ContractTypeDto createContractType(@RequestBody UpsertContractTypeDto createCommand) {
    return contractTypeService.create(createCommand);
  }

  @PutMapping("{id}")
  public ContractTypeDto updateContractType(@PathVariable long id, @RequestBody UpsertContractTypeDto updateCommand) {
    return contractTypeService.update(id, updateCommand);
  }

  @DeleteMapping("{id}")
  public void deleteContractType(@PathVariable long id) {
    contractTypeService.delete(id);
  }
}
