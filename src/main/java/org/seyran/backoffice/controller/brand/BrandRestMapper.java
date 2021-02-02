package org.seyran.backoffice.controller.brand;

import org.mapstruct.Mapper;
import org.seyran.backoffice.controller.brand.dto.BrandResp;
import org.seyran.backoffice.controller.brand.dto.CreateBrandReq;
import org.seyran.backoffice.controller.brand.dto.UpdateBrandReq;
import org.seyran.backoffice.service.brand.dto.BrandDto;
import org.seyran.backoffice.service.brand.dto.CreateBrandInput;
import org.seyran.backoffice.service.brand.dto.UpdateBrandInput;
import org.seyran.backoffice.utils.MapperUtil;

@Mapper(componentModel = MapperUtil.COMPONENT_MODEL_SPRING)
public interface BrandRestMapper {

  BrandResp toResponse(BrandDto brandDto);

  CreateBrandInput toUpdateBrandInput(CreateBrandReq req);

  UpdateBrandInput toUpdateBrandInput(UpdateBrandReq req);
}
