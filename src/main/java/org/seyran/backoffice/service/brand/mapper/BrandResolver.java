package org.seyran.backoffice.service.brand.mapper;

import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import org.seyran.backoffice.entity.BrandEntity;
import org.seyran.backoffice.repository.BrandRepository;
import org.seyran.backoffice.service.ResourceNotFoundException;
import org.seyran.backoffice.service.brand.dto.ImportBrandInput;
import org.seyran.backoffice.service.brand.dto.UpdateBrandInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BrandResolver {

  @Autowired private BrandRepository brandRepository;

  /**
   * Load entity object from database if it exist to avoid duplicates.
   */
  @ObjectFactory
  public BrandEntity resolve(
      ImportBrandInput importBrandInput, @TargetType Class<BrandEntity> type) {
    BrandEntity brandEntity = brandRepository.findFirstByBrandId(importBrandInput.getBrandId());
    if (brandEntity == null) {
      brandEntity = new BrandEntity();
    }
    return brandEntity;
  }

  /**
   * When brand is updated entity always have to be loaded from database.
   */
  @ObjectFactory
  public BrandEntity resolve(
      UpdateBrandInput updateBrandInput, @TargetType Class<BrandEntity> type) {
    return brandRepository
        .findById(updateBrandInput.getId())
        .orElseThrow(ResourceNotFoundException::new);
  }
}
