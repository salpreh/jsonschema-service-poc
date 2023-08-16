package com.salpreh.jvalidator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.salpreh.jvalidator.config.ModelMapperConfig;
import com.salpreh.jvalidator.dtos.ContractDto;
import com.salpreh.jvalidator.dtos.UpsertContractDto;
import com.salpreh.jvalidator.entities.ContractEntity;
import com.salpreh.jvalidator.entities.ContractTypeEntity;
import java.time.OffsetDateTime;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

public class MappingTest {

  private ModelMapper modelMapper = new ModelMapperConfig().modelMapper();

  @Test
  void givenContractDto_whenMappingToContractEntity_thenCorrect() {
    // given
    UpsertContractDto contractDto = UpsertContractDto.builder()
        .contractTypeId(1L)
        .validFrom(OffsetDateTime.now())
        .data(Map.of("name", "John Doe", "age", 30))
        .build();

    // when
    ContractEntity contractEntity = modelMapper.map(contractDto, ContractEntity.class);

    // then
    assertNotNull(contractEntity.getContractType());
    assertNotNull(contractEntity.getData());
  }

  @Test
  void givenContractEntity_whenMappingToContractDto_thenCorrect() {
    // given
    ContractEntity contractEntity = ContractEntity.builder()
        .id(1L)
        .validFrom(OffsetDateTime.now())
        .data(Map.of("name", "John Doe", "age", 30))
        .contractType(ContractTypeEntity.builder()
            .id(1L)
            .name("somename")
            .schema(Map.of("name", "string", "age", "number"))
            .build()
        )
        .build();

    // when
    ContractDto contractDto = modelMapper.map(contractEntity, ContractDto.class);

    // then
    assertEquals(1L, contractDto.getContractTypeId());
  }
}
