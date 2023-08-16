package com.salpreh.jvalidator.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonMetaSchema;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaException;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.JsonSchemaVersion;
import com.networknt.schema.NonValidationKeyword;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.SpecVersion.VersionFlag;
import com.networknt.schema.SpecVersionDetector;
import com.networknt.schema.ValidationMessage;
import com.networknt.schema.ValidatorTypeCode;
import com.salpreh.jvalidator.constants.ValidationType;
import com.salpreh.jvalidator.exceptions.SchemaValidationException;
import com.salpreh.jvalidator.validators.MetaValidator;
import com.salpreh.jvalidator.validators.jsonkeywords.MetaValidationsKeyword;
import java.net.URI;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JsonValidatorService {

  private static final String SCHEMA_URI = "https://json-schema.org/draft/2019-09/schema";
  private static final String ID_PROPERTY = "$id";

  private final Map<ValidationType, MetaValidator> validatorsMap;
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

    return getJsonSchemaFactory().getSchema(schemaNode);
  }

  private JsonSchemaFactory getJsonSchemaFactory() {
    JsonMetaSchema jsonMetaSchema = getCustomMetaSchema();

    return JsonSchemaFactory.builder()
        .defaultMetaSchemaURI(jsonMetaSchema.getUri())
        .addMetaSchema(getCustomMetaSchema())
        .build();
  }

  private JsonMetaSchema getCustomMetaSchema() {
    return new JsonMetaSchema.Builder(SCHEMA_URI)
        .idKeyword(ID_PROPERTY)
        .addFormats(JsonSchemaVersion.BUILTIN_FORMATS)
        .addKeywords(ValidatorTypeCode.getNonFormatKeywords(SpecVersion.VersionFlag.V201909))
        // keywords that may validly exist, but have no validation aspect to them
        .addKeywords(Arrays.asList(
            new NonValidationKeyword("$recursiveAnchor"),
            new NonValidationKeyword("$schema"),
            new NonValidationKeyword("$vocabulary"),
            new NonValidationKeyword("$id"),
            new NonValidationKeyword("title"),
            new NonValidationKeyword("description"),
            new NonValidationKeyword("default"),
            new NonValidationKeyword("definitions"),
            new NonValidationKeyword("$comment"),
            new NonValidationKeyword("$defs"),  // newly added in 2019-09 release.
            new NonValidationKeyword("$anchor"),
            new NonValidationKeyword("additionalItems"),
            new NonValidationKeyword("deprecated"),
            new NonValidationKeyword("contentMediaType"),
            new NonValidationKeyword("contentEncoding"),
            new NonValidationKeyword("examples"),
            new NonValidationKeyword("then"),
            new NonValidationKeyword("else")
        ))
        .addKeyword(new MetaValidationsKeyword(validatorsMap))
        .build();
  }
}
