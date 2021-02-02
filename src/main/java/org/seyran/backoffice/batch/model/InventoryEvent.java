package org.seyran.backoffice.batch.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import org.seyran.backoffice.utils.TextUtil;

@Data
public class InventoryEvent {
  private String brandId;
  private String quantity;
  private String received;

  public static class Mapper {

    private static final HashMap<String, String> columnToFieldNameMap = new HashMap<>();

    static {
      columnToFieldNameMap.put("TIME_RECEIVED", "received");
      columnToFieldNameMap.put("QUANTITY", "quantity");
      columnToFieldNameMap.put("BRAND_ID", "brandId");
    }

    /**
     * Get list of entity fields in correct order based on column names. 
     */
    public static List<String> getFieldNames(String headLine) {
      String[] tokens = headLine.replace(TextUtil.BOM_SYMBOL, "").split("\\s+");

      return Arrays.stream(tokens)
          .map(TextUtil::trimDoubleQuotes)
          .map(columnToFieldNameMap::get)
          .collect(Collectors.toList());
    }
  }
}
