package org.seyran.backoffice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "brands")
@Data
public class BrandEntity {
  public static final int NAME_MIN_LENGTH = 3;
  public static final int NAME_MAX_LENGTH = 255;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** external brand id, used for gathering data from external data sources. */
  @Column(name = "brand_id")
  private Long brandId;

  @Column(name = "name")
  private String name;
  /** Calculated based on inventories table. */
  @Column(name = "inventory_sum")
  private Long inventorySum;
}
