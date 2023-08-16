package com.salpreh.jvalidator.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaException;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion.VersionFlag;
import com.networknt.schema.SpecVersionDetector;
import com.networknt.schema.ValidationMessage;
import com.salpreh.jvalidator.exceptions.SchemaValidationException;
import java.net.URI;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JsonValidatorService {

  private final ObjectMapper objectMapper;

  public Set<ValidationMessage> validateJsonData(Map<String, Object> schema, Map<String, Object> data) {
    JsonSchema jsonSchema;
    try {
      jsonSchema = loadSchema(schema);
    } catch (JsonSchemaException e) {
      return e.getValidationMessages();
    }

    JsonNode jsonData = objectMapper.valueToTree(data);

    return jsonSchema.validate(jsonData);
  }

  public void validateSchema(Map<String, Object> schema) throws SchemaValidationException {
    JsonNode schemaNode = objectMapper.valueToTree(schema);

    try {
      VersionFlag schemaVersion = SpecVersionDetector.detect(schemaNode);
      JsonSchema jsonSchema = JsonSchemaFactory.getInstance(schemaVersion).getSchema(URI.create(schemaVersion.getId()));

      Set<ValidationMessage> errors = jsonSchema.validate(objectMapper.valueToTree(schema));
      if (!errors.isEmpty()) throw new SchemaValidationException("Schema not valid", errors);

    } catch (JsonSchemaException e) {
      throw new SchemaValidationException(e.getMessage(), e.getValidationMessages(), e);
    }
  }

  private JsonSchema loadSchema(Map<String, Object> schema) throws JsonSchemaException {
    JsonNode schemaNode = objectMapper.valueToTree(schema);

    return JsonSchemaFactory.getInstance(SpecVersionDetector.detect(schemaNode)).getSchema(schemaNode);
  }
}
