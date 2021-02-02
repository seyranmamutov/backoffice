package org.seyran.backoffice.batch.processor;

import lombok.extern.slf4j.Slf4j;
import org.seyran.backoffice.batch.model.BrandEvent;
import org.seyran.backoffice.service.brand.dto.ImportBrandInput;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BrandEventProcessor implements ItemProcessor<BrandEvent, ImportBrandInput> {

  @Override
  public ImportBrandInput process(final BrandEvent event) throws Exception {

    ImportBrandInput importBrandInput = new ImportBrandInput();
    importBrandInput.setName(event.getName());
    importBrandInput.setBrandId(Long.valueOf(event.getBrandId()));

    return importBrandInput;
  }
}
