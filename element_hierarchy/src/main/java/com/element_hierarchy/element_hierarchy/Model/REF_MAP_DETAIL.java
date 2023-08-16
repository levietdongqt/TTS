package com.element_hierarchy.element_hierarchy.Model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "REF_MAP_DETAIL_TEST")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class REF_MAP_DETAIL {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIgnore
  private int ID;
  @Column(name = "map_code")
  private String mapCode;
  @Column(name = "UI_CTRL_CODE")
  private String uiCtrlCode;
  @Column(name = "bo_field_code")
  private String boFieldCode;
  @Column(name = "service_field_code")
  private String serviceFieldCode;
  @Column(name = "rule_field_code")
  private String ruleFieldCode;
  @Column(name = "proc_field_code")
  private String procFieldCode;
  @Column(name = "tmpl_field_code")
  private String tmplFieldCode;
  @Column(name = "description  ")
  private String description;
  @Column(name = "status")
  private String status;
  @Column(name = "created_date")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private Timestamp createdDate;
  @Column(name = "created_user")
  private String createdUser;

  @Override
  public String toString() {
    return String.format(
        "(%d,'%s','%s','%s','%s','%s','%s','%s','%s','%s', TO_DATE('%s', 'YYYY-MM-DD HH24:MI:SS'),'%s')", ID, mapCode,
        uiCtrlCode, boFieldCode, serviceFieldCode, ruleFieldCode, procFieldCode, tmplFieldCode, description, status,
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createdDate), createdUser);
  }
}
