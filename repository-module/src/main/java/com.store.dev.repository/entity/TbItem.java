package com.store.dev.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "tb_item")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class TbItem implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long itemId;
  private String title;
  private String sellPoint;
  private Integer price;
  private int num;
  private String barcode;
  private String image;
  private Integer cid;
  private Integer status;
  @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
  private Date created;
  @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
  private Date updated;




}
