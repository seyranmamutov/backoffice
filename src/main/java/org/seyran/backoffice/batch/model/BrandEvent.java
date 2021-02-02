package org.seyran.backoffice.batch.model;

import static org.seyran.backoffice.utils.TextUtil.BOM_SYMBOL;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import org.seyran.backoffice.utils.TextUtil;

@Data
public class BrandEvent {
  private String brandId;
  private String name;

  public static class Mapper {
    private static final HashMap<String, String> columnToFieldNameMap = new HashMap<>();

    static {
      columnToFieldNameMap.put("BRAND_ID", "brandId");
      columnToFieldNameMap.put("Name", "name");
    }

    /**
     *  * Map columns names to fields of @{@link org.seyran.backoffice.batch.model.BrandEvent}.
     *
     * @param headLine - the first line of TVS
     * @return mapped field names in correct order
     */
    public static List<String> getFieldNames(String headLine) {
      String[] tokens = headLine.replace(BOM_SYMBOL, "").split("\\s+");

      return Arrays.stream(tokens)
          .map(TextUtil::trimDoubleQuotes)
          .map(columnToFieldNameMap::get)
          .collect(Collectors.toList());
    }
  }
}
