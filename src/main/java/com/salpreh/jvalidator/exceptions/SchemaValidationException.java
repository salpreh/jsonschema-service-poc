package com.salpreh.jvalidator.exceptions;

import com.networknt.schema.ValidationMessage;
import java.util.Set;

public class SchemaValidationException extends RuntimeException {

  private Set<ValidationMessage> messages;

  public SchemaValidationException(String message) {
    super(message);
  }

  public SchemaValidationException(String message, Throwable cause) {
    super(message, cause);
  }

  public SchemaValidationException(String message, Set<ValidationMessage> messages) {
    this.messages = messages;
  }

  public SchemaValidationException(String message, Set<ValidationMessage> messages, Throwable cause) {
    super(message, cause);
    this.messages = messages;
  }


  @Override
  public String getMessage() {
    StringBuilder messageBuilder = new StringBuilder();

    if (super.getMessage() != null) messageBuilder.append(super.getMessage()).append("\n");
    if (messages != null && !messages.isEmpty()) {
      messageBuilder.append("Validation errors:\n");
      messages.forEach(message -> messageBuilder.append(message.getMessage()).append("\n"));
    }

    return messageBuilder.toString();
  }
}
