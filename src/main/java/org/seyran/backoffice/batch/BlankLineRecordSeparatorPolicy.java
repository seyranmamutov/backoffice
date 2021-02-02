package org.seyran.backoffice.batch;

import org.springframework.batch.item.file.separator.SimpleRecordSeparatorPolicy;

/** Allow to skip bland lines in TVS files. */
public class BlankLineRecordSeparatorPolicy extends SimpleRecordSeparatorPolicy {

  @Override
  public final boolean isEndOfRecord(final String line) {
    if (line.trim().length() == 0) {
      return false;
    }
    return super.isEndOfRecord(line);
  }

  @Override
  public String postProcess(String record) {
    if (record == null || record.trim().length() == 0) {
      return null;
    }
    return super.postProcess(record);
  }
}
