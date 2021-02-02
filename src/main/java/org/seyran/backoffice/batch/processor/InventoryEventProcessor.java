package org.seyran.backoffice.batch.processor;

import java.text.SimpleDateFormat;
import lombok.extern.slf4j.Slf4j;
import org.seyran.backoffice.batch.model.InventoryEvent;
import org.seyran.backoffice.service.inventory.dto.CreateInventoryInput;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InventoryEventProcessor
    implements ItemProcessor<InventoryEvent, CreateInventoryInput> {
  private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

  @Override
  public CreateInventoryInput process(final InventoryEvent event) throws Exception {

    CreateInventoryInput createInventoryInput = new CreateInventoryInput();
    createInventoryInput.setBrandId(Long.valueOf(event.getBrandId()));
    createInventoryInput.setQuantity(Long.valueOf(event.getQuantity()));
    createInventoryInput.setReceived(formatter.parse(event.getReceived()));

    return createInventoryInput;
  }
}
