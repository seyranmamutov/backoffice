package org.seyran.backoffice.service;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Paging {
  public static final int DEFAULT_PAGE = 1;
  public static final int MAX_SIZE = 100;
  public static final int DEFAULT_SIZE = 5;
  private int size = DEFAULT_SIZE;
  private int page = DEFAULT_PAGE;
  private List<Sort> sort;

  
  public void setSize(int size) {
    this.size = Math.min(size, MAX_SIZE);
  }
}
