package org.seyran.backoffice.controller.brand.dto;

import lombok.Data;

@Data
public class BrandResp {
  private Long id;
  private String name;
  private Long inventorySum;
}
