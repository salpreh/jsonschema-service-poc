package com.salpreh.jvalidator.dtos;

import java.time.OffsetDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpsertContractDto {
  private Long contractTypeId;
  private OffsetDateTime validFrom;
  private Map<String, Object> data;
}
