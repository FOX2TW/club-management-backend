package com.tw.clubmanagement.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "t_role")
public class Role extends BaseEntity {
  public final static Integer ROLE_ADMIN_ID = 1;
  public final static Integer ROLE_MANGER_ID = 2;
  public final static Integer ROLE_USER_ID = 2;

  @Column(nullable = false)
  private String name;
}
