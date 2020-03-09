package com.tw.clubmanagement.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "t_user_role")
public class UserRole {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(nullable = false)
  private Integer userId;
  @Column(nullable = false)
  private Integer roleId;
}
