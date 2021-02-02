package org.seyran.backoffice.service.inventory.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.seyran.backoffice.entity.InventoryEntity;
import org.seyran.backoffice.service.inventory.dto.CreateInventoryInput;
import org.seyran.backoffice.utils.MapperUtil;

@Mapper(componentModel = MapperUtil.COMPONENT_MODEL_SPRING)
@DecoratedWith(InventoryMapperDecorator.class)
public interface InventoryMapper {

  InventoryEntity toEntity(CreateInventoryInput createInventoryInput);
}
