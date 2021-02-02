package org.seyran.backoffice.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.seyran.backoffice.service.brand.BrandService;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobListener extends JobExecutionListenerSupport {

  @Autowired private BrandService brandService;

  @Override
  public void afterJob(JobExecution jobExecution) {
    if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.info("updating inventory sum...");
      brandService.updateInventorySum();
    }
  }
}
