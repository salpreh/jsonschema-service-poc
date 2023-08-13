package com.salpreh.jvalidator.dtos;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractTypeDto {
  private Long id;
  private String name;
  private Map<String, Object> schema;
}
