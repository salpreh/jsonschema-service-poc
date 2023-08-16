package com.salpreh.jvalidator.config;

import com.salpreh.jvalidator.dtos.ContractDto;
import com.salpreh.jvalidator.entities.ContractEntity;
import com.salpreh.jvalidator.entities.ContractTypeEntity;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    contractMapping(modelMapper);

    return modelMapper;
  }

  private void contractMapping(ModelMapper mapper) {
    Converter<Long, ContractTypeEntity> idToEntity = ctx -> {
      ContractTypeEntity entity = new ContractTypeEntity();
      entity.setId(ctx.getSource());
      return entity;
    };
    mapper.addConverter(idToEntity);

    TypeMap<ContractEntity, ContractDto> entityToDto = mapper.createTypeMap(ContractEntity.class, ContractDto.class);
    entityToDto.addMapping(e -> e.getContractType().getId(), ContractDto::setContractTypeId);
  }
}
