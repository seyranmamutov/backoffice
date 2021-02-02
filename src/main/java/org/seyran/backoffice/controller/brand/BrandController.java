package org.seyran.backoffice.controller.brand;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import org.seyran.backoffice.controller.RequestUtil;
import org.seyran.backoffice.controller.brand.dto.BrandResp;
import org.seyran.backoffice.controller.brand.dto.CreateBrandReq;
import org.seyran.backoffice.controller.brand.dto.UpdateBrandReq;
import org.seyran.backoffice.doc.ApiAcceptHeader;
import org.seyran.backoffice.doc.ApiImplicitParamsForPageable;
import org.seyran.backoffice.service.Paging;
import org.seyran.backoffice.service.brand.BrandService;
import org.seyran.backoffice.service.brand.dto.BrandDto;
import org.seyran.backoffice.service.brand.dto.CreateBrandInput;
import org.seyran.backoffice.service.brand.dto.UpdateBrandInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "Brand API", tags = {"Brand"})
@RestController
@RequestMapping(value = "/brands")
public class BrandController {

  @Autowired
  private BrandService brandService;

  @Autowired
  private BrandRestMapper restMapper;

  /**
   * Endpoint to get pageable list of brands.
   */
  @ApiAcceptHeader
  @ApiImplicitParamsForPageable
  @ApiOperation("Pageable list of brands")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Page<BrandResp> page(HttpServletRequest servletRequest) {
    Paging paging = RequestUtil.getPaging(servletRequest);
    Page<BrandDto> dtoPage = brandService.getPage(paging);

    return dtoPage.map(brandDto -> restMapper.toResponse(brandDto));
  }


  @ApiOperation("Create a new brand")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void add(@RequestBody CreateBrandReq request) {
    CreateBrandInput createBrandInput = restMapper.toUpdateBrandInput(request);
    brandService.create(createBrandInput);
  }

  /**
   * Endpoint to update brands.
   */

  @ApiOperation("Update brand")
  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void update(@PathVariable("id") Long id, @RequestBody UpdateBrandReq request) {

    UpdateBrandInput updateBrandInput = restMapper.toUpdateBrandInput(request);
    updateBrandInput.setId(id);
    brandService.update(updateBrandInput);
  }

  @ApiOperation("Delete brand")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") Long id) {
    brandService.delete(id);
  }

  @ApiOperation("Get brand by id")
  @ApiAcceptHeader
  @GetMapping("/{id}")
  public BrandResp getById(@PathVariable("id") Long id) {
    BrandDto brandDto = brandService.getById(id);
    return restMapper.toResponse(brandDto);
  }
}
