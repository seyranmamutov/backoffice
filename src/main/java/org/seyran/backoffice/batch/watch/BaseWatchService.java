package org.seyran.backoffice.batch.watch;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.seyran.backoffice.batch.model.BrandEvent;
import org.seyran.backoffice.service.brand.dto.ImportBrandInput;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Watches specified directory for TSV file and runs ETL process as soon as new file appears.
 * 
 * @param <P> Item Processor
 * @param <W> Item Writer
 * @param <L> Job Execution Listener
 */
@Slf4j
@EnableBatchProcessing
public abstract class BaseWatchService<
        P extends ItemProcessor, W extends ItemWriter, L extends JobExecutionListenerSupport>
    implements Runnable {
  public static final int CHUNK_SIZE = 1000;
  public static final String FILENAME_JOB_PARAM = "filename";

  @Autowired public JobBuilderFactory jobBuilderFactory;
  @Autowired public StepBuilderFactory stepBuilderFactory;

  @Autowired private JobLauncher jobLauncher;

  protected abstract String getWatchDirectory();

  protected abstract P getProcessor();

  protected abstract W getItemWriter();

  protected abstract L getListener();

  protected abstract FlatFileItemReader getReader(String fileName);

  protected String getJobName() {
    return "Job";
  }

  protected String getStepName() {
    return getJobName();
  }

  /**
   * Create directories for watching and runs watcher.
   */
  @PostConstruct
  public void init() {
    log.info("Directory to watch TSV is: " + getWatchDirectory());
    // create directories if not exist
    new File(getWatchDirectory()).mkdirs();
    new Thread(this).start();
  }

  @Override
  public void run() {
    try {
      WatchService watcher = FileSystems.getDefault().newWatchService();
      Path dir = Paths.get(getWatchDirectory());
      dir.register(watcher, ENTRY_CREATE);

      while (true) {
        WatchKey key;
        try {
          log.info("Watching for the next file...");
          key = watcher.take();
        } catch (InterruptedException ex) {
          return;
        }

        for (WatchEvent<?> event : key.pollEvents()) {
          WatchEvent<Path> ev = (WatchEvent<Path>) event;
          Path fileName = ev.context();
          String absoluteFilePath = getWatchDirectory() + File.separator + fileName.toString();
          runJob(absoluteFilePath);
        }

        boolean valid = key.reset();
        if (!valid) {
          break;
        }
      }
    } catch (Exception e) {
      throw new RuntimeException("Can not start watch service ", e);
    }
  }

  private void runJob(String fileName) {

    log.info("New file detected: " + fileName);

    try {
      TaskletStep step =
          stepBuilderFactory
              .get(getStepName())
              .<BrandEvent, ImportBrandInput>chunk(CHUNK_SIZE)
              .reader(getReader(fileName))
              .processor(getProcessor())
              .writer(getItemWriter())
              .build();

      Job job =
          jobBuilderFactory
              .get(getJobName())
              .incrementer(new RunIdIncrementer())
              .listener(getListener())
              .flow(step)
              .end()
              .build();

      HashMap<String, JobParameter> params = new HashMap<>();
      params.put(FILENAME_JOB_PARAM, new JobParameter(fileName));

      jobLauncher.run(job, new JobParameters(params));
    } catch (Throwable e) {
      log.error("Can not execute job " + getJobName(), e);
    }
  }
}
