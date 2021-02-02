package org.seyran.backoffice.service.brand.dto;

import javax.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;
import org.seyran.backoffice.entity.BrandEntity;

@Data
@ToString
public class ImportBrandInput {

  private Long brandId;

  @Size(max = BrandEntity.NAME_MAX_LENGTH)
  private String name;
}
