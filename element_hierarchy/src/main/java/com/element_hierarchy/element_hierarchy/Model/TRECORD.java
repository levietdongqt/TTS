package com.element_hierarchy.element_hierarchy.Model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TRECORD implements Serializable{
  private int ID;
  private String map_code;
  private String UI_CTRL_CODE;
  private String bo_field_code;
  private String service_field_code;
  private String rule_field_code;
  private String proc_field_code;
  private String tmpl_field_code;
  private String description;
  private String status;
  private Timestamp created_date;
  private String created_user;
  @Override
  public String toString() {
      return String.format("(%d,'%s','%s','%s','%s','%s','%s','%s','%s','%s', TO_DATE('%s', 'YYYY-MM-DD HH24:MI:SS'),'%s')",ID,map_code,UI_CTRL_CODE,bo_field_code,service_field_code,rule_field_code,proc_field_code,tmpl_field_code,description,status,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(created_date),created_user);
  }
}
