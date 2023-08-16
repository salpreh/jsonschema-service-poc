package com.salpreh.jvalidator.validators.jsonkeywords;

import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.AbstractJsonValidator;
import com.networknt.schema.AbstractKeyword;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaException;
import com.networknt.schema.JsonValidator;
import com.networknt.schema.ValidationContext;
import com.networknt.schema.ValidationMessage;
import com.salpreh.jvalidator.constants.MetaKeys.Validation;
import com.salpreh.jvalidator.constants.ValidationType;
import com.salpreh.jvalidator.validators.MetaValidator;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MetaValidationsKeyword extends AbstractKeyword {

  private final Map<ValidationType, MetaValidator> validatorsMap;

  public MetaValidationsKeyword(Map<ValidationType, MetaValidator> validatorsMap) {
    super("metaValidations");
    this.validatorsMap = validatorsMap;
  }

  @Override
  public JsonValidator newValidator(
      String schemaPath,
      JsonNode schemaNode,
      JsonSchema parentSchema,
      ValidationContext validationContext
  ) throws JsonSchemaException, Exception {
    return new AbstractJsonValidator() {
      @Override
      public Set<ValidationMessage> validate(JsonNode node, JsonNode rootNode, String at) {
        log.info("MetaValidation:\nconfig:{}\npath:{}\nnode:{}\n", schemaNode, at, node);

        ValidationType validationType;
        try {
          validationType =
              ValidationType.valueOf(schemaNode.get(Validation.VALIDATION_TYPE).asText());
        } catch (IllegalArgumentException e) {
          throw new RuntimeException("Unknown validation type: " + schemaNode.get(Validation.VALIDATION_TYPE).asText());
        }

        if (!validatorsMap.containsKey(validationType))
          throw new RuntimeException("No validator found for validation type: " + validationType);

        return validatorsMap.get(validationType).validate(schemaNode, schemaPath, node, rootNode, at);
      }
    };
  }
}
