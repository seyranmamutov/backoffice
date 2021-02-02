package org.seyran.backoffice.service.inventory.mapper;

import org.seyran.backoffice.entity.BrandEntity;
import org.seyran.backoffice.entity.InventoryEntity;
import org.seyran.backoffice.repository.BrandRepository;
import org.seyran.backoffice.service.inventory.dto.CreateInventoryInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class InventoryMapperDecorator implements InventoryMapper {
  @Autowired
  @Qualifier("delegate")
  private InventoryMapper delegate;

  @Autowired private BrandRepository brandRepository;

  @Override
  public InventoryEntity toEntity(CreateInventoryInput createInventoryInput) {

    InventoryEntity inventoryEntity = delegate.toEntity(createInventoryInput);
    BrandEntity brandEntity = brandRepository.findFirstByBrandId(createInventoryInput.getBrandId());
    inventoryEntity.setBrand(brandEntity);

    return inventoryEntity;
  }
}
