package com.element_hierarchy.element_hierarchy.Model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ADM_REF_UI")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ADM_REF_BO {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int ID;
  @Column(name = "bo_field_code")
  private String boFieldCode;
  @Column(name = "bo_code ")
  private String boCode;
  @Column(name = "process_code ")
  private String processCode;
  @Column(name = "bo_field_dir ")
  private String boFieldDir;
  @Column(name = "bo_field_name ")
  private String boFieldName;
  @Column(name = "data_type ")
  private String dataType;
  @Column(name = "container ")
  private String container;
  @Column(name = "description ")
  private String description;
  @Column(name = "status ")
  private String status;
  @Column(name = "created_date")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private Timestamp createdDate;
  @Column(name = "created_user")
  private String createdUser;
  @Transient
  private String uiCtrlCode;
  @JsonIgnore
  @Transient
  private int level = 1;

  @Override
  public int hashCode() {
    if(this.boFieldDir==null){
       throw new RuntimeException("Lỗi,boFildDir null, Xem lại file Excel");
    }
    int indexDot = this.boFieldDir.lastIndexOf(".");
    if (indexDot == -1) {
      return boFieldDir.hashCode();
    }
    int firstDot = boFieldDir.indexOf(".");
    StringBuilder sub = new StringBuilder(boFieldDir);
    while (indexDot > 0) {
      if (indexDot == firstDot) {
        break;
      }
      sub.setLength(indexDot);
      indexDot = sub.lastIndexOf(".");
    }
    return sub.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    ADM_REF_BO other = (ADM_REF_BO) obj;
    String thisBoFieldDir = this.getBoFieldDir();
    String otherBoFieldDir = other.getBoFieldDir();
    if (thisBoFieldDir.equalsIgnoreCase(otherBoFieldDir)
        && thisBoFieldDir.indexOf("..") < 0
        && thisBoFieldDir.lastIndexOf(".") != thisBoFieldDir.length() - 1) {
      return true;
    }
    ;
    return false;
  }
}
