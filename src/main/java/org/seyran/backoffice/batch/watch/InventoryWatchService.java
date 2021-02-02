package org.seyran.backoffice.batch.watch;

import lombok.extern.slf4j.Slf4j;
import org.seyran.backoffice.batch.listener.JobListener;
import org.seyran.backoffice.batch.processor.InventoryEventProcessor;
import org.seyran.backoffice.batch.reader.InventoryEventReader;
import org.seyran.backoffice.batch.writer.InventoryEventWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InventoryWatchService
    extends BaseWatchService<InventoryEventProcessor, InventoryEventWriter, JobListener> {

  @Value("${backoffice.inventories-watch-directory}")
  private String inventoryWatchDirectory;

  @Autowired private JobListener listener;

  @Autowired private InventoryEventProcessor inventoryEventProcessor;

  @Autowired private InventoryEventWriter inventoryEventWriter;

  @Override
  protected String getWatchDirectory() {
    return inventoryWatchDirectory;
  }

  @Override
  protected InventoryEventProcessor getProcessor() {
    return inventoryEventProcessor;
  }

  @Override
  protected InventoryEventWriter getItemWriter() {
    return inventoryEventWriter;
  }

  @Override
  protected JobListener getListener() {
    return listener;
  }

  @Override
  protected FlatFileItemReader getReader(String fileName) {
    return new InventoryEventReader(fileName);
  }

  @Override
  protected String getJobName() {
    return "Inventory ETL Job";
  }
}
