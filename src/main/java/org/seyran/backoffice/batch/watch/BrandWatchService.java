package org.seyran.backoffice.batch.watch;

import lombok.extern.slf4j.Slf4j;
import org.seyran.backoffice.batch.listener.JobListener;
import org.seyran.backoffice.batch.processor.BrandEventProcessor;
import org.seyran.backoffice.batch.reader.BrandEventReader;
import org.seyran.backoffice.batch.writer.BrandEventWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BrandWatchService
    extends BaseWatchService<BrandEventProcessor, BrandEventWriter, JobListener> {

  public static final String JOB_NAME = "Brands ETL Job";

  @Value("${backoffice.brands-watch-directory}")
  private String brandWatchDirectory;

  @Autowired private JobListener listener;

  @Autowired private BrandEventProcessor brandEventProcessor;

  @Autowired private BrandEventWriter brandEventWriter;

  @Override
  protected String getWatchDirectory() {
    return brandWatchDirectory;
  }

  @Override
  protected BrandEventProcessor getProcessor() {
    return brandEventProcessor;
  }

  @Override
  protected BrandEventWriter getItemWriter() {
    return brandEventWriter;
  }

  @Override
  protected JobListener getListener() {
    return listener;
  }

  @Override
  protected FlatFileItemReader getReader(String fileName) {
    return new BrandEventReader(fileName);
  }

  @Override
  protected String getJobName() {
    return JOB_NAME;
  }
}
