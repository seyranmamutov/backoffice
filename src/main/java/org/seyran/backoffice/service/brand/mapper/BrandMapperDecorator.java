package org.seyran.backoffice.service.brand.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class BrandMapperDecorator implements BrandMapper {
  @Autowired
  @Qualifier("delegate")
  private BrandMapper delegate;
}
