package org.seyran.backoffice.service;

import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@UtilityClass
public class PagingUtil {

  /**
   * convert @{@link Paging} to  @{@link Pageable}.
   */
  public Pageable getPageable(Paging paging) {
    org.springframework.data.domain.Sort sortParam;
    List<Sort> sortList = paging.getSort();
    if (sortList == null || sortList.isEmpty()) {
      sortParam = org.springframework.data.domain.Sort.unsorted();
    } else {
      List<org.springframework.data.domain.Sort.Order> orders = new ArrayList<>();
      for (Sort sort : sortList) {
        org.springframework.data.domain.Sort.Direction direction =
            sort.getDirection() == Sort.Direction.ASC
                ? org.springframework.data.domain.Sort.Direction.ASC
                : org.springframework.data.domain.Sort.Direction.DESC;
        orders.add(new org.springframework.data.domain.Sort.Order(direction, sort.getName()));
      }
      sortParam = org.springframework.data.domain.Sort.by(orders);
    }
    return PageRequest.of(paging.getPage() - 1, paging.getSize(), sortParam);
  }
}
