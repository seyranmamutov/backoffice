package org.seyran.backoffice.service.inventory;

import javax.validation.Valid;
import org.seyran.backoffice.entity.InventoryEntity;
import org.seyran.backoffice.repository.InventoryRepository;
import org.seyran.backoffice.service.inventory.dto.CreateInventoryInput;
import org.seyran.backoffice.service.inventory.mapper.InventoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class InventoryService {
  @Autowired private InventoryRepository inventoryRepository;

  @Autowired private InventoryMapper inventoryMapper;

  @Transactional
  public void addInventory(@Valid CreateInventoryInput createInventoryInput) {
    InventoryEntity inventoryEntity = inventoryMapper.toEntity(createInventoryInput);
    inventoryRepository.save(inventoryEntity);
  }
}
