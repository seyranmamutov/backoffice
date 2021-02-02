package org.seyran.backoffice.controller.exception;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.validation.FieldError;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetail {

  private EntityErrorMsgKey key;
  private String message;

  private List<FormControlError> form;

  public ErrorDetail(EntityErrorMsgKey code, String message) {
    this.key = code;
    this.message = message;
  }

  public ErrorDetail(String key, String value) {
    form = Arrays.asList(FormControlError.of(key, value));
  }

  @Getter
  @NoArgsConstructor
  public static class FormControlError {
    private String field;
    private String message;

    public FormControlError(String field, String message) {
      this.field = field;
      this.message = message;
    }

    public static FormControlError of(FieldError error) {
      return new FormControlError(error.getField(), error.getDefaultMessage());
    }

    public static FormControlError of(String field, String message) {
      return new FormControlError(field, message);
    }

    /**
     * Creates form errors based on @{@link ConstraintViolation}.
     */
    public static FormControlError of(ConstraintViolation<?> constraintViolation) {
      Path propertyPath = constraintViolation.getPropertyPath();

      List<Path.Node> nodes = IterableUtils.toList(propertyPath);
      nodes.remove(0);
      if (nodes.size() > 1) {
        nodes.remove(0);
      }
      return new FormControlError(
          nodes.stream().map(String::valueOf).collect(Collectors.joining(".")),
          constraintViolation.getMessage());
    }
  }
}
