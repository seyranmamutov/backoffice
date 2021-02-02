package org.seyran.backoffice.batch.reader;

import org.seyran.backoffice.batch.model.BrandEvent;

public class BrandEventReader extends PermutableTvsItemReader<BrandEvent> {

  public BrandEventReader(String tsvFile) {
    super(tsvFile);
  }

  @Override
  protected Class<? extends BrandEvent> getType() {
    return BrandEvent.class;
  }

  @Override
  protected String[] getFieldNames(String tvsHeadLine) {
    return BrandEvent.Mapper.getFieldNames(tvsHeadLine).toArray(new String[0]);
  }
}
