package org.seyran.backoffice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.seyran.backoffice.service.Paging;
import org.seyran.backoffice.service.Sort;

@UtilityClass
public class RequestUtil {

  public static final String PARAM_SORT = "sort";
  public static final String PARAM_PAGE = "page";
  public static final String PARAM_SIZE = "size";

  /**
   * Extract paging params from http request.
   */
  public Paging getPaging(HttpServletRequest request) {
    String size = request.getParameter(PARAM_SIZE);
    String page = request.getParameter(PARAM_PAGE);
    String sortParam = request.getParameter(PARAM_SORT);

    return getPaging(size, page, sortParam);
  }

  /**
   * Initialize Paging objects based on the given search criteria map...
   */
  public Paging getPaging(Map<String, String> searchCriteria) {
    return getPaging(
        searchCriteria.get(PARAM_SIZE),
        searchCriteria.get(PARAM_PAGE),
        searchCriteria.get(PARAM_SORT));
  }

  /**
   * Initialize Paging objects based on given data...
   */
  public Paging getPaging(String size, String page, String sortParam) {
    Paging paging = new Paging();
    if (size != null) {
      try {
        paging.setSize(Integer.valueOf(size));
      } catch (NumberFormatException e) {
        // default page size
      }
    }

    if (page != null) {
      try {
        paging.setPage(Integer.valueOf(page));
      } catch (NumberFormatException e) {
        // default page number
      }
    }

    if (sortParam != null) {
      try {
        paging.setSort(getSortParam(sortParam));
      } catch (Exception e) {
        // empty sort
      }
    }

    return paging;
  }

  /**
   * Example of sort params.
   * Sort params will look like this: sort=key1,key2 (default direction is asc) sort=key1,key2:desc
   * sort=key1:asc,key2:asc
   */
  private static List<Sort> getSortParam(String sortParam) {
    List<Sort> sort = new ArrayList<>();
    String[] sortOptions = sortParam.split(",");
    for (String sortOption : sortOptions) {
      String[] tokens = sortOption.split(":");

      String columnValue = tokens[0];
      Sort.Direction direction =
          tokens.length > 1
              ? "asc".equalsIgnoreCase(tokens[1]) ? Sort.Direction.ASC : Sort.Direction.DESC
              : Sort.Direction.ASC;
      sort.add(new Sort(columnValue, direction));
    }
    return sort;
  }
}
