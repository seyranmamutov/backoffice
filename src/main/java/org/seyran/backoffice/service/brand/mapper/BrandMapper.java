package org.seyran.backoffice.service.brand.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.seyran.backoffice.entity.BrandEntity;
import org.seyran.backoffice.service.brand.dto.BrandDto;
import org.seyran.backoffice.service.brand.dto.CreateBrandInput;
import org.seyran.backoffice.service.brand.dto.ImportBrandInput;
import org.seyran.backoffice.service.brand.dto.UpdateBrandInput;
import org.seyran.backoffice.utils.MapperUtil;

@Mapper(componentModel = MapperUtil.COMPONENT_MODEL_SPRING, uses = BrandResolver.class)
@DecoratedWith(BrandMapperDecorator.class)
public interface BrandMapper {

  BrandEntity toEntity(CreateBrandInput create);

  BrandEntity toEntity(UpdateBrandInput update);

  BrandEntity toEntity(ImportBrandInput importBrand);

  BrandDto toBrandDto(BrandEntity brandEntity);
}
