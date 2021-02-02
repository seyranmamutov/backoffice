package org.seyran.backoffice.service.inventory.dto;

import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CreateInventoryInput {
  @NotNull private Long brandId;
  @NotNull private Date received;
  @NotNull private Long quantity;
}
