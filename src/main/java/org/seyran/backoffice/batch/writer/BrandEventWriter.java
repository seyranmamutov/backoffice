package org.seyran.backoffice.batch.writer;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.seyran.backoffice.service.brand.BrandService;
import org.seyran.backoffice.service.brand.dto.ImportBrandInput;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BrandEventWriter implements ItemWriter<ImportBrandInput> {

  @Autowired private BrandService brandService;

  @Override
  public void write(List<? extends ImportBrandInput> items) throws Exception {
    items.forEach(
        brand -> {
          try {
            brandService.importBrand(brand);
          } catch (Exception e) {
            // todo error handling
            log.error("can not import brand " + brand);
          }
        });
  }
}
