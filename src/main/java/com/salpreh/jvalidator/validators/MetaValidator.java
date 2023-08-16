package com.salpreh.jvalidator.validators;

import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.ValidationMessage;
import com.salpreh.jvalidator.constants.ValidationType;
import java.util.Set;

public interface MetaValidator {

  Set<ValidationMessage> validate(JsonNode schemaNode,
      String schemaPath,
      JsonNode dataNode,
      JsonNode rootNode,
      String at
  );
  ValidationType getValidationType();

  default boolean isValidType(JsonNode schemaNode) {
    return schemaNode.get("validationType").textValue().equals(getValidationType().name());
  }
}
