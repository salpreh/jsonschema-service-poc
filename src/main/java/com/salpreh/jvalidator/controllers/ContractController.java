package com.salpreh.jvalidator.controllers;

import com.salpreh.jvalidator.dtos.ContractDto;
import com.salpreh.jvalidator.dtos.UpsertContractDto;
import com.salpreh.jvalidator.services.ContractService;
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
@RequestMapping("contracts")
@RequiredArgsConstructor
public class ContractController {

  private final ContractService contractService;

  @GetMapping
  public Page<ContractDto> getContractTypes(Pageable pageable) {
    return contractService.getAll(pageable);
  }

  @GetMapping("{id}")
  public Optional<ContractDto> getContractType(@PathVariable long id) {
    return contractService.get(id);
  }

  @PostMapping
  public ContractDto createContractType(@RequestBody UpsertContractDto createCommand) {
    return contractService.create(createCommand);
  }

  @PutMapping("{id}")
  public ContractDto updateContractType(@PathVariable long id, @RequestBody UpsertContractDto updateCommand) {
    return contractService.update(id, updateCommand);
  }

  @DeleteMapping("{id}")
  public void deleteContractType(@PathVariable long id) {
    contractService.delete(id);
  }
}
