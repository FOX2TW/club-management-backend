package com.tw.clubmanagement.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "t_club")
public class ClubEntity extends BaseEntity{
    @Column(nullable = false)
    private String name;
    private int type;
    private boolean approveStatus;
    private String introduction;
    private boolean deleteStatus;
}
