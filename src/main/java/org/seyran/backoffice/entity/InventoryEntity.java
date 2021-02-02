package org.seyran.backoffice.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

@Entity
@Table(name = "inventories")
@Data
public class InventoryEntity {
  public static final int NAME_MAX_LENGTH = 255;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "brand_id")
  private BrandEntity brand;

  @Column(name = "quantity")
  private Long quantity;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "received")
  private Date received;
}
