package org.seyran.backoffice.service.brand;

import javax.validation.Valid;
import org.seyran.backoffice.entity.BrandEntity;
import org.seyran.backoffice.repository.BrandRepository;
import org.seyran.backoffice.service.Paging;
import org.seyran.backoffice.service.PagingUtil;
import org.seyran.backoffice.service.ResourceNotFoundException;
import org.seyran.backoffice.service.brand.dto.BrandDto;
import org.seyran.backoffice.service.brand.dto.CreateBrandInput;
import org.seyran.backoffice.service.brand.dto.ImportBrandInput;
import org.seyran.backoffice.service.brand.dto.UpdateBrandInput;
import org.seyran.backoffice.service.brand.mapper.BrandMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class BrandService {

  @Autowired private BrandRepository brandRepository;
  @Autowired private BrandMapper mapper;

  @Transactional
  public void create(@Valid CreateBrandInput brand) {
    BrandEntity brandEntity = mapper.toEntity(brand);
    brandRepository.save(brandEntity);
  }

  @Transactional
  public void update(@Valid UpdateBrandInput brand) {
    BrandEntity brandEntity = mapper.toEntity(brand);
    brandRepository.save(brandEntity);
  }

  /**
   * Find brand by id.
   */
  @Transactional
  public BrandDto getById(Long brandId) {
    BrandEntity brandEntity =
        brandRepository.findById(brandId).orElseThrow(ResourceNotFoundException::new);
    return mapper.toBrandDto(brandEntity);
  }

  /**
   * Returns pageable list of brands. 
   */
  @Transactional(readOnly = true)
  public Page<BrandDto> getPage(Paging paging) {
    Pageable pageable = PagingUtil.getPageable(paging);
    Page<BrandEntity> entityPage = brandRepository.findAll(pageable);
    return entityPage.map(brandEntity -> mapper.toBrandDto(brandEntity));
  }

  @Transactional
  public void delete(Long brandId) {
    brandRepository.deleteById(brandId);
  }

  @Transactional
  public void importBrand(ImportBrandInput brand) {
    BrandEntity brandEntity = mapper.toEntity(brand);
    brandRepository.save(brandEntity);
  }

  @Transactional
  public void updateInventorySum() {
    brandRepository.updateInventorySum();
  }
}
