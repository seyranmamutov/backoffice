package org.seyran.backoffice.batch.reader;

import java.io.File;
import org.seyran.backoffice.batch.BlankLineRecordSeparatorPolicy;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;

public abstract class PermutableTvsItemReader<O extends Object> extends FlatFileItemReader<O> {

  private String tsvFileAbsolutePath;

  public PermutableTvsItemReader(String tsvFile) {
    this.tsvFileAbsolutePath = tsvFile;
    init();
  }

  protected abstract Class<? extends O> getType();

  protected abstract String[] getFieldNames(String tvsHeadLine);

  /**
   * Item Reader configuration.
   */
  public void init() {

    this.setResource(new FileSystemResource(new File(tsvFileAbsolutePath)));
    this.setLinesToSkip(1);

    this.setRecordSeparatorPolicy(new BlankLineRecordSeparatorPolicy());

    this.setSkippedLinesCallback(
        tvsHeadLine -> {
          // process skipped headline of file and set correct fields order
          DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
          tokenizer.setDelimiter("\t");
          String[] fieldNames = getFieldNames(tvsHeadLine);
          tokenizer.setNames(fieldNames);

          BeanWrapperFieldSetMapper<O> fieldSetMapper = new BeanWrapperFieldSetMapper<O>();
          fieldSetMapper.setTargetType(getType());

          DefaultLineMapper<O> lineMapper = new DefaultLineMapper<O>();

          lineMapper.setLineTokenizer(tokenizer);
          lineMapper.setFieldSetMapper(fieldSetMapper);

          this.setLineMapper(lineMapper);
        });
  }
}
