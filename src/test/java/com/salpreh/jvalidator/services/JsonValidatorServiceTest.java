package com.salpreh.jvalidator.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import com.networknt.schema.ValidationMessage;
import com.salpreh.jvalidator.helpers.DataLoader;
import com.salpreh.jvalidator.repositories.PartnerRepository;
import com.salpreh.jvalidator.repositories.SalesmanRepository;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class JsonValidatorServiceTest {

  @Autowired
  private JsonValidatorService jsonValidatorService;

  @MockBean
  private SalesmanRepository salesmanRepository;

  @MockBean
  private PartnerRepository partnerRepository;

  @Test
  void givenDataWithCustomValidations_whenValidateAndDoNotPassValidation_thenReturnErrors() {
    // given
    Map<String, Object> schema = DataLoader.loadData(DataLoader.SCHEMA_1);
    Map<String, Object> data = DataLoader.loadData(DataLoader.DATA_1_WITH_CUSTOM);

    // when
    Set<ValidationMessage> messages = jsonValidatorService.validateJsonData(schema, data);

    // then
    assertFalse(messages.isEmpty());
    assertEquals(2, messages.size());
  }

  @Test
  void givenDataWithCustomValidations_whenValidateAndValid_thenReturnNoErrors() {
    // given
    Map<String, Object> schema = DataLoader.loadData(DataLoader.SCHEMA_1);
    Map<String, Object> data = DataLoader.loadData(DataLoader.DATA_1_WITH_CUSTOM);

    given(salesmanRepository.existsById(1L)).willReturn(true);
    given(partnerRepository.existsById(1L)).willReturn(true);

    // when
    Set<ValidationMessage> messages = jsonValidatorService.validateJsonData(schema, data);

    // then
    assertTrue(messages.isEmpty());
  }
}