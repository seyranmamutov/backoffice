package org.seyran.backoffice.repository;

import org.seyran.backoffice.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Long> {
  BrandEntity findFirstByBrandId(Long brandId);

  @Modifying
  @Query(
      "update BrandEntity b  set b.inventorySum = "
          + "(select  sum(i.quantity) from InventoryEntity i where i.brand.id = b.id)")
  int updateInventorySum();
}
