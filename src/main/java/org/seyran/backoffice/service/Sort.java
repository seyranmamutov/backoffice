package org.seyran.backoffice.service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Sort {
  private String name;
  private Direction direction;

  public enum Direction {
    ASC,
    DESC
  }
}
