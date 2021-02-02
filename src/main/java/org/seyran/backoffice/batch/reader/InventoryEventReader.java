package org.seyran.backoffice.batch.reader;

import org.seyran.backoffice.batch.model.InventoryEvent;

public class InventoryEventReader extends PermutableTvsItemReader<InventoryEvent> {

  public InventoryEventReader(String tsvFile) {
    super(tsvFile);
  }

  @Override
  protected Class<? extends InventoryEvent> getType() {
    return InventoryEvent.class;
  }

  @Override
  protected String[] getFieldNames(String tvsHeadLine) {
    return InventoryEvent.Mapper.getFieldNames(tvsHeadLine).toArray(new String[0]);
  }
}
