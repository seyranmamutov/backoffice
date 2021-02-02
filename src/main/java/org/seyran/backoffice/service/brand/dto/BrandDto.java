package org.seyran.backoffice.service.brand.dto;

import lombok.Data;

@Data
public class BrandDto {
  private Long id;
  private String name;
  private Long inventorySum;
}
