package org.seyran.backoffice.batch.writer;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.seyran.backoffice.service.inventory.InventoryService;
import org.seyran.backoffice.service.inventory.dto.CreateInventoryInput;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InventoryEventWriter implements ItemWriter<CreateInventoryInput> {

  @Autowired private InventoryService inventoryService;

  @Override
  public void write(List<? extends CreateInventoryInput> items) throws Exception {
    items.forEach(
        inventory -> {
          try {
            inventoryService.addInventory(inventory);
          } catch (Exception e) {
            // todo error handling
            log.error("can not import inventory " + inventory);
          }
        });
  }
}
