package org.seyran.backoffice.service.brand.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import org.seyran.backoffice.entity.BrandEntity;

@Data
public class UpdateBrandInput {

  @NotNull private Long id;

  @NotNull
  @Size(min = BrandEntity.NAME_MIN_LENGTH, max = BrandEntity.NAME_MAX_LENGTH)
  private String name;
}
