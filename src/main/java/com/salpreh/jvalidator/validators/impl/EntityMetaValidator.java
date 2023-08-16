package com.salpreh.jvalidator.validators.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.networknt.schema.CustomErrorMessageType;
import com.networknt.schema.ValidationMessage;
import com.salpreh.jvalidator.constants.EntityType;
import com.salpreh.jvalidator.constants.MetaKeys.Validation;
import com.salpreh.jvalidator.constants.ValidationType;
import com.salpreh.jvalidator.validators.MetaValidator;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EntityMetaValidator implements MetaValidator {

  private final Map<EntityType, JpaRepository<?, Long>> entityRepositoriesMap;

  @Override
  public Set<ValidationMessage> validate(
      JsonNode schemaNode,
      String schemaPath, JsonNode dataNode,
      JsonNode rootNode,
      String at
  ) {
    if (!isValidType(schemaNode)) throw new RuntimeException(String.format(
        "Unexpected type for validation. Expected %s, got %s",
        getValidationType(),
        schemaNode.get("validationType").textValue()
    ));

    ObjectNode metadata = (ObjectNode) schemaNode.get(Validation.VALIDATION_METADATA);
    EntityType entityType;
    try {
      entityType = EntityType.valueOf(metadata.get(Validation.ENTITY_TYPE).textValue());
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Unknown entity type: " + metadata.get(Validation.ENTITY_TYPE).textValue());
    }

    if (!entityRepositoriesMap.containsKey(entityType))
      throw new RuntimeException("No repository found for entity type: " + entityType);

    long entityId = dataNode.asLong();
    JpaRepository<?, Long> repository = entityRepositoriesMap.get(entityType);
    Set<ValidationMessage> errors = new HashSet<>();
    if (!repository.existsById(entityId)) errors.add(buildErrorMessage(schemaPath, at, entityId));

    return errors;
  }

  @Override
  public ValidationType getValidationType() {
    return ValidationType.ENTITY_ID;
  }

  protected ValidationMessage buildErrorMessage(String schemaPath, String dataPath, Long entityId) {
    return ValidationMessage.of(
        getValidationType().name(),
        CustomErrorMessageType.of("custom.entityId"),
        new MessageFormat("{0}: Entity with id {1} not found"),
        dataPath,
        schemaPath,
        String.valueOf(entityId)
    );
  }
}
